/*
 * The MIT License
 *
 * Copyright 2018 ws.duarte.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package healthcheck;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import programa.ClienteMQTT;
import programa.MailSender;
import programa.ClienteMQTT.Connection;

/**
 *
 * @author ws.duarte
 */
public class HealthCheck {

    private static ArrayList<Verificador> verificadores;
    private static final int time = 10000, max = 5;

    //A:idAlarma:idDispositivo:unidadResidencial:torre:apto
    private final static String ID_ALARMA = "8";

    public static void empezarVerificador(final String id) {
        System.out.println(">>>>>>>>>>>>>> " + id);
        final String[] data = id.split(":");//ID+":"+UNIDAD_RESIDENCIAL+":"+TORRE+":"+APTO
        final String alerta = "A:" + ID_ALARMA + ":" + data[0] + ":" + data[1] + ":" + data[2] + ":" + data[3];
        new Thread(new Verificador(time, max, new ReporteHub(),
                () -> {
                    try {
                        System.out.println("El hub se encuentra desconectado: " + id + "\nAlerta Enviada: " + alerta);
                        new MailSender("Hub fuera de linea", "elay.arquisoft.201810@hotmail.com", "El hub esta fuera de linea").start();

//                    ClienteMQTT.publicar(ClienteMQTT.Topicos.SUSCRIBIR.getTopic(), alerta, 2, false );
                        new Thread(new Notificar(System.currentTimeMillis() + ":" + ID_ALARMA + ":El hub está fuera de linea:" + data[0] + ":" + data[2] + ":" + data[3] + ":" + data[1])).start();
                    } catch (Exception e) {
                    }
                }, data[0])).start();
    }

    public static class Notificar implements Runnable {

        private String msg;

        public Notificar(String msg) {
            String[] data = msg.split(":");
            this.msg = "{\n"
                    + "\"timestamp\": \"" + data[0] + "\",\n"
                    + "\"alertaID\": " + data[1] + ",\n"
                    + "\"mensajeAlera\": \"" + data[2] + "\",\n"
                    + "\"idDispositivo\": \"" + data[3] + "\",\n"
                    + "\"torre\": " + data[4] + ",\n"
                    + "\"apto\": " + data[5] + ",\n"
                    + "\"unidadResidencial\": \"" + data[6] + "\"\n"
                    + "}";
        }

        @Override
        public void run() {
            try {
                URL url = new URL("http://localhost:8080/healthcheck");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "text/plain");
                con.setRequestProperty("Authorization", "Bearer "+generarToken());
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(msg.getBytes());
                os.flush();
                os.close();
                int responseCode = con.getResponseCode();
                System.out.println("Response Code :" + responseCode);
                BufferedReader reader = null;
                String json = null;
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder jsonSb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    jsonSb.append(line);
                }
                json = jsonSb.toString();
                System.out.println(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String msg = "{\n"
            + "\"grant_type\":\"http://auth0.com/oauth/grant-type/password-realm\",\n"
            + "\"username\": \"administrador@yale.com.co\",\n"
            + "\"password\": \"Administrador.\",\n"
            + "\"audience\": \"uniandes.edu.co/elay\", \n"
            + "\"scope\": \"openid\",\n"
            + "\"client_id\": \"Fm291VvLyWt5V48H5OQCUzn4dKO7NSVA\", \n"
            + "\"client_secret\": \"ZcmR8xtKS8KNpFTASw46_tGc8izsOjTF6VdkECkDUQuAqgjdTbIHVFKc_t6R8q2_\", \n"
            + "\"realm\": \"Username-Password-Authentication\"\n"
            + "}";

    public static String generarToken() {
        try {
            URL url = new URL("https://isis2503-jdtrujillom.auth0.com/oauth/token");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(msg.getBytes());
            os.flush();
            os.close();
            int responseCode = con.getResponseCode();
            System.out.println("Response Code :" + responseCode);
            BufferedReader reader = null;
            String json = null;
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder jsonSb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonSb.append(line);
            }
            json = jsonSb.toString();
            return new JsonParser().parse(json).getAsJsonObject().get("id_token").getAsString();
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
