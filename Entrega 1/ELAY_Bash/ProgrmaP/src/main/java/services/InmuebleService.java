/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import convert.convert;
import dto.AlarmaDTO;
import dto.InmuebleDTO;
import dto.InmuebleDTO;
import entidad.Alarma;
import entidad.Inmueble;
import entidad.Inmueble;
import entidad.UnidadResidencial;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import persistencia.InmueblePersistence;
import persistencia.InmueblePersistence;
import persistencia.UnidadResidencialPersistence;
import auth.AuthorizationFilter.Role;
import auth.Secured;

/**
 *
 * @author jd.trujillom
 */
@Path("inmuebles")
@Secured({Role.yale})

public class InmuebleService {
    
    @Context
    ServletContext servletContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        InmueblePersistence URP = new InmueblePersistence();
        List<InmuebleDTO> list = toDTOList(URP.all());

        GenericEntity<List<InmuebleDTO>> entity = new GenericEntity<List<InmuebleDTO>>(list) {
        };

        return Response.status(200).entity(entity).build();

    }

//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("{nombreUnidadResidencial}")
//    public Response createInmueble(InmuebleDTO dto, @PathParam("nombreUnidadResidencial") String nombre) throws Exception {
//        InmueblePersistence IMP = new InmueblePersistence();
//        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
//        
//        UnidadResidencial unidadAsociada = URP.find(nombre);
//        if(unidadAsociada == null)
//            throw new Exception("La unidad residencial con nombre + " + nombre + " no existe");
//        
//        convert<InmuebleDTO, Inmueble> convert = new convert<>(InmuebleDTO.class, Inmueble.class);
//        Inmueble inmueble = convert.dtoToEntity(dto);
//        
//        unidadAsociada.addInmueble(inmueble);
//        inmueble.setUnidadResidencial(unidadAsociada);
//        
//        
//        Inmueble nuevo = IMP.add(inmueble);
//        InmuebleDTO nuevoDTO = null;
//        if (nuevo != null) {
//            nuevoDTO = convert.entityToDto(nuevo);
//        }
//        return Response.status(200).entity(nuevoDTO).build();
//
//    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{nombre}")
    public Response getInmueblePorId(@PathParam("nombre") String nombre) {
        InmueblePersistence URP = new InmueblePersistence();
        convert<InmuebleDTO, Inmueble> convert = new convert<>(InmuebleDTO.class, Inmueble.class);

        Inmueble entity = URP.find(nombre);
        if (entity == null) {
            return Response.status(404).build();
        }

        InmuebleDTO dto = convert.entityToDto(entity);

        return Response.status(200).entity(dto).build();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("buscar")
    public Response getInmuebleByTorreAndApartamento(@QueryParam("torre") int torre, @QueryParam("apartamento") int apartamento){
        try{
           InmueblePersistence persistence = new InmueblePersistence();
           Inmueble buscado = persistence.findInmuebleByTorreAndApartamento(torre, apartamento);
           
           if(buscado == null){
              return Response.status(404).entity("No existe un inmueble con esas caracteristcas").build();
           }
           
           InmuebleDTO dto = new InmuebleDTO();
           dto.toDTO(buscado);

            return Response.status(200).entity(dto).build();
        }
        
        catch(Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
              
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("alarmas")
    @Secured({Role.propietario})
    public Response getAlarmasInmuebleByTorreAndApartamento(@QueryParam("torre") int torre, @QueryParam("apartamento") int apartamento){
        try{
           InmueblePersistence persistence = new InmueblePersistence();
           Inmueble buscado = persistence.findInmuebleByTorreAndApartamento(torre, apartamento);
           
           if(buscado == null){
              return Response.status(404).entity("No existe un inmueble con esas caracteristcas").build();
           }
           
           List<Alarma> alarmasEntity = buscado.getAlarmas();
           List<AlarmaDTO> alarmaDTO = toDTOAlarmaList(alarmasEntity);
           
           GenericEntity<List<AlarmaDTO>> listEntity = new GenericEntity<List<AlarmaDTO>>(alarmaDTO) { };
           

            return Response.status(200).entity(listEntity).build();
        }
        
        catch(Exception e){
            return Response.status(500).entity(e.getMessage()).build();
        }
              
    }
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response updateUnidadResidencial(InmuebleDTO dto, @PathParam("id") String id) {
        InmueblePersistence URP = new InmueblePersistence();
        convert<InmuebleDTO, Inmueble> convert = new convert<>(InmuebleDTO.class, Inmueble.class);
        Inmueble Inmueble = convert.dtoToEntity(dto);
        
        
        Inmueble nuevo = URP.find(id);
        InmuebleDTO nuevoDTO = null;
        if (nuevo != null) {
            nuevoDTO = convert.entityToDto(URP.update(Inmueble));
        }
        return Response.status(200).entity(nuevoDTO).build();

    }
    
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{nombre}/desactivar")
    public Response deleteUnidadResidencialPorNombre(@PathParam("nombre") String nombre) {
        InmueblePersistence URP = new InmueblePersistence();
        convert<InmuebleDTO, Inmueble> convert = new convert<>(InmuebleDTO.class, Inmueble.class);

        Inmueble entity = URP.find(nombre);
        entity.setActivado(false);
        URP.update(entity);
        if (entity == null) {
            return Response.status(404).build();
        }

        InmuebleDTO dto = convert.entityToDto(entity);

        return Response.status(200).entity(dto).build();
    }
    
    
    private List<InmuebleDTO> toDTOList(List<Inmueble> entidades){
        List<InmuebleDTO> lista = new ArrayList<>();
        for(Inmueble inmueble: entidades){
            InmuebleDTO nuevo = new InmuebleDTO();
            nuevo.toDTO(inmueble);
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
