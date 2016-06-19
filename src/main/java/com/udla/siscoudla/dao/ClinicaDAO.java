package com.udla.siscoudla.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Clinica;

public class ClinicaDAO extends EntityManagerFactoryDAO {
	public Clinica crear(Clinica objeto) {
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

	public Clinica editar(Clinica objeto) {
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

	public Clinica eliminar(Clinica objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Clinica EntidadToBeRemoved = em.getReference(Clinica.class,
					objeto.getIdClinica());
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

	public List<Clinica> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Clinica> query = em.createQuery(
					"SELECT e FROM Clinica e order by e.nombre", Clinica.class);
			List<Clinica> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Clinica buscarPorId(int id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Clinica clinica = new Clinica();
		try {
			TypedQuery<Clinica> query = em.createQuery(
					"SELECT c FROM Clinica c where c.idClinica = :id ", Clinica.class)
					.setParameter("id", id);
			List<Clinica> results = query.getResultList();
			clinica = results.get(0);
			return clinica;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return clinica;
		} finally {
			em.close();
		}
	}

	public List<Clinica> buscarActivos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Clinica> results = null;
		try {
			TypedQuery<Clinica> query = em.createQuery(
					"SELECT c FROM Clinica c WHERE c.activo =:valorActivo",
					Clinica.class).setParameter("valorActivo", true);
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}

}
