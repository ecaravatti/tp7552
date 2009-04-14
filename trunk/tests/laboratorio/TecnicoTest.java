package laboratorio;
import general.dominio.Practica;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import laboratorio.dominio.Area;
import laboratorio.dominio.Tecnico;
import laboratorio.dominio.excepciones.ExcepcionAreaDeTrabajoInvalida;
import laboratorio.dominio.excepciones.ExcepcionTareaNoAsignable;
import laboratorio.dominio.excepciones.ExcepcionTareaYaAsignada;
import laboratorio.dominio.practica.resultado.TipoResultado;
import laboratorio.dominio.practica.resultado.TipoResultadoCadena;
import laboratorio.dominio.practica.resultado.TipoResultadoEntero;
import laboratorio.dominio.tarea.EstadoTarea;
import laboratorio.dominio.tarea.Tarea;
import clinica.dominio.Prestacion;

public class TecnicoTest extends TestCase {
	
	private Tecnico tecnico1;
	private Tecnico tecnico2;
	private Area area1;
	private Area area2;
	private Tarea tarea;
	private Practica practica;

	protected void setUp() throws Exception {
		
		super.setUp();
		
		area1 = new Area("Radiografias");
		area2 = new Area("Ecografias");
		tarea = new Tarea(area1, practica, "descripcion");
		
		// Datos necesarios para crear una Practica
		Map<String, TipoResultado> valoresResultados = new HashMap<String, TipoResultado>();
		valoresResultados.put("globulos rojos", new TipoResultadoEntero());
		valoresResultados.put("globulos blancos", new TipoResultadoEntero());
		valoresResultados.put("color", new TipoResultadoCadena());
		
		practica = new Practica(new Prestacion(1, "Analisis de Sangre", valoresResultados));
		
		tecnico1 = new Tecnico("Laura Rodriguez", new Integer(23), new Double(2600));
		tecnico1.getAreas().add(area1);
		tecnico1.getTareas().add(tarea);
		tarea.modificarEstado(EstadoTarea.Asignada);
		
		tecnico2 = new Tecnico("Damian Pisaturo", new Integer(22), new Double(2600));
		tecnico2.getAreas().add(area1);
	}

	public void testAsignarTarea() {
		
		// Asignaci贸n de una tarea con un 谩rea que corresponde al t茅cnico
		Tarea tareaAAsignar = new Tarea(area1, practica, "descripcion");
		try {
			tecnico1.asignarTarea(tareaAAsignar);
		} catch (Exception e) {
			fail("No deberia haber lanzado una excepcion");
		}
		// Compruebo que se le haya asignado la tarea
		assertTrue(tecnico1.getTareas().contains(tareaAAsignar));
		// Compruebo que el estado de la tarea es ASIGNADA
		assertEquals(EstadoTarea.Asignada, tareaAAsignar.getEstado());
	}
	
	public void testAsignarTareaConAreaDeTrabajoInvalida() {
		
		// Asignaci髇 de una tarea con un 醨ea que no corresponde al t閏nico
		Area otraArea = new Area("Traumatologia");
		Tarea tareaOtraArea = new Tarea(otraArea, practica, "descripcion");
		try{
			tecnico1.asignarTarea(tareaOtraArea);
			fail("Deberia haber lanzado una ExcepcionAreaDeTrabajoInvalida");
		}
		catch(ExcepcionAreaDeTrabajoInvalida e){
			
		} catch (Exception e) {
			fail("Deberia haber lanzado una ExcepcionAreaDeTrabajoInvalida");
		}
		// Compruebo que no se le haya asignado al t茅cnico la tarea "tareaOtraArea"
		assertFalse(tecnico1.getTareas().contains(tareaOtraArea));
		// Compruebo que el estado de la tarea no haya sido modificado
		assertEquals(EstadoTarea.NoAsignada, tareaOtraArea.getEstado());
	}
	
	public void testAsignarTareaNoAsignable() {
		
		// Asignaci贸n de una tarea que corresponde al 谩rea pero que no tiene un estado asignable
		Tarea tareaEstadoNoAsignable = new Tarea(area1, practica, "descripcion");
		tareaEstadoNoAsignable.modificarEstado(EstadoTarea.EnProgreso);
		try{
			tecnico1.asignarTarea(tareaEstadoNoAsignable);
			fail("Deberia haber lanzado una ExcepcionTareaNoAsignable");
		}
		catch(ExcepcionTareaNoAsignable e){
			
		} catch (Exception e) {
			fail("Deberia haber lanzado una ExcepcionTareaNoAsignable");
		}
		
		assertFalse(tecnico1.getTareas().contains(tareaEstadoNoAsignable));
		assertEquals(EstadoTarea.EnProgreso, tareaEstadoNoAsignable.getEstado());
	}
	
	public void testAsignarTareaYaAsignada() {
		
		// Asignaci贸n de una tarea que el t茅cnico ya tiene asignada
		try{
			tecnico1.asignarTarea(tarea);
			fail("Deberia haber lanzado una ExcepcionTareaYaAsignada");
		}
		catch(ExcepcionTareaYaAsignada e){
			
		} catch (Exception e) {
			fail("Deberia haber lanzado una ExcepcionTareaYaAsignada");
		}
		
		assertTrue(tecnico1.getTareas().contains(tarea));
		assertEquals(EstadoTarea.Asignada, tarea.getEstado());
	}
	
	public void testReasignarTarea() {
		
		// Reasignaci贸n correcta de una tarea
		try {
			tecnico2.reasignarTarea(tecnico1, tarea);
		} catch (Exception e) {
			fail("No deberia haber lanzado una excepcion");
		}
		// Compruebo que el tecnico1 se haya quedado sin tareas
		assertEquals(0, tecnico1.getTareas().size());
		// Compruebo que se haya asigando "tarea" al tecnico2
		assertTrue(tecnico2.getTareas().contains(tarea));
	}
	
	public void testReasignarTareaConAreaDeTrabajoInvalida() {
		
		Tecnico tecnicoOtraArea = new Tecnico("Estefania Caravatti", new Integer(23), new Double(2600));
		tecnicoOtraArea.getAreas().add(area2);
		
		// Reasignaci贸n de una tarea que no corresponde al 谩rea
		try{
			tecnicoOtraArea.reasignarTarea(tecnico1, tarea);
			fail("Deberia haber lanzado una ExcepcionAreaDeTrabajoInvalida");
		}
		catch(ExcepcionAreaDeTrabajoInvalida e){
			
		} catch (Exception e) {
			fail("Deberia haber lanzado una ExcepcionAreaDeTrabajoInvalida");
		}
		
		// Compruebo que no se le haya desasignado la tarea al tecnico1
		assertTrue(tecnico1.getTareas().contains(tarea));
		// Compruebo que no se le haya asignado la tarea al tecnicoOtraArea
		assertFalse(tecnicoOtraArea.getTareas().contains(tarea));
	}
	
	public void testReasignarTareaNoAsignable() {
		
		// Reasignaci贸n de una tarea en estado no asignable
		Tarea tareaNoAsignable = new Tarea(area1, practica, "descripcion");
		tareaNoAsignable.modificarEstado(EstadoTarea.EnProgreso);
		tecnico2.getTareas().add(tareaNoAsignable);
		
		try{
			tecnico1.reasignarTarea(tecnico2, tareaNoAsignable);
			fail("Deberia haber lanzado una ExcepcionTareaNoAsignable");
		}
		catch(ExcepcionTareaNoAsignable e){
			
		} catch (Exception e) {
			fail("Deberia haber lanzado una ExcepcionTareaNoAsignable");
		}
		
		// Compruebo que no se le haya desasignado la tarea al tecnico2
		assertTrue(tecnico2.getTareas().contains(tareaNoAsignable));
		// Compruebo que no se le haya asignado la tarea al tecnico1
		assertFalse(tecnico1.getTareas().contains(tareaNoAsignable));
	}

	public void testReasignarTareaYaAsignada() {
		
		// Reasignaci贸n de una tarea ya asignada
		try{
			tecnico1.reasignarTarea(tecnico1, tarea);
			fail("Deberia haber lanzado una ExcepcionTareaYaAsignada");
		}
		catch(ExcepcionTareaYaAsignada e){
			
		} catch (Exception e) {
			fail("Deberia haber lanzado una ExcepcionTareaYaAsignada");
		}
		
		assertTrue(tecnico1.getTareas().contains(tarea));
	}
	
	public void testDesasignarTarea() {
		
		tecnico1.desasignarTarea(tarea);
		
		assertFalse(tecnico1.getTareas().contains(tarea));
		assertEquals(EstadoTarea.NoAsignada, tarea.getEstado());
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
