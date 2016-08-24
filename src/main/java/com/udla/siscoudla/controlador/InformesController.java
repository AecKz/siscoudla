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
import com.udla.siscoudla.dao.TurnoDAO;
import com.udla.siscoudla.modelo.Especialidad;
import com.udla.siscoudla.modelo.Estudiante;
import com.udla.siscoudla.modelo.Persona;
import com.udla.siscoudla.modelo.Turno;
import com.udla.siscoudla.util.Utilitarios;

/**
 * Servlet implementation class RegistroTurnosController
 */
@WebServlet("/InformesController")
public class InformesController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InformesController() {
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
		HttpSession session = request.getSession();
		String valorUsuario = session.getAttribute("login").toString();
		String valorRol = session.getAttribute("rol").toString();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");
			String idTratamiento = request.getParameter("codigo") == null ? "" : request.getParameter("codigo");
			String nombre = request.getParameter("nombre") == null ? "" : request.getParameter("nombre").toUpperCase();
			String descripcion = request.getParameter("descripcion") == null ? ""
					: request.getParameter("descripcion").toUpperCase();
			String especialidadId = request.getParameter("especialidad") == null ? ""
					: request.getParameter("especialidad");

			JSONObject entidadJSONObject = new JSONObject();
			JSONArray entidadJSONArray = new JSONArray();

			Turno turno = new Turno();
			TurnoDAO turnoDAO = new TurnoDAO();

			Especialidad especialidadVO = new Especialidad();
			EspecialidadDAO especialidadDAO = new EspecialidadDAO();

			// if (!idTratamiento.equals("")){
			// tratamiento.setIdTratamiento(Integer.parseInt(idTratamiento));
			// }
			// if (!nombre.equals("")){
			// tratamiento.setNombre(nombre);
			// }
			// if (!descripcion.equals("")){
			// tratamiento.setDescripcion(descripcion);
			// }
			// if (!especialidadId.equals("")){
			// especialidadVO =
			// especialidadDAO.buscarPorId(Integer.parseInt(especialidadId));
			// tratamiento.setEspecialidad(especialidadVO);
			// }

			if (tipoConsulta.equals("cargarDatosMenus")) {
				PersonaDAO personaDAO = new PersonaDAO();
				Persona persona = new Persona();
				persona = personaDAO.buscarPorEmail(valorUsuario);
				String nombreCompleto = persona.getNombres() + " " + persona.getApellidos();
				result.put("nombreCompleto", nombreCompleto);
			}

			if (tipoConsulta.equals("encontrarTodos")) {
				int i = 0;				
				 if(valorRol.equals("Coordinador") || valorRol.equals("Administrador")){
					List<Turno> results = turnoDAO.buscarTodos();
					for (i = 0; i < results.size(); i++) {
						turno = results.get(i);
						entidadJSONObject.put("fecha", Utilitarios.dateToString(turno.getFecha()));
						entidadJSONObject.put("clinica",
								turno.getHorarioestudiante().getEstudiante().getClinica().getNombre());
						entidadJSONObject.put("estudiante",
								turno.getHorarioestudiante().getEstudiante().getPersona().getNombres() + " "
										+ turno.getHorarioestudiante().getEstudiante().getPersona().getApellidos());
						entidadJSONObject.put("especialidad", turno.getTratamiento().getEspecialidad().getNombre());
						entidadJSONObject.put("tratamiento", turno.getTratamiento().getNombre());
						entidadJSONObject.put("cubiculo",
								turno.getHorariocubiculoestado().getHorariocubiculo().getCubiculo().getNumero());
						entidadJSONObject.put("estado",
								turno.getHorariocubiculoestado().getEstado());
						entidadJSONArray.add(entidadJSONObject);
					}					
				}else{ //Si no es coordinador o administrador es Estudiante									
					Estudiante estudiante = Utilitarios.buscarEstudianteUsuario(valorUsuario);
					List<Turno> results = turnoDAO.buscarTodosPorEstudiante(estudiante.getIdEstudiante());
					for (i = 0; i < results.size(); i++) {
						turno = results.get(i);
						entidadJSONObject.put("fecha", Utilitarios.dateToString(turno.getFecha()));						
						entidadJSONObject.put("paciente",
								turno.getPaciente().getPersona().getNombres() + " "
										+ turno.getPaciente().getPersona().getApellidos());
						entidadJSONObject.put("especialidad", turno.getTratamiento().getEspecialidad().getNombre());
						entidadJSONObject.put("tratamiento", turno.getTratamiento().getNombre());
						entidadJSONObject.put("cubiculo",
								turno.getHorariocubiculoestado().getHorariocubiculo().getCubiculo().getNumero());
						entidadJSONObject.put("estado",
								turno.getHorariocubiculoestado().getEstado());
						entidadJSONArray.add(entidadJSONObject);
				}
				}
				result.put("numRegistros", i);
				result.put("listadoTurnos", entidadJSONArray);
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