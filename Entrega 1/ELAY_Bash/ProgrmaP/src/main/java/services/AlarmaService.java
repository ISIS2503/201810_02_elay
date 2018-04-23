package services;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import convert.Convert;
import dto.CountentDTO;
import entidad.Countent;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import persistencia.CuntentPersistence;
import auth.AuthorizationFilter.Role;
import auth.Secured;

@Path("alarmas")
@Secured({Role.yale})
public class AlarmaService {
	@Context 
	ServletContext servletContext;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllAlarmas () {
		CuntentPersistence CP = new CuntentPersistence();
		Convert<CountentDTO, Countent> convert = new Convert<>(CountentDTO.class, Countent.class);
		 List<CountentDTO> listaDTO = convert.listEntityToListDto(CP.all());
		
                 List<CountentDTO> list = new LinkedList<>();
                 GenericEntity<List<CountentDTO>> entity = new GenericEntity<List<CountentDTO>>(listaDTO) {};
                 
		return Response.status(200).entity(entity).build();
	}
        
        @GET
        @Produces({MediaType.APPLICATION_JSON})
        @Path("{id : \\d+}")
        public Response getAlarmaPorId(@PathParam("id") String id){ 
            CuntentPersistence CP = new CuntentPersistence();
            Convert<CountentDTO, Countent> convert = new Convert<>(CountentDTO.class, Countent.class);
            
            Countent entity = CP.find(id);
            if(entity == null)
                return Response.status(404).build();
            
            CountentDTO dto = convert.entityToDto(entity);
            
            return Response.status(200).entity(dto).build();
        }
        
        
	@Consumes({MediaType.APPLICATION_JSON})
        @POST
        public Response addAlarma(CountentDTO dto){
            Convert<CountentDTO, Countent> convert = new Convert<>(CountentDTO.class, Countent.class);
            Countent countent = convert.dtoToEntity(dto);
            
            
            CuntentPersistence CP = new CuntentPersistence();
            CP.add(countent);
            
            
            
            return Response.status(200).build();
        }     
                
                
	@GET
	@Path("holaMundo")
	public Response holaMundo() {
		return Response.status(200).entity("Hola mundo").build();
	}
	
	
}
