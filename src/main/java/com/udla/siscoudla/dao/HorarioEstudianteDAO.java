package com.udla.siscoudla.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Horarioestudiante;

public class HorarioEstudianteDAO extends EntityManagerFactoryDAO {
	public Horarioestudiante crear(Horarioestudiante objeto) {
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

	public Horarioestudiante editar(Horarioestudiante objeto) {
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

	public Horarioestudiante eliminar(Horarioestudiante objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Horarioestudiante EntidadToBeRemoved = em.getReference(Horarioestudiante.class,
					objeto.getIdhorarioEstudiante());
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

	public List<Horarioestudiante> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Horarioestudiante> query = em.createQuery("SELECT e FROM Horarioestudiante e order by e.nombre",
					Horarioestudiante.class);
			List<Horarioestudiante> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Horarioestudiante buscarPorId(String id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Horarioestudiante horarioEstudiante = new Horarioestudiante();
		try {
			TypedQuery<Horarioestudiante> query = em
					.createQuery("SELECT c FROM Horarioestudiante c where c.id = :id ", Horarioestudiante.class)
					.setParameter("id", id);
			List<Horarioestudiante> results = query.getResultList();
			horarioEstudiante = results.get(0);
			return horarioEstudiante;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return horarioEstudiante;
		} finally {
			em.close();
		}
	}	
}
