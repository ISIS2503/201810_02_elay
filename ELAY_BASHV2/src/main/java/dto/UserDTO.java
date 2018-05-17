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
    
    public String correo;

    public String fechaNacimiento;

    public String usuario;
    
    public String contraseña;

    public String nombre;

    public List<String> roles;

    public UserDTO(String correo, String fechaNacimiento, String usuario, String contraseña, String nombre, List<String> roles) {
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.roles = roles;
    }


    public UserDTO(User entity){
       this.correo = entity.getCorreo();
        this.fechaNacimiento = entity.getFechaNacimiento();
        this.usuario = entity.getUsuario();
        this.contraseña = contraseña;
        this.nombre = entity.getNombre();
        this.roles = entity.getRoles();
    }
    
    public UserDTO(){
        //Empty constructor
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String id) {
        this.correo = correo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
     public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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
        user.setCorreo(this.correo);
        user.setFechaNacimiento(this.fechaNacimiento);
        user.setNombre(this.nombre);
        user.setUsuario(this.usuario);
        user.setContraseña(this.contraseña);
        return user;
    }
    
    public void toDTO(User user){
        this.correo = user.getCorreo();
        this.fechaNacimiento = user.getFechaNacimiento();
        this.nombre = user.getNombre();
        this.usuario = user.getUsuario();
        this.roles = user.getRoles();
        this.contraseña = user.getContraseña();
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
