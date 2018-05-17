/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase que modela las unidades residenciales. 
 * @author jd.trujillom
 */
@Entity
@Table(name = "UNIDADRESIDENCIAL")
public class UnidadResidencial implements Serializable {

    /**
     * Id de la unidad residencial. Se autogenera en la base de datos. 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    /**
     * Atributo que modela la dirección en la que se encuentra ubicada la
     * unidad residencial. 
     */
    @Column(name = "direccion")
    private String direccion;
    
    /**
     * Atributo que modela el nombre de una unidad residencial.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Asociación con las alarmas de una unidad residencial. 
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "unidadResidencial")
    private List<Alarma> alarmas;

    /**
     * Asociaón que modela todos los inmuebles que pertenecen a una unidad residencial. 
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //@JoinColumn(name = "unidadResidencial")
    private List<Inmueble> inmuebles;

    public UnidadResidencial(String id, String direccion, String nombre, List<Alarma> alarmas, List<Inmueble> inmuebles) {
        this.id = id;
        this.direccion = direccion;
        this.nombre = nombre;
        this.alarmas = alarmas;
        this.inmuebles = inmuebles;
    }

    
    
    public UnidadResidencial() {
        //Empty constructor
    }

    public List<Alarma> getAlarmas() {
        return alarmas;
    }

    public void setAlarmas(List<Alarma> alarmas) {
        this.alarmas = alarmas;
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

    public List<Inmueble> getInmuebles() {
        return inmuebles;
    }

    public void setInmuebles(List<Inmueble> inmuebles) {
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

    public void addInmueble(Inmueble inmueble) {
        if(inmuebles == null)
            inmuebles = new ArrayList<>();
        inmuebles.add(inmueble);
    }

}
