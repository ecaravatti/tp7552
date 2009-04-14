package laboratorio.dominio.practica.resultado;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Esta clase valida resultados de formato decimal.
 * El mismo puede ser expresado tanto con '.' como con ','. 
 */
@Entity
@DiscriminatorValue("Decimal")
public class TipoResultadoDecimal extends TipoResultado {

	@Transient
	private String TIPO = "decimal";
	
	@Override
	public Boolean validarTipoResultado(String valor) {
		valor = valor.replace(',', '.');
		try {
			new Double(valor);
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
