package entidad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ALERTA")
public class Countent {
	@Id
	public String id;
	public String timestamp;
	public int alertaId;
	public String mensajeAlerta;
	public int idDispositivo;
	public int torre;
	public int apto;

	public Countent(String id, String timestamp, int alertaId, String mensajeAlerta, int idDispositivo, int torre, int apto) {
		this.alertaId = alertaId;
		this.mensajeAlerta = mensajeAlerta;
		this.idDispositivo = idDispositivo;
		this.torre = torre;
		this.apto = apto;
		this.id = id;
		this.timestamp = timestamp;
	}

	public Countent() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getAlertaId() {
		return alertaId;
	}

	public void setAlertaId(int alertaId) {
		this.alertaId = alertaId;
	}

	public String getMensajeAlerta() {
		return mensajeAlerta;
	}

	public void setMensajeAlerta(String mensajeAlerta) {
		this.mensajeAlerta = mensajeAlerta;
	}

	public int getIdDispositivo() {
		return idDispositivo;
	}

	public void setIdDispositivo(int idDispositivo) {
		this.idDispositivo = idDispositivo;
	}

	public int getTorre() {
		return torre;
	}

	public void setTorre(int torre) {
		this.torre = torre;
	}

	public int getApto() {
		return apto;
	}

	public void setApto(int apto) {
		this.apto = apto;
	}

	@Override
	public String toString() {
		return timestamp + ": [ " + mensajeAlerta + " ]";
	}

	public String toText() {
		return "[timestamp=" + timestamp + ", info=" + "[alertaId=" + alertaId + ", mensajeAlerta=" + mensajeAlerta
				+ ", idDispositivo=" + idDispositivo + ", torre=" + torre + ", apto=" + apto + "]" + "]";
	}
}
