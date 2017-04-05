package com.udla.siscoudla.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Estudiante;
import com.udla.siscoudla.modelo.Persona;

public class EstudianteDAO extends EntityManagerFactoryDAO {
	public Estudiante crear(Estudiante objeto) {
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

	public Estudiante editar(Estudiante objeto) {
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

	public Estudiante eliminar(Estudiante objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Estudiante EntidadToBeRemoved = em.getReference(Estudiante.class,
					objeto.getIdEstudiante());
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

	public List<Estudiante> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Estudiante> query = em.createQuery(
					"SELECT e FROM Estudiante e order by e.nombre", Estudiante.class);
			List<Estudiante> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Estudiante buscarPorId(String id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Estudiante estudiante = new Estudiante();
		try {
			TypedQuery<Estudiante> query = em.createQuery(
					"SELECT c FROM Estudiante c where c.id = :id ", Estudiante.class)
					.setParameter("id", id);
			List<Estudiante> results = query.getResultList();
			estudiante = results.get(0);
			return estudiante;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return estudiante;
		} finally {
			em.close();
		}
	}

	public List<Estudiante> buscarActivos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Estudiante> results = null;
		try {
			TypedQuery<Estudiante> query = em.createQuery(
					"SELECT c FROM Estudiante c WHERE c.activo =:valorActivo",
					Estudiante.class).setParameter("valorActivo", true);
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}
	public Estudiante buscarPorPersona(Persona persona) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Estudiante estudiante = new Estudiante();
		try {
			TypedQuery<Estudiante> query = em.createQuery(
					"SELECT e FROM Estudiante e "
					+ "JOIN FETCH e.persona p "													
					+ "where e.persona = :persona and e.estado = 'ACT'", Estudiante.class)
					.setParameter("persona", persona);
			List<Estudiante> results = query.getResultList();
			estudiante = results.get(0);
			return estudiante;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return estudiante;
		} finally {
			em.close();
		}
	}

}
