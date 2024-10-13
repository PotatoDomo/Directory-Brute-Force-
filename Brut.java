import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WordlistScanner {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java WordlistScanner <URL> <wordlist>");
            return;
        }

        String baseUrl = args[0]; 
        String wordlistPath = args[1]; 

        if (!baseUrl.startsWith("http://") && !baseUrl.startsWith("https://")) {
            System.out.println("Please enter a valid URL with 'http://' or 'https://'.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(wordlistPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); 
                String fullUrl = baseUrl + "/" + line;
                int statusCode = getStatusCode(fullUrl); 
                if (statusCode != 404) {
                    System.out.println(fullUrl + " : " + statusCode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getStatusCode(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0"); 
            connection.connect();
            int responseCode = connection.getResponseCode();
            connection.disconnect(); 
            return responseCode;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1; 
    }
}