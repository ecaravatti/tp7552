package laboratorio.vo;

import java.io.Serializable;

public class ItemResultadoVO implements Serializable {
	
	private static final long serialVersionUID = -5877108595033734796L;

	private String clave;
	
	private String valor;
	
	public ItemResultadoVO() {
	}

	public ItemResultadoVO(String clave) {
		this.clave = clave;
	}
	
	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getClave() {
		return clave;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
}
