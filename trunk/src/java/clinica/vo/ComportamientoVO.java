package clinica.vo;

import java.io.Serializable;

public class ComportamientoVO implements Serializable {

	private static final long serialVersionUID = -3194509795637826346L;

	private Integer id;	
	private String descripcion;
		
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ComportamientoVO(Integer id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}