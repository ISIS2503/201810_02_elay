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
public class HubDTO {
    
    private String id;
    
    private Integer frecuenciaReporte;

    private Integer numeroPerdidasToleradas;
    
    private boolean activado;

    
     public HubDTO(String id, Integer frecuenciaReporte, Integer numeroPerdidasToleradas, boolean activado) {
        this.id = id;
        this.frecuenciaReporte = frecuenciaReporte;
        this.numeroPerdidasToleradas = numeroPerdidasToleradas;
        this.activado = activado;
    }
    
    public HubDTO(){
        
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }
    
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFrecuenciaReporte() {
        return frecuenciaReporte;
    }

    public void setFrecuenciaReporte(Integer frecuenciaReporte) {
        this.frecuenciaReporte = frecuenciaReporte;
    }

    public Integer getNumeroPerdidasToleradas() {
        return numeroPerdidasToleradas;
    }

    public void setNumeroPerdidasToleradas(Integer numeroPerdidasToleradas) {
        this.numeroPerdidasToleradas = numeroPerdidasToleradas;
    }

    @Override
    public String toString() {
        return "entidad.Hub[ id=" + id + " ]";
    }
}
