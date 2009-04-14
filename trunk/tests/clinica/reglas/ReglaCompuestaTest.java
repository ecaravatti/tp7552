package clinica.reglas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import clinica.dominio.excepciones.ExcepcionReglaCompuestaSinComportamiento;
import clinica.dominio.reglas.Comportamiento;
import clinica.dominio.reglas.Frecuencia;
import clinica.dominio.reglas.Regla;
import clinica.dominio.reglas.ReglaCompuesta;
import clinica.dominio.reglas.comportamientos.ComportamientoAND;
import clinica.dominio.reglas.comportamientos.ComportamientoOR;
import clinica.dominio.reglas.condiciones.CondicionCantidad;
import clinica.dominio.reglas.condiciones.CondicionCarencia;
import clinica.dominio.reglas.condiciones.CondicionCombinacion;

public class ReglaCompuestaTest extends TestCase {

	private ReglaCompuesta unaRegla;
	private Comportamiento unComportamiento;
	
	protected void setUp() throws Exception {
		super.setUp();		
		unComportamiento= new ComportamientoAND();
		unaRegla = new ReglaCompuesta (unComportamiento);
	}
	
	public void tearDown() throws Exception {
	}
	
	public void testConstructorRegla() {		
		assertEquals(unaRegla.getComportamiento(),unComportamiento);
	}
	
	public void testInsertarCondiciones() {
		//Creo dos reglas simples
		Regla regla1 = new CondicionCantidad(2, Frecuencia.BIMESTRAL);
		Regla regla2 = new CondicionCarencia(4);
		//Las inserto a la regla compuesta
		unaRegla.agregarRegla(regla1);
		unaRegla.agregarRegla(regla2);		
		Iterator<Regla> unIterador = unaRegla.obtenerIteratorReglas();
		//Verifico que la insercion sea Correcta
		assertEquals(regla1,unIterador.next());
		assertEquals(regla2,unIterador.next());				
	}
	
	public void testModificarComportamiento() {
		//Creo un nuevo Comportamiento y lo agrego a la regla
		Comportamiento nuevoComportamiento = new ComportamientoOR();
		unaRegla.setComportamiento(nuevoComportamiento);
		assertEquals(unaRegla.getComportamiento(),nuevoComportamiento);
	}
	
	public void testEjecutarReglaSinCondicion() {
		unaRegla.setComportamiento(null);		
		try {
			unaRegla.evaluar(null,null);
			fail("Deberia haber lanzado una ExcepcionReglaCompuestaSinComportamiento.");
		} catch (ExcepcionReglaCompuestaSinComportamiento e) {
			// Excepcion esperada.
		}
	}
	
	public void testCrearReglaCompuesta() {
		//Creo una regla Compuesta
		Regla regla1 = new CondicionCantidad(4, Frecuencia.MENSUAL);
		Regla regla2 = new CondicionCarencia(1);
		Comportamiento otroComportamiento = new ComportamientoOR();
		Regla nuevaReglaCompuesta = new ReglaCompuesta(otroComportamiento);
		nuevaReglaCompuesta.agregarRegla(regla1);
		nuevaReglaCompuesta.agregarRegla(regla2);
		//Creo una Regla Simple
		List<Integer> codigos = new ArrayList<Integer>();
		codigos.add(1);
		codigos.add(2);
		Regla reglaSimple = new CondicionCombinacion(2, codigos);
		
		//Las inserto a la regla compuesta 
		unaRegla.agregarRegla(nuevaReglaCompuesta);
		unaRegla.agregarRegla(reglaSimple);
		
		Iterator<Regla> unIterador = unaRegla.obtenerIteratorReglas();
		//Verifico que la insercion sea Correcta de las reglas y de los comportamientos
		Regla reglaCompuesta= unIterador.next();
		assertEquals(nuevaReglaCompuesta,reglaCompuesta);
		assertEquals(otroComportamiento,((ReglaCompuesta) reglaCompuesta).getComportamiento());
		Iterator<Regla> otroIterador=reglaCompuesta.obtenerIteratorReglas();
		assertEquals(regla1,otroIterador.next());
		assertEquals(regla2,otroIterador.next());
		//Verfico que se inserte la Regla simple
		assertEquals(reglaSimple,unIterador.next());	
	}

}
