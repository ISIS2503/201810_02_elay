/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entidad.Alarma;
import entidad.Inmueble;
import entidad.UnidadResidencial;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jd.trujillom
 */
public class UnidadResidencialDTO {

    public String id;

    public String direccion;

    public String nombre;

    public List<InmuebleDTO> inmuebles;

    public List<AlarmaDTO> alarmas;

    public UnidadResidencialDTO(String id, String direccion, String nombre, List<InmuebleDTO> inmuebles, List<AlarmaDTO> alarmas) {
        this.id = id;
        this.direccion = direccion;
        this.nombre = nombre;
        this.inmuebles = inmuebles;
        this.alarmas = alarmas;
    }

    public UnidadResidencialDTO() {

    }

    public List<AlarmaDTO> getAlarmas() {
        return alarmas;
    }

    public void setAlarmas(List<AlarmaDTO> alarmas) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<InmuebleDTO> getInmuebles() {
        return inmuebles;
    }

    public void setInmuebles(List<InmuebleDTO> inmuebles) {
        this.inmuebles = inmuebles;
    }

    @Override
    public String toString() {
        return "entidad.UnidadResidencial[ id=" + id + " ]";
    }

    public UnidadResidencial toEntity() {

        UnidadResidencial entity = new UnidadResidencial();

        entity.setId(id);
        entity.setInmuebles(toEntityInmuebleList(inmuebles));
        entity.setDireccion(direccion);
        entity.setNombre(nombre);
        entity.setAlarmas(toEntityAlarmaList(alarmas));

        return entity;
    }

    public void toDTO(UnidadResidencial unidadResidencial) {
        this.id = unidadResidencial.getId();
        this.nombre = unidadResidencial.getNombre();
        this.direccion = unidadResidencial.getDireccion();
        
        this.inmuebles = unidadResidencial.getInmuebles() != null?toDTOInmuebleList(unidadResidencial.getInmuebles()):new ArrayList<InmuebleDTO>();
        this.alarmas = toDTOAlarmaList(unidadResidencial.getAlarmas());
    }

    private List<InmuebleDTO> toDTOInmuebleList(List<Inmueble> entidades) {
        List<InmuebleDTO> lista = new ArrayList<>();
        for (Inmueble inmueble : entidades) {
            InmuebleDTO nuevo = new InmuebleDTO();
            nuevo.toDTO(inmueble);
            lista.add(nuevo);
        }

        return lista;
    }

    private List<AlarmaDTO> toDTOAlarmaList(List<Alarma> entidades) {
        List<AlarmaDTO> lista = null;
        if (entidades != null) {
            lista = new ArrayList<>();
            for (Alarma inmueble : entidades) {
                AlarmaDTO nuevo = new AlarmaDTO(inmueble);
                lista.add(nuevo);
            }
        }

        return lista;
    }

    private List<Alarma> toEntityAlarmaList(List<AlarmaDTO> list) {
        List<Alarma> lista = null;
        if (list != null) {
            lista = new ArrayList<>();
            for (AlarmaDTO inmueble : list) {
                lista.add(inmueble.toEntity());
            }
        }

        return lista;
    }
    
    private List<Inmueble> toEntityInmuebleList(List<InmuebleDTO> list) {
        List<Inmueble> lista = null;
        if (list != null) {
            lista = new ArrayList<>();
            for (InmuebleDTO inmueble : list) {
                lista.add(inmueble.toEntity());
            }
        }

        return lista;
    }

}
