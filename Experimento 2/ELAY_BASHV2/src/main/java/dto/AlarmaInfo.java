package dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlarmaInfo {

    public String id;
    public String timestamp;
    public int alertaId;
    public String mensajeAlerta;
    public String idDispositivo;
    public int torre;
    public int apto;
    public String unidadResidencial;

    public AlarmaInfo() {
    }

    public AlarmaInfo(int alertaId, String mensajeAlerta, String idDispositivo, int torre, int apto, String id, String timestamp, String unidadResidencial) {
        this.alertaId = alertaId;
        this.mensajeAlerta = mensajeAlerta;
        this.idDispositivo = idDispositivo;
        this.torre = torre;
        this.apto = apto;
        this.id = id;
        this.timestamp = timestamp;
        this.unidadResidencial = unidadResidencial;
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

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
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

    public String getUnidadResidencial() {
        return unidadResidencial;
    }

    public void setUnidadResidencial(String unidadResidencial) {
        this.unidadResidencial = unidadResidencial;
    }

    @Override
    public String toString() {
        return "CountentDTO [id=" + id + ", timestamp=" + timestamp + ", alertaId=" + alertaId + ", mensajeAlerta="
                + mensajeAlerta + ", idDispositivo=" + idDispositivo + ", torre=" + torre + ", apto=" + apto + "]";
    }

}
