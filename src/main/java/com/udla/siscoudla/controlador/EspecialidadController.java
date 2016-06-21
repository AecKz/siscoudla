package com.udla.siscoudla.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.udla.siscoudla.dao.EspecialidadDAO;
import com.udla.siscoudla.dao.PersonaDAO;
import com.udla.siscoudla.modelo.Especialidad;
import com.udla.siscoudla.modelo.Persona;
import com.udla.siscoudla.util.Utilitarios;

/**
 * Servlet implementation class PersonaController
 */
@WebServlet("/EspecialidadController")
public class EspecialidadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EspecialidadController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		String valorUsuario = session.getAttribute("login").toString();
		String valorRol = session.getAttribute("rol").toString();
		if (valorRol.equals("Administrador")) {
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");
			String idEspecialidad = request.getParameter("codigo") == null ? ""
					: request.getParameter("codigo");
			String nombre = request.getParameter("nombre") == null ? ""
					: request.getParameter("nombre").toUpperCase();
			String descripcion = request.getParameter("descripcion") == null ? ""
					: request.getParameter("descripcion").toUpperCase();			

			JSONObject entidadJSONObject = new JSONObject();
			JSONArray entidadJSONArray = new JSONArray();

			Especialidad especialidad = new Especialidad();
			EspecialidadDAO especialidadDAO = new EspecialidadDAO();

			if (!idEspecialidad.equals("")){
				especialidad.setIdEspecialidad(Integer.parseInt(idEspecialidad));
			}
			if (!nombre.equals("")){
				especialidad.setNombre(nombre);
			}
			if (!descripcion.equals("")){
				especialidad.setDescripcion(descripcion);
			}
			
			if (tipoConsulta.equals("cargarDatosMenus")) {
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
					PersonaDAO personaDAO = new PersonaDAO();
					Persona persona = new Persona();
					persona = personaDAO.buscarPorEmail(valorUsuario);
					String nombreCompleto = persona.getNombres() + " " + persona.getApellidos();
					result.put("nombreCompleto", nombreCompleto);
				}
			}
			
			if (tipoConsulta.equals("encontrarTodos")) {
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
				List<Especialidad> results = especialidadDAO.buscarActivos();
				int i = 0;
				for (i = 0; i < results.size(); i++) {
					especialidad = results.get(i);
					entidadJSONObject.put("codigo", especialidad.getIdEspecialidad());
					entidadJSONObject.put("nombre", especialidad.getNombre());
					entidadJSONObject.put("descripcion", especialidad.getDescripcion());										
					entidadJSONArray.add(entidadJSONObject);
				}
				result.put("numRegistros", i);
				result.put("listadoEspecialidads", entidadJSONArray);
				}
			}

			if (tipoConsulta.equals("crear")){
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
					especialidad.setEstado("ACT");
					especialidadDAO.crear(especialidad);
				}
			}

			if (tipoConsulta.equals("actualizar")){
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
					especialidad.setEstado("ACT");
					especialidadDAO.editar(especialidad);
				}
			}

			if (tipoConsulta.equals("eliminar")){
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
					especialidad = especialidadDAO.buscarPorId(Integer.parseInt(idEspecialidad));
					especialidad.setEstado("INA");
					especialidadDAO.editar(especialidad);
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
	}
}
