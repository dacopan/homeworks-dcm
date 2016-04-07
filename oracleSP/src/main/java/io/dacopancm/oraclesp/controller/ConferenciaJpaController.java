/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.dacopancm.oraclesp.controller;

import io.dacopancm.oraclesp.controller.exceptions.NonexistentEntityException;
import io.dacopancm.oraclesp.controller.exceptions.PreexistingEntityException;
import io.dacopancm.oraclesp.model.Conferencia;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author dacopan
 */
public class ConferenciaJpaController implements Serializable {

    public ConferenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Conferencia conferencia) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(conferencia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConferencia(conferencia.getCfrId()) != null) {
                throw new PreexistingEntityException("Conferencia " + conferencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Conferencia conferencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            conferencia = em.merge(conferencia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = conferencia.getCfrId();
                if (findConferencia(id) == null) {
                    throw new NonexistentEntityException("The conferencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Conferencia conferencia;
            try {
                conferencia = em.getReference(Conferencia.class, id);
                conferencia.getCfrId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conferencia with id " + id + " no longer exists.", enfe);
            }
            em.remove(conferencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Conferencia> findConferenciaEntities() {
        return findConferenciaEntities(true, -1, -1);
    }

    public List<Conferencia> findConferenciaEntities(int maxResults, int firstResult) {
        return findConferenciaEntities(false, maxResults, firstResult);
    }

    private List<Conferencia> findConferenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Conferencia.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Conferencia findConferencia(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Conferencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getConferenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Conferencia> rt = cq.from(Conferencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
