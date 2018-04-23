/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import convert.convert;
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

/**
 *
 * @author jd.trujillom
 */
@Path("hub")
public class HubService {
    
    @Context
    ServletContext servletContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        HubPersistence URP = new HubPersistence();
        List<HubDTO> list = toDTOHub(URP.all());

        GenericEntity<List<HubDTO>> entity = new GenericEntity<List<HubDTO>>(list) {
        };

        return Response.status(200).entity(entity).build();

    }

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

//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("{nombre}")
//    public Response getUnidadResidencialPorNombre(@PathParam("nombre") String nombre) {
//        HubPersistence URP = new HubPersistence();
//        Convert<HubDTO, Hub> convert = new Convert<>(HubDTO.class, Hub.class);
//
//        Hub entity = URP.find(nombre);
//        if (entity == null) {
//            return Response.status(404).build();
//        }
//
//        HubDTO dto = convert.entityToDto(entity);
//
//        return Response.status(200).entity(dto).build();
//    }
    
//    @PUT
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response updateUnidadResidencial(HubDTO dto) {
//        HubPersistence URP = new HubPersistence();
//        Convert<HubDTO, Hub> convert = new Convert<>(HubDTO.class, Hub.class);
//        Hub Hub = convert.dtoToEntity(dto);
//        
//        
//        Hub nuevo = URP.find(dto.getNombre());
//        HubDTO nuevoDTO = null;
//        if (nuevo != null) {
//            nuevoDTO = convert.entityToDto(URP.update(Hub));
//        }
//        return Response.status(200).entity(nuevoDTO).build();
//
//    }
    
//    @PUT
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("{nombre}/desactivar")
//    public Response deleteUnidadResidencialPorNombre(@PathParam("nombre") String nombre) {
//        HubPersistence URP = new HubPersistence();
//        Convert<HubDTO, Hub> convert = new Convert<>(HubDTO.class, Hub.class);
//
//        Hub entity = URP.find(nombre);
//        entity.setActivado(false);
//        URP.update(entity);
//        if (entity == null) {
//            return Response.status(404).build();
//        }
//
//        HubDTO dto = convert.entityToDto(entity);
//
//        return Response.status(200).entity(dto).build();
//    }
    
    private List<HubDTO> toDTOHub(List<Hub> entity){
        List<HubDTO> list = new ArrayList<>();
        for(Hub hub: entity){
            list.add(new HubDTO(hub));
        }
        
        return list;
    }
    
}
