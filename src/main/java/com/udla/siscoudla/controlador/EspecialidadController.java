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

import com.udla.siscoudla.dao.HorarioDAO;
import com.udla.siscoudla.dao.PersonaDAO;
import com.udla.siscoudla.modelo.Horario;
import com.udla.siscoudla.modelo.Persona;
import com.udla.siscoudla.util.Utilitarios;

/**
 * Servlet implementation class PersonaController
 */
@WebServlet("/HorarioController")
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
			String idHorario = request.getParameter("codigo") == null ? ""
					: request.getParameter("codigo");
			String horaInicio = request.getParameter("horaInicio") == null ? ""
					: request.getParameter("horaInicio").toUpperCase();
			String horaFinal = request.getParameter("horaFinal") == null ? ""
					: request.getParameter("horaFinal").toUpperCase();
			String dia = request.getParameter("dia") == null ? ""
					: request.getParameter("dia");

			JSONObject entidadJSONObject = new JSONObject();
			JSONArray entidadJSONArray = new JSONArray();

			Horario horario = new Horario();
			HorarioDAO horarioDAO = new HorarioDAO();

			if (!idHorario.equals("")){
				horario.setIdHorario(Integer.parseInt(idHorario));
			}
			if (!horaInicio.equals("")){
				horario.setHoraInicio(horaInicio);
			}
			if (!horaFinal.equals("")){
				horario.setHoraFinal(horaFinal);
			}
			if (!dia.equals("")){
				horario.setDia(dia);
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
				List<Horario> results = horarioDAO.buscarTodosActivos();
				int i = 0;
				for (i = 0; i < results.size(); i++) {
					horario = results.get(i);
					entidadJSONObject.put("codigo", horario.getIdHorario());
					entidadJSONObject.put("horaInicio", horario.getHoraInicio());
					entidadJSONObject.put("horaFinal", horario.getHoraFinal());
					entidadJSONObject.put("dia", horario.getDia());					
					entidadJSONArray.add(entidadJSONObject);
				}
				result.put("numRegistros", i);
				result.put("listadoHorarios", entidadJSONArray);
				}
			}

			if (tipoConsulta.equals("crear")){
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
					horario.setEstado("ACT");
					horarioDAO.crear(horario);
				}
			}

			if (tipoConsulta.equals("actualizar")){
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
					horario.setEstado("ACT");
					horarioDAO.editar(horario);
				}
			}

			if (tipoConsulta.equals("eliminar")){
				if (Utilitarios.verificarRolAdministrador(valorUsuario)) {
					horario = horarioDAO.buscarPorId(Integer.parseInt(idHorario));
					horario.setEstado("INA");
					horarioDAO.editar(horario);
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
