package com.udla.siscoudla.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.udla.siscoudla.dao.RolDAO;
import com.udla.siscoudla.modelo.Rol;

/**
 * Servlet implementation class RegistroTurnosController
 */
@WebServlet("/RegistroTurnosController")
public class RegistroTurnosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistroTurnosController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");

			if (tipoConsulta.equals("cargarDatosWizard")) {
				//Comprueba si el rol del usuario es Estudiante
				if(verificarRolEstudiante(request)){
					//Si se verifica el rol, traemos los horarios que le pertenecen
				}

			}
			result.put("success", Boolean.TRUE);
			response.setContentType("application/json; charset=UTF-8");
			result.write(response.getWriter());
		} catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("error", e.getLocalizedMessage());
			response.setContentType("application/json; charset=UTF-8");
			result.write(response.getWriter());
			e.printStackTrace();
		}
	}

	public Boolean verificarRolEstudiante(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String valorUsuario = session.getAttribute("login").toString();
		RolDAO rolDAO = new RolDAO();
		Boolean esEstudiante = false;
		List<Rol> lista = rolDAO.buscarRolPorUsuario(valorUsuario);
		for (Rol rol : lista) {
			if (rol.getNombre().equals("Estudiante")) {
				esEstudiante = true;
			}
		}
		return esEstudiante;
	}
}
