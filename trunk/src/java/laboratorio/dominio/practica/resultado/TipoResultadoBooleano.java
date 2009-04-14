package laboratorio.dominio.practica.resultado;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("Booleano")
public class TipoResultadoBooleano extends TipoResultado {

	@Transient
	private String TIPO = "booleano";
	
	@Override
	public Boolean validarTipoResultado(String valor) {
		if ("true".equalsIgnoreCase(valor) || "false".equalsIgnoreCase(valor) || "falso".equalsIgnoreCase(valor)
			|| "verdadero".equalsIgnoreCase(valor) || "f".equalsIgnoreCase(valor) || "v".equalsIgnoreCase(valor)
			|| "si".equalsIgnoreCase(valor) || "no".equalsIgnoreCase(valor) || "s".equalsIgnoreCase(valor)
			|| "n".equalsIgnoreCase(valor)) {
			return true;
		}
		return false;
	}

	@Override
	public String getTipo() {
		return TIPO;
	}

}
