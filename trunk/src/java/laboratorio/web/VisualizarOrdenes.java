package laboratorio.web;

import general.web.PaginaBase;
import laboratorio.presenter.VisualizarOrdenesPresenter;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class VisualizarOrdenes extends PaginaBase implements VisualizarOrdenesVista {
		
	@SpringBean(name = "visualizarOrdenesPresenter")
	private VisualizarOrdenesPresenter presenter;			
	private Integer numeroOrdenMedica;
	
	public VisualizarOrdenes() {
		presenter.setVista(this);
		presenter.inicializar();
	}

	public void inicializar() {
		Form form = new Form("form");
		
		RequiredTextField numeroOrdenMedicaIdTextField = crearNumeroOrdenMedicaIdTextField();
        Button btnSeleccionarOrden = crearBtnSeleccionarOrden();
        
		form.add(numeroOrdenMedicaIdTextField);
		form.add(btnSeleccionarOrden);
		this.add(form);
		this.add(new WebMarkupContainer("panelDatos"));
	}

	private RequiredTextField crearNumeroOrdenMedicaIdTextField() {
		RequiredTextField numeroOrdenMedicaIdTextField = new RequiredTextField("numeroOrdenMedicaId", new PropertyModel(this, "numeroOrdenMedica"), Integer.class);
		return numeroOrdenMedicaIdTextField;
	}

	private Button crearBtnSeleccionarOrden() {
		Button btnSeleccionarOrden = new Button("btnSeleccionarOrden") {			
			private static final long serialVersionUID = -6594991856524228478L;

			@Override
			public void onSubmit() {
				super.onSubmit();		
				VisualizarOrdenes.this.replace(new DatosOrden("panelDatos",numeroOrdenMedica));
			}
		};
		return btnSeleccionarOrden;
	}	
	
	public Integer getNumeroOrden() {
		return this.numeroOrdenMedica;
	}
	
}
