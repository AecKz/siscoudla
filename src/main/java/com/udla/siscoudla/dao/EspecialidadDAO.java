package com.udla.siscoudla.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Especialidad;

public class EspecialidadDAO extends EntityManagerFactoryDAO {
	public Especialidad crear(Especialidad objeto) {
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

	public Especialidad editar(Especialidad objeto) {
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

	public Especialidad eliminar(Especialidad objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Especialidad EntidadToBeRemoved = em.getReference(Especialidad.class,
					objeto.getIdEspecialidad());
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

	public List<Especialidad> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Especialidad> query = em.createQuery(
					"SELECT e FROM Especialidad e order by e.nombre", Especialidad.class);
			List<Especialidad> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Especialidad buscarPorId(int id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Especialidad especialidad = new Especialidad();
		try {
			TypedQuery<Especialidad> query = em.createQuery(
					"SELECT e FROM Especialidad e where e.idEspecialidad = :id ", Especialidad.class)
					.setParameter("id", id);
			List<Especialidad> results = query.getResultList();
			especialidad = results.get(0);
			return especialidad;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return especialidad;
		} finally {
			em.close();
		}
	}

	public List<Especialidad> buscarActivos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Especialidad> results = null;
		try {
			TypedQuery<Especialidad> query = em.createQuery(
					"SELECT e FROM Especialidad e WHERE e.estado =:valorActivo",
					Especialidad.class).setParameter("valorActivo", "ACT");
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}
	/**
	 * Metodo que devuelve la especialidad a la que pertenece el tratamiento
	 * @param idTratamiento
	 * @return Objeto Especialidad
	 * */
	public Especialidad buscarEspecialidadTratamiento(int idTratamiento) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Especialidad results = new Especialidad();
		try {
			TypedQuery<Especialidad> query = em.createQuery(
					"SELECT e FROM Tratamiento t JOIN FETCH t.especialidad e WHERE t.idTratamiento =:idTratamiento AND t.estado = :valorEstado",
					Especialidad.class).setParameter("idTratamiento", idTratamiento).setParameter("valorEstado", "ACT");
			List <Especialidad> lista = query.getResultList();
			if(!lista.isEmpty()){
				results = lista.get(0);
			}			
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}

}
