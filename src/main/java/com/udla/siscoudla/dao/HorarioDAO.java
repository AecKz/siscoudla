package com.udla.siscoudla.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Estudiante;
import com.udla.siscoudla.modelo.Horario;
import com.udla.siscoudla.util.Utilitarios;

public class HorarioDAO extends EntityManagerFactoryDAO {
	public Horario crear(Horario objeto) {
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

	public Horario editar(Horario objeto) {
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

	public Horario eliminar(Horario objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Horario EntidadToBeRemoved = em.getReference(Horario.class,
					objeto.getIdHorario());
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

	public List<Horario> buscarTodosActivos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Horario> query = em.createQuery("SELECT h FROM Horario h where h.estado ='ACT' order by h.idHorario",
					Horario.class);
			List<Horario> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}
	
	public List<Horario> buscarActivosPorFecha() {
		Date fecha = new Date();
		int diaFecha = fecha.getDay();
		String diaNombre = Utilitarios.buscarDia(diaFecha);		
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Horario> query = em.createQuery("SELECT h FROM Horario h "
					+ "where h.estado ='ACT' and h.dia =:diaNombre order by h.idHorario",
					Horario.class).setParameter("diaNombre", diaNombre);
			List<Horario> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Horario buscarPorId(int idHorario) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Horario horario = new Horario();
		try {
			TypedQuery<Horario> query = em
					.createQuery("SELECT h FROM Horario h where h.idHorario = :idHorario ", Horario.class)
					.setParameter("idHorario", idHorario);
			List<Horario> results = query.getResultList();
			if(!results.isEmpty()){
				horario = results.get(0);
			}			
			return horario;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return horario;
		} finally {
			em.close();
		}
	}

	/**
	 * Metodo para buscar los horarios registrados para el estudiante
	 * @param estudiante
	 * @return Lista de objetos horario
	 * */
	public List<Horario> buscarHorariosEstudiante(Estudiante estudiante) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Horario> results = null;
		try {
			TypedQuery<Horario> query = em
					.createQuery("SELECT h FROM Horarioestudiante he JOIN FETCH he.horario h WHERE he.estudiante =:estudiante AND he.estado = :valorEstado",
							Horario.class)
					.setParameter("estudiante", estudiante).setParameter("valorEstado", "ACT");
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}

}
