/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import entidad.Inmueble;

/**
 *
 * @author jd.trujillom
 */
public class InmueblePersistence extends Persistencer<Inmueble, Long> {

    public InmueblePersistence() {
    this.entityClass = Inmueble.class;
        
    }
    
    
    
}
