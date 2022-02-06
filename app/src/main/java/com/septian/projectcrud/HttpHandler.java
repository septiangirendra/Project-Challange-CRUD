package com.septian.projectcrud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class HttpHandler {

    // constructor : 1. send POST request
    public String sendPostRequest(String requestUrl, HashMap<String, String> postDataParams) {
        // membuat URL
        URL url;

        // Objek StringBuilder untuk menyimpan pesan diambil dari server
        StringBuilder sb = new StringBuilder();

        // Error Handling
        try {
            //inidialisasi URL
            url = new URL(requestUrl);

            // membuat koneksi HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Konfigurasi koneksi
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // mengirim request dari client ke server
            // membuat keluaran stream
            OutputStream os = connection.getOutputStream();

            // Menulis Parameter untuk Permintaan
            // Metode getPostDataString
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8")
            );
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            // cek HTTP status code untuk memastikan request diterima oleh server
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //response dikrim dari server ke client
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                sb = new StringBuilder();
                String response;
                //Reading server response
                while ((response = reader.readLine()) != null) {
                    sb.append(response);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // error message
        }
        return sb.toString();

    }

    public String sendGetRequest(String requestURL){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String s;
            while ((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }

        }catch (Exception e){

        }
        return sb.toString();
    }
    private String getPostDataString(HashMap<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    // constructor 2: send GET response ~> GET_ALL dan GET_DETAIL
    // GET_ALL
    public String sendGetResponse(String responseUrl) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(responseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String response;
            while ((response = reader.readLine()) != null) {
                sb.append(response + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    // GET_DETAIL
    public String sendGetResponse(String responseUrl, String id) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(responseUrl + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String response;
            while ((response = reader.readLine()) != null) {
                sb.append(response + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();

    }

    // Get
    public String sendGetRequestParam(String requestURL, String id) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(requestURL + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                    (con.getInputStream()));

            String s;
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(s + "\n");
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }
}

