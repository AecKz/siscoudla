package com.udla.siscoudla.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Horariocubiculoestado;

public class HorarioCubiculoEstadoDAO extends EntityManagerFactoryDAO {
	public Horariocubiculoestado crear(Horariocubiculoestado objeto) {
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

	public Horariocubiculoestado editar(Horariocubiculoestado objeto) {
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

	public Horariocubiculoestado eliminar(Horariocubiculoestado objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Horariocubiculoestado EntidadToBeRemoved = em.getReference(Horariocubiculoestado.class,
					objeto.getIdhorarioCubiculoEstado());
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

	public List<Horariocubiculoestado> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Horariocubiculoestado> query = em.createQuery("SELECT e FROM Horariocubiculoestado e order by e.fecha",
					Horariocubiculoestado.class);
			List<Horariocubiculoestado> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Horariocubiculoestado buscarPorId(String id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Horariocubiculoestado horarioCubiculoEstado = new Horariocubiculoestado();
		try {
			TypedQuery<Horariocubiculoestado> query = em
					.createQuery("SELECT c FROM Horariocubiculoestado c where c.idhorarioCubiculoEstado = :id ", Horariocubiculoestado.class)
					.setParameter("id", id);
			List<Horariocubiculoestado> results = query.getResultList();
			horarioCubiculoEstado = results.get(0);
			return horarioCubiculoEstado;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return horarioCubiculoEstado;
		} finally {
			em.close();
		}
	}	
	/**
	 * Metodo para verificar el estado de un HorarioCubiculoEstado
	 * @param idHorarioCubiculoEstado
	 * @param estado
	 * @return Flag true/false
	 * */
	public Boolean verificarEstado(int idHorarioCubiculoEstado, String estado) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();		
		Boolean flag = false;
		try {
			TypedQuery<Horariocubiculoestado> query = em
					.createQuery("SELECT c.estado FROM Horariocubiculoestado c where c.idhorarioCubiculoEstado = :id "
							+ "and c.estado = :estado", Horariocubiculoestado.class)
					.setParameter("id", idHorarioCubiculoEstado).setParameter("estado", estado);
			List<Horariocubiculoestado> results = query.getResultList();
			if(!results.isEmpty()){
				flag = true;
			}			
			return flag;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return flag;
		} finally {
			em.close();
		}
	}
	/**
	 * Metodo para buscar los cubiculos libres para el horario y especialidad seleccionados
	 * @param fecha
	 * @param idEspecialidad
	 * @param idHorario
	 * @param tipoCubiculo
	 * @return Lista numeros de cubiculos libres
	 * */	
	public List<String> buscarCubiculosLibres(Date fecha, int idEspecialidad, int idHorario, String tipoCubiculo) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<String> results = null;
		try {
			Query query = em
					.createQuery("SELECT c.numero FROM Horariocubiculoestado hce "
							+ "JOIN FETCH hce.horariocubiculo hc "
							+ "JOIN FETCH hc.horario h "
							+ "JOIN FETCH hc.cubiculo c "
							+ "JOIN FETCH c.especialidad e "							
							+ "WHERE hce.fecha =:fecha AND e.idEspecialidad = :idEspecialidad "
							+ "AND h.idHorario =:idHorario AND c.tipo = :tipoCubiculo "
							+ "AND hce.estado = 'LIB' AND hc.estado = 'ACT' AND c.estado = 'ACT'")
					.setParameter("fecha", fecha).setParameter("idEspecialidad", idEspecialidad)
					.setParameter("idHorario", idHorario).setParameter("tipoCubiculo", tipoCubiculo);
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}
	
}
