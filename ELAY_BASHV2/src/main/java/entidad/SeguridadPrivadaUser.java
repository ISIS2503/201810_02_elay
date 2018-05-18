/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

import java.util.List;

/**
 * Clase que modela a los usuarios de una unidad residencial.
 *
 * @author jd.trujillom
 */
public class SeguridadPrivadaUser extends User {

    /**
     * Modela la relación entre la seguridad privada y la unidad residencial a
     * que pertenecen. Esto se así porque esto se persiste en Auth0.
     */
    private String idUnidadResidencial;

    /**
     * Construye un nuevo usuario de seguridad privada.
     *
     * @param id del usuario. Este id es asignado por Auth0
     * @param correo electronico del usuario.
     * @param nombre del usuario.
     * @param usuario unico del usuario.
     * @param contrasenia
     * @param fechaNacimiento
     * @param roles
     */
    public SeguridadPrivadaUser(String id, String correo, String nombre, String usuario, String contrasenia, String fechaNacimiento, List<String> roles, String idUnidadResidencial) {
        super(id, correo, nombre, usuario, contrasenia, fechaNacimiento, roles);
        this.idUnidadResidencial = idUnidadResidencial;
    }

    /**
     * Constructor vacio
     */
    public SeguridadPrivadaUser() {
    }

    /**
     * Retorna el id de la unidad residencial a la cual está asociado el usuario
     * de la seguridad privada.
     * @return el id de la unidad residencial. 
     */
    public String getIdUnidadResidencial() {
        return idUnidadResidencial;
    }

    /**
     * Asigna el id de una unidad residencial.
     * @param idUnidadResidencial 
     */
    public void setIdUnidadResidencial(String idUnidadResidencial) {
        this.idUnidadResidencial = idUnidadResidencial;
    }

}
