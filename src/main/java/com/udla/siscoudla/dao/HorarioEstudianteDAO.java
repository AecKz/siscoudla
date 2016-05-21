package com.udla.siscoudla.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Estudiante;
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
	public Horarioestudiante buscarPorIdHorarioYEstudiante(int idHorario, Estudiante estudiante) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Horarioestudiante horarioEstudiante = new Horarioestudiante();
		try {
			TypedQuery<Horarioestudiante> query = em
					.createQuery("SELECT he FROM Horarioestudiante he "
							+ "JOIN FETCH he.horario h "
							+ "JOIN FETCH he.estudiante e "
							+ "where h.idHorario = :idHorario "
							+ "and he.estudiante = :estudiante ", Horarioestudiante.class)
					.setParameter("idHorario", idHorario).setParameter("estudiante", estudiante);
			List<Horarioestudiante> results = query.getResultList();
			if(!results.isEmpty()){
				horarioEstudiante = results.get(0);
			}			
			return horarioEstudiante;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return horarioEstudiante;
		} finally {
			em.close();
		}
	}
	/**
	 * Buscar idHorario por idEstudiante y el nombre del dia
	 * @param diaNombre
	 * @param idEstudiante
	 * @return idHorario 
	 * */
	public int buscarPorDiaEstudiante(String diaNombre, int idEstudiante) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		int resultado = 0;
		try {
			TypedQuery<Horarioestudiante> query = em
					.createQuery("SELECT he FROM Horarioestudiante he "
							+ "JOIN FETCH he.horario h "
							+ "JOIN FETCH he.estudiante e "
							+ "where h.dia = :diaNombre and e.idEstudiante = :idEstudiante", Horarioestudiante.class)
					.setParameter("idEstudiante", idEstudiante).setParameter("diaNombre", diaNombre);
			List<Horarioestudiante> results = query.getResultList();
			resultado = results.get(0).getHorario().getIdHorario();
			return resultado;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return resultado;
		} finally {
			em.close();
		}
	}
}
