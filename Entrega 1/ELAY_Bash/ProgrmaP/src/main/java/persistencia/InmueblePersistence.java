/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import entidad.Inmueble;
import entidad.UnidadResidencial;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author jd.trujillom
 */
public class InmueblePersistence extends Persistencer<Inmueble, Long> {

    public InmueblePersistence() {
        this.entityClass = Inmueble.class;

    }

    public Inmueble find(String nombre) {
        Inmueble entity;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.nombre = :nombre1";
        Query query = entityManager.createQuery(queryString).setParameter("nombre1", nombre);
        try {
            entity = (Inmueble) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            entity = null;
        }
        return entity;
    }

    public Inmueble findInmuebleByTorreAndApartamento(int torre, int apartamento) {
        Inmueble entity;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.torre = :torre1 AND c.apartamento = :apartamento1";
        Query query = entityManager.createQuery(queryString).setParameter("torre1", torre).setParameter("apartamento1", apartamento);
        try {
            entity = (Inmueble) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            entity = null;
        }
        return entity;
    }

}
