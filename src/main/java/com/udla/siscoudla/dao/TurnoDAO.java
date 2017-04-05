package com.udla.siscoudla.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Turno;

public class TurnoDAO extends EntityManagerFactoryDAO {
	public Turno crear(Turno objeto) {
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

	public Turno editar(Turno objeto) {
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

	public Turno eliminar(Turno objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Turno EntidadToBeRemoved = em.getReference(Turno.class,
					objeto.getIdTurno());
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

	public List<Turno> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Turno> query = em.createQuery(
					"SELECT t FROM Turno t order by t.fecha desc", Turno.class);
			List<Turno> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}
	
	public Turno buscarPorId(int idTurno) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Turno turno = new Turno();
		try {
			TypedQuery<Turno> query = em.createQuery(
					"SELECT t FROM Turno t where t.idTurno = :idTurno ", Turno.class)
					.setParameter("idTurno", idTurno);
			List<Turno> results = query.getResultList();
			if(!results.isEmpty()){
				turno = results.get(0);
			}			
			return turno;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return turno;
		} finally {
			em.close();
		}
	}
	/**
	 * Metodo para buscar Turnos por Estudiante
	 * @param idEstudiante
	 * @param estado del turno
	 * @return Lista de objetos Turno
	 * */
	public List<Turno> buscarReservadosPorEstudiante(int idEstudiante, String estado) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Turno> results = null;
		try {
			TypedQuery<Turno> query = em.createQuery(
					"SELECT t FROM Turno t "
					+ "JOIN FETCH t.horariocubiculoestado hce "
					+ "JOIN FETCH t.horarioestudiante he "
					+ "JOIN FETCH t.tratamiento tr "
					+ "JOIN FETCH t.paciente pa "										
					+ "JOIN FETCH he.estudiante e "
					+ "JOIN FETCH hce.horariocubiculo hc "
					+ "JOIN FETCH hc.cubiculo c "
					+ "JOIN FETCH he.horario h "
					+ "JOIN FETCH pa.persona p "
					+ "WHERE t.estado =:estado AND e.idEstudiante = :idEstudiante",
					Turno.class).setParameter("estado", estado).setParameter("idEstudiante", idEstudiante);
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}
	/**
	 * Metodo para buscar Turnos por Estudiante
	 * @param idEstudiante
	 * @param estado del turno
	 * @return Lista de objetos Turno
	 * */
	public List<Turno> buscarTodosPorEstudiante(int idEstudiante) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Turno> results = null;
		try {
			TypedQuery<Turno> query = em.createQuery(
					"SELECT t FROM Turno t "
					+ "JOIN FETCH t.horariocubiculoestado hce "
					+ "JOIN FETCH t.horarioestudiante he "
					+ "JOIN FETCH t.tratamiento tr "
					+ "JOIN FETCH t.paciente pa "										
					+ "JOIN FETCH he.estudiante e "
					+ "JOIN FETCH hce.horariocubiculo hc "
					+ "JOIN FETCH hc.cubiculo c "
					+ "JOIN FETCH he.horario h "
					+ "JOIN FETCH pa.persona p "
					+ "WHERE e.idEstudiante = :idEstudiante",
					Turno.class).setParameter("idEstudiante", idEstudiante);
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}
	
	/**
	 * Metodo para buscar Turnos por Estado
	 * @param estado del turno
	 * @return Lista de objetos Turno
	 * */
	public List<Turno> buscarReservadosPorEstado(String estado) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Turno> results = null;
		try {
			TypedQuery<Turno> query = em.createQuery(
					"SELECT t FROM Turno t "
					+ "JOIN FETCH t.horariocubiculoestado hce "
					+ "JOIN FETCH t.horarioestudiante he "
					+ "JOIN FETCH t.tratamiento tr "
					+ "JOIN FETCH t.paciente pa "										
					+ "JOIN FETCH he.estudiante e "
					+ "JOIN FETCH hce.horariocubiculo hc "
					+ "JOIN FETCH hc.cubiculo c "
					+ "JOIN FETCH he.horario h "
					+ "JOIN FETCH pa.persona p "
					+ "WHERE t.estado =:estado ",
					Turno.class).setParameter("estado", estado);
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}
	
	/**
	 * Metodo para buscar Turnos por Reservados por Horario (Tomar Lista)
	 * @param idHorario del turno
	 * @return Lista de objetos Turno
	 * */
	public List<Turno> buscarReservadosPorHorario(int idHorario, Date fecha) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Turno> results = null;
		try {
			TypedQuery<Turno> query = em.createQuery(
					"SELECT t FROM Turno t "
					+ "JOIN FETCH t.horariocubiculoestado hce "
					+ "JOIN FETCH t.horarioestudiante he "
					+ "JOIN FETCH t.tratamiento tr "
					+ "JOIN FETCH t.paciente pa "										
					+ "JOIN FETCH he.estudiante e "
					+ "JOIN FETCH hce.horariocubiculo hc "
					+ "JOIN FETCH hc.cubiculo c "
					+ "JOIN FETCH he.horario h "
					+ "JOIN FETCH pa.persona p "
					+ "WHERE t.estado =:estado "
					+ "AND h.idHorario = :idHorario "
					+ "AND t.fecha =:fecha",
					Turno.class).setParameter("estado", "RES")
					.setParameter("idHorario", idHorario)
					.setParameter("fecha", fecha);
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}

}
