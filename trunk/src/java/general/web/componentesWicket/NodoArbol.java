package general.web.componentesWicket;

import java.io.Serializable;

public class NodoArbol implements Serializable {

	private static final long serialVersionUID = -7400991317560197383L;

	/** Id del objeto que representa el nodo */
	private Integer id;
	
	/** Nombre a mostrar en el nodo del arbol*/
	private String nombre;
	
	public NodoArbol(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public NodoArbol(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
}
