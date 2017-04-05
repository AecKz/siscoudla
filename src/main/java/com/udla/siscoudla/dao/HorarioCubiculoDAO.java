package com.udla.siscoudla.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Horariocubiculo;

public class HorarioCubiculoDAO extends EntityManagerFactoryDAO {
	public Horariocubiculo crear(Horariocubiculo objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(objeto);
			em.flush();
			em.getTransaction().commit();

			return objeto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return objeto;
		} finally {
			em.close();
		}
	}

	public Horariocubiculo editar(Horariocubiculo objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(objeto);
			em.getTransaction().commit();
			return objeto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			em.getTransaction().rollback();
			return objeto;
		} finally {
			em.close();
		}
	}

	public Horariocubiculo eliminar(Horariocubiculo objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Horariocubiculo EntidadToBeRemoved = em.getReference(Horariocubiculo.class,
					objeto.getIdhorarioCubiculo());
			em.remove(EntidadToBeRemoved);
			em.getTransaction().commit();
			return objeto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return objeto;
		} finally {
			em.close();
		}
	}

	public List<Horariocubiculo> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Horariocubiculo> query = em.createQuery("SELECT e FROM Horariocubiculo e order by e.nombre",
					Horariocubiculo.class);
			List<Horariocubiculo> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Horariocubiculo buscarPorId(String id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Horariocubiculo horarioCubiculo = new Horariocubiculo();
		try {
			TypedQuery<Horariocubiculo> query = em
					.createQuery("SELECT c FROM Horariocubiculo c where c.id = :id ", Horariocubiculo.class)
					.setParameter("id", id);
			List<Horariocubiculo> results = query.getResultList();
			horarioCubiculo = results.get(0);
			return horarioCubiculo;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return horarioCubiculo;
		} finally {
			em.close();
		}
	}	
}
