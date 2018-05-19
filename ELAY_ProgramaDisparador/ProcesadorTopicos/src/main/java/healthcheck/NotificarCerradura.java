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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import procesadortopicos.Disparador;

/**
 *
 * @author ws.duarte
 */
public class NotificarCerradura implements Notificador{

    private String id;
    public static final String ID_ALARMA = "7";   

    public NotificarCerradura(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
    @Override
    public void notificar() {
        try {
            //A:idAlarma:idDispositivo:unidadResidencial:torre:apto
            final String alerta = "A:"+ID_ALARMA+":"+id+":"+Disparador.UNIDAD_RESIDENCIAL+":"+Disparador.TORRE+":"+Disparador.APTO;
            System.out.println("================================ Se perdió la conexión con la cerradura: "+id+"\nEnvio de alerta: "+alerta);   
            Disparador.sampleClient.publish("alarmasCerradura", alerta.getBytes(), 2, false);
            URL url = new URL("http://localhost:8080/speed/healdcheck");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(id.getBytes());
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
        } catch (IOException | MqttException ex) {
            Logger.getLogger(NotificarCerradura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
