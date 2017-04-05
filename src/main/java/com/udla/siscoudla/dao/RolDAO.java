package com.udla.siscoudla.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Rol;

public class RolDAO extends EntityManagerFactoryDAO {
	public Rol crear(Rol objeto) {
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

	public Rol editar(Rol objeto) {
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

	public Rol eliminar(Rol objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Rol RolToBeRemoved = em.getReference(Rol.class,
					objeto.getIdRol());
			em.remove(RolToBeRemoved);
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

	public List<Rol> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Rol> query = em.createQuery(
					"SELECT e FROM Rol e order by e.nombre", Rol.class);
			List<Rol> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Rol buscarPorId(String id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Rol rol = new Rol();
		try {
			TypedQuery<Rol> query = em.createQuery(
					"SELECT c FROM Rol c where c.id = :id ", Rol.class)
					.setParameter("id", id);
			List<Rol> results = query.getResultList();
			rol = results.get(0);
			return rol;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return rol;
		} finally {
			em.close();
		}
	}

	public List<Rol> buscarRolPorUsuario(String valorUsuario) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Rol> results = null;
		try {
			TypedQuery<Rol> query = em.createQuery(
					"SELECT r FROM Usuario u JOIN FETCH u.rol r WHERE u.usuario =:valorUsuario AND u.estado = :valorEstado",
					Rol.class).setParameter("valorUsuario", valorUsuario).setParameter("valorEstado", "ACT");
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}

}
