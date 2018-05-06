/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import ch.qos.logback.classic.util.ContextInitializer;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author jd.trujillom
 */
@Path("users")
public class UsuariosService {

    @Context
    ServletContext servletContext;

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public Response getAllUsers() throws UnirestException {

        HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"25tCEjxZRjwnXlHunRcCA7o9A8jSHTuo\",\"client_secret\": \"TfUnvMc86IHEG6vaxcw6mVsFX7NV9fqSBonVuQGqTCT9mApY4OX3foyyxX-5se42\",\"audience\": \"https://isis2503-jdtrujillom.auth0.com/api/v2/\"}")
                .asString();

        ObjectMapper mapper = new ObjectMapper();
        String token = "";
        System.out.println(token);
        try {
            JsonNode node = mapper.readTree(response.getBody());
            token = node.get("access_token").asText();

        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }

        HttpResponse<String> response2 = Unirest.get("https://isis2503-jdtrujillom.auth0.com/api/v2/users")
                .header("content-type", "application/json")
                .header("authorization", "Bearer " + token)
                .asString();

        return Response.status(200).entity(response2.getBody()).build();

    }

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("{correo}")
    public Response getUserByEmail(String correo) throws UnirestException {

        HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"25tCEjxZRjwnXlHunRcCA7o9A8jSHTuo\",\"client_secret\": \"TfUnvMc86IHEG6vaxcw6mVsFX7NV9fqSBonVuQGqTCT9mApY4OX3foyyxX-5se42\",\"audience\": \"https://isis2503-jdtrujillom.auth0.com/api/v2/\"}")
                .asString();

        ObjectMapper mapper = new ObjectMapper();
        String token = "";
        System.out.println(token);
        try {
            JsonNode node = mapper.readTree(response.getBody());
            token = node.get("access_token").asText();

        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        HttpResponse<String> response2 = Unirest.get("https://isis2503-jdtrujillom.auth0.com/api/v2/users-by-email?email="+correo)
                .header("content-type", "application/json")
                .header("authorization", "Bearer " + token)
                .asString();

        return Response.status(200).entity(response2.getBody()).build();
    }

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("{name : \\d+}")
    public Response createUser(@PathParam("name") String name) throws UnirestException {

        HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"25tCEjxZRjwnXlHunRcCA7o9A8jSHTuo\",\"client_secret\": \"TfUnvMc86IHEG6vaxcw6mVsFX7NV9fqSBonVuQGqTCT9mApY4OX3foyyxX-5se42\",\"audience\": \"https://isis2503-jdtrujillom.auth0.com/api/v2/\"}")
                .asString();

        ObjectMapper mapper = new ObjectMapper();
        String token = "";
        System.out.println(token);
        try {
            JsonNode node = mapper.readTree(response.getBody());
            token = node.get("access_token").asText();

        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //String jsonName = "{"name": name}";
        
        HttpResponse<String> response2 = Unirest.post("https://isis2503-jdtrujillom.auth0.com/api/v2/clients")
                .header("content-type", "application/json")
                .header("authorization", "Bearer " + token)
                .body("{\"name\":\"123\"}")
                .asString();

        return Response.status(200).entity(response2.getBody()).build();
    }
}
