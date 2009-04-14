package laboratorio.dominio.practica.resultado;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("Cadena")
public class TipoResultadoCadena extends TipoResultado {
	
	@Transient
	private String TIPO = "cadena";
	
	@Override
	public Boolean validarTipoResultado(String valor) {
		if (valor == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public String getTipo() {
		return TIPO;
	}
}
