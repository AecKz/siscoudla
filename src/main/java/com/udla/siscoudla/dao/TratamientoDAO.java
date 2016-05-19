package com.udla.siscoudla.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Tratamiento;

public class TratamientoDAO extends EntityManagerFactoryDAO {
	public Tratamiento crear(Tratamiento objeto) {
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

	public Tratamiento editar(Tratamiento objeto) {
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

	public Tratamiento eliminar(Tratamiento objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Tratamiento EntidadToBeRemoved = em.getReference(Tratamiento.class,
					objeto.getIdTratamiento());
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

	public List<Tratamiento> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Tratamiento> query = em.createQuery(
					"SELECT e FROM Tratamiento e order by e.nombre", Tratamiento.class);
			List<Tratamiento> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Tratamiento buscarPorId(int idTratamiento) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Tratamiento tratamiento = new Tratamiento();
		try {
			TypedQuery<Tratamiento> query = em.createQuery(
					"SELECT t FROM Tratamiento t where t.idTratamiento = :idTratamiento ", Tratamiento.class)
					.setParameter("idTratamiento", idTratamiento);
			List<Tratamiento> results = query.getResultList();
			if(!results.isEmpty()){
				tratamiento = results.get(0);
			}			
			return tratamiento;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return tratamiento;
		} finally {
			em.close();
		}
	}

	public List<Tratamiento> buscarActivos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Tratamiento> results = null;
		try {
			TypedQuery<Tratamiento> query = em.createQuery(
					"SELECT c FROM Tratamiento c WHERE c.activo =:valorActivo",
					Tratamiento.class).setParameter("valorActivo", true);
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}
	
	public List<Tratamiento> buscarTratamientosEspecialidad(int idEspecialidad) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Tratamiento> results = null;
		try {
			TypedQuery<Tratamiento> query = em.createQuery(
					"SELECT t FROM Tratamiento t JOIN FETCH t.especialidad e WHERE e.idEspecialidad =:idEspecialidad AND e.estado = :valorEstado",
					Tratamiento.class).setParameter("idEspecialidad", idEspecialidad).setParameter("valorEstado", "ACT");
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}

}
