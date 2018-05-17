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

/**
 *
 * @author ws.duarte
 */
public class Verificador implements Runnable {

    private boolean activo;
    private final int time;
    private final int cantLost;
    private int actLost;
    private final Reporte reporte;
    private final Notificador notificador;
    private String id;

    public Verificador(int time, int cantLost, Reporte reporte, Notificador notificador, String id) {
        activo = true;
        actLost = 0;
        this.time = time;
        this.cantLost = cantLost;
        this.reporte = reporte;
        this.notificador = notificador;
        this.id = id;
    }

    @Override
    public void run() {
        while (activo) {
            if (reporte.Reportar(time)) {
                actLost = 0;
            } else {
                actLost++;
            }
            if (actLost == cantLost) {
                activo = false;
                notificador.notificar();
            }
        }
    }

    public void revivir() {
        activo = true;
        run();
    }
    
    
    
    public Reporte darReporte() {
        return reporte;
    }

    public String getId() {
        return id;
    }
    
    

}
