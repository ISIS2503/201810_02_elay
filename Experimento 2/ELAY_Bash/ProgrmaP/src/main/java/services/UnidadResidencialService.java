/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import convert.convert;
import dto.AlarmaDTO;
import dto.InmuebleDTO;
import dto.UnidadResidencialDTO;
import entidad.Alarma;
import entidad.Inmueble;
import entidad.UnidadResidencial;
import static java.awt.event.PaintEvent.UPDATE;
import java.util.ArrayList;
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
import persistencia.AlarmaPersistence;
import persistencia.InmueblePersistence;
import persistencia.UnidadResidencialPersistence;
import auth.AuthorizationFilter.Role;
import auth.Secured;

/**
 *
 * @author jd.trujillom
 */
@Path("unidadesResidenciales")
@Secured({Role.yale})
public class UnidadResidencialService {

    @Context
    ServletContext servletContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        List<UnidadResidencialDTO> listaDTO = toDTOList(URP.all());

        GenericEntity<List<UnidadResidencialDTO>> entity = new GenericEntity<List<UnidadResidencialDTO>>(listaDTO) {};

        return Response.status(200).entity(entity).build();

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createUnidadResidencial(UnidadResidencialDTO dto) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        UnidadResidencial unidadResidencial = dto.toEntity();

        
        unidadResidencial.setInmuebles(new ArrayList<>());
        UnidadResidencial nuevo = URP.add(unidadResidencial);

        UnidadResidencialDTO nuevoDTO = null;
        if (nuevo != null) {
            nuevoDTO = new UnidadResidencialDTO();
            nuevoDTO.toDTO(nuevo);
        }
        return Response.status(200).entity(nuevoDTO).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{nombre}")
    public Response createInmueble(InmuebleDTO dto, @PathParam("nombre") String nombre) {
        try {
            UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
            UnidadResidencial unidadAsociada = URP.find(nombre);
            
            if (unidadAsociada == null) {
                return Response.status(500).entity("La unidad residencial con nombre + " + nombre + " no existe").build();
            }
            Inmueble inmueble = dto.toEntity();
            
            unidadAsociada.addInmueble(inmueble);
            inmueble.setUnidadResidencial(unidadAsociada);
            unidadAsociada = URP.update(unidadAsociada);

            UnidadResidencialDTO nuevoDTO = new UnidadResidencialDTO();
            nuevoDTO.toDTO(unidadAsociada);

            return Response.status(200).entity(nuevoDTO).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e.getMessage()).build();
        }

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{nombre}")
    public Response getUnidadResidencialPorNombre(@PathParam("nombre") String nombre) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();

        UnidadResidencial entity = URP.find(nombre);
        if (entity == null) {
            return Response.status(404).build();
        }

        UnidadResidencialDTO dto = new UnidadResidencialDTO();
        dto.toDTO(entity);

        return Response.status(200).entity(dto).build();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{nombre}/alarmas")
    @Secured({Role.seguridad_privada})
    public Response getAlarmasUnidadResidencial(@PathParam("nombre") String nombre) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();

        UnidadResidencial entity = URP.find(nombre);
        if (entity == null) {
            return Response.status(404).build();
        }

        List<Alarma> alarmasEntity = entity.getAlarmas();
        List<AlarmaDTO> alarmas = toDTOAlarmaList(alarmasEntity);
        
        GenericEntity<List<AlarmaDTO>> listEntity = new GenericEntity<List<AlarmaDTO>>(alarmas) { };

        return Response.status(200).entity(listEntity).build();
    }
    

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{nombre}")
    public Response updateUnidadResidencial(UnidadResidencialDTO dto, @PathParam("nombre") String nombre) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        convert<UnidadResidencialDTO, UnidadResidencial> convert = new convert<>(UnidadResidencialDTO.class, UnidadResidencial.class);
        UnidadResidencial unidadResidencial = convert.dtoToEntity(dto);

        UnidadResidencial nuevo = URP.find(nombre);
        UnidadResidencialDTO nuevoDTO = null;
        if (nuevo != null) {
            nuevoDTO = convert.entityToDto(URP.update(unidadResidencial));
        } else {
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
        convert<UnidadResidencialDTO, UnidadResidencial> convert = new convert<>(UnidadResidencialDTO.class, UnidadResidencial.class);

        UnidadResidencial entity = URP.find(nombre);
        entity.setActivado(false);
        URP.update(entity);
        if (entity == null) {
            return Response.status(404).build();
        }

        UnidadResidencialDTO dto = convert.entityToDto(entity);

        return Response.status(200).entity(dto).build();
    }
    
    
    private List<UnidadResidencialDTO> toDTOList(List<UnidadResidencial> entidades){
        List<UnidadResidencialDTO> lista = new ArrayList<>();
        for(UnidadResidencial unidadResidencial: entidades){
            UnidadResidencialDTO nuevo = new UnidadResidencialDTO();
            nuevo.toDTO(unidadResidencial);
            lista.add(nuevo);
        }
        
        return lista;
    }
    
    
     private List<AlarmaDTO> toDTOAlarmaList(List<Alarma> entidades) {
        List<AlarmaDTO> lista = null;
        if (entidades != null) {
            lista = new ArrayList<>();
            for (Alarma inmueble : entidades) {
                AlarmaDTO nuevo = new AlarmaDTO(inmueble);
                lista.add(nuevo);
            }
        }

        return lista;
    }

}
