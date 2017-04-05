package com.udla.siscoudla.controlador;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.udla.siscoudla.dao.HorarioDAO;
import com.udla.siscoudla.dao.PersonaDAO;
import com.udla.siscoudla.dao.TurnoDAO;
import com.udla.siscoudla.modelo.Horario;
import com.udla.siscoudla.modelo.Persona;
import com.udla.siscoudla.modelo.Turno;
import com.udla.siscoudla.util.Utilitarios;

/**
 * Servlet implementation class RegistroTurnosController
 */
@WebServlet("/TomarListaController")
public class TomarListaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TomarListaController() {
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
		if (valorRol.equals("Coordinador")) {
			try {
				String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
						: request.getParameter("tipoConsulta");
				String idHorario = request.getParameter("codigo") == null ? "" : request.getParameter("codigo");

				JSONObject entidadJSONObject = new JSONObject();
				JSONArray entidadJSONArray = new JSONArray();

				Turno turno = new Turno();
				TurnoDAO turnoDAO = new TurnoDAO();

				Horario horario = new Horario();
				HorarioDAO horarioDAO = new HorarioDAO();
				Horario horarioVO = new Horario();

				if (tipoConsulta.equals("cargarDatosMenus")) {
					if (Utilitarios.verificarRolCoordinador(valorUsuario)) {
						PersonaDAO personaDAO = new PersonaDAO();
						Persona persona = new Persona();
						persona = personaDAO.buscarPorEmail(valorUsuario);
						String nombreCompleto = persona.getNombres() + " " + persona.getApellidos();
						result.put("nombreCompleto", nombreCompleto);
					}
				}

				if (tipoConsulta.equals("cargarHorariosCombos")) {
					// Se consultan todos los Horarios
					JSONObject horarioJSONObject = new JSONObject();
					JSONArray horarioJSONArray = new JSONArray();
					List<Horario> listaHorarios = horarioDAO.buscarActivosPorFecha();
					for (int i = 0; i < listaHorarios.size(); i++) {
						horarioVO = listaHorarios.get(i);
						horarioJSONObject.put("codigo", horarioVO.getIdHorario());
						horarioJSONObject.put("horario", horarioVO.getDia() + " "+horarioVO.getHoraInicio() + "-" + horarioVO.getHoraFinal());
						horarioJSONArray.add(horarioJSONObject);
					}
					result.put("listadoHorariosCombos", horarioJSONArray);
				}

				if (tipoConsulta.equals("tomarLista")) {
					if (Utilitarios.verificarRolCoordinador(valorUsuario)) {
						Date fecha = new Date();
						horario = horarioDAO.buscarPorId(Integer.parseInt(idHorario));
						List<Turno> results = turnoDAO.buscarReservadosPorHorario(horario.getIdHorario(), fecha);
						int i = 0;
						for (i = 0; i < results.size(); i++) {
							turno = results.get(i);
							entidadJSONObject.put("codigo", turno.getIdTurno());
							entidadJSONObject.put("clinica",
									turno.getHorarioestudiante().getEstudiante().getClinica().getNombre());
							entidadJSONObject.put("estudiante",
									turno.getHorarioestudiante().getEstudiante().getPersona().getNombres() + " "
											+ turno.getHorarioestudiante().getEstudiante().getPersona().getApellidos());
							entidadJSONObject.put("especialidad", turno.getTratamiento().getEspecialidad().getNombre());
							entidadJSONObject.put("tratamiento", turno.getTratamiento().getNombre());
							entidadJSONObject.put("cubiculo",
									turno.getHorariocubiculoestado().getHorariocubiculo().getCubiculo().getNumero());
							entidadJSONArray.add(entidadJSONObject);
						}
						result.put("numRegistros", i);
						result.put("listadoTurnos", entidadJSONArray);
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