/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import entidad.Inmueble;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author jd.trujillom
 */
public class InmueblePersistence extends Persistencer<Inmueble, String> {

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

    public Inmueble inmueblesOfUnidadResidencial(String idUnidadResidencial, int torre, int apartamento) {
        Inmueble entity;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.torre = :torre1 AND c.apartamento = :apartamento1 AND c.unidadResidencial = :idUnidadResidencial1";
        Query query = entityManager.createQuery(queryString).setParameter("torre1", torre).setParameter("apartamento1", apartamento).setParameter("idUnidadResidencial1", idUnidadResidencial);
        try {
            System.out.println(query.toString());
            entity = (Inmueble) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            entity = null;
        }
        return entity;
    }
    
    public List<Inmueble> inmueblesOfUnidadResidencial(String idInmueble) {
        List<Inmueble> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.unidadResidencial = :idInmueble1";

        Query query = entityManager.createNamedQuery(queryString).setParameter("idnmueble1", idInmueble);
        try {
            entities = (List<Inmueble>) query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
        }
        return entities;
    }
}
