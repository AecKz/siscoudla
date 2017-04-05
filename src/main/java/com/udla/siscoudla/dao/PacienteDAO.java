package com.udla.siscoudla.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Paciente;
import com.udla.siscoudla.modelo.Persona;

public class PacienteDAO extends EntityManagerFactoryDAO {
	public Paciente crear(Paciente objeto) {
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

	public Paciente editar(Paciente objeto) {
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

	public Paciente eliminar(Paciente objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Paciente EntidadToBeRemoved = em.getReference(Paciente.class,
					objeto.getIdPaciente());
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

	public List<Paciente> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Paciente> query = em.createQuery(
					"SELECT e FROM Paciente e order by e.nombre", Paciente.class);
			List<Paciente> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Paciente buscarPorId(String id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Paciente paciente = new Paciente();
		try {
			TypedQuery<Paciente> query = em.createQuery(
					"SELECT c FROM Paciente c where c.id = :id ", Paciente.class)
					.setParameter("id", id);
			List<Paciente> results = query.getResultList();
			paciente = results.get(0);
			return paciente;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return paciente;
		} finally {
			em.close();
		}
	}

	public List<Paciente> buscarActivos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Paciente> results = null;
		try {
			TypedQuery<Paciente> query = em.createQuery(
					"SELECT c FROM Paciente c WHERE c.activo =:valorActivo",
					Paciente.class).setParameter("valorActivo", true);
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}
	/**
	 * Buscar paciente por numero de historia
	 * @param numeroHistoria
	 * @return objeto Paciente
	 * */
	public Paciente buscarPorNumeroHistoria(String numeroHistoria) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Paciente paciente = new Paciente();
		try {
			TypedQuery<Paciente> query = em.createQuery(
					"SELECT p FROM Paciente p where p.numeroHistoria = :numeroHistoria ", Paciente.class)
					.setParameter("numeroHistoria", numeroHistoria);
			List<Paciente> results = query.getResultList();
			if(!results.isEmpty()){
				paciente = results.get(0);
			}
			return paciente;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return paciente;
		} finally {
			em.close();
		}
	}
	/**
	 * Buscar paciente por objeto Persona
	 * @param persona
	 * @return objeto Paciente
	 * */
	public Paciente buscarPorPersona(Persona persona) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Paciente paciente = new Paciente();		
		try {
			TypedQuery<Paciente> query = em.createQuery(
					"SELECT p FROM Paciente p "							
					+ "where p.persona = :persona ", Paciente.class)
					.setParameter("persona", persona);
			List<Paciente> results = query.getResultList();
			if(!results.isEmpty()){
				paciente = results.get(0);
			}			
			return paciente;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return paciente;
		} finally {
			em.close();
		}
	}

}
