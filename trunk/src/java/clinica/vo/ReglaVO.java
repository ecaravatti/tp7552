package clinica.vo;

import java.io.Serializable;

public class ReglaVO implements Serializable {

	private static final long serialVersionUID = -4435779804127208264L;

	private Integer id;	
	private String descripcion;
	private ComportamientoVO comportamiento;
	
	public ComportamientoVO getComportamiento() {
		return comportamiento;
	}

	public void setComportamiento(ComportamientoVO comportamiento) {
		this.comportamiento = comportamiento;
	}

	public ReglaVO(Integer id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public ReglaVO(Integer id, String descripcion, ComportamientoVO comportamientoVO) {
		this.id = id;
		this.descripcion = descripcion;
		this.comportamiento = comportamientoVO;
	}
	
	public Integer getCodigo() {
		return id;
	}

	public void setCodigo(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String nombre) {
		this.descripcion = nombre;
	}
}
