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
package healdcheck;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import procesadortopicos.Disparador;


/**
 *
 * @author ws.duarte
 */
public class ReporteCerradura implements Reporte{

    private boolean reportar;
    private String id;

    public ReporteCerradura(String id) {
        reportar = false;
        this.id = id;
    }
    
    @Override
    public boolean Reportar(int time) {
        try {
            Disparador.sampleClient.publish("entradasCerradura", "06:".getBytes(),2,false);
            Thread.sleep(time);
            boolean ret= reportar;
            reportar = false;
            return ret;
        } catch (MqttException | InterruptedException ex) {
            Logger.getLogger(ReporteCerradura.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ReporteCerradura && ((ReporteCerradura) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    public void reportar() {
        reportar = true;
    }    
}
