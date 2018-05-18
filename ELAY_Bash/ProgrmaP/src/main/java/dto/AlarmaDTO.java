package dto;

import entidad.Alarma;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlarmaDTO {

    public String id;
    public String timestamp;
    public int alertaId;
    public String mensajeAlerta;

    public AlarmaDTO() {
    }

    public AlarmaDTO(String id, String timestamp, int alertaId, String mensajeAlerta) {
        this.id = id;
        this.timestamp = timestamp;
        this.alertaId = alertaId;
        this.mensajeAlerta = mensajeAlerta;
    }
    
    public AlarmaDTO(Alarma entity){
        this.id = entity.getId();
        this.alertaId = entity.getAlertaId();
        this.mensajeAlerta = entity.getMensajeAlerta();
        this.timestamp = entity.getTimestamp();
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
    
    public Alarma toEntity(){
        Alarma alarma = new Alarma();
        alarma.setId(id);
        alarma.setAlertaId(alertaId);
        alarma.setMensajeAlerta(mensajeAlerta);
        alarma.setTimestamp(timestamp);
        
        return alarma;
    }

}
