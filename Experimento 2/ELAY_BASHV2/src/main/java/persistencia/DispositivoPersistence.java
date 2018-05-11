/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import entidad.Dispositivo;

/**
 *
 * @author jd.trujillom
 */
public class DispositivoPersistence extends  Persistencer<Dispositivo, String> {
    
    public DispositivoPersistence(){
        this.entityClass = Dispositivo.class;
    }
    
}
