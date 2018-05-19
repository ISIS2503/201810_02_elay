package dto;

import entidad.Alarma;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlarmaDTO {

    public String id;
    public String timestamp;
    public int alertaId;
    public String mensajeAlerta;
    public int aptoAlarma;
    public int torreoAlarma;

    public AlarmaDTO() {
    }

    public AlarmaDTO(String id, String timestamp, int alertaId, String mensajeAlerta, int aptoAlarma, int torreoAlarma) {
        this.id = id;
        this.timestamp = timestamp;
        this.alertaId = alertaId;
        this.mensajeAlerta = mensajeAlerta;
        this.aptoAlarma = aptoAlarma;
        this.torreoAlarma = torreoAlarma;
    }

    public AlarmaDTO(Alarma entity){
        this.id = entity.getId();
        this.alertaId = entity.getAlertaId();
        this.mensajeAlerta = entity.getMensajeAlerta();
        this.timestamp = entity.getTimestamp();
        this.aptoAlarma = entity.getAptoAlarma();
        this.torreoAlarma = entity.getTorreAlarma();
    }

    public int getAptoAlarma() {
        return aptoAlarma;
    }

    public void setAptoAlarma(int aptoAlarma) {
        this.aptoAlarma = aptoAlarma;
    }

    public int getTorreoAlarma() {
        return torreoAlarma;
    }

    public void setTorreoAlarma(int torreoAlarma) {
        this.torreoAlarma = torreoAlarma;
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
        alarma.setAptoAlarma(aptoAlarma);
        alarma.setTorreAlarma(torreoAlarma);
        
        return alarma;
    }

}
