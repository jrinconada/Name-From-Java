
package com.profejuan.from;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;

public class From {   
    static JFrame frame;
    
    public static void main(String[] args) {
        showInterface();
    }
    
    static void showInterface() {        
        // Campo de texto
        JTextField nameField = new JTextField("Tu nombre...");        
        
        // Botón
        JButton ok = new JButton("¿De dónde eres?");
        ok.addActionListener((e) -> nationalizeRequest(nameField.getText()));
        
        // Pantalla        
        frame = new JFrame(); // Crear pantalla
        frame.add(nameField); // Añadir campo de texto
        frame.add(ok); // Añadir botón
        frame.setLayout(new GridBagLayout()); // Colocar elementos
        frame.setSize(500, 500);  // Ajustar dimensiones
        frame.setVisible(true); // Mostrar todo
    }
    
    static void showResults(List<Country> countries) {
        // Lista de resultados
        JList list = new JList(Country.toStringList(countries));
        frame.add(list);        
        frame.setVisible(true); // Mostrar todo
    }
    
    static void nationalizeRequest(String name) {
        // Petición web
        var client = HttpClient.newHttpClient();
        String url = "https://api.nationalize.io/?name=";        
        var resquest = HttpRequest.newBuilder(URI.create(url + name)).build();        
        try {
            var response = client.send(resquest, BodyHandlers.ofString());            
            List<Country> countries = jsonParser(response.body());
            showResults(countries);
        } catch (Exception e) {
            System.out.println("Error de conexión");
        }
    }
    
    static List<Country> jsonParser(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        JsonNode jsonList = mapper.readTree(json).get("country");
        
        List<Country> countries = new ArrayList<>();
        for (JsonNode countryJson : jsonList) {
            String id = countryJson.get("country_id").asText();
            double probability = countryJson.get("probability").asDouble();                
            countries.add(new Country(id, probability));
        }
        
        return countries;
    }
}
