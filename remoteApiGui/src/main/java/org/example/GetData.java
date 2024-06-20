package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GetData {
    private static final String API_URL_COUNTRY = "https://restcountries.com/v3.1/name/";

    public static void main(String[] args) {
        JFrame frame = new JFrame("API reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JButton fetchButton = new JButton("Fetch Data");
        JButton clearButton = new JButton("Clear Data");

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        String[] countries = {"Italy", "France", "Germany", "Spain", "Poland",
                "Canada", "Australia", "Brazil", "China", "Japan", "India", "Russia",
                "Mexico", "Argentina", "Chile", "Egypt", "Nigeria", "Kenya", "Turkey"};
        JComboBox<String> countrySelector = new JComboBox<>(countries);

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedCountry = (String) countrySelector.getSelectedItem();
                    String response = fetchDataFromAPI(API_URL_COUNTRY + selectedCountry);
                    ObjectMapper objectMapper = new ObjectMapper();
                    Country[] countryData = objectMapper.readValue(response, new TypeReference<Country[]>() {});

                    for (Country country : countryData) {
                        textArea.append(country.toString() + "\n");
                    }
                } catch (Exception ex) {
                    textArea.setText("Error fetching data.\n");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });

        JPanel panel = new JPanel();
        panel.add(countrySelector);
        panel.add(fetchButton);
        panel.add(clearButton);

        frame.getContentPane().add(panel, "North");
        frame.getContentPane().add(scrollPane, "Center");

        frame.setVisible(true);
    }

    private static String fetchDataFromAPI(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine());
            }
            return sb.toString();
        } else {
            throw new RuntimeException("HTTP error code: " + responseCode);
        }
    }

}