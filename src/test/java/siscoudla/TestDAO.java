package siscoudla;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.udla.siscoudla.dao.HorarioDAO;
import com.udla.siscoudla.dao.RolDAO;
import com.udla.siscoudla.dao.TratamientoDAO;
import com.udla.siscoudla.modelo.Estudiante;
import com.udla.siscoudla.modelo.Horario;
import com.udla.siscoudla.modelo.Rol;
import com.udla.siscoudla.modelo.Tratamiento;

public class TestDAO {

	@Test
	public void test() {
		System.out.println("Iniciando Test DAO");
		testBuscarHorariosEstudiante();
		testBuscarRolUsuario();
		testBuscarTratamientosEspecialidad();
		System.out.println("Fin Test DAO");
	}

	public void testBuscarRolUsuario() {
		try{
		RolDAO rolDAO = new RolDAO();
		List<Rol> roles = rolDAO.buscarRolPorUsuario("paajacome@udlanet.ec");
		if (!roles.isEmpty()) {			
			for (Rol rol : roles) {
				System.out.println("Resultado: " +rol.getNombre());
			}
		} else {
			System.out.println("No trae resultados");			
		}
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	
	public void testBuscarHorariosEstudiante() {
		try{
		HorarioDAO horarioDAO = new HorarioDAO();
		Estudiante estudiante = new Estudiante ();
		estudiante.setIdEstudiante(Integer.parseInt("1"));		
		List<Horario> horarios = horarioDAO.buscarHorariosEstudiante(estudiante);
		if (!horarios.isEmpty()) {			
			for (Horario he : horarios) {
				System.out.println("Resultado: " +he.getDia() +"-"+ he.getHoraInicio() +"-"+ he.getHoraFinal());
			}
		} else {
			System.out.println("No trae resultados");			
		}
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	public void testBuscarTratamientosEspecialidad() {
		try{
		TratamientoDAO tratamientoDAO = new TratamientoDAO();
		int idEspecialidad = 4;		
		List<Tratamiento> tratamientos = tratamientoDAO.buscarTratamientosEspecialidad(idEspecialidad);
		if (!tratamientos.isEmpty()) {			
			for (Tratamiento he : tratamientos) {
				System.out.println("Resultado: " +he.getNombre() +"-"+ he.getEspecialidad().getNombre());
			}
		} else {
			System.out.println("No trae resultados");			
		}
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}

}
