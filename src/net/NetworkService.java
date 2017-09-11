/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Roberto
 */
public class NetworkService {

    public boolean hostOnline(String ip) {
        boolean estado = false;
        InetAddress ping;
        try {
            ping = InetAddress.getByName(ip);
            if (ping.isReachable(5000)) {
                System.out.println(ip + " - responde!");
            } else {
                System.out.println(ip + " - no responde!");
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return estado;
    }

    public boolean webPageOnline(String page) {
        boolean estado = false;
        HttpURLConnection connection = null;
        try {
            long inicio = System.currentTimeMillis();
            URL u = new URL(page);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();
            long fin = System.currentTimeMillis();
            System.out.println("Codigo: " + code + " t:" + (fin - inicio));
            estado = true;
        } catch (MalformedURLException e) {
            System.out.println("Error de URL: " + e);
        } catch (IOException e) {
            System.out.println("Error de conexion: " + e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return estado;
    }
}
