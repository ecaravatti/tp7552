package clinica;
import junit.framework.TestCase;
import clinica.dominio.Cobertura;
import clinica.dominio.Plan;
import clinica.dominio.Prestacion;
import clinica.dominio.reglas.Comportamiento;
import clinica.dominio.reglas.Frecuencia;
import clinica.dominio.reglas.Regla;
import clinica.dominio.reglas.ReglaCompuesta;
import clinica.dominio.reglas.comportamientos.ComportamientoAND;
import clinica.dominio.reglas.comportamientos.ComportamientoOR;
import clinica.dominio.reglas.condiciones.CondicionCantidad;
import clinica.dominio.reglas.condiciones.CondicionCarencia;

public class PlanTest extends TestCase {
	
	private Plan unPlan;
	private Prestacion unaPrestacion;

	protected void setUp() throws Exception {
		super.setUp();
		unPlan = new Plan();
		unaPrestacion = new Prestacion();
		unaPrestacion.setCodigo(1);		
		unaPrestacion.setDescripcion("Colposcop�a");
		unPlan.agregarPrestacion(unaPrestacion);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
		
	public void testPrestacionEnPlan() {						
		assertEquals(unaPrestacion.getCodigo(), unPlan.buscarPrestacion(1).getCodigo());		
		assertEquals(unaPrestacion.getDescripcion(), unPlan.buscarPrestacion(1).getDescripcion());
		assertEquals(unaPrestacion, unPlan.buscarPrestacion(1));
		//Para probar si el objeto es exactamente el mismo que inserte
		assertSame(unaPrestacion, unPlan.buscarPrestacion(1));
	}
	
	/*
	 * Creo una regla Compuesta y se la agrego a un prestacion, verifico que la insercion
	 * de la regla haya sido Correcta
	 */
	public void testInsertarReglaCompuestaAPrestacion() {
		Comportamiento comportamientoAnd = new ComportamientoAND();
		ReglaCompuesta reglaCompuesta = new ReglaCompuesta(comportamientoAnd);
		Regla condicionCantidad = new CondicionCantidad(3, Frecuencia.DIARIO);
		Regla condicionCarencia = new CondicionCarencia(1);
		reglaCompuesta.agregarRegla(condicionCantidad);
		reglaCompuesta.agregarRegla(condicionCarencia);
		Comportamiento comportamientoOr = new ComportamientoOR();
		unPlan.agregarReglaAPrestacion(reglaCompuesta, unaPrestacion, comportamientoOr);
		
		assertSame(reglaCompuesta, unPlan.buscarCobertura(1).getRegla().obtenerIteratorReglas().next());		
		assertSame(condicionCantidad, unPlan.buscarCobertura(1).getRegla().obtenerIteratorReglas().next().obtenerIteratorReglas().next());		
		reglaCompuesta = (ReglaCompuesta) unPlan.buscarCobertura(1).getRegla();
		assertSame(comportamientoOr , reglaCompuesta.getComportamiento());
				
	}
	
	public void testInsertarReglaSimpleAPrestacion() {
		Regla condicionCarencia = new CondicionCarencia(5);
		Cobertura unaCobertura = unPlan.buscarCobertura(unaPrestacion.getCodigo());
		unaCobertura.agregarRegla(condicionCarencia, new ComportamientoAND());
		
		assertSame(condicionCarencia, unPlan.buscarCobertura(1).getRegla().obtenerIteratorReglas().next());		
	}
	
}
