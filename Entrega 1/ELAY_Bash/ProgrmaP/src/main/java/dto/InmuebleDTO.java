/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entidad.Alarma;
import entidad.Hub;
import entidad.Inmueble;
import entidad.UnidadResidencial;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jd.trujillom
 */
public class InmuebleDTO {

    public String id;

    public boolean activado;

    public Integer torre;

    public Integer apartamento;

    public Hub hub;
    
    public List<AlarmaDTO> alarmas;

    public InmuebleDTO(String id, boolean activado, Integer torre, Integer apartamento, Hub hub, List<AlarmaDTO> alarmas) {
        this.id = id;
        this.activado = activado;
        this.torre = torre;
        this.apartamento = apartamento;
        this.hub = hub;
        this.alarmas = alarmas;
    }


    public InmuebleDTO() {
        //Empty constructor
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

    public Hub getHub() {
        return hub;
    }

    public void setHub(Hub hub) {
        this.hub = hub;
    }
    
    public Inmueble toEntity(){
        Inmueble inmueble = new Inmueble();
        inmueble.setActivado(this.activado);
        inmueble.setApartamento(this.apartamento);
        inmueble.setId(this.id);
        inmueble.setTorre(this.torre);
        inmueble.setUnidadResidencial(null);
        inmueble.setHub(hub);
        inmueble.setAlarmas(toEntityAlarmaList(alarmas));
        return inmueble;
    }
    
    public void toDTO(Inmueble entidad){
        this.activado = entidad.isActivado();
        this.apartamento = entidad.getApartamento();
        this.id = entidad.getId();
        this.hub = entidad.getHub();
        this.torre = entidad.getTorre();
        this.alarmas = toDTOAlarmaList(entidad.getAlarmas());
    }

    @Override
    public String toString() {
        return "InmuebleDTO{" + "id=" + id + ", activado=" + activado + ", torre=" + torre + ", apartamento=" + apartamento + '}';
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
    
    
}
