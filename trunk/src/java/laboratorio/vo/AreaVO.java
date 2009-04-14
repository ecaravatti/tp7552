package laboratorio.vo;

import java.io.Serializable;

public class AreaVO implements Serializable {

	private static final long serialVersionUID = -4704439952681305489L;

	private Integer id;
	
	private String nombre;
	
	public AreaVO(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public AreaVO(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
