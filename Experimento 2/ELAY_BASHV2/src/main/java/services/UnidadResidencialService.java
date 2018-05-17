/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dto.AlarmaDTO;
import dto.InmuebleDTO;
import dto.UnidadResidencialDTO;
import entidad.Alarma;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import persistencia.UnidadResidencialPersistence;
import auth.AuthorizationFilter.Role;
import auth.Secured;
import entidad.ErrorEdited;
import java.util.Objects;
import javax.ws.rs.DELETE;

/**
 * Clase que contiene los servicios de las unidades residenciales del sistema.
 *
 * @author jd.trujillom
 */
@Path("unidadesResidenciales")
@Secured({Role.yale})
public class UnidadResidencialService {

    @Context
    ServletContext servletContext;

    /**
     * Retorna todas las unidades residenciales que están alojadas en el
     * sistema.
     *
     * @return todas las unidades residenciales alojadas en el sistema..
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {

        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        List<UnidadResidencial> lista = URP.all();
        List<UnidadResidencialDTO> listaDTO = null;
        GenericEntity<List<UnidadResidencialDTO>> entity = null;

        if (lista != null) {
            listaDTO = toDTOList(lista);
            entity = new GenericEntity<List<UnidadResidencialDTO>>(listaDTO) {
            };
        }
    

    return Response.status (

200).entity(entity).build();

    }

    /**
     * Crear una nueva unidad residencial.
     *
     * @param dto de la unidad residencial que se desea crear.
     * @return la unidad residencial que fue persistida en la base de datos.
     */
    @POST
        @Consumes({MediaType.APPLICATION_JSON})
        @Produces({MediaType.APPLICATION_JSON})
        public Response createUnidadResidencial(UnidadResidencialDTO dto) {
        //Crea la persistencia de una unidad residencial y transforma el dto
        //a un entity
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();

        //Envia una excepción si falta alguno de los elementos.
        if (dto.getNombre() == null || dto.getDireccion() == null) {
            return Response.status(500).entity(new ErrorEdited("Los datos están incompletos")).build();
        }

        //Verifica si una unidad residencial ya existe. 
        UnidadResidencial urBuscada = URP.find(dto.getNombre());

        if (urBuscada != null) {
            return Response.status(500).entity(new ErrorEdited("La unidad residencial con el nombre dado ya existe.")).build();
        }

        UnidadResidencial unidadResidencial = dto.toEntity();

        //Crea los muevk
        unidadResidencial.setInmuebles(new ArrayList<Inmueble>());
        UnidadResidencial nuevo = URP.add(unidadResidencial);

        UnidadResidencialDTO nuevoDTO = null;
        if (nuevo != null) {
            nuevoDTO = new UnidadResidencialDTO();
            nuevoDTO.toDTO(nuevo);
        }
        return Response.status(200).entity(nuevoDTO).build();
    }

    /**
     * Crea un inmueble que va a estar asociado a una unidad residencial.
     *
     * @param dto del inmueble que se quiere asociar a la unidad residencial.
     * @param nombre nombre da la unidad residencial a la que se quiere el
     * inmueble.
     * @return la unidad residencial con el inmueble asociado.
     */
    @POST
        @Consumes({MediaType.APPLICATION_JSON})
        @Produces({MediaType.APPLICATION_JSON})
        @Path("{nombre}/crearInmueble")
        public Response createInmueble(InmuebleDTO dto, @PathParam("nombre") String nombre) {
        try {
            //Verifica que el inmueble tenga un formato correcto
            if (dto.getApartamento() == null || dto.getTorre() == null) {
                return Response.status(500).entity(new ErrorEdited("El formato del inmueble es invalido")).build();
            }

            //Instancia un persistence de la unidad residencial. 
            UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
            UnidadResidencial unidadAsociada = URP.find(nombre);

            //Lanza excepcion en caso de que no encuentre una unidad residencial
            if (unidadAsociada == null) {
                return Response.status(500).entity(new ErrorEdited("La unidad residencial con nombre " + nombre + " no existe")).build();
            }

            //Lanza una excepcion en caso de que en la unidad residencial exista
            //el inmueble
            if (unidadAsociada.getInmuebles() != null) {
                for (Inmueble inmueble : unidadAsociada.getInmuebles()) {
                    System.out.println(inmueble.getApartamento() + ":" + inmueble.getTorre());
                    if (Objects.equals(inmueble.getApartamento(), dto.getApartamento()) && Objects.equals(inmueble.getTorre(), dto.getTorre())) {
                        return Response.status(500).entity(new ErrorEdited("El inmueble ya existe en la unidad residencial")).build();
                    }
                }
            }
            
            else{
                unidadAsociada.setInmuebles(new ArrayList<Inmueble>());
            }

            //Asocio el inmueble con la unidad residencial
            Inmueble inmueble = dto.toEntity();
            unidadAsociada.addInmueble(inmueble);
            inmueble.setUnidadResidencial(unidadAsociada);

            //Actualiza la unidad residencial. 
            unidadAsociada = URP.update(unidadAsociada);

            //Crea el deto de la unidad residencial
            UnidadResidencialDTO nuevoDTO = new UnidadResidencialDTO();
            nuevoDTO.toDTO(unidadAsociada);

            return Response.status(200).entity(nuevoDTO).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(e.getMessage()).build();
        }

    }

    /**
     * Busca una unidad residencial por su nombre.
     *
     * @param nombre de la unidad residencial que se quiere encontrar.
     * @return la unidad residencial con el nombre pasado por parametro. Null en
     * caso que no lo encuentre.
     */
    @GET
        @Produces({MediaType.APPLICATION_JSON})
        @Path("{nombre}")
        public Response getUnidadResidencialPorNombre(@PathParam("nombre") String nombre) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();

        UnidadResidencial entity = URP.find(nombre);
        if (entity == null) {
            return Response.status(404).entity(new ErrorEdited("La unidad residencial con el nombre " + nombre + " no existe")).build();
        }

        UnidadResidencialDTO dto = new UnidadResidencialDTO();
        dto.toDTO(entity);

        return Response.status(200).entity(dto).build();
    }

    /**
     * Obtiene todas las alarmas de una unidad residencial durante todo el
     * tiempo de actividad en el sistema. Solo tiene acceso la seguridad
     * privada.
     *
     * @param nombre de la unidad residencial de la cual se quieren obtener sus
     * alarmas
     * @return todas las alarmas de una unidad residencial.
     */
        @GET
        @Produces({MediaType.APPLICATION_JSON})
        @Path("{nombre}/alarmas")
        @Secured({Role.seguridad_privada})
        public Response getAlarmasUnidadResidencial(@PathParam("nombre") String nombre) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();

        UnidadResidencial entity = URP.find(nombre);
        if (entity == null) {
            return Response.status(404).entity(new ErrorEdited("La unidad residencial con el nombre " + nombre + " no existe")).build();
        }

        List<Alarma> alarmasEntity = entity.getAlarmas();
        List<AlarmaDTO> alarmas = toDTOAlarmaList(alarmasEntity);

        GenericEntity<List<AlarmaDTO>> listEntity = new GenericEntity<List<AlarmaDTO>>(alarmas) {
        };

        return Response.status(200).entity(listEntity).build();
    }

    /**
     * Actualiza la información de una unidad residencial. El id no puede ser
     * cambiado
     *
     * @param dto con la información de la unidad residencial con la que se
     * quiere actualizar a una unidad residencial.
     * @param nombre de la unidad residencial de la que se quiere actualizar la
     * información.
     * @return la unidad residencial actualizada.
     */
    @PUT
        @Consumes({MediaType.APPLICATION_JSON})
        @Produces({MediaType.APPLICATION_JSON})
        @Path("{nombre}")
        public Response updateUnidadResidencial(UnidadResidencialDTO dto, @PathParam("nombre") String nombre) {
        //Verifica que tanto la nueva dirección como el nuevo nombre estén ahí
        if (dto.getDireccion() == null || dto.getNombre() == null) {
            return Response.status(500).entity(new ErrorEdited("Hace falta información para actualizar la unidad residencial")).build();
        }

        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();
        UnidadResidencial nuevo = URP.find(nombre);

        if (nuevo == null) {
            return Response.status(500).entity(new ErrorEdited("La unidad residencial con el nombre " + nombre + " no existe")).build();
        }

        //Se cambian los atributos
        nuevo.setNombre(dto.getNombre());
        nuevo.setDireccion(dto.getDireccion());

        UnidadResidencialDTO nuevoDTO = null;
        nuevoDTO = new UnidadResidencialDTO();

        //Actualiza en la base de datos
        nuevoDTO.toDTO(URP.update(nuevo));

        return Response.status(200).entity(nuevoDTO).build();

    }

    /**
     *
     * @param nombre
     * @return
     */
    @DELETE
        @Path("{nombre}")
        public Response deleteUnidadResidencialPorNombre(@PathParam("nombre") String nombre) {
        UnidadResidencialPersistence URP = new UnidadResidencialPersistence();

        //Busca el objeto y comprueba que la unidad residencial existe
        UnidadResidencial entity = URP.find(nombre);
        if (entity == null) {
            return Response.status(404).entity(new ErrorEdited("La unidad residencial con el nombre " + nombre + " no existe")).build();
        }

        //Elimina la unidad residencial de la base de datos
        URP.delete(nombre);

        return Response.status(200).build();
    }

    private List<UnidadResidencialDTO> toDTOList(List<UnidadResidencial> entidades) {
        List<UnidadResidencialDTO> lista = new ArrayList<>();
        for (UnidadResidencial unidadResidencial : entidades) {
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
