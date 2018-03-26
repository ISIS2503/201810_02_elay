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
@Table(name = "INMUEBLE")
public class Inmueble implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    //@Column("activado")
    private boolean activado;
    
   // @Column("torre")
    private Integer torre;
    
    //@Column("apartamento")
    private Integer apartamento;

       public Inmueble(String id, boolean activado, Integer torre, Integer apartamento) {
        this.id = id;
        this.activado = activado;
        this.torre = torre;
        this.apartamento = apartamento;
    }
       
       public Inmueble(){
           
       }


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


    @Override
    public String toString() {
        return "entidad.Inmueble[ id=" + id + " ]";
    }
    
}
