/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entidad.Alarma;
import entidad.Dispositivo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jd.trujillom
 */
public class DispositivoDTO {
    private String id;
   
    private Integer nivelCriticoBateria;
    
    private Integer tiempoMaximoAbierta;
    
    private Integer frecuenciaReporte;
    
    private Integer cantidadIntentosFallidos;
    
    private Integer numeroIntentosTolerancia;
    
    private String clave;
    
    private boolean activado;
    
    private List<AlarmaDTO> alarmas;

    public DispositivoDTO(String id, Integer nivelCriticoBateria, Integer tiempoMaximoAbierta, Integer frecuenciaReporte, Integer cantidadIntentosFallidos, Integer numeroIntentosTolerancia, String clave, boolean activado, List<AlarmaDTO> alarmas) {
        this.id = id;
        this.nivelCriticoBateria = nivelCriticoBateria;
        this.tiempoMaximoAbierta = tiempoMaximoAbierta;
        this.frecuenciaReporte = frecuenciaReporte;
        this.cantidadIntentosFallidos = cantidadIntentosFallidos;
        this.numeroIntentosTolerancia = numeroIntentosTolerancia;
        this.clave = clave;
        this.activado = activado;
        this.alarmas = alarmas;
    }
    
    public DispositivoDTO(Dispositivo entidad){
        this.id = entidad.getId();
        this.activado = entidad.isActivado();
        this.alarmas = toDTOAlarmaList(entidad.getAlarmas());
        this.clave = entidad.getClave();
        this.cantidadIntentosFallidos = entidad.getNivelCriticoBateria();
        this.tiempoMaximoAbierta = entidad.getTiempoMaximoAbierta();
        this.frecuenciaReporte = entidad.getFrecuenciaReporte();
        this.nivelCriticoBateria = entidad.getNivelCriticoBateria();
        this.numeroIntentosTolerancia = entidad.getNumeroIntentosTolerancia();
    }


    public DispositivoDTO(){
        
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }
    
    
    
    public Integer getNivelCriticoBateria() {
        return nivelCriticoBateria;
    }

    public void setNivelCriticoBateria(Integer nivelCriticoBateria) {
        this.nivelCriticoBateria = nivelCriticoBateria;
    }

    public Integer getTiempoMaximoAbierta() {
        return tiempoMaximoAbierta;
    }

    public void setTiempoMaximoAbierta(Integer tiempoMaximoAbierta) {
        this.tiempoMaximoAbierta = tiempoMaximoAbierta;
    }

    public Integer getFrecuenciaReporte() {
        return frecuenciaReporte;
    }

    public void setFrecuenciaReporte(Integer frecuenciaReporte) {
        this.frecuenciaReporte = frecuenciaReporte;
    }

    public Integer getCantidadIntentosFallidos() {
        return cantidadIntentosFallidos;
    }

    public void setCantidadIntentosFallidos(Integer cantidadIntentosFallidos) {
        this.cantidadIntentosFallidos = cantidadIntentosFallidos;
    }

    public Integer getNumeroIntentosTolerancia() {
        return numeroIntentosTolerancia;
    }

    public void setNumeroIntentosTolerancia(Integer numeroIntentosTolerancia) {
        this.numeroIntentosTolerancia = numeroIntentosTolerancia;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AlarmaDTO> getAlarmas() {
        return alarmas;
    }

    public void setAlarmas(List<AlarmaDTO> alarmas) {
        this.alarmas = alarmas;
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
    
    public Dispositivo toEntity(){
        Dispositivo entity = new Dispositivo();
        entity.setActivado(activado);
        entity.setAlarmas(toEntityAlarmaList(alarmas));
        entity.setCantidadIntentosFallidos(cantidadIntentosFallidos);
        entity.setClave(clave);
        entity.setFrecuenciaReporte(frecuenciaReporte);
        entity.setId(id);
        entity.setNivelCriticoBateria(nivelCriticoBateria);
        entity.setNumeroIntentosTolerancia(numeroIntentosTolerancia);
        entity.setTiempoMaximoAbierta(tiempoMaximoAbierta);
        
        return entity;
    }


    @Override
    public String toString() {
        return "entidad.Dispositivo[ id=" + id + " ]";
    }
}
