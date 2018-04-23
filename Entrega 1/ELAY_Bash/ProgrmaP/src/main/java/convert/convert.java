package convert;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import dto.*;
import entidad.*;
import javax.ws.rs.core.GenericEntity;
import persistencia.*;
//import dto.CountentDTO;
//import entidad.Countent;
//import persistencia.CountentPersistence;


public class convert <DTO, Entity>
{
	
	protected Class<DTO> claseDTO;
	protected Class<Entity> claseEntity;
	private Field[] eF; 
	private Field[] dF;

	public convert(Class<DTO> claseDTO, Class<Entity> claseEntity) {
		this.claseDTO = claseDTO;
		this.claseEntity = claseEntity;
		eF = claseEntity.getDeclaredFields();
		dF = claseDTO.getFields();
		if(eF.length != dF.length) 
                {
                    
                    throw new NullPointerException("La entidad no corresponde con el DTO. DTO: " + dF.length + " Entity: " + eF.length);
                }
		Arrays.sort(dF, new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return o1.getType().getName().compareTo(o2.getType().getName());
			}
		});
		Arrays.sort(eF, new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return o1.getType().getName().compareTo(o2.getType().getName());
			}
		});
		for (int i = 0; i < dF.length; i++) {
			if(!eF[i].getType().getName().equals(dF[i].getType().getName())) throw new NullPointerException("La entidad no corresponde con el DTO");	
			eF[i].setAccessible(true);
			dF[i].setAccessible(true);
		}
	}
	
	public convert() {}
	
	public DTO entityToDto(Entity e)  
	{
		try {
			DTO ret = claseDTO.newInstance();
			for (int i = 0; i < eF.length; i++) {
				dF[i].set(ret, eF[i].get(e));
			}
                  
			return ret;
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	
	public Entity dtoToEntity(DTO e)  
	{
		try {
			Entity ret = claseEntity.newInstance();
			for (int i = 0; i < eF.length; i++) {
				eF[i].set(ret, dF[i].get(e));
			}
			return ret;
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	public  List<DTO>listEntityToListDto(List<Entity> le) {
		List<DTO> ret = new LinkedList<>();
		for(Entity e : le) {
			ret.add(entityToDto(e));
		}
                
            
		return ret;
	}
	
	public List<Entity> listDtoToListEntity(List<DTO> ld) {
		List<Entity> ret = new LinkedList<Entity>();
              
		for(DTO d : ld) {
			ret.add(dtoToEntity(d));
		}   
                
		return ret;
	}
	

}
