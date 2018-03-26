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
public class InmuebleDTO {
    
    public String id;
    
    public boolean activado;

    public Integer torre;
    
    public Integer apartamento;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    public Integer getTorre() {
        return torre;
    }

    public void setTorre(Integer torre) {
        this.torre = torre;
    }

    public Integer getApartamento() {
        return apartamento;
    }

    public void setApartamento(Integer apartamento) {
        this.apartamento = apartamento;
    }

    public InmuebleDTO(String id, boolean activado, Integer torre, Integer apartamento) {
        this.id = id;
        this.activado = activado;
        this.torre = torre;
        this.apartamento = apartamento;
    }

      
    public InmuebleDTO(){
           
    }


    @Override
    public String toString() {
        return "entidad.Inmueble[ id=" + id + " ]";
    }
}
