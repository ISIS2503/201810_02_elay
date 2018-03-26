/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jd.trujillom
 */
@XmlRootElement
public class UnidadResidencialDTO {
    public String id;

    public String direccion;
    
    public String nombre;
    
    public boolean activado;

    public UnidadResidencialDTO(String id, String direccion, String nombre, boolean activado) {
        this.id = id;
        this.direccion = direccion;
        this.nombre = nombre;
        this.activado = activado;
    }

    public UnidadResidencialDTO(){
        
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getActivado() {
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

    @Override
    public String toString() {
        return "entidad.UnidadResidencial[ id=" + id + " ]";
    }
    
}
