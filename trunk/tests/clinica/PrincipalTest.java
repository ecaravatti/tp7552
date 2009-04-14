package clinica;
import general.dominio.Practica;

import java.util.Date;

import junit.framework.TestCase;
import clinica.dominio.OrdenMedica;
import clinica.dominio.Plan;
import clinica.dominio.Prestacion;
import clinica.dominio.paciente.HistoriaClinica;
import clinica.dominio.paciente.Paciente;
import clinica.dominio.reglas.Comportamiento;
import clinica.dominio.reglas.Frecuencia;
import clinica.dominio.reglas.Regla;
import clinica.dominio.reglas.ReglaCompuesta;
import clinica.dominio.reglas.comportamientos.ComportamientoAND;
import clinica.dominio.reglas.comportamientos.ComportamientoOR;
import clinica.dominio.reglas.condiciones.CondicionCantidad;
import clinica.dominio.reglas.condiciones.CondicionCombinacion;


public class PrincipalTest extends TestCase {
	
	private Plan nuevoPlan;
	private Prestacion nuevaPrestacion;
	private Prestacion otraPrestacion;
	private Practica practica;
	private Practica otraPractica;
	private CondicionCantidad condicion;
	private CondicionCantidad condicion2;
	private CondicionCombinacion condicion3;
	private Regla regla;
	private Regla regla2;
	private Paciente paciente;
	private HistoriaClinica hClinica;
//	private OrdenMedica ordenMedica;
	private Comportamiento comportamiento;


	public void setUp() throws Exception {

		// Generaci�n de un Plan
		nuevoPlan = new Plan();
		nuevoPlan.setDescripcion("Plan A");
		
		nuevaPrestacion = new Prestacion();
		nuevaPrestacion.setCodigo(001);
		nuevaPrestacion.setDescripcion("Colposcop�a");

		nuevoPlan.agregarPrestacion(nuevaPrestacion);
		
		condicion = new CondicionCantidad(2, Frecuencia.ANUAL);
				
		comportamiento = new ComportamientoAND();
		regla = new ReglaCompuesta(comportamiento);
		regla.agregarRegla(condicion);
				
		nuevoPlan.agregarReglaAPrestacion(regla, nuevaPrestacion, new ComportamientoOR());
		
		// Otra Prestaci�n
		otraPrestacion = new Prestacion();
		otraPrestacion.setCodigo(003);
		otraPrestacion.setDescripcion("Electrocardiograma");
		// Le Agrego la prestacion al plan
		nuevoPlan.agregarPrestacion(otraPrestacion);

		
		// Nueva Condicion
		condicion2 = new CondicionCantidad(1, Frecuencia.MENSUAL);

		// Nueva Condicion
		condicion3 = new CondicionCombinacion(1, 006);

		// Nueva Regla
		comportamiento = new ComportamientoOR();
		regla2 = new ReglaCompuesta(comportamiento);
		regla2.agregarRegla(condicion2);
		regla2.agregarRegla(condicion3);
		// Asocio una Regla a una prestacion
		nuevoPlan.agregarReglaAPrestacion(regla2, otraPrestacion, new ComportamientoAND());

	
		// Paciente
		paciente = new Paciente();
		paciente.setPlanMedico(nuevoPlan);
		
		// Nueva Historia Clinica
		hClinica = new HistoriaClinica();		
		
		// Nueva Practica
		practica = new Practica();		
		practica.setPrestacion(nuevaPrestacion);
		practica.setFechaRealizacion(new Date());
		practica.setFechaResultado(new Date());
		hClinica.agregarPractica(practica);				
		
		// Otra Practica
		otraPractica = new Practica();
		otraPractica.setPrestacion(otraPrestacion);
		otraPractica.setFechaRealizacion(new Date());
		otraPractica.setFechaResultado(new Date());
		hClinica.agregarPractica(otraPractica);
		
		// Asocio la HC al Paciente
		paciente.setHistClinica(hClinica);
		
	}

	public void tearDown() throws Exception {
	}
	
	public void testAutorizarOrdenMedica() {
		OrdenMedica nOrdenMedica = new OrdenMedica();
		nOrdenMedica.setPaciente(paciente);
		nOrdenMedica.agregarPractica(practica);
		nOrdenMedica.agregarPractica(otraPractica);
		nOrdenMedica.autorizar();
		// Verifica Que La Orden Medica Quede Aceptada
		assertTrue(nOrdenMedica.estaAprobada());
	}
}
