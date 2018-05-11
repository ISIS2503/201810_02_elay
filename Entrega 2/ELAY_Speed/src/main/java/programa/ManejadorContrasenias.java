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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author ws.duarte
 */
public class ManejadorContrasenias {
    
    public static final int CANT_CONTRASENIAS = 20;
    public static List<Contrasena> contrasenas = new ArrayList<>(CANT_CONTRASENIAS);
    static {
        for(int i = 0; i < CANT_CONTRASENIAS; i++) contrasenas.add(new Contrasena("-1", System.currentTimeMillis()));  //contrasenias[i] = "-1";
    }
    
    private static Contrasena buscar(Predicate<Contrasena> p) {
        return contrasenas.stream().filter(p).findFirst().orElse(null);
    }
    
    public static int agregarNuevaContrasenia(String contrasenia, long time) throws Exception {
        if(contrasenia.length() != 4) throw new Exception("La contraseña tiene que tener obligatoriamente 4 caracteres");
        Contrasena con =  buscar(x -> x.contrasena.equals("-1"));
        if(con != null) {
            con.asignar(contrasenia, time);
            return contrasenas.indexOf(con);
        }
        throw new Exception("No hay espacio para almacenar más contraseñas");
    }
    
    public static int cambiarContraseña(String antigua, String nueva, long time) throws Exception {
        if(nueva.length() != 4) throw new Exception("La contraseña tiene que tener obligatoriamente 4 caracteres");
        Contrasena con = buscar(x -> x.contrasena.equals(antigua));
        if(con != null) {
            con.asignar(nueva, time);
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
        for(int i = 0; i < CANT_CONTRASENIAS; i++) contrasenas.add(new Contrasena("-1", System.currentTimeMillis()));
    }
    
//    public static void cargarContrasenias(String s){
//        String[] sp = s.split(":");
//        eliminarTodo();
//        System.arraycopy(sp, 0, contrasenias, 0, CANT_CONTRASENIAS);
//    }

    private static final class Contrasena {
        String contrasena;
        long timestap;

        public Contrasena(String contrasena, long timestap) {
            asignar(contrasena, timestap);
        }
        
        public void asignar(String contrasena, long timestap) {
            this.contrasena = contrasena;
            this.timestap = timestap;
        }
    }
}
