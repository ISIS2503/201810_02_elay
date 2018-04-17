/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author jd.trujillom
 */
public class DispositivoDTO {
    private String id;
   
    private Integer nivelCriticoBateria;
    
    private Integer tiempoMaximoAbierta;
    
    private Integer frecuenciaReporte;
    
    private Integer cantidadIntentosFallidos;
    
    private Integer numeroIntentosTolerancia;
    
    private String clave;
    
    private boolean activado;

    public DispositivoDTO(String id, Integer nivelCriticoBateria, Integer tiempoMaximoAbierta, Integer frecuenciaReporte, Integer cantidadIntentosFallidos, Integer numeroIntentosTolerancia, String clave, boolean activado) {
        this.id = id;
        this.nivelCriticoBateria = nivelCriticoBateria;
        this.tiempoMaximoAbierta = tiempoMaximoAbierta;
        this.frecuenciaReporte = frecuenciaReporte;
        this.cantidadIntentosFallidos = cantidadIntentosFallidos;
        this.numeroIntentosTolerancia = numeroIntentosTolerancia;
        this.clave = clave;
        this.activado = activado;
    }

    public DispositivoDTO(){
        
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

    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "entidad.Dispositivo[ id=" + id + " ]";
    }
}
