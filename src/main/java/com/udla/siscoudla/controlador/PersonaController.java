package com.udla.siscoudla.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.udla.siscoudla.dao.PersonaDAO;
import com.udla.siscoudla.modelo.Persona;

/**
 * Servlet implementation class PersonaController
 */
@WebServlet("/PersonaController")
public class PersonaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PersonaController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject result = new JSONObject();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");
			String id = request.getParameter("codigoEnsurance") == null ? ""
					: request.getParameter("codigoEnsurance");
			String nombre = request.getParameter("nombre") == null ? ""
					: request.getParameter("nombre").toUpperCase();
			String apellido = request.getParameter("apellido") == null ? ""
					: request.getParameter("apellido").toUpperCase();
//			String identificacion = request.getParameter("identificacion") == null ? ""
//					: request.getParameter("identificacion");
			String email = request.getParameter("email") == null ? "" : request
					.getParameter("email");
			String telefono = request.getParameter("telefono") == null ? ""
					: request.getParameter("telefono");
			// String direccionId = request.getParameter("direccionId") == null
			// ? "" : request.getParameter("direccionId");
			// String actividadEconomicaId =
			// request.getParameter("actividadEconomica") == null ? "" :
			// request.getParameter("actividadEconomica");
			// String descripcion = request.getParameter("descripcion") == null
			// ? "" : request.getParameter("descripcion");
			// String ciudadId = request.getParameter("ciudadId") == null ? "" :
			// request.getParameter("ciudadId");
			// String referencia = request.getParameter("referencia") == null ?
			// "" : request.getParameter("referencia");
			// String sectorId = request.getParameter("sectorId") == null ? "" :
			// request.getParameter("sectorId");

			JSONObject entidadJSONObject = new JSONObject();
			JSONArray entidadJSONArray = new JSONArray();

			Persona persona = new Persona();
			PersonaDAO personaDAO = new PersonaDAO();

			if (!id.equals(""))
				persona.setIdPersona(Integer.parseInt(id));

//			if (!identificacion.equals("")) {
//				if (personaDAO.buscarPorId(identificacion)(identificacion) == 0)
//					;
//				persona.setIdentificacion(identificacion);
//			}

			if (!email.equals(""))
				persona.setEmail(email);

			if (!telefono.equals(""))
				persona.setTelefono(telefono);

			if (!nombre.equals(""))
				persona.setNombres(nombre);

			if (!apellido.equals(""))
				persona.setApellidos(apellido);

			if (tipoConsulta.equals("encontrarTodos")) {
				List<Persona> results = personaDAO.buscarTodos();
				int i = 0;
				for (i = 0; i < results.size(); i++) {
					persona = results.get(i);
					entidadJSONObject.put("id", persona.getIdPersona());
					entidadJSONObject.put("nombre", persona.getNombres());
					entidadJSONObject.put("apellido", persona.getApellidos());
					entidadJSONObject.put("identificacion",
							persona.getNumeroDocumento());
					entidadJSONObject.put("email", persona.getEmail());
					entidadJSONObject.put("telefono", persona.getTelefono());

					entidadJSONArray.add(entidadJSONObject);
				}
				result.put("numRegistros", i);
				result.put("listadoEntidades", entidadJSONArray);
			}

			if (tipoConsulta.equals("crear"))
				personaDAO.crear(persona);

			if (tipoConsulta.equals("actualizar"))
				personaDAO.editar(persona);

			if (tipoConsulta.equals("eliminar"))
				personaDAO.eliminar(persona);

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
