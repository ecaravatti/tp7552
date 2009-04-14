package laboratorio;

import general.dominio.Practica;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import laboratorio.dominio.excepciones.ExcepcionClaveResultadoPracticaInexistente;
import laboratorio.dominio.excepciones.ExcepcionResultadoInvalido;
import laboratorio.dominio.practica.resultado.Resultado;
import laboratorio.dominio.practica.resultado.TipoResultado;
import laboratorio.dominio.practica.resultado.TipoResultadoBooleano;
import laboratorio.dominio.practica.resultado.TipoResultadoCadena;
import laboratorio.dominio.practica.resultado.TipoResultadoDecimal;
import laboratorio.dominio.practica.resultado.TipoResultadoEntero;
import laboratorio.dominio.practica.resultado.TipoResultadoPorcentual;
import clinica.dominio.Prestacion;

public class ResultadosPracticaTest extends TestCase {

	private final static String GLOBULOS_ROJOS = "Hemat�es (millones/ml)";
	private final static String GLOBULOS_BLANCOS = "Leucositos (miles/ml)";
	private final static String HEMOGLOBINA = "Hemoglobina (HGB) (g/dl)";
	private final static String HEMATOCRITO = "Hematocrito (%)";
	private final static String VIH = "virus de inmunodeficiencia humana";
	private final static String COLOR = "color";
	private final static String VALOR_INVALIDO = "Globulos verdes";
	private final static String ENTERO_INVALIDO = "entero";
	private final static String CADENA_INVALIDA = null;
	private final static String DECIMAL_INVALIDO = "23.377,3";
	private final static String BOOLEANO_INVALIDO = "falsa";
	private final static String PORCENTAJE_INVALIDO = "100.1";
	
	
	private Practica practica;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Map<String, TipoResultado> valoresResultados = new HashMap<String, TipoResultado>();
		valoresResultados.put(GLOBULOS_ROJOS, new TipoResultadoDecimal());
		valoresResultados.put(GLOBULOS_BLANCOS, new TipoResultadoDecimal());
		valoresResultados.put(HEMOGLOBINA, new TipoResultadoEntero());
		valoresResultados.put(HEMATOCRITO, new TipoResultadoPorcentual());
		valoresResultados.put(VIH, new TipoResultadoBooleano());
		valoresResultados.put(COLOR, new TipoResultadoCadena());
		
		practica = new Practica(new Prestacion(1, "An�lisis de Sangre", valoresResultados));
	}
	
	public void testModificarResultadosPositivo() {
		Map<String, String> resultados = new HashMap<String, String>();
		resultados.put(GLOBULOS_ROJOS, "4.3");
		resultados.put(GLOBULOS_BLANCOS, "7,1");
		resultados.put(HEMOGLOBINA, "14");
		resultados.put(HEMATOCRITO, "43");
		resultados.put(VIH, "false");
		resultados.put(COLOR, "carmin");
		
		practica.modificarResultados(resultados);
		
		Resultado resultado = practica.getResultado();
		assertEquals(resultado.getResultados().get(GLOBULOS_ROJOS),resultados.get(GLOBULOS_ROJOS));
		assertEquals(resultado.getResultados().get(GLOBULOS_BLANCOS),resultados.get(GLOBULOS_BLANCOS));
		assertEquals(resultado.getResultados().get(HEMOGLOBINA),resultados.get(HEMOGLOBINA));
		assertEquals(resultado.getResultados().get(HEMATOCRITO),resultados.get(HEMATOCRITO));
		assertEquals(resultado.getResultados().get(VIH),resultados.get(VIH));
		assertEquals(resultado.getResultados().get(COLOR),resultados.get(COLOR));
	}
	
	public void testModificarValorResultadoInexistente() {
		Map<String, String> resultados = new HashMap<String, String>();
		resultados.put(VALOR_INVALIDO, "4.7");
		resultados.put(GLOBULOS_BLANCOS, "6,8");
		resultados.put(COLOR, "carmin");
		
		try {
			practica.modificarResultados(resultados);
			fail("Deberia haber lanzado una ExcepcionClaveResultadoPracticaInexistente.");
		} catch (ExcepcionClaveResultadoPracticaInexistente e) {
			// Excepcion esperada.
		}
	}
	
	public void testModificarResultadoTipoEnteroInvalido() {
		Map<String, String> resultados = new HashMap<String, String>();
		resultados.put(GLOBULOS_ROJOS, "2345");
		resultados.put(HEMOGLOBINA, ENTERO_INVALIDO);
		resultados.put(COLOR, "carmin");
		
		try {
			practica.modificarResultados(resultados);
			fail("Deberia haber lanzado una ExcepcionResultadoInvalido.");
		} catch (ExcepcionResultadoInvalido e) {
			// Excepcion esperada.
		}
	}
	
	public void testModificarResultadoTipoCadenaInvalida() {
		Map<String, String> resultados = new HashMap<String, String>();
		resultados.put(GLOBULOS_ROJOS, "2345");
		resultados.put(GLOBULOS_BLANCOS, "346556");
		resultados.put(COLOR, CADENA_INVALIDA);
		
		try {
			practica.modificarResultados(resultados);
			fail("Deberia haber lanzado una ExcepcionResultadoInvalido.");
		} catch (ExcepcionResultadoInvalido e) {
			// Excepcion esperada.
		}
	}
	
	public void testModificarResultadoTipoDecimalInvalido() {
		Map<String, String> resultados = new HashMap<String, String>();
		resultados.put(GLOBULOS_ROJOS, "2345");
		resultados.put(GLOBULOS_BLANCOS, DECIMAL_INVALIDO);
		
		try {
			practica.modificarResultados(resultados);
			fail("Deberia haber lanzado una ExcepcionResultadoInvalido.");
		} catch (ExcepcionResultadoInvalido e) {
			// Excepcion esperada.
		}
	}
	
	public void testModificarResultadoTipoBooleanoInvalido() {
		Map<String, String> resultados = new HashMap<String, String>();
		resultados.put(GLOBULOS_ROJOS, "11");
		resultados.put(VIH, BOOLEANO_INVALIDO);
		
		try {
			practica.modificarResultados(resultados);
			fail("Deberia haber lanzado una ExcepcionResultadoInvalido.");
		} catch (ExcepcionResultadoInvalido e) {
			// Excepcion esperada.
		}
	}
	
	public void testModificarResultadoTipoPorcentualInvalido() {
		Map<String, String> resultados = new HashMap<String, String>();
		resultados.put(GLOBULOS_ROJOS, "11");
		resultados.put(HEMATOCRITO, PORCENTAJE_INVALIDO);
		
		try {
			practica.modificarResultados(resultados);
			fail("Deberia haber lanzado una ExcepcionResultadoInvalido.");
		} catch (ExcepcionResultadoInvalido e) {
			// Excepcion esperada.
		}
	}
	
}
