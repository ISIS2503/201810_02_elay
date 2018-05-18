package services;

import auth.AuthorizationFilter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.AlarmaDTO;
import dto.AlarmaInfo;
import entidad.Alarma;
import entidad.Dispositivo;
import entidad.Inmueble;
import entidad.UnidadResidencial;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import persistencia.AlarmaPersistence;
import persistencia.DispositivoPersistence;
import persistencia.InmueblePersistence;
import persistencia.UnidadResidencialPersistence;
import auth.Secured;

@Path("alarmas")
@Secured({AuthorizationFilter.Role.yale})
public class AlarmaService {

    @Context
    ServletContext servletContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Secured({AuthorizationFilter.Role.yale})
    public Response getAllAlarmas() {
        AlarmaPersistence CP = new AlarmaPersistence();
        List<AlarmaDTO> listaDTO = toDTOAlarmaList(CP.all());

        GenericEntity<List<AlarmaDTO>> entity = new GenericEntity<List<AlarmaDTO>>(listaDTO) {
        };

        return Response.status(200).entity(entity).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id : \\d+}")
    @Secured({AuthorizationFilter.Role.yale})
    public Response getAlarmaPorId(@PathParam("id") String id) {
        AlarmaPersistence CP = new AlarmaPersistence();

        Alarma entity = CP.find(id);
        if (entity == null) {
            return Response.status(404).build();
        }

        AlarmaDTO dto = new AlarmaDTO(entity);
        return Response.status(200).entity(dto).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addAlarma(AlarmaInfo dto) {
        AlarmaPersistence alarmaPersistence = new AlarmaPersistence();
        UnidadResidencialPersistence unidadResidencialPersistence = new UnidadResidencialPersistence();
        InmueblePersistence inmueblePersistence = new InmueblePersistence();
        DispositivoPersistence dispositivoPersistence = new DispositivoPersistence();

        AlarmaDTO alarma = new AlarmaDTO(null, dto.getTimestamp(), dto.getAlertaId(), dto.getMensajeAlerta());
        Alarma entidad = alarma.toEntity();

        UnidadResidencial unidadResidencialBuscado = unidadResidencialPersistence.findByNombre(dto.getUnidadResidencial());
        Inmueble inmuebleBuscado = inmueblePersistence.findInmuebleByTorreAndApartamento(dto.getTorre(), dto.getApto());
        Dispositivo dispositivo = dispositivoPersistence.find(dto.getIdDispositivo());

        if (unidadResidencialBuscado == null) {
            return Response.status(404).entity("La unidad residencial ingresada no existe").build();
        }

        if (inmuebleBuscado == null) {
            return Response.status(404).entity("El inmueble ingresado no existe").build();
        }

        if (dispositivo == null) {
            return Response.status(404).entity("El dispositivo ingresado no existe").build();
        }

        entidad.setInmueble(inmuebleBuscado);
        entidad.setUnidadResidencial(unidadResidencialBuscado);
        entidad.setDispositivo(dispositivo);
        alarmaPersistence.add(entidad);

        alarma = new AlarmaDTO(entidad);

        return Response.status(200).entity(alarma).build();
    }

    @GET
    @Path("holaMundo")
    public Response holaMundo() {
        return Response.status(200).entity("Hola mundo").build();
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
