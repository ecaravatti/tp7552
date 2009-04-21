package common.web;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.springframework.util.ClassUtils;

public class BasePage extends WebPage {
	
	public static final Integer PAGINACION_TABLA = 10;
	
	public BasePage() {
		// Estilos CSS. Todas las paginas que heredan de esta van a usar los estilos definidos en BasePage.css
		// y otro archivo de nombre igual a su clase.
		this.add(HeaderContributor.forCss("css/BasePage.css"));
		this.add(HeaderContributor.forCss("css/styles.css"));
		this.add(HeaderContributor.forCss("css/" + ClassUtils.getShortName(this.getClass())+ ".css"));		
}
	
}
