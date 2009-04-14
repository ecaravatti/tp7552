package general.dominio.excepciones;

public class ExcepcionNegocio extends RuntimeException {

	private static final long serialVersionUID = 700072956194147351L;
	
	protected String clave;
	
	public String getClave() {
		return clave;
	}
	
	public ExcepcionNegocio() {
		super();		
	}

	public boolean equals(ExcepcionNegocio e){
		return (this.getClave().equals(e.getClave()));
	}
}
