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

import com.udla.siscoudla.dao.TratamientoDAO;
import com.udla.siscoudla.dao.EspecialidadDAO;
import com.udla.siscoudla.dao.PersonaDAO;
import com.udla.siscoudla.modelo.Tratamiento;
import com.udla.siscoudla.modelo.Especialidad;
import com.udla.siscoudla.modelo.Persona;
import com.udla.siscoudla.util.Utilitarios;

/**
 * Servlet implementation class PersonaController
 */
@WebServlet("/TratamientoController")
public class TratamientoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TratamientoController() {
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
			String idTratamiento = request.getParameter("codigo") == null ? ""
					: request.getParameter("codigo");
			String nombre = request.getParameter("nombre") == null ? ""
					: request.getParameter("nombre").toUpperCase();
			String descripcion = request.getParameter("descripcion") == null ? ""
					: request.getParameter("descripcion").toUpperCase();
			String especialidadId = request.getParameter("especialidad") == null ? ""
					: request.getParameter("especialidad");

			JSONObject entidadJSONObject = new JSONObject();
			JSONArray entidadJSONArray = new JSONArray();

			Tratamiento tratamiento = new Tratamiento();
			TratamientoDAO tratamientoDAO = new TratamientoDAO();
			
			Especialidad especialidadVO = new Especialidad();
			EspecialidadDAO especialidadDAO = new EspecialidadDAO();

			if (!idTratamiento.equals("")){
				tratamiento.setIdTratamiento(Integer.parseInt(idTratamiento));
			}
			if (!nombre.equals("")){
				tratamiento.setNombre(nombre);
			}
			if (!descripcion.equals("")){
				tratamiento.setDescripcion(descripcion);
			}
			if (!especialidadId.equals("")){
				especialidadVO = especialidadDAO.buscarPorId(Integer.parseInt(especialidadId));
				tratamiento.setEspecialidad(especialidadVO);
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
			if(tipoConsulta.equals("cargarEspecialidades")){
				// Se consultan todas las Especialidades
				JSONObject especialidadJSONObject = new JSONObject();
				JSONArray especialidadJSONArray = new JSONArray();
				List <Especialidad> listaEspecialidades =  especialidadDAO.buscarTodos();
				for (int i=0; i<listaEspecialidades.size(); i++){
					especialidadVO = listaEspecialidades.get(i);
					especialidadJSONObject.put("codigo", especialidadVO.getIdEspecialidad());
					especialidadJSONObject.put("nombre", especialidadVO.getNombre());
					especialidadJSONArray.add(especialidadJSONObject);
				}
				result.put("listadoEspecialidades", especialidadJSONArray);
			}
			
			if (tipoConsulta.equals("encontrarTodos")) {
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
				List<Tratamiento> results = tratamientoDAO.buscarActivos();
				int i = 0;
				for (i = 0; i < results.size(); i++) {
					tratamiento = results.get(i);
					entidadJSONObject.put("codigo", tratamiento.getIdTratamiento());
					entidadJSONObject.put("nombre", tratamiento.getNombre());
					entidadJSONObject.put("descripcion", tratamiento.getDescripcion());
					entidadJSONObject.put("especialidad", tratamiento.getEspecialidad().getNombre());					
					entidadJSONArray.add(entidadJSONObject);
				}
				result.put("numRegistros", i);
				result.put("listadoTratamientos", entidadJSONArray);
				}
			}

			if (tipoConsulta.equals("crear")){
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
					tratamiento.setEstado("ACT");
					tratamientoDAO.crear(tratamiento);
				}
			}

			if (tipoConsulta.equals("actualizar")){
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
					tratamiento.setEstado("ACT");
					tratamientoDAO.editar(tratamiento);
				}
			}

			if (tipoConsulta.equals("eliminar")){
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
					tratamiento = tratamientoDAO.buscarPorId(Integer.parseInt(idTratamiento));
					tratamiento.setEstado("INA");
					tratamientoDAO.editar(tratamiento);
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
