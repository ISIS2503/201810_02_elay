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

/**
 *
 * @author ws.duarte
 */
public class ManejadorContrasenias {
    
    public static final int CANT_CONTRASENIAS = 20;
    public static String[] contrasenias = new String[CANT_CONTRASENIAS]; 
    static {
        for(int i = 0; i < CANT_CONTRASENIAS; i++) contrasenias[i] = "-1";
    }
    
    public static int agregarNuevaContrasenia(String contrasenia) throws Exception {
        if(contrasenia.length() != 4) throw new Exception("La contraseña tiene que tener obligatoriamente 4 caracteres");
        for(int i = 0; i < CANT_CONTRASENIAS; i++) {
            if(contrasenias[i].equals("-1")) {
                contrasenias[i] = contrasenia;
                return i;
            }
        }
        throw new Exception("No hay espacio para almacenar más contraseñas");
    }
    
    public static int cambiarContraseña(String antigua, String nueva) throws Exception {
        if(nueva.length() != 4) throw new Exception("La contraseña tiene que tener obligatoriamente 4 caracteres");
        for (int i = 0; i < contrasenias.length; i++) {
            if(contrasenias[i].equals(antigua)) {
                contrasenias[i] = nueva;
                return i;
            }
        }
        throw new Exception("No se encontro la ontraseña a cambiar");
    }
    
    public static int eliminar(String contrasenia) throws Exception{
        for (int i = 0; i < contrasenias.length; i++) {
            if(contrasenias[i].equals(contrasenia)) {
                contrasenias[i] = "-1";
                return i;
            }
        }
        throw new Exception("No se encontro la ontraseña a eliminar");
    }
    
    public static void eliminarTodo() {
        for(int i = 0; i < CANT_CONTRASENIAS; i++) contrasenias[i] = "-1";
    }
    
    public static void cargarContrasenias(String s){
        String[] sp = s.split(":");
        eliminarTodo();
        System.arraycopy(sp, 0, contrasenias, 0, CANT_CONTRASENIAS);
    }

    
}
