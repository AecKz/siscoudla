package siscoudla;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import com.udla.siscoudla.dao.HorarioCubiculoEstadoDAO;
import com.udla.siscoudla.dao.HorarioDAO;
import com.udla.siscoudla.dao.RolDAO;
import com.udla.siscoudla.dao.TratamientoDAO;
import com.udla.siscoudla.modelo.Estudiante;
import com.udla.siscoudla.modelo.Horario;
import com.udla.siscoudla.modelo.Rol;
import com.udla.siscoudla.modelo.Tratamiento;
import com.udla.siscoudla.util.Utilitarios;

public class Test {

	@org.junit.Test
	public void test() {
		//testFlujoTurnoNormal();
	}

	public void testFlujoTurnoNormal() {
		//Test del Flujo de reservar turnos normales
		System.out.println("*******Inicia el Flujo********");
		//1.- Se comprueba si el usuario ingresado es Estudiante
		RolDAO rolDAO = new RolDAO();
		String usuario = "paajacome@udlanet.ec";
		List<Rol> roles = rolDAO.buscarRolPorUsuario(usuario);		
		if(roles.get(0).getNombre().equals("Estudiante")){
			System.out.println("El usuario: " + usuario +" es Estudiante");
			System.out.println("Verificar o crear paciente");
			//2.- Se crea un paciente si este no existe
				//2.1-Verificar si existe el paciente a traves de numero de historia
				//2.2- Si no existe, crear objeto Persona y Paciente
			//3.- Se presenta los horarios para el presente semestre del estudiante
			HorarioDAO horarioDAO = new HorarioDAO();
			Estudiante estudiante = new Estudiante ();
			estudiante.setIdEstudiante(Integer.parseInt("1"));		
			List<Horario> horarios = horarioDAO.buscarHorariosEstudiante(estudiante);
			if (!horarios.isEmpty()) {
				System.out.println("El estudiante registra los siguientes horarios: ");
				for (Horario he : horarios) {					
					System.out.println("Horario: " +he.getDia() +"-"+ he.getHoraInicio() +"-"+ he.getHoraFinal());
				}
			} else {
				System.out.println("No existen horarios para el estudiante");			
			}
			//4.- El estudiante selecciona el tratamiento a realizar
			//Se presentan los tratamientos categorizados por especialidad en la pantalla
			TratamientoDAO tratamientoDAO = new TratamientoDAO();
			int idEspecialidad = 2;		
			List<Tratamiento> tratamientos = tratamientoDAO.buscarTratamientosEspecialidad(idEspecialidad);
			if (!tratamientos.isEmpty()) {
				System.out.println("Existen los siguientes tratamientos: ");
				for (Tratamiento he : tratamientos) {
					System.out.println("Tratamiento: " +he.getNombre() +"-"+ he.getEspecialidad().getNombre());
				}
			} else {
				System.out.println("No existen tratamientos");			
			}
			//5.- Se comprueba si existen cubiculos disponibles para el turno
			int idHorario = 1;			
			Date fecha = Utilitarios.stringToDate("2016-05-09");
			String tipoCubiculo = "NORMAL";
			HorarioCubiculoEstadoDAO hceDAO = new HorarioCubiculoEstadoDAO();
			List<String> cubiculos = hceDAO.buscarCubiculosLibres(fecha, idEspecialidad, idHorario, tipoCubiculo);		
			if (!cubiculos.isEmpty()) {
				System.out.println("Existen disponibles los siguientes cubiculos: ");
				for (String he : cubiculos) {
					System.out.println("Cubiculo: " +he);
				}
			} else {
				System.out.println("No trae resultados");			
			}
			//6.- Se asigna un cubiculo al estudiante al azar
			//String cubiculo = cubiculos.get(new Random().nextInt(cubiculos.size()));
			String cubiculo = cubiculos.get(0);
			System.out.println("Se ha asignado el cubiculo: "+ cubiculo);
			//7.- Si el estudiante acepta, se comprueba una vez mas si el cubiculo asignado esta Libre
			//Ojo: Es posible que por concurrencia se asigne un cubiculo, y luego otro usuario lo registre.			
			Boolean flag = hceDAO.verificarEstado(Integer.parseInt(cubiculo), "LIB");
			if(flag){
				System.out.println("El cubiculo esta libre");
				//Si el cubiculo está libre se crea el objeto Turno en estado RESERVADO y se cambia el estado de hce a OCU
				System.out.println("Se crea el Turno");
			}else{
				System.out.println("El cubiculo NO esta libre");
			}		
			System.out.println("*******Fin del Flujo********");
		}else{		
			fail("No es estudiante");
		}
	}

}
