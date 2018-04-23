/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

    @Column(name = "activado")
    private boolean activado;

    @Column(name = "torre")
    private Integer torre;

    @Column(name = "apartamento")
    private Integer apartamento;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "inmueble")
    private List<Alarma> alarmas;
    
    @ManyToOne
    private UnidadResidencial unidadResidencial;

    @OneToOne
    private Hub hub;

    public Inmueble(String id, boolean activado, Integer torre, Integer apartamento, List<Alarma> alarmas, UnidadResidencial unidadResidencial, Hub hub) {
        this.id = id;
        this.activado = activado;
        this.torre = torre;
        this.apartamento = apartamento;
        this.alarmas = alarmas;
        this.unidadResidencial = unidadResidencial;
        this.hub = hub;
    }
  
    

    public Inmueble() {

    }
    
     public List<Alarma> getAlarmas() {
        return alarmas;
    }

    public void setAlarmas(List<Alarma> alarmas) {
        this.alarmas = alarmas;
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

    public UnidadResidencial getUnidadResidencial() {
        return unidadResidencial;
    }

    public void setUnidadResidencial(UnidadResidencial unidadResidencial) {
        this.unidadResidencial = unidadResidencial;
    }

    public Hub getHub() {
        return hub;
    }

    public void setHub(Hub hub) {
        this.hub = hub;
    }
    
    @Override
    public String toString() {
        return "Inmueble{" + "id=" + id + ", activado=" + activado + ", torre=" + torre + ", apartamento=" + apartamento + '}';
    }
     
    

}
