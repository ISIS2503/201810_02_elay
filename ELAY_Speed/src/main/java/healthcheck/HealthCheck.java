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

import java.util.ArrayList;
import programa.MailSender;

/**
 *
 * @author ws.duarte
 */
public class HealthCheck {

    private static ArrayList<Verificador> verificadores;
    private static final int time = 1000, max = 10;

    public static void empezarVerificador(String id) {
        new Thread(new Verificador(time, max, new ReporteHub(),
                () -> { try { new MailSender("Hub fuera de linea", "elay.arquisoft.201810@hotmail.com", "El hub esta fuera de linea").enviarCorreo(); System.out.println("======================= El hub esta fuera de linea");} catch (Exception e) {} }, id))
                .start();
    }
}
