/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author jd.trujillom
 */
@Entity
@Table(name = "DISPOSITIVO")
public class Dispositivo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    //@Column("nivelCriticoBateria")
    private Integer nivelCriticoBateria;
    
    //@Column("tiempoMaximoAbierta")
    private Integer tiempoMaximoAbierta;
    
   // @Column("frecuenciaReporte")
    private Integer frecuenciaReporte;
    
    //@Column("cantidadIntentosFallidos")
    private Integer cantidadIntentosFallidos;
    
   // @Column("numeroIntentosTolerancia")
    private Integer numeroIntentosTolerancia;
    
    //@Column("clave")
    private String clave;
    
    //@Column("activado")
    private boolean activado;

    public Dispositivo(Long id, Integer nivelCriticoBateria, Integer tiempoMaximoAbierta, Integer frecuenciaReporte, Integer cantidadIntentosFallidos, Integer numeroIntentosTolerancia, String clave, boolean activado) {
        this.id = id;
        this.nivelCriticoBateria = nivelCriticoBateria;
        this.tiempoMaximoAbierta = tiempoMaximoAbierta;
        this.frecuenciaReporte = frecuenciaReporte;
        this.cantidadIntentosFallidos = cantidadIntentosFallidos;
        this.numeroIntentosTolerancia = numeroIntentosTolerancia;
        this.clave = clave;
        this.activado = activado;
    }
    
    public Dispositivo(){
        
    }
    
       public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    
    public Integer getNivelCriticoBateria() {
        return nivelCriticoBateria;
    }

    public void setNivelCriticoBateria(Integer nivelCriticoBateria) {
        this.nivelCriticoBateria = nivelCriticoBateria;
    }

    public Integer getTiempoMaximoAbierta() {
        return tiempoMaximoAbierta;
    }

    public void setTiempoMaximoAbierta(Integer tiempoMaximoAbierta) {
        this.tiempoMaximoAbierta = tiempoMaximoAbierta;
    }

    public Integer getFrecuenciaReporte() {
        return frecuenciaReporte;
    }

    public void setFrecuenciaReporte(Integer frecuenciaReporte) {
        this.frecuenciaReporte = frecuenciaReporte;
    }

    public Integer getCantidadIntentosFallidos() {
        return cantidadIntentosFallidos;
    }

    public void setCantidadIntentosFallidos(Integer cantidadIntentosFallidos) {
        this.cantidadIntentosFallidos = cantidadIntentosFallidos;
    }

    public Integer getNumeroIntentosTolerancia() {
        return numeroIntentosTolerancia;
    }

    public void setNumeroIntentosTolerancia(Integer numeroIntentosTolerancia) {
        this.numeroIntentosTolerancia = numeroIntentosTolerancia;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "entidad.Dispositivo[ id=" + id + " ]";
    }
    
}
