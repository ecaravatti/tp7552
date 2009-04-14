package clinica.vo;

import java.io.Serializable;

public class PrestacionVO implements Serializable {

	private static final long serialVersionUID = -7494695460128445336L;

	private Integer codigo;
	
	private String descripcion;
	
	public PrestacionVO(Integer codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer id) {
		this.codigo = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String nombre) {
		this.descripcion = nombre;
	}
}
