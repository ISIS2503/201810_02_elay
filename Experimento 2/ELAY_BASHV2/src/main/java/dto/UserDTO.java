/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entidad.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ma.forero11
 */
public class UserDTO {
    
    public String id;
    
    public String correo;

    public String fechaNacimiento;

    public String usuario;
    
    public String contrasenia;

    public String nombre;

    public List<String> roles;

    public UserDTO(String id, String correo, String fechaNacimiento, String usuario, String contrasenia, String nombre, List<String> roles) {
        this.id = id;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.roles = roles;
        
    }


    public UserDTO(User entity){
        this.id = entity.getId();
       this.correo = entity.getCorreo();
        this.fechaNacimiento = entity.getFechaNacimiento();
        this.usuario = entity.getUsuario();
        this.contrasenia = entity.getContraseña();
        this.nombre = entity.getNombre();
        this.roles = entity.getRoles();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    
    
    public UserDTO(){
        //Empty constructor
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
     public String getContraseña() {
        return contrasenia;
    }

    public void setContraseña(String contraseña) {
        this.contrasenia = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public User toEntity(){
        User user = new User();
        user.setId(this.id);
        user.setCorreo(this.correo);
        user.setFechaNacimiento(this.fechaNacimiento);
        user.setNombre(this.nombre);
        user.setUsuario(this.usuario);
        user.setContraseña(this.contrasenia);
        return user;
    }
    
    public void toDTO(User user){
        this.id = user.getId();
        this.correo = user.getCorreo();
        this.fechaNacimiento = user.getFechaNacimiento();
        this.nombre = user.getNombre();
        this.usuario = user.getUsuario();
        this.roles = user.getRoles();
        this.contrasenia = user.getContraseña();
    }

    @Override
    public String toString() {
        return "UserDTO{" + "correo=" + correo + ", fechaNacimiento=" + fechaNacimiento + ", usuario=" + usuario + ", nombre=" + nombre + ", roles=" + roles + '}';
    }
    
//     private List<String> toDTOUserList(List<User> entidades) {
//        List<String> lista = null;
//        if (entidades != null) {
//            lista = new ArrayList<>();
//            for (User usuario : entidades) {
//                UserDTO nuevo = new UserDTO(usuario);
//                lista.add(nuevo);
//            }
//        }
//
//        return lista;
//    }

    private List<User> toEntityUserList(List<UserDTO> list) {
        List<User> lista = null;
        if (list != null) {
            lista = new ArrayList<>();
            for (UserDTO user : list) {
                lista.add(user.toEntity());
            }
        }
        return lista;
    }
}
