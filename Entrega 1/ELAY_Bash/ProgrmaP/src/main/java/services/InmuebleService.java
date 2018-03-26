/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import convert.Convert;
import dto.InmuebleDTO;
import dto.UnidadResidencialDTO;
import entidad.Inmueble;
import entidad.UnidadResidencial;
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
import persistencia.InmueblePersistence;
import persistencia.UnidadResidencialPersistence;

/**
 *
 * @author jd.trujillom
 */
public class InmuebleService {
    
    @Context
    ServletContext servletContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        InmueblePersistence URP = new InmueblePersistence();
        Convert<InmuebleDTO, Inmueble> convert = new Convert<>(InmuebleDTO.class, Inmueble.class);
        List<InmuebleDTO> list = convert.listEntityToListDto(URP.all());

        GenericEntity<List<InmuebleDTO>> entity = new GenericEntity<List<InmuebleDTO>>(list) {
        };

        return Response.status(200).entity(entity).build();

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createUnidadResidencual(UnidadResidencialDTO dto) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        Convert<UnidadResidencialDTO, UnidadResidencial> convert = new Convert<>(UnidadResidencialDTO.class, UnidadResidencial.class);
        UnidadResidencial unidadResidencial = convert.dtoToEntity(dto);

        UnidadResidencial nuevo = URP.add(unidadResidencial);
        UnidadResidencialDTO nuevoDTO = null;
        if (nuevo != null) {
            nuevoDTO = convert.entityToDto(nuevo);
        }
        return Response.status(200).entity(nuevoDTO).build();

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{nombre}")
    public Response getUnidadResidencialPorNombre(@PathParam("nombre") String nombre) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        Convert<UnidadResidencialDTO, UnidadResidencial> convert = new Convert<>(UnidadResidencialDTO.class, UnidadResidencial.class);

        UnidadResidencial entity = URP.find(nombre);
        if (entity == null) {
            return Response.status(404).build();
        }

        UnidadResidencialDTO dto = convert.entityToDto(entity);

        return Response.status(200).entity(dto).build();
    }
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateUnidadResidencial(UnidadResidencialDTO dto) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        Convert<UnidadResidencialDTO, UnidadResidencial> convert = new Convert<>(UnidadResidencialDTO.class, UnidadResidencial.class);
        UnidadResidencial unidadResidencial = convert.dtoToEntity(dto);
        
        
        UnidadResidencial nuevo = URP.find(dto.getNombre());
        UnidadResidencialDTO nuevoDTO = null;
        if (nuevo != null) {
            nuevoDTO = convert.entityToDto(URP.update(unidadResidencial));
        }
        return Response.status(200).entity(nuevoDTO).build();

    }
    
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{nombre}/desactivar")
    public Response deleteUnidadResidencialPorNombre(@PathParam("nombre") String nombre) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        Convert<UnidadResidencialDTO, UnidadResidencial> convert = new Convert<>(UnidadResidencialDTO.class, UnidadResidencial.class);

        UnidadResidencial entity = URP.find(nombre);
        entity.setActivado(false);
        URP.update(entity);
        if (entity == null) {
            return Response.status(404).build();
        }

        UnidadResidencialDTO dto = convert.entityToDto(entity);

        return Response.status(200).entity(dto).build();
    }
    
}
