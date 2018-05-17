/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dto.HubDTO;
import dto.HubDTO;
import entidad.Hub;
import entidad.Hub;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import persistencia.HubPersistence;
import persistencia.HubPersistence;
import auth.AuthorizationFilter.Role;
import auth.Secured;
import entidad.ErrorEdited;
import javax.ws.rs.DELETE;

/**
 *
 * @author jd.trujillom
 */
@Path("hub")
@Secured({Role.yale})
public class HubService {

    @Context
    ServletContext servletContext;

    /**
     * Obtener todas los hubs del sistema. Solo puede llevarse a cabo po un
     * administrador de yale.
     *
     * @return
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        HubPersistence URP = new HubPersistence();
        List<HubDTO> list = toDTOHub(URP.all());

        GenericEntity<List<HubDTO>> entity = new GenericEntity<List<HubDTO>>(list) {
        };

        return Response.status(200).entity(entity).build();

    }

    /**
     * Registra un nuevo hub en el sistama
     *
     * @param dto del hub que se quiere registrar
     * @return el hub persisitido en la base de datos.
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createHUB(HubDTO dto) {
        HubPersistence hubPersistence = new HubPersistence();
        Hub Hub = dto.toEntity();

        Hub nuevo = hubPersistence.add(Hub);
        HubDTO nuevoDTO = null;
        if (nuevo != null) {
            nuevoDTO = new HubDTO(nuevo);
        }
        return Response.status(200).entity(nuevoDTO).build();

    }

    /**
     * Obtiene un hub por su id.
     *
     * @param id del hub que se está buscando.
     * @return el hub con el id dado por parametro. Null si no lo encuentra.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response getHubPorId(@PathParam("id") String id) {
        HubPersistence URP = new HubPersistence();

        Hub entity = URP.find(id);
        if (entity == null) {
            return Response.status(404).entity(new ErrorEdited("La hub con id " + id + " no existe")).build();
        }

        HubDTO dto = new HubDTO(entity);

        return Response.status(200).entity(dto).build();
    }

    /**
     * Actuliza la información basica de un HUB
     *
     * @param dto con la información que se quiere actualizar.
     * @param id del hub que se quiere actualizar.
     * @return el hub con la información actualizada.
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response updateHub(HubDTO dto, @PathParam("id") String id) {
        HubPersistence URP = new HubPersistence();
        Hub nuevo = URP.find(id);

        if (nuevo == null) {
            return Response.status(404).entity(new ErrorEdited("La hub con id " + id + " no existe")).build();
        }

        nuevo.setFrecuenciaReporte(dto.getFrecuenciaReporte());
        nuevo.setNumeroPerdidasToleradas(dto.getNumeroPerdidasToleradas());
        nuevo = URP.update(nuevo);

        HubDTO nuevoDTO = new HubDTO(nuevo);
        return Response.status(200).entity(nuevoDTO).build();
    }

    /**
     * Desactiva un hub.
     *
     * @param id del hub que se quiere desactivar.
     * @return el hub desactivado.
     */
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}/desactivar")
    public Response desactivarHub(@PathParam("id") String id) {
        HubPersistence URP = new HubPersistence();
        Hub nuevo = URP.find(id);

        if (nuevo == null) {
            return Response.status(404).entity(new ErrorEdited("La hub con id " + id + " no existe")).build();
        }

        if (!nuevo.isActivado()) {
            return Response.status(404).entity(new ErrorEdited("La hub con id " + id + " ya se encontraba desactivado")).build();
        }
        
        nuevo.setActivado(false);
        nuevo = URP.update(nuevo);

        HubDTO nuevoDTO = new HubDTO(nuevo);
        return Response.status(200).entity(nuevoDTO).build();
    }
    
     /**
     * Desactiva un hub.
     * @param id del hub que se quiere desactivar.
     * @return el hub desactivado.
     */
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}/activar")
    public Response activarHub(@PathParam("id") String id) {
        HubPersistence URP = new HubPersistence();
        Hub nuevo = URP.find(id);

        if (nuevo == null) {
            return Response.status(404).entity(new ErrorEdited("La hub con id " + id + " no existe")).build();
        }

        if (nuevo.isActivado()) {
            return Response.status(404).entity(new ErrorEdited("La hub con id " + id + " ya se encontraba activado")).build();
        }
        
        nuevo.setActivado(true);
        nuevo = URP.update(nuevo);

        HubDTO nuevoDTO = new HubDTO(nuevo);
        return Response.status(200).entity(nuevoDTO).build();
    }
    
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response eliminarHub(@PathParam("id") String id) {
        HubPersistence URP = new HubPersistence();
        Hub nuevo = URP.find(id);

        if (nuevo == null) {
            return Response.status(404).entity(new ErrorEdited("La hub con id " + id + " no existe")).build();
        }
        
        URP.delete(id);
    
        return Response.status(200).entity(new ErrorEdited("El hub fue eliminado correctamente")).build();
    }

    private List<HubDTO> toDTOHub(List<Hub> entity) {
        List<HubDTO> list = new ArrayList<>();
        for (Hub hub : entity) {
            list.add(new HubDTO(hub));
        }

        return list;
    }

}
