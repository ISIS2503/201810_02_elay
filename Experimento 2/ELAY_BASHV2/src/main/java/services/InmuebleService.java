/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dto.AlarmaDTO;
import dto.InmuebleDTO;
import entidad.Alarma;
import entidad.Inmueble;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import auth.AuthorizationFilter.Role;
import auth.Secured;
import entidad.ErrorEdited;
import entidad.Hub;
import entidad.UnidadResidencial;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import persistencia.HubPersistence;
import persistencia.UnidadResidencialPersistence;

/**
 *
 * @author jd.trujillom
 */
@Path("inmuebles")
@Secured({Role.yale})

public class InmuebleService {

    @Context
    ServletContext servletContext;

    /**
     * Obtiene todos los inmuebles. Solo pueden ser obtenidos por un
     * administrador de Yale.
     *
     * @return todos los inmuebles que están en el sistema.
     */
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
    /**
     * Obtiene un inmueble que pertene a la unidad residencial y es del bloque y
     * es el apartamento pasados por parametros. Solo pueden ser obtenidos por
     * un administrador Yale.
     *
     * @param nombre de la unidad residencial.
     * @param torre del inmueble
     * @param apartamento del inmueble.
     * @return el inmueble con estos atributos. Null en caso de no encontrarlos.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("buscar/{nombre}")
    public Response getInmuebleByTorreAndApartamento(@PathParam("nombre") String nombre, @QueryParam("torre") int torre, @QueryParam("apartamento") int apartamento) {
        try {
            UnidadResidencialPersistence URP = new UnidadResidencialPersistence();

            //Verifica que la unidad residencial exista.
            UnidadResidencial unidadResidencial = URP.find(nombre);
            if (unidadResidencial == null) {
                return Response.status(404).entity(new ErrorEdited("La unidad residencial con nombre " + nombre + " no existe")).build();
            }

            Inmueble buscado = null;

            //Busca al inmueble por su unidad residencial.
            for (Inmueble inmueble : unidadResidencial.getInmuebles()) {
                if (inmueble.getApartamento().equals(apartamento) && inmueble.getTorre().equals(torre)) {
                    buscado = inmueble;
                    break;
                }
            }

            //Lanza exceptión si no encuentra nada
            if (buscado == null) {
                return Response.status(404).entity(new ErrorEdited("No existe un inmueble con esas caracteristicas")).build();
            }

            //Devuelve el inmueble encontrado.
            InmuebleDTO dto = new InmuebleDTO();
            dto.toDTO(buscado);

            return Response.status(200).entity(dto).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }

    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("asociarHUB/{nombre}")
    public Response asociarHub(@PathParam("nombre") String nombre, @QueryParam("torre") int torre, @QueryParam("apartamento") int apartamento, @QueryParam("idHub") String idHub) {
        try {
            UnidadResidencialPersistence URP = new UnidadResidencialPersistence();

            //Verifica que la unidad residencial exista.
            UnidadResidencial unidadResidencial = URP.find(nombre);
            if (unidadResidencial == null) {
                return Response.status(404).entity(new ErrorEdited("La unidad residencial con nombre " + nombre + " no existe")).build();
            }

            Inmueble buscado = null;

            //Busca al inmueble por su unidad residencial.
            for (Inmueble inmueble : unidadResidencial.getInmuebles()) {
                if (inmueble.getApartamento().equals(apartamento) && inmueble.getTorre().equals(torre)) {
                    buscado = inmueble;
                    break;
                }
            }

            //Lanza exceptión si no encuentra nada
            if (buscado == null) {
                return Response.status(404).entity(new ErrorEdited("No existe un inmueble con esas caracteristicas")).build();
            }

            //Busca el hub
            HubPersistence hubPersistence = new HubPersistence();
            Hub hubBuscado = hubPersistence.find(idHub);
            
            if (hubBuscado == null) {
                return Response.status(404).entity(new ErrorEdited("La hub con id" + idHub + " no existe")).build();
            }
            
            buscado.setHub(hubBuscado);
            InmueblePersistence inmueblePersistence = new InmueblePersistence();
            buscado = inmueblePersistence.update(buscado);
            
            InmuebleDTO dto = new InmuebleDTO();
            dto.toDTO(buscado);
            

            return Response.status(200).entity(dto).build();
        } catch (Exception e) {
            return Response.status(500).entity(e.getMessage()).build();
        }

    }
    
    

//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    @Path("alarmas")
//    @Secured({Role.propietario})
//    public Response getAlarmasInmuebleByTorreAndApartamento(@QueryParam("torre") int torre, @QueryParam("apartamento") int apartamento) {
//        try {
//            InmueblePersistence persistence = new InmueblePersistence();
//            Inmueble buscado = persistence.findInmuebleByTorreAndApartamento(torre, apartamento);
//
//            if (buscado == null) {
//                return Response.status(404).entity("No existe un inmueble con esas caracteristcas").build();
//            }
//
//            List<Alarma> alarmasEntity = buscado.getAlarmas();
//            List<AlarmaDTO> alarmaDTO = toDTOAlarmaList(alarmasEntity);
//
//            GenericEntity<List<AlarmaDTO>> listEntity = new GenericEntity<List<AlarmaDTO>>(alarmaDTO) {
//            };
//
//            return Response.status(200).entity(listEntity).build();
//        } catch (Exception e) {
//            return Response.status(500).entity(e.getMessage()).build();
//        }
//
//    }
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Path("actualizar/{nombre}")
    public Response updateInmueble(InmuebleDTO dto, @PathParam("nombre") String nombre, @QueryParam("torre") int torre, @QueryParam("apartamento") int apartamento) {
        
        //Verifica que los datos necesarios esten en el dto
        if(dto.getApartamento() == null || dto.getTorre() == null){
            return Response.status(404).entity(new ErrorEdited("Los datos para actualizar el inmueble están incompletos")).build();
        }
        
        //Verifica la unidad residencial exista
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        UnidadResidencial unidadResidencial = URP.find(nombre);
        
        //Excepción si la unidad residencial no existe
        if (unidadResidencial == null) {
            return Response.status(404).entity(new ErrorEdited("La unidad residencial con nombre " + nombre + " no existe")).build();
        }

        //Busca al inmueble por su unidad residencial.
        Inmueble buscado = null;
        for (Inmueble inmueble : unidadResidencial.getInmuebles()) {
            if (inmueble.getApartamento().equals(apartamento) && inmueble.getTorre().equals(torre)) {
                buscado = inmueble;
                //unidadResidencial.getInmuebles().remove(buscado);
                break;
            }
        }
         if (buscado == null) {
            return Response.status(404).entity(new ErrorEdited("El inmueble con las caracteristicas descritas no existe")).build();
        }
        
        //Procede a actualizar la unidad residecial
        InmueblePersistence UP = new InmueblePersistence();
        buscado.setTorre(dto.getTorre());
        buscado.setApartamento(dto.getApartamento());
        buscado.setUnidadResidencial(unidadResidencial);
        unidadResidencial.addInmueble(buscado);
        
        //Revisar si existe alguna forma de que la actualización sea desde el persistence del inmueble
        unidadResidencial = URP.update(unidadResidencial);
        buscado = UP.update(buscado);
        buscado = unidadResidencial.getInmuebles().get(unidadResidencial.getInmuebles().size()-1);
        
        InmuebleDTO inmuebleDTO = new InmuebleDTO();
        inmuebleDTO.toDTO(buscado);
        
        return Response.status(200).entity(inmuebleDTO).build();

    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("eliminar/{nombre}")
    public Response desactivarUnidadResidencialPorNombre(@PathParam("nombre") String nombre, @QueryParam("torre") int torre, @QueryParam("apartamento") int apartamento) {
        
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        UnidadResidencial unidadResidencial = URP.find(nombre);
        
        //Excepción si la unidad residencial no existe
        if (unidadResidencial == null) {
            return Response.status(404).entity(new ErrorEdited("La unidad residencial con nombre " + nombre + " no existe")).build();
        }

        //Busca al inmueble por su unidad residencial.
        Inmueble buscado = null;
        for (Inmueble inmueble : unidadResidencial.getInmuebles()) {
            if (inmueble.getApartamento().equals(apartamento) && inmueble.getTorre().equals(torre)) {
                buscado = inmueble;
                //unidadResidencial.getInmuebles().remove(buscado);
                break;
            }
        }
         if (buscado == null) {
            return Response.status(404).entity(new ErrorEdited("El inmueble con las caracteristicas descritas no existe")).build();
         }

        InmueblePersistence inmueblePersistence = new InmueblePersistence();
        inmueblePersistence.delete(buscado.getId());

        return Response.status(200).entity(new ErrorEdited("La inmueble fue eliminado correctamente")).build();
    }

    private List<InmuebleDTO> toDTOList(List<Inmueble> entidades) {
        List<InmuebleDTO> lista = new ArrayList<>();
        for (Inmueble inmueble : entidades) {
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
