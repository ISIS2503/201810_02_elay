/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;


import java.util.List;

/**
 *
 * @author ma.forero11
 */
public class User {
    
    private String id;
    
    private String correo;
    
    private String nombre;
    
    private String usuario;
    
    private String contrasenia;
    
    private String fechaNacimiento;
    
    private List<String> roles;

    public User(String id, String correo, String nombre, String usuario, String contrasenia, String fechaNacimiento, List<String> roles) {
        this.id = id;
        this.correo = correo;
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.fechaNacimiento = fechaNacimiento;
        this.roles = roles;
    }

   
    
    public User(){
        //Empty constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

     public String getContraseña() {
        return contrasenia;
    }

    public void setContraseña(String contraseña) {
        this.contrasenia = contraseña;
    }
    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
    
  //  @Override
   // public String toString() {
   //     return "entidad.Hub[ id=" + id + " ]";
    //}
    
}
