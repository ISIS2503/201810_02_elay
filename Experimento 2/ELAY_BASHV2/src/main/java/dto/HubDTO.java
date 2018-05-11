/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entidad.Alarma;
import entidad.Dispositivo;
import entidad.Hub;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jd.trujillom
 */
public class HubDTO {
    
    private String id;
    
    private Integer frecuenciaReporte;

    private Integer numeroPerdidasToleradas;
    
    private boolean activado;
    
    private List<DispositivoDTO> dispositivos;

    public HubDTO(String id, Integer frecuenciaReporte, Integer numeroPerdidasToleradas, boolean activado, List<DispositivoDTO> dispositivos) {
        this.id = id;
        this.frecuenciaReporte = frecuenciaReporte;
        this.numeroPerdidasToleradas = numeroPerdidasToleradas;
        this.activado = activado;
        this.dispositivos = dispositivos;
    }
    
    public HubDTO(Hub hub){
        this.id = hub.getId();
        this.activado = hub.isActivado();
        this.dispositivos = toDTODispositivosList(hub.getDispositivos());
        this.frecuenciaReporte = hub.getFrecuenciaReporte();
        this.numeroPerdidasToleradas = hub.getNumeroPerdidasToleradas();
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

    public List<DispositivoDTO> getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(List<DispositivoDTO> dispositivos) {
        this.dispositivos = dispositivos;
    }
    
    private List<DispositivoDTO> toDTODispositivosList(List<Dispositivo> entidades) {
        List<DispositivoDTO> lista = null;
        if (entidades != null) {
            lista = new ArrayList<>();
            for (Dispositivo dispositivo : entidades) {
                DispositivoDTO nuevo = new DispositivoDTO(dispositivo);
                lista.add(nuevo);
            }
        }

        return lista;
    }

    private List<Dispositivo> toEntityDispostivosList(List<DispositivoDTO> list) {
        List<Dispositivo> lista = null;
        if (list != null) {
            lista = new ArrayList<>();
            for (DispositivoDTO dispositivo : list) {
                lista.add(dispositivo.toEntity());
            }
        }

        return lista;
    }
    
    public Hub toEntity(){
        Hub entity = new Hub();
        entity.setActivado(activado);
        entity.setDispositivos(toEntityDispostivosList(dispositivos));
        entity.setFrecuenciaReporte(frecuenciaReporte);
        entity.setId(id);
        entity.setNumeroPerdidasToleradas(numeroPerdidasToleradas);
        
        return entity;
    }
    

    @Override
    public String toString() {
        return "entidad.Hub[ id=" + id + " ]";
    }
}
