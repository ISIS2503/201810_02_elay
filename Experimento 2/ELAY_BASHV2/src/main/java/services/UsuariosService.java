/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import auth.AuthorizationFilter.Role;
import auth.Secured;
import ch.qos.logback.classic.util.ContextInitializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import dto.UserDTO;
import entidad.User;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.PATCH;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

/**
 * Clase que modela a los usuarios almacenados en el sistema.
 *
 * @author jd.trujillom
 */
@Path("users")
public class UsuariosService {

    @Context
    ServletContext servletContext;

    /**
     * Obtiene todos los usuarios en el sistema. Solo pueder ser usado por un
     * administrador Yale.
     *
     * @return
     * @throws UnirestException
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers() throws UnirestException {

        //Envia una peticiob a Auth0 para obtener el token de inicio de sesion. 
        HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"25tCEjxZRjwnXlHunRcCA7o9A8jSHTuo\",\"client_secret\": \"TfUnvMc86IHEG6vaxcw6mVsFX7NV9fqSBonVuQGqTCT9mApY4OX3foyyxX-5se42\",\"audience\": \"https://isis2503-jdtrujillom.auth0.com/api/v2/\"}")
                .asString();

        //Obtiene el token de el archivo Json
        ObjectMapper mapper = new ObjectMapper();
        String token = "";
        try {
            JsonNode node = mapper.readTree(response.getBody());
            token = node.get("access_token").asText();

        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Envia la nueva petici{on con todos los usuarios
        HttpResponse<String> response2 = Unirest.get("https://isis2503-jdtrujillom.auth0.com/api/v2/users")
                .header("content-type", "application/json")
                .header("authorization", "Bearer " + token)
                .asString();

        //Obtiene el archivo Json de la peticion y mapea con la representacion actual de un usuario. 
        List<UserDTO> users = new ArrayList<>();
        try {
            JsonNode usersNode = mapper.readTree(response2.getBody());
            System.out.println(usersNode.isArray());

            for (JsonNode userNode : usersNode) {
                User userEntity = new User();
                String id = userNode.findValue("user_id") != null ? userNode.findValue("user_id").getTextValue() : null;
                String correoEntity = userNode.findValue("email") != null ? userNode.findValue("email").asText() : null;
                String nombre = userNode.findValue("name") != null ? userNode.findValue("name").asText() : null;

                userEntity.setId(id);
                userEntity.setCorreo(correoEntity);
                userEntity.setNombre(nombre);
                userEntity.setUsuario(correoEntity);

                //Saca la informacion de app_metadata
                JsonNode app_metadata = userNode.findValue("app_metadata");
                if (app_metadata != null) {
                    JsonNode roles = userNode.findValue("roles");
                    if (roles != null) {
                        List<String> rolesArray = new ArrayList<>();

                        if (roles.isArray()) {
                            for (JsonNode rol : roles) {
                                rolesArray.add(rol.asText());
                            }
                        }
                        userEntity.setRoles(rolesArray);
                    }
                }

                users.add(new UserDTO(userEntity));
            }

        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(200).entity(users).build();

    }

    /**
     * Obtiene un usuarios con el correo dado.
     *
     * @param correo del usuario que se está buscando.
     * @return el usuario con el correo dado.
     * @throws UnirestException si exite algún error al mandar la petición.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{correo}")
    public Response getUserByEmail(@PathParam("correo") String correo) throws UnirestException {

        HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"25tCEjxZRjwnXlHunRcCA7o9A8jSHTuo\",\"client_secret\": \"TfUnvMc86IHEG6vaxcw6mVsFX7NV9fqSBonVuQGqTCT9mApY4OX3foyyxX-5se42\",\"audience\": \"https://isis2503-jdtrujillom.auth0.com/api/v2/\"}")
                .asString();

        ObjectMapper mapper = new ObjectMapper();
        String token = "";
        try {
            JsonNode node = mapper.readTree(response.getBody());
            token = node.get("access_token").asText();

        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }

        correo = correo.replace("@", "%40");
        System.out.println("ARQUIIIIIIIIII:" + correo);
        HttpResponse<String> response2 = Unirest.get("https://isis2503-jdtrujillom.auth0.com/api/v2/users-by-email?email=" + correo)
                .header("content-type", "application/json")
                .header("authorization", "Bearer " + token)
                .asString();
        JsonNode userNode = null;
        try {
            userNode = mapper.readTree(response2.getBody());
        } catch (IOException exception) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, exception);
        }

        User userEntity = new User();
        String id = userNode.findValue("user_id") != null ? userNode.findValue("user_id").getTextValue() : null;
        String correoEntity = userNode.findValue("email") != null ? userNode.findValue("email").asText() : null;
        String nombre = userNode.findValue("name") != null ? userNode.findValue("name").asText() : null;

        userEntity.setId(id);
        userEntity.setCorreo(correoEntity);
        userEntity.setNombre(nombre);
        userEntity.setUsuario(correoEntity);

        //Saca la informacion de app_metadata
        JsonNode app_metadata = userNode.findValue("app_metadata");
        if (app_metadata != null) {
            JsonNode roles = userNode.findValue("roles");
            if (roles != null) {
                List<String> rolesArray = new ArrayList<>();

                if (roles.isArray()) {
                    for (JsonNode rol : roles) {
                        rolesArray.add(rol.asText());
                    }
                }
                userEntity.setRoles(rolesArray);
            }
        }

        UserDTO userDTO = new UserDTO(userEntity);

        return Response.status(200).entity(userDTO).build();
    }

//    @POST
//    @Produces({MediaType.TEXT_PLAIN})
//    @Path("{name : \\d+}")
//    public Response createUser(@PathParam("name") String name) throws UnirestException {
//
//        HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/oauth/token")
//                .header("content-type", "application/json")
//                .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"25tCEjxZRjwnXlHunRcCA7o9A8jSHTuo\",\"client_secret\": \"TfUnvMc86IHEG6vaxcw6mVsFX7NV9fqSBonVuQGqTCT9mApY4OX3foyyxX-5se42\",\"audience\": \"https://isis2503-jdtrujillom.auth0.com/api/v2/\"}")
//                .asString();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String token = "";
//        System.out.println(token);
//        try {
//            JsonNode node = mapper.readTree(response.getBody());
//            token = node.get("access_token").asText();
//
//        } catch (IOException ex) {
//            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        //String jsonName = "{"name": name}";
//        
//        HttpResponse<String> response2 = Unirest.post("https://isis2503-jdtrujillom.auth0.com/api/v2/clients")
//                .header("content-type", "application/json")
//                .header("authorization", "Bearer " + token)
//                .body("{\"name\":\"123\"}")
//                .asString();
//
//        return Response.status(200).entity(response2.getBody()).build();
//    }
    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param dto con la informaci{on del usuario que se quiere crear
     * @return
     * @throws Exception
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/add")
    public Response add(UserDTO dto) throws Exception {

        HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"25tCEjxZRjwnXlHunRcCA7o9A8jSHTuo\",\"client_secret\": \"TfUnvMc86IHEG6vaxcw6mVsFX7NV9fqSBonVuQGqTCT9mApY4OX3foyyxX-5se42\",\"audience\": \"https://isis2503-jdtrujillom.auth0.com/api/v2/\"}")
                .asString();

        ObjectMapper mapper = new ObjectMapper();
        String token = "";
        try {
            JsonNode node = mapper.readTree(response.getBody());
            token = node.get("access_token").asText();

        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONObject node = new JSONObject();
        node.put("email", dto.getCorreo());
        node.put("connection", "Username-Password-Authentication");
        node.put("password", dto.getContraseña());

        HttpResponse<String> response2 = Unirest.post("https://isis2503-jdtrujillom.auth0.com/api/v2/users")
                .header("content-type", "application/json").header("authorization", "Bearer " + token)
                .body(node)
                .asString();

        return Response.ok().entity(response2.getBody()).status(Response.Status.ACCEPTED).build();
    }

    /**
     * Elimina un usuario con la contraseña pasada por parametro.
     *
     * @param id del usuario que se quiere eliminar.
     * @return 200 si la eliminacion se llevó a cabo correctamente.
     * @throws Exception si hubo algun error.
     */
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response delete(@PathParam("id") String id) throws Exception {

        HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"25tCEjxZRjwnXlHunRcCA7o9A8jSHTuo\",\"client_secret\": \"TfUnvMc86IHEG6vaxcw6mVsFX7NV9fqSBonVuQGqTCT9mApY4OX3foyyxX-5se42\",\"audience\": \"https://isis2503-jdtrujillom.auth0.com/api/v2/\"}")
                .asString();

        ObjectMapper mapper = new ObjectMapper();
        String token = "";
        try {
            JsonNode node = mapper.readTree(response.getBody());
            token = node.get("access_token").asText();

        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }

        id = id.replace("|", "%7c");

        HttpResponse<String> response2 = Unirest.delete("https://isis2503-jdtrujillom.auth0.com/api/v2/users/" + id)
                .header("content-type", "application/json").header("authorization", "Bearer " + token).asString();

        return Response.ok().entity(response2.getBody()).status(Response.Status.ACCEPTED).build();
    }

    /**
     * Actualiza el usuario cuyo id es pasado por parametro. 
     * @param dto con la información con la que se quiere actualizar el dto. 
     * @param id del usuario que se quiere actualizar
     * @return el usuario con la información actualizada. 
     * @throws Exception 
     */
    @PATCH
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response update(UserDTO dto, @PathParam("id") String id) throws Exception {

        HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"25tCEjxZRjwnXlHunRcCA7o9A8jSHTuo\",\"client_secret\": \"TfUnvMc86IHEG6vaxcw6mVsFX7NV9fqSBonVuQGqTCT9mApY4OX3foyyxX-5se42\",\"audience\": \"https://isis2503-jdtrujillom.auth0.com/api/v2/\"}")
                .asString();

        ObjectMapper mapper = new ObjectMapper();
        String token = "";
        try {
            JsonNode node = mapper.readTree(response.getBody());
            token = node.get("access_token").asText();

        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONObject node = new JSONObject();
        node.put("email", dto.getCorreo());
        node.put("connection", "Username-Password-Authentication"); 
        
        id = id.replace("|", "%7c");

        HttpResponse<String> response2 = Unirest.patch("https://isis2503-jdtrujillom.auth0.com/api/v2/users/" + id)
                .header("content-type", "application/json").header("authorization", "Bearer " + token).body(node).asString();

        return Response.ok().entity(response2.getBody()).status(Response.Status.ACCEPTED).build();
    }

    @PATCH
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("changePassword/{id}")
    public Response changePassword(UserDTO dto, @PathParam("id") String id) throws Exception {

        HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/oauth/token")
                .header("content-type", "application/json")
                .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"25tCEjxZRjwnXlHunRcCA7o9A8jSHTuo\",\"client_secret\": \"TfUnvMc86IHEG6vaxcw6mVsFX7NV9fqSBonVuQGqTCT9mApY4OX3foyyxX-5se42\",\"audience\": \"https://isis2503-jdtrujillom.auth0.com/api/v2/\"}")
                .asString();

        ObjectMapper mapper = new ObjectMapper();
        String token = "";
        try {
            JsonNode node = mapper.readTree(response.getBody());
            token = node.get("access_token").asText();

        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONObject node = new JSONObject();
        node.put("password", dto.getContrasenia());
        node.put("connection", "Username-Password-Authentication"); 
        
        id = id.replace("|", "%7c");

        HttpResponse<String> response2 = Unirest.patch("https://isis2503-jdtrujillom.auth0.com/api/v2/users/" + id)
                .header("content-type", "application/json").header("authorization", "Bearer " + token).body(node).asString();

        return Response.ok().entity(response2.getBody()).status(Response.Status.ACCEPTED).build();
    }
    
    
    @POST
    @Path("/updatePassword")
    public Response update(UserDTO dto) throws Exception {
        HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/dbconnections/change_password")
                .header("content-type", "application/json")
                .body("{\"client_id\":\"Fm291VvLyWt5V48H5OQCUzn4dKO7NSVA\",\"email\":\"" + dto.getCorreo() + "\",\"password\":\"\",\"connection\":\"Username-Password-Authentication\"}")
                .asString();
        return Response.ok().entity("{\"Se le envio un link al correo para cambiar contraseña\"}").status(Response.Status.ACCEPTED).build();
    }

    private List<UserDTO> toDTOUserList(List<User> entidades) {
        List<UserDTO> lista = null;
        if (entidades != null) {
            lista = new ArrayList<>();
            for (User usuario : entidades) {
                UserDTO nuevo = new UserDTO(usuario);
                lista.add(nuevo);
            }
        }

        return lista;
    }
}
