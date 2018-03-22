package dto;


public class CountentDTO {
	
	public String id;
	public String timestamp;
	public int alertaId;
	public String mensajeAlerta;
	public int idDispositivo;
	public int torre;
	public int apto;
	
	public CountentDTO() {
	}

	public CountentDTO(int alertaId, String mensajeAlerta, int idDispositivo, int torre, int apto, String id, String timestamp) {
		this.alertaId = alertaId;
		this.mensajeAlerta = mensajeAlerta;
		this.idDispositivo = idDispositivo;
		this.torre = torre;
		this.apto = apto;
		this.id = id;
		this.timestamp = timestamp;
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
		return "CountentDTO [id=" + id + ", timestamp=" + timestamp + ", alertaId=" + alertaId + ", mensajeAlerta="
				+ mensajeAlerta + ", idDispositivo=" + idDispositivo + ", torre=" + torre + ", apto=" + apto + "]";
	}
	
	
}
