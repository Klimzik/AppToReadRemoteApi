package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    private static final String API_URL = "https://catfact.ninja/fact";

    public static void main(String[] args) {
        // Tworzenie okna
        JFrame frame = new JFrame("API Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Tworzenie przycisku
        JButton fetchButton = new JButton("Fetch Data");

        // Tworzenie pola tekstowego do wyświetlania danych
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Dodanie akcji do przycisku
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Odczyt danych z API
                    String response = fetchDataFromAPI();
                    // Dopisywanie nowych danych do pola tekstowego
                    textArea.append(response + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    textArea.setText("Error fetching data.");
                }
            }
        });

        // Dodanie komponentów do okna
        frame.getContentPane().add(fetchButton, "North");
        frame.getContentPane().add(scrollPane, "Center");

        // Wyświetlenie okna
        frame.setVisible(true);
    }

    private static String fetchDataFromAPI() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        StringBuilder response = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            response.append(output);
        }
        conn.disconnect();

        return response.toString();
    }
}
