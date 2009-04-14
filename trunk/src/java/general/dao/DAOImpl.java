package general.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



public class DAOImpl<T> extends HibernateDaoSupport implements DAO<T> {
	
	/** Es la clase sobre la que va a trabajar el DAO. */
	private Class<T> clase;

	public DAOImpl(Class<T> clase) {
		this.clase = clase;
	}

	/**
	 * Inserta el objeto 't' en la BD.
	 */
	public void insertar(T t) {
		getHibernateTemplate().save(t);
	}

	/**
	 * Actualiza el objeto 't' en la BD. El mismo ya debe existir en base.
	 */
	public void actualizar(T t) {
		getHibernateTemplate().update(t);
	}

	/**
	 * Devuelve al objeto de tipo T con el id dado.
	 * Si no existe, devuelve null
	 */
	@SuppressWarnings("unchecked")
	public T obtener(Integer id) {
		T resultado = (T) this.getHibernateTemplate().get(this.clase, id);
		return resultado;
	}

	/**
	 * Devuelve una lista con todos los objetos de tipo T
	 * en la BD.
	 */
	@SuppressWarnings("unchecked")
	public List<T> obtenerTodos() {
		return getHibernateTemplate().loadAll(this.clase);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> obtenerTodos(final String ordenarPor,final String orden,final Integer desde,final Integer cantidad) {
		List<T> resultado = (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("from " + DAOImpl.this.clase.getName() + " order by " + ordenarPor + " " + orden);
				query.setFetchSize(cantidad);
				query.setFirstResult(desde);
				return query.list();
			}
		});
		return resultado;
	}

	/**
	 * Remueve el objeto 't' de la BD.
	 */
	public void remover(T t) {
		getHibernateTemplate().delete(t);
	}

	/**
	 * Devuelve un solo objeto de tipo T, que cumpla con la condicion
	 * "WHERE parametro = valor"
	 * Si ningun objeto cumple, devuelve null
	 */
	@SuppressWarnings("unchecked")
	public T obtenerDonde(final String parametro, final String valor) {

	    T resultado = (T) getHibernateTemplate().execute(new HibernateCallback() {
	    	public Object doInHibernate(Session session) throws HibernateException, SQLException {
	                    Query query = session.createQuery("from " + DAOImpl.this.clase.getName() + " where "+ parametro + "= :value");
	                    query.setParameter("value", valor);
	                    query.setMaxResults(1);
	                    return query.uniqueResult();
	        }
	    });
	    return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> obtenerTodosDonde(final String parametro, final String valor) {
		List<T> resultado = (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("from " + DAOImpl.this.clase.getName() + " where " + parametro + "= :value");
				query.setParameter("value", valor);
				return query.list();
			}
		});
		return resultado;
	}
	
	public List<T> ejecutarQuery(final String SQLquery, final String valor) {
//		List<T> resultado = (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

//			public Object doInHibernate(Session session) throws HibernateException, SQLException {
//				SQLQuery query = session.createSQLQuery("select * from area a join tecnico_area ta on ta.area_id = a.id " +
//						"join supervisor_tecnico st on ta.tecnico_id = st.tecnico_id" +
//						"where st.tecnico_id = :parametro ");
//				query.addEntity(clase);
//				query.setInteger("parametro", 1);
//				Query query = session.createQuery("FROM Area a WHERE a IN "))
//				return query.list();
//			}
//		});
//		return resultado;
		return null;
	}
	
}
