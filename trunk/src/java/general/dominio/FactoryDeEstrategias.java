package general.dominio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import laboratorio.dominio.estrategias.EstrategiaAsignacionTarea;

import clinica.dominio.reglas.Comportamiento;


public class FactoryDeEstrategias {

	private static FactoryDeEstrategias instancia;
	public static final String ESTRATEGIAS_CONFIG = "estrategias/estrategias.config";
	public static final String COMPORTAMIENTOS_CONFIG = "estrategias/comportamientos.config";
	
	/* Singleton */
	public static FactoryDeEstrategias instancia(){
		if(instancia == null)
			instancia = new FactoryDeEstrategias();
		
		return instancia;
	}
	
	public EstrategiaAsignacionTarea getEstrategia(String key) {
		return (EstrategiaAsignacionTarea) getStrategy(key, ESTRATEGIAS_CONFIG);
	}
	
	public static List<String> getListadoEstrategias() {
		return getEveryKey(ESTRATEGIAS_CONFIG);
	}
	
	public Comportamiento getComportamiento(String key) {
		return (Comportamiento) getStrategy(key, COMPORTAMIENTOS_CONFIG);
	}
	
	public static List<String> getListadoComportamientos() {
		return getEveryKey(COMPORTAMIENTOS_CONFIG);
	}
	
	private Object getStrategy(String key, String configDir) {
		Properties properties = new Properties();
		try {
			ClassLoader loader = FactoryDeEstrategias.class.getClassLoader();
			properties.load(loader.getResourceAsStream(configDir));
		} catch (IOException e) {
			return null;
		}

		String nombreClase = properties.getProperty(key);
		Class<?> clase;
		try {
			clase = Class.forName(nombreClase);
		} catch (ClassNotFoundException e) {
			return null;
		}		
		
		try {
			return clase.newInstance();
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}
	
	private static List<String> getEveryKey(String configDir) {
		List<String> list = new ArrayList<String>();
		Properties properties = new Properties();
		try {
			ClassLoader loader = FactoryDeEstrategias.class.getClassLoader();
			properties.load(loader.getResourceAsStream(configDir));
		} catch (IOException e) {
			return null;
		}
		
		Enumeration<Object> keys = properties.keys();
		while(keys.hasMoreElements()){
			list.add(keys.nextElement().toString());
		}
		
		return list;
	}
}
