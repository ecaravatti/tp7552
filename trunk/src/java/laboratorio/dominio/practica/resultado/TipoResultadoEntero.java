package laboratorio.dominio.practica.resultado;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("Entero")
public class TipoResultadoEntero extends TipoResultado {

	@Transient
	private String TIPO = "entero";
	
	@Override
	public Boolean validarTipoResultado(String valor) {
		try {
			new Integer(valor);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public String getTipo() {
		return TIPO;
	}

}
