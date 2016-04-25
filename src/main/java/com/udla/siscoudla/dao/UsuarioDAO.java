package com.udla.siscoudla.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.udla.siscoudla.entitymanagerfactory.EntityManagerFactoryDAO;
import com.udla.siscoudla.modelo.Usuario;

public class UsuarioDAO extends EntityManagerFactoryDAO {
	public Usuario crear(Usuario objeto) {
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

	public Usuario editar(Usuario objeto) {
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

	public Usuario eliminar(Usuario objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Usuario EntidadToBeRemoved = em.getReference(Usuario.class,
					objeto.getIdUsuario());
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

	public List<Usuario> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Usuario> query = em.createQuery(
					"SELECT e FROM Usuario e order by e.nombre", Usuario.class);
			List<Usuario> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Usuario buscarPorId(String id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Usuario usuario = new Usuario();
		try {
			TypedQuery<Usuario> query = em.createQuery(
					"SELECT c FROM Usuario c where c.id = :id ", Usuario.class)
					.setParameter("id", id);
			List<Usuario> results = query.getResultList();
			usuario = results.get(0);
			return usuario;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return usuario;
		} finally {
			em.close();
		}
	}
	
	public Boolean login (String usuarioLogin, String contrasenaLogin) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Boolean flagLogin = false;
		try {
			TypedQuery<Usuario> query = em.createQuery(
					"SELECT u.idUsuario FROM Usuario u where u.usuario = :usuarioLogin and u.password = :contrasenaLogin "
					+ "and u.estado = 'ACT'", Usuario.class)
					.setParameter("usuarioLogin", usuarioLogin).setParameter("contrasenaLogin", contrasenaLogin);
			List<Usuario> results = query.getResultList();
			if(!results.isEmpty()){
				flagLogin = true;
			}else{
				flagLogin = false;
			}
			return flagLogin;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return flagLogin;
		} finally {
			em.close();
		}
	}

	public List<Usuario> buscarActivos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		List<Usuario> results = null;
		try {
			TypedQuery<Usuario> query = em.createQuery(
					"SELECT c FROM Usuario c WHERE c.activo =:valorActivo",
					Usuario.class).setParameter("valorActivo", true);
			results = query.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return results;
	}

}
