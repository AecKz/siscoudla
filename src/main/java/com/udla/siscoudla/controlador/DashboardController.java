package com.udla.siscoudla.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.udla.siscoudla.dao.EstudianteDAO;
import com.udla.siscoudla.dao.PersonaDAO;
import com.udla.siscoudla.dao.TurnoDAO;
import com.udla.siscoudla.modelo.Especialidad;
import com.udla.siscoudla.modelo.Estudiante;
import com.udla.siscoudla.modelo.Persona;
import com.udla.siscoudla.modelo.Turno;
import com.udla.siscoudla.util.Utilitarios;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Servlet implementation class DashboardController
 */
@WebServlet("/DashboardController")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardController() {
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
		JSONObject reservadosJSONObject = new JSONObject();
		JSONArray reservadosJSONArray = new JSONArray();
		JSONObject ocupadosJSONObject = new JSONObject();
		JSONArray ocupadosJSONArray = new JSONArray();
		JSONObject canceladosJSONObject = new JSONObject();
		JSONArray canceladosJSONArray = new JSONArray();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");

			TurnoDAO turnoDAO = new TurnoDAO();
			Estudiante estudiante = new Estudiante();
			estudiante = Utilitarios.buscarEstudianteUsuario(valorUsuario);
			int idEstudiante = estudiante.getIdEstudiante();
			if (tipoConsulta.equals("cargarDatosMenus")){
				if(Utilitarios.verificarRolEstudiante(valorUsuario)){
					PersonaDAO personaDAO = new PersonaDAO();
					Persona persona = new Persona();
					persona = personaDAO.buscarPorEmail(valorUsuario);
					String nombreCompleto = persona.getNombres() +" "+ persona.getApellidos();
					result.put("nombreCompleto", nombreCompleto);
				}
			}
			if (tipoConsulta.equals("cargarDatosProfile")){
				if(Utilitarios.verificarRolEstudiante(valorUsuario)){
					String matricula = estudiante.getMatricula();
					String clinica = estudiante.getClinica().getNombre();					
					result.put("usuario", valorUsuario);
					result.put("matricula", matricula);
					result.put("clinica", clinica);
				}
			}
			if (tipoConsulta.equals("cargarDatosReservados")) {
				String estado = "RES";
				List<Turno> turnos = turnoDAO.buscarReservadosPorEstudiante(idEstudiante, estado);
				for (Turno turno : turnos) {
					String fechaTurno = Utilitarios.dateToString(turno.getFecha());
					reservadosJSONObject.put("fecha", fechaTurno);
					reservadosJSONObject.put("horario", turno.getHorarioestudiante().getHorario().getHoraInicio()
							+ " - " + turno.getHorarioestudiante().getHorario().getHoraFinal());
					reservadosJSONObject.put("tratamiento", turno.getTratamiento().getNombre());
					reservadosJSONObject.put("paciente", turno.getPaciente().getPersona().getNombres() + " "
							+ turno.getPaciente().getPersona().getApellidos());
					reservadosJSONObject.put("cubiculo",
							turno.getHorariocubiculoestado().getHorariocubiculo().getCubiculo().getNumero());
					reservadosJSONArray.add(reservadosJSONObject);
				}
				result.put("numRegistros", (reservadosJSONArray.size()));
				result.put("listadoReservados", reservadosJSONArray);
			}
			if (tipoConsulta.equals("cargarDatosOcupados")){
				String estado = "OCU";
				List<Turno> turnos = turnoDAO.buscarReservadosPorEstudiante(idEstudiante, estado);
				for (Turno turno : turnos) {
					String fechaTurno = Utilitarios.dateToString(turno.getFecha());
					reservadosJSONObject.put("fecha", fechaTurno);
					ocupadosJSONObject.put("horario", turno.getHorarioestudiante().getHorario().getHoraInicio()
							+ " - " + turno.getHorarioestudiante().getHorario().getHoraFinal());
					ocupadosJSONObject.put("tratamiento", turno.getTratamiento().getNombre());
					ocupadosJSONObject.put("paciente", turno.getPaciente().getPersona().getNombres() + " "
							+ turno.getPaciente().getPersona().getApellidos());
					ocupadosJSONObject.put("cubiculo",
							turno.getHorariocubiculoestado().getHorariocubiculo().getCubiculo().getNumero());
					ocupadosJSONArray.add(ocupadosJSONObject);
				}
				result.put("numRegistros", (ocupadosJSONArray.size()));
				result.put("listadoOcupados", ocupadosJSONArray);
			}
			if (tipoConsulta.equals("cargarDatosCancelados")){
				String estado = "CAN";
				List<Turno> turnos = turnoDAO.buscarReservadosPorEstudiante(idEstudiante, estado);
				for (Turno turno : turnos) {
					String fechaTurno = Utilitarios.dateToString(turno.getFecha());
					reservadosJSONObject.put("fecha", fechaTurno);
					canceladosJSONObject.put("horario", turno.getHorarioestudiante().getHorario().getHoraInicio()
							+ " - " + turno.getHorarioestudiante().getHorario().getHoraFinal());
					canceladosJSONObject.put("tratamiento", turno.getTratamiento().getNombre());
					canceladosJSONObject.put("paciente", turno.getPaciente().getPersona().getNombres() + " "
							+ turno.getPaciente().getPersona().getApellidos());
					canceladosJSONObject.put("cubiculo",
							turno.getHorariocubiculoestado().getHorariocubiculo().getCubiculo().getNumero());
					canceladosJSONArray.add(canceladosJSONObject);
				}
				result.put("numRegistros", (canceladosJSONArray.size()));
				result.put("listadoCancelados", canceladosJSONArray);
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
