/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Roberto
 */
public class HttpService {

    private static Logger logger = LogManager.getLogger(HttpService.class);

    public HttpService() {
    }

    public String requestPost(String request, Map<String, String> parametros) throws UnsupportedEncodingException {
        String response = "";
        String data = "";
        if (parametros != null) {
            for (Map.Entry<String, String> entry : parametros.entrySet()) {
                if (data.length() > 0) {
                    data += "&" + URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
                } else {
                    data += URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
                }
            }
        }
        try {
            OutputStreamWriter wr = null;
            BufferedReader rd = null;
            try {
                URL url = new URL(request);
                String line;
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = rd.readLine()) != null) {
                    response += line;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                if (wr != null) {
                    wr.close();
                }
                if (rd != null) {
                    rd.close();
                }
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        return response;
    }

    public static void main(String[] args) {
        HttpService service = new HttpService();
        String respuesta = "";
        try {
            Map<String, String> parametros = new HashMap();
            parametros.put("anexo", "229731");
            parametros.put("devicename", "SEP20BBC020053D");
            parametros.put("password", "1234");
            respuesta = service.requestPost("http://localhost:8084/AcdKall/index.jsp", parametros);
        } catch (UnsupportedEncodingException ex) {
            logger.error("Encoding parametros " + ex.getMessage());
        }
        System.out.println(respuesta);
    }
}
