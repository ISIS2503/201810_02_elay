package elay.ProgrmaP;

import convert.Convert;
import dto.CountentDTO;
import entidad.Countent;
import persistencia.CuntentPersistence;

public class App {

	public static void main(String[] args) {
		System.out.println("Inicio...");
		Countent c = new Countent("1", "2", 3, "4", 5, 6, 7);
		System.out.println(c.toText());
		Convert<CountentDTO, Countent> convert = new Convert<>(CountentDTO.class, Countent.class);
//		//new CountentPersistence().add(c);
//		//new Countent
		new CuntentPersistence().add(c);
		System.out.println("No exploto\n"+convert.dtoToEntity(convert.entityToDto(c)).toText() );
		//new Client(new Interfaz());
	}
}
