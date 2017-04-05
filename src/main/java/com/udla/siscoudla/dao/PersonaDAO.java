package com.udla.siscoudla.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Persona;

public class PersonaDAO extends EntityManagerFactoryDAO {
	public Persona crear(Persona objeto) {
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

	public Persona editar(Persona objeto) {
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

	public Persona eliminar(Persona objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Persona EntidadToBeRemoved = em.getReference(Persona.class,
					objeto.getIdPersona());
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

	public List<Persona> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Persona> query = em.createQuery(
					"SELECT e FROM Persona e order by e.nombre", Persona.class);
			List<Persona> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Persona buscarPorId(String id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Persona persona = new Persona();
		try {
			TypedQuery<Persona> query = em.createQuery(
					"SELECT c FROM Persona c where c.id = :id ", Persona.class)
					.setParameter("id", id);
			List<Persona> results = query.getResultList();
			persona = results.get(0);
			return persona;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return persona;
		} finally {
			em.close();
		}
	}

	public List<Persona> buscarActivos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Persona> results = null;
		try {
			TypedQuery<Persona> query = em.createQuery(
					"SELECT c FROM Persona c WHERE c.activo =:valorActivo",
					Persona.class).setParameter("valorActivo", true);
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}
	
	public Persona buscarPorEmail(String email) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Persona persona = new Persona();
		try {
			TypedQuery<Persona> query = em.createQuery(
					"SELECT p FROM Persona p where p.email = :email ", Persona.class)
					.setParameter("email", email);
			List<Persona> results = query.getResultList();
			if(!results.isEmpty()){
				persona = results.get(0);
			}
			return persona;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return persona;
		} finally {
			em.close();
		}
	}
	public Persona buscarPorUsuario(String usuario) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Persona persona = new Persona();		
		try {
			TypedQuery<Persona> query = em.createQuery(
					"SELECT p FROM Usuario u "
					+ "JOIN FETCH u.persona p "		
					+ "where u.usuario = :usuario ", Persona.class)
					.setParameter("usuario", usuario);
			List<Persona> results = query.getResultList();
			if(!results.isEmpty()){
				persona = results.get(0);
			}
			
			return persona;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return persona;
		} finally {
			em.close();
		}
	}

}
