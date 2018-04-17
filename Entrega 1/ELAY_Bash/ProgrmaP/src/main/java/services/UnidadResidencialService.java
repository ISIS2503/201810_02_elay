/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import convert.Convert;
import dto.UnidadResidencialDTO;
import entidad.UnidadResidencial;
import static java.awt.event.PaintEvent.UPDATE;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import persistencia.CuntentPersistence;
import persistencia.UnidadResidencialPersistence;

/**
 *
 * @author jd.trujillom
 */
@Path("unidadesResidenciales")
public class UnidadResidencialService {

    @Context
    ServletContext servletContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        Convert<UnidadResidencialDTO, UnidadResidencial> convert = new Convert<>(UnidadResidencialDTO.class, UnidadResidencial.class);
        List<UnidadResidencialDTO> listaDTO = convert.listEntityToListDto(URP.all());

        GenericEntity<List<UnidadResidencialDTO>> entity = new GenericEntity<List<UnidadResidencialDTO>>(listaDTO) {
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
    @Path("{nombre}")
    public Response updateUnidadResidencial(UnidadResidencialDTO dto, @PathParam("nombre")String nombre) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        Convert<UnidadResidencialDTO, UnidadResidencial> convert = new Convert<>(UnidadResidencialDTO.class, UnidadResidencial.class);
        UnidadResidencial unidadResidencial = convert.dtoToEntity(dto);
        
        
        UnidadResidencial nuevo = URP.find(nombre);
        UnidadResidencialDTO nuevoDTO = null;
        if (nuevo != null) {
            nuevoDTO = convert.entityToDto(URP.update(unidadResidencial));
        }
        
        else{
            return Response.status(500).entity("No existe una unidad residencial"
                    + "con ese nombre").build();
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
