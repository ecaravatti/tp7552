package general.dominio.excepciones;

public class ExcepcionAplicacion extends RuntimeException {

	private static final long serialVersionUID = 700072956194147351L;
	
	protected String clave;

	public String getClave() {
		return clave;
	}

	public ExcepcionAplicacion() {
		super();		
	}
}
