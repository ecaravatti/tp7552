package laboratorio;
import general.dominio.Practica;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import laboratorio.dominio.Area;
import laboratorio.dominio.Supervisor;
import laboratorio.dominio.Tecnico;
import laboratorio.dominio.excepciones.ExcepcionTecnicoNoSupervisado;
import laboratorio.dominio.practica.resultado.TipoResultado;
import laboratorio.dominio.practica.resultado.TipoResultadoCadena;
import laboratorio.dominio.practica.resultado.TipoResultadoEntero;
import laboratorio.dominio.tarea.EstadoTarea;
import laboratorio.dominio.tarea.Tarea;
import clinica.dominio.Prestacion;

public class SupervisorTest extends TestCase {
	
	private Tecnico tecnico1;
	private Tecnico tecnico2;
	private Area area;
	private Tarea tarea;
	private Practica practica;
	private Supervisor supervisor;

	protected void setUp() throws Exception {
		
		super.setUp();
		
		area = new Area("Radiografias");
		tarea = new Tarea(area, practica, "descripcion");
		
		// Datos necesarios para crear una Practica
		Map<String, TipoResultado> valoresResultados = new HashMap<String, TipoResultado>();
		valoresResultados.put("globulos rojos", new TipoResultadoEntero());
		valoresResultados.put("globulos blancos", new TipoResultadoEntero());
		valoresResultados.put("color", new TipoResultadoCadena());
		
		practica = new Practica(new Prestacion(1, "Analisis de Sangre", valoresResultados));
		
		tecnico1 = new Tecnico("Laura Rodriguez", new Integer(23), new Double(2600));
		tecnico1.getAreas().add(area);
		
		tecnico2 = new Tecnico("Damian Pisaturo", new Integer(22), new Double(2600));
		tecnico2.getAreas().add(area);
		
		supervisor = new Supervisor("Jose", new Integer(35), new Double(3400));
	}

	public void testAsignarTarea() {
		
		// Asignaci�n de una tarea a un técnico que está supervisado por 'supervisor'
		supervisor.getTecnicos().add(tecnico1);
		try {
			supervisor.asignarTarea(tecnico1, tarea);
		} catch (Exception e) {
			fail("No deberia haber lanzado una excepcion");
		}
		// Compruebo que se le haya asignado la tarea
		assertTrue(tecnico1.getTareas().contains(tarea));
		// Compruebo que el estado de la tarea es ASIGNADA
		assertEquals(EstadoTarea.Asignada, tarea.getEstado());
	}
	
	public void testAsignarTareaConTecnicoNoSupervisado() {
		
		// Asignaci�n de una tarea a un técnico que no está supervisado por 'supervisor'
		try{
			supervisor.asignarTarea(tecnico1, tarea);
			fail("Deberia haber lanzado una ExcepcionTecnicoNoSupervisado");
		}
		catch(ExcepcionTecnicoNoSupervisado e){
			
		} catch (Exception e) {
			fail("Deberia haber lanzado una ExcepcionTecnicoNoSupervisado");
		}
		// Compruebo que no se le haya asignado la tarea al técnico
		assertFalse(tecnico1.getTareas().contains(tarea));
		// Compruebo que el estado de la tarea no haya sido modificado
		assertEquals(EstadoTarea.NoAsignada, tarea.getEstado());
	}
	
	public void testReasignarTarea() {
		
		// Reasignación correcta de una tarea
		tecnico1.asignarTarea(tarea);
		supervisor.getTecnicos().add(tecnico1);
		supervisor.getTecnicos().add(tecnico2);
		try {
			supervisor.reasignarTarea(tecnico1, tecnico2, tarea);
		} catch (Exception e) {
			fail("No deberia haber lanzado una excepcion");
		}
		// Compruebo que el tecnico1 se haya quedado sin tareas
		assertEquals(0, tecnico1.getTareas().size());
		// Compruebo que se haya asigando "tarea" al tecnico2
		assertTrue(tecnico2.getTareas().contains(tarea));
	}
	
	public void testReasignarTareaConTecnicoOrigenNoSupervisado() {
		
		tecnico1.asignarTarea(tarea);
		supervisor.getTecnicos().add(tecnico2);
		try {
			supervisor.reasignarTarea(tecnico1, tecnico2, tarea);
			fail("Deberia haber lanzado una ExcepcionTecnicoNoSupervisado");
		}catch (ExcepcionTecnicoNoSupervisado e) {
		} catch (Exception e) {
			fail("Deberia haber lanzado una ExcepcionTecnicoNoSupervisado");
		}
		// Compruebo que el tecnico1 no se haya quedado sin tareas
		assertEquals(1, tecnico1.getTareas().size());
		// Compruebo que no se haya asigando "tarea" al tecnico2
		assertTrue(tecnico2.getTareas().isEmpty());
	}
	
	public void testReasignarTareaConTecnicoDestinoNoSupervisado() {
		
		tecnico1.asignarTarea(tarea);
		supervisor.getTecnicos().add(tecnico1);
		try {
			supervisor.reasignarTarea(tecnico1, tecnico2, tarea);
			fail("Deberia haber lanzado una ExcepcionTecnicoNoSupervisado");
		}catch (ExcepcionTecnicoNoSupervisado e) {
		} catch (Exception e) {
			fail("Deberia haber lanzado una ExcepcionTecnicoNoSupervisado");
		}
		// Compruebo que el tecnico1 no se haya quedado sin tareas
		assertEquals(1, tecnico1.getTareas().size());
		// Compruebo que no se haya asigando "tarea" al tecnico2
		assertTrue(tecnico2.getTareas().isEmpty());
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
