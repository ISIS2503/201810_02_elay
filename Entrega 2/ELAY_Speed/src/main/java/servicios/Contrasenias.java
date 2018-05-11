/*
 * The MIT License
 *
 * Copyright 2018 hs.hernandez.
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
package servicios;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import programa.ClienteMQTT;
import programa.ManejadorContrasenias;

/**
 *
 * @author hs.hernandez
 */
@Path("contrasenias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Contrasenias {

    public enum Protocolo {
        AGREGAR("01"), 
        MODIFICAR("02"), 
        ELIMINAR("03"), 
        ELIMINAR_ALL("04"),
        OK("OK"),
        ERROR("ERROR");
        private final String cmd;
        Protocolo(String cmd) {
            this.cmd = cmd;
        }

        public String getCmd() {
            return cmd;
        }
        
    }
    
    public static final String MSG = "Operación realizada exitosamente.";
    
    public Contrasenias() {
    }
    
    @POST
    public String agregar(String j) {
        try {
        JsonObject info = new JsonParser().parse(j).getAsJsonObject();
        String contra = info.get("contrasenia").getAsString();
        long time = info.get("timestamp").getAsLong();
        ClienteMQTT.publicar(Protocolo.AGREGAR.cmd+":"+ManejadorContrasenias.agregarNuevaContrasenia(contra, time)+":"+contra);
        return "{ \"mensaje\":\""+MSG+"\" }";
        } catch(Exception e) {
            return error(e.getMessage());
        }
    }
    
    private String error(String msg) {
        return "{ \"ERROR\":\" "+msg+" \" }";
    }
    
    @PUT
    public String modificar(String j) {
        try {
        JsonObject info = new JsonParser().parse(j).getAsJsonObject();
        String contraNueva = info.get("contraseniaNueva").getAsString();
        String contraAntigua = info.get("contraseniaAntigua").getAsString();
        long time = info.get("timestamp").getAsLong();
        ClienteMQTT.publicar(Protocolo.MODIFICAR.cmd+":"+ManejadorContrasenias.cambiarContraseña(contraAntigua, contraNueva, time)+":"+contraNueva);
        return "{ \"mensaje\":\""+MSG+"\" }";
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
    
    @DELETE
    public String eliminar(String j) {
        try {
        JsonObject info = new JsonParser().parse(j).getAsJsonObject();
        ClienteMQTT.publicar(Protocolo.ELIMINAR.cmd+":"+ManejadorContrasenias.eliminar(info.get("contrasenia").getAsString()));
        return "{ \"mensaje\":\""+MSG+"\" }";
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
    
    @DELETE
    @Path("/todo")
    public String eliminar() {
        ClienteMQTT.publicar(Protocolo.ELIMINAR_ALL.cmd+":");
        return "{ \"mensaje\":\""+MSG+"\" }";
    }
    
    
}
