package general.dao;

import java.util.List;

public interface DAO<T> {

	/**
	 * Devuelve el objeto T con el id dado, o null si no existe en la BD.
	 */
	public T obtener(Integer id);

	/**
	 * Devuelve una lista con todos los objetos T en la BD.
	 */
	public List<T> obtenerTodos();

	/**
	 * Devuelve una lista con todos los objetos T en la BD, ordenados por el campo 'ordenarPor' en orden 'orden'
	 * (orden puede ser ASC o DESC). La lista traera 'cantidad' objetos a partir del numero 'desde'.
	 */
	public List<T> obtenerTodos(final String ordenarPor,final String orden,final Integer desde,final Integer cantidad);

	/**
	 * Agrega el objeto 't' a la BD.
	 */
	public void insertar(T t);

	/**
	 * Remueve el objeto 't' de la BD.
	 */
	public void remover(T t);

	/**
	 * Actualiza el objeto 't' en la BD.
	 */
	public void actualizar(T t);

	/**
	 * Devuelve un unico objeto T que cumple con la condicion
	 * "WHERE parametro = valor".
	 * Si ninguno cumple, devuelve null.
	 */
	public T obtenerDonde(final String parametro, final String valor);
	
	/**
	 * Devuelve una lista de los objetos de tipo T que cumplen con la condicion
	 * "WHERE parametro = valor"
	 * Si ninguno cumple, devuelve null. 
	 */
	public List<T> obtenerTodosDonde(final String parametro, final String valor);
	
	public List<T> ejecutarQuery(final String SQLquery, final String valor);
}
