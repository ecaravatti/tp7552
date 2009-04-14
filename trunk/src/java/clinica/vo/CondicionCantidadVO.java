package clinica.vo;

import java.io.Serializable;

public class CondicionCantidadVO implements Serializable {

	private static final long serialVersionUID = 5913984055936922077L;

	private Integer cantidadPruebas;
	
	private String frecuencia;

	public CondicionCantidadVO(Integer cantidadPruebas, String frecuencia) {
		super();
		this.cantidadPruebas = cantidadPruebas;
		this.frecuencia = frecuencia;
	}

	public Integer getCantidadPruebas() {
		return cantidadPruebas;
	}

	public void setCantidadPruebas(Integer cantidadPruebas) {
		this.cantidadPruebas = cantidadPruebas;
	}

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

}
