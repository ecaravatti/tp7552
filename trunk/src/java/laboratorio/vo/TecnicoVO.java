package laboratorio.vo;

import java.io.Serializable;


public class TecnicoVO implements Serializable {

	private static final long serialVersionUID = -7772163872256294367L;

	private Integer id;
	private String nombre;

	public TecnicoVO() {}
	
	public TecnicoVO(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
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
