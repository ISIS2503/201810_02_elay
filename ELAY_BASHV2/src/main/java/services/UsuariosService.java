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
import dto.SeguridadPrivadaUserDTO;
import dto.UnidadResidencialDTO;
import dto.UserDTO;
import entidad.ErrorEdited;
import entidad.SeguridadPrivadaUser;
import entidad.UnidadResidencial;
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
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import persistencia.UnidadResidencialPersistence;

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

        String token = getToken();
        ObjectMapper mapper = new ObjectMapper();

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

        ObjectMapper mapper = new ObjectMapper();
        String token = getToken();

        correo = correo.replace("@", "%40");
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

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param dto con la información del usuario que se quiere crear
     * @return
     * @throws Exception
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/add")
    public Response add(UserDTO dto) throws Exception {

        String token = getToken();

        JSONObject node = new JSONObject();
        node.put("email", dto.getCorreo());
        node.put("connection", "Username-Password-Authentication");
        node.put("password", dto.getContrasenia());

        HttpResponse<String> response2 = Unirest.post("https://isis2503-jdtrujillom.auth0.com/api/v2/users")
                .header("content-type", "application/json").header("authorization", "Bearer " + token)
                .body(node)
                .asString();

        return Response.ok().entity(response2.getBody()).status(Response.Status.ACCEPTED).build();
    }

    /**
     * Elimina un usuario con el id pasado por parametro.
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

        String token = getToken();

        id = id.replace("|", "%7c");

        HttpResponse<String> response2 = Unirest.delete("https://isis2503-jdtrujillom.auth0.com/api/v2/users/" + id)
                .header("content-type", "application/json").header("authorization", "Bearer " + token).asString();

        return Response.ok().entity(response2.getBody()).status(Response.Status.ACCEPTED).build();
    }

    /**
     * Crea un nuevo usuario relacionado con la seguridad privada.
     *
     * @param spUser de la seguridad privada.
     * @return el usuario creado en la base de datos de Auth0.
     */
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("seguridadPrivada")
    public Response crearSeguridadPrivada(SeguridadPrivadaUserDTO spUser) {

        String token = getToken();

        JSONObject node = new JSONObject();
        JSONObject user_metadata = new JSONObject();

        //Coloca el id de la unidad residencial en el metadata del usuario
        user_metadata.put("idUnidadResidencial", spUser.getIdUnidadResidencial());

        node.put("email", spUser.getCorreo());
        node.put("connection", "Username-Password-Authentication");
        node.put("password", spUser.getContrasenia());
        node.put("user_metadata", user_metadata);

        HttpResponse<String> response2 = null;

        try {
            response2 = Unirest.post("https://isis2503-jdtrujillom.auth0.com/api/v2/users")
                    .header("content-type", "application/json").header("authorization", "Bearer " + token)
                    .body(node)
                    .asString();
        } catch (UnirestException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.ok().entity(response2.getBody()).status(Response.Status.ACCEPTED).build();

    }

    /**
     * 
     * @param idUsuario
     * @return 
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{idUsuarioSP}/unidadResidencial")
    public Response getUnidadResidencialFromSecurity(@PathParam("idUsuarioSP") String idUsuario) {

        JsonNode usuario = getUserOfAuth0(idUsuario);
        
        if(usuario == null)
            return Response.status(500).entity(new ErrorEdited("El usuario con id" + idUsuario + " no existe")).build();
        
        JsonNode user_metadata = usuario.get("user_metadata");
        String idUnidadResidencial = user_metadata.findValue("idUnidadResidencial").asText();
        
        if(idUnidadResidencial == null)
            return Response.status(500).entity(new ErrorEdited(("El usuario no está asociado con ninguna unidad residencial"))).build();
        
        UnidadResidencialPersistence unidadResidencialPersistence = new UnidadResidencialPersistence();
        UnidadResidencial unidadResidencial = unidadResidencialPersistence.find(idUnidadResidencial);
        
        if(unidadResidencial == null)
            return Response.status(500).entity(new ErrorEdited(("La unidad residencial no existe"))).build();
        
        UnidadResidencialDTO unidadResidencialDTO = new UnidadResidencialDTO();
        unidadResidencialDTO.toDTO(unidadResidencial);
        
        return Response.status(200).entity(unidadResidencialDTO).build();
       
    }

    /**
     * Actualiza el usuario cuyo id es pasado por parametro.
     *
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

        String token = getToken();

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

        String token = getToken();

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

    /**
     * Obtiene el token que da acceso a la base de datos de Auth0
     *
     * @return el token para acceder a la base de datos.
     */
    private String getToken() {
        ObjectMapper mapper = new ObjectMapper();
        String token = "";
        try {

            HttpResponse<String> response = Unirest.post("https://isis2503-jdtrujillom.auth0.com/oauth/token")
                    .header("content-type", "application/json")
                    .body("{\"grant_type\":\"client_credentials\",\"client_id\": \"25tCEjxZRjwnXlHunRcCA7o9A8jSHTuo\",\"client_secret\": \"TfUnvMc86IHEG6vaxcw6mVsFX7NV9fqSBonVuQGqTCT9mApY4OX3foyyxX-5se42\",\"audience\": \"https://isis2503-jdtrujillom.auth0.com/api/v2/\"}")
                    .asString();

            JsonNode node = mapper.readTree(response.getBody());
            token = node.get("access_token").asText();

        } catch (IOException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnirestException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return token;
    }

    private JsonNode getUserOfAuth0(String idUsuario) {
        ObjectMapper mapper = new ObjectMapper();
        String token = getToken();
        idUsuario = idUsuario.replace("|", "%7c");     
        JsonNode userNode = null;

        try {
            HttpResponse<String> response2 = Unirest.get("https://isis2503-jdtrujillom.auth0.com/api/v2/users/" + idUsuario)
                    .header("content-type", "application/json")
                    .header("authorization", "Bearer " + token)
                    .asString();
            userNode = mapper.readTree(response2.getBody());
        } catch (IOException exception) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, exception);
        } catch (UnirestException ex) {
            Logger.getLogger(UsuariosService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userNode;
    }

}
