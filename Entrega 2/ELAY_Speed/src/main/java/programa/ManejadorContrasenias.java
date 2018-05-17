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
package programa;


import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author ws.duarte
 */
public class ManejadorContrasenias {
    
    public static final int CANT_CONTRASENIAS = 20;
    public static final List<Contrasena> contrasenas = new ArrayList<>(CANT_CONTRASENIAS);
    static {
        for(int i = 0; i < CANT_CONTRASENIAS; i++) contrasenas.add(new Contrasena("-1", new Time(System.currentTimeMillis()), new Time(System.currentTimeMillis())));  //contrasenias[i] = "-1";
    }
    
    private static Contrasena buscar(Predicate<Contrasena> p) {
        return contrasenas.stream().filter(p).findFirst().orElse(null);
    }
    
    public static int agregarNuevaContrasenia(String contrasenia, Time hi, Time hf, boolean[] dias) throws Exception {
        if(contrasenia.length() != 4) throw new Exception("La contraseña tiene que tener obligatoriamente 4 caracteres");
        Contrasena con =  buscar(x -> x.contrasena.equals("-1"));
        if(con != null) {
            con.asignar(contrasenia, hi, hf, dias);
            return contrasenas.indexOf(con);
        }
        throw new Exception("No hay espacio para almacenar más contraseñas");
    }
    
    public static int cambiarContraseña(String antigua, String nueva, Time hi, Time hf, boolean[] dias) throws Exception {
        if(nueva.length() != 4) throw new Exception("La contraseña tiene que tener obligatoriamente 4 caracteres");
        Contrasena con = buscar(x -> x.contrasena.equals(antigua));
        if(con != null) {
            con.asignar(nueva, hi, hf, dias);
            return contrasenas.indexOf(con);
        }
        throw new Exception("No se encontro la ontraseña a cambiar");
    }
    
    public static int eliminar(String contrasenia) throws Exception{
        Contrasena con = buscar(x -> x.contrasena.equals(contrasenia));
        if(con != null) {
            con.contrasena = "-1";
            return contrasenas.indexOf(con);
        }
        throw new Exception("No se encontro la ontraseña a eliminar");
    }
    
    public static void eliminarTodo() {
        contrasenas.forEach( (con) -> con.contrasena = "-1" );
    }
    
    public static List<String> darInvalidas() {
        List<String> ret = new ArrayList<>();
        contrasenas.stream().filter(x -> between(new Time(System.currentTimeMillis()), x.hi, x.hf) && x.dias[new Date().getDay()]  ).collect(Collectors.toList()).forEach((con) -> { ret.add(con.contrasena);  });
        return ret;
    }
    
    private static boolean between(Time t, Time ti, Time tf) {
        return ti.compareTo(t) < 0 && t.compareTo(tf) < 0;
    }
    
    public static Time convertTime(String time) {
        String[] data = time.split(":");
        return new Time(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
    }
    
    public static boolean[] convertDays(String days) {
        char[] dd = {68,76,77,73,74,86,83};//Dias en español D-L-M-I-J-V-S
        boolean[] ret = new boolean[7];
        for(int i = 0, p = 0; i < ret.length && p < days.length(); i++) {
            if(days.charAt(p) == dd[i]) {
                ret[i] = true;
                p++;
            }
        }
        return ret;
    }

    private static final class Contrasena {
        private String contrasena;
        private boolean[] dias; // 0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday
        private Time hi, hf;        
        

        public Contrasena(String contrasena, Time hi, Time hf) {
            asignar(contrasena, hi, hf, new boolean[7]);
        }
        
        public void asignar(String contrasena, Time hi, Time hf, boolean[] dias) {
            this.contrasena = contrasena;
            this.hi = hi; 
            this.hf = hf;
            this.dias = dias;
        }
    }
}
