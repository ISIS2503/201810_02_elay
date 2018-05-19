package entidad;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ALARMA")
public class Alarma implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String timestamp;

    private int alertaId;

    private String mensajeAlerta;
    
    private int aptoAlarma;
    
    private int torreAlarma;

    @ManyToOne(cascade = CascadeType.ALL)
    private UnidadResidencial unidadResidencial;

    @ManyToOne(cascade = CascadeType.ALL)
    private Inmueble inmueble;

    @ManyToOne(cascade = CascadeType.ALL)
    private Dispositivo dispositivo;

    public Alarma(String id, String timestamp, int alertaId, String mensajeAlerta, int aptoAlarma, int torreAlarma, UnidadResidencial unidadResidencial, Inmueble inmueble, Dispositivo dispositivo) {
        this.id = id;
        this.timestamp = timestamp;
        this.alertaId = alertaId;
        this.mensajeAlerta = mensajeAlerta;
        this.aptoAlarma = aptoAlarma;
        this.torreAlarma = torreAlarma;
        this.unidadResidencial = unidadResidencial;
        this.inmueble = inmueble;
        this.dispositivo = dispositivo;
    }

    public Alarma() {

    }

    public int getAptoAlarma() {
        return aptoAlarma;
    }

    public void setAptoAlarma(int aptoAlarma) {
        this.aptoAlarma = aptoAlarma;
    }

    public int getTorreAlarma() {
        return torreAlarma;
    }

    public void setTorreAlarma(int torreAlarma) {
        this.torreAlarma = torreAlarma;
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

    public UnidadResidencial getUnidadResidencial() {
        return unidadResidencial;
    }

    public void setUnidadResidencial(UnidadResidencial unidadResidencial) {
        this.unidadResidencial = unidadResidencial;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

}
