/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import convert.Convert;
import dto.DispositivoDTO;
import dto.DispositivoDTO;
import entidad.Dispositivo;
import entidad.Dispositivo;
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
import persistencia.DispositivoPersistence;
import persistencia.DispositivoPersistence;
import auth.AuthorizationFilter.Role;
import auth.Secured;

/**
 *
 * @author jd.trujillom
 */
@Path("dispositivos")
public class DispositivoService {
    
    @Context
    ServletContext servletContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        DispositivoPersistence URP = new DispositivoPersistence();
        Convert<DispositivoDTO, Dispositivo> convert = new Convert<>(DispositivoDTO.class, Dispositivo.class);
        List<DispositivoDTO> list = convert.listEntityToListDto(URP.all());

        GenericEntity<List<DispositivoDTO>> entity = new GenericEntity<List<DispositivoDTO>>(list) {
        };

        return Response.status(200).entity(entity).build();

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createInmueble(DispositivoDTO dto) {
        DispositivoPersistence URP = new DispositivoPersistence();
        Convert<DispositivoDTO, Dispositivo> convert = new Convert<>(DispositivoDTO.class, Dispositivo.class);
        Dispositivo Dispositivo = convert.dtoToEntity(dto);

        Dispositivo nuevo = URP.add(Dispositivo);
        DispositivoDTO nuevoDTO = null;
        if (nuevo != null) {
            nuevoDTO = convert.entityToDto(nuevo);
        }
        return Response.status(200).entity(nuevoDTO).build();

    }

//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("{nombre}")
//    public Response getUnidadResidencialPorNombre(@PathParam("nombre") String nombre) {
//        DispositivoPersistence URP = new DispositivoPersistence();
//        Convert<DispositivoDTO, Dispositivo> convert = new Convert<>(DispositivoDTO.class, Dispositivo.class);
//
//        Dispositivo entity = URP.find(nombre);
//        if (entity == null) {
//            return Response.status(404).build();
//        }
//
//        DispositivoDTO dto = convert.entityToDto(entity);
//
//        return Response.status(200).entity(dto).build();
//    }
    
//    @PUT
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response updateUnidadResidencial(DispositivoDTO dto) {
//        DispositivoPersistence URP = new DispositivoPersistence();
//        Convert<DispositivoDTO, Dispositivo> convert = new Convert<>(DispositivoDTO.class, Dispositivo.class);
//        Dispositivo Dispositivo = convert.dtoToEntity(dto);
//        
//        
//        Dispositivo nuevo = URP.find(dto.getNombre());
//        DispositivoDTO nuevoDTO = null;
//        if (nuevo != null) {
//            nuevoDTO = convert.entityToDto(URP.update(Dispositivo));
//        }
//        return Response.status(200).entity(nuevoDTO).build();
//
//    }
    
//    @PUT
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("{nombre}/desactivar")
//    public Response deleteUnidadResidencialPorNombre(@PathParam("nombre") String nombre) {
//        DispositivoPersistence URP = new DispositivoPersistence();
//        Convert<DispositivoDTO, Dispositivo> convert = new Convert<>(DispositivoDTO.class, Dispositivo.class);
//
//        Dispositivo entity = URP.find(nombre);
//        entity.setActivado(false);
//        URP.update(entity);
//        if (entity == null) {
//            return Response.status(404).build();
//        }
//
//        DispositivoDTO dto = convert.entityToDto(entity);
//
//        return Response.status(200).entity(dto).build();
//    }
    
}
