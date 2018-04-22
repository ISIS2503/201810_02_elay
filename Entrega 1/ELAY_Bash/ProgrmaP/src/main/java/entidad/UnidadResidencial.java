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
@Table(name = "UNIDADRESIDENCIAL")
public class UnidadResidencial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    //@Column("direccion")
    private String direccion;
    
    //@Column("nombre")
    private String nombre;
    
    //@Column("activado")
    private boolean activado;
    
    @PodamExclude
    @OneToMany(mappedBy="unidadResidencial")
    private List<Inmueble> inmuebles = new ArrayList<Inmueble>; 

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

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }
    
    public List<Inmueble> getInmuebles(){
        return inmuebles;
    }
    
    public void setInmuebles(List<Inmueble> inmuebles){
        this.inmuebles = inmuebles;
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
