
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import entidad.UnidadResidencial;
import java.util.logging.Level;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author jd.trujillom
 */
public class UnidadResidencialPersistence extends Persistencer<UnidadResidencial, Long> {

    public UnidadResidencialPersistence() {
        this.entityClass = UnidadResidencial.class;
    }

    public UnidadResidencial find(String nombre) {
        UnidadResidencial entity;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.nombre = :nombre1";
        Query query = entityManager.createQuery(queryString).setParameter("nombre1", nombre);
        try {
            entity = (UnidadResidencial) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            entity = null;
        }
        return entity;
    }

    public void delete(String nombre) {
        UnidadResidencial entity;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.nombre = :nombre1";
        Query query = entityManager.createQuery(queryString).setParameter("nombre1", nombre);
        try {
            entity = (UnidadResidencial) query.getSingleResult();
            if (entity != null) {
                entityManager.remove(entity);
            }
        } catch (NoResultException | NonUniqueResultException e) {
            entity = null;
        }
    }

}
