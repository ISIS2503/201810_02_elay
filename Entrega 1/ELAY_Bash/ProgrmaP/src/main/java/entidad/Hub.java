/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jd.trujillom
 */
@Entity
@Table(name = "HUB")
public class Hub implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    //@Column("frecuenciaReporte")
    private Integer frecuenciaReporte;
    
    //@Column("numeroPerdidasToleradas")
    private Integer numeroPerdidasToleradas;
    
    //@Column("activado")
    private boolean activado;
    
    @PodamExclude
    @OneToMany(mappedBy="hub")
    private List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();

    
     public Hub(String id, Integer frecuenciaReporte, Integer numeroPerdidasToleradas, boolean activado) {
        this.id = id;
        this.frecuenciaReporte = frecuenciaReporte;
        this.numeroPerdidasToleradas = numeroPerdidasToleradas;
        this.activado = activado;
    }
    
    public Hub(){
        
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }
    
    public List<Dispositivo> getDispositivos(){
        return dispositivos;
    }
    
    public void setDispositivos(List<Dispositivos> dispositivos){
        this.dispositivos = dispositivos;
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
