/*
 * The MIT License
 *
 * Copyright 2018 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
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

/**
 *
 * @author ws.duarte
 */
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import programa.MailSender;

@Path("speed")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnviarMensaje extends Application {

    public EnviarMensaje() {
    }

    @POST
    //@Path("{rol}")
    public String envioCorreo(String j, @PathParam("rol") String rol) {
        try {
            JsonObject info = new JsonParser().parse(j).getAsJsonObject().get("info").getAsJsonObject();
            new MailSender("Mensaje de alerta " + info.get("alertaId").getAsInt(),
                    "ws.duarte@uniandes.edu.co", info.get("mensajeAlerta").getAsString()).start();
        } catch (JsonSyntaxException ex) {
            Logger.getLogger(EnviarMensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        return j;
    }    
    
    private String[] darValues(String rol, JsonObject msg) {
        switch(rol) {
            case "propietario": return new String[]{"ws.duarte@misena.edu.co",msg.get("mensajeAlerta").getAsString()}; 
            case "seguridad": return new String[]{"wsduarte7@misena.edu.co",msg.get("mensajeAlerta").getAsString()};
            case "yale": return new String[]{"wsduarte7@misena.edu.co",msg.get("mensajeAlerta").getAsString()};
            case "administrador": return new String[]{"wsduarte7@misena.edu.co",msg.get("mensajeAlerta").getAsString()};
            default: return null;
        }
    }
}
