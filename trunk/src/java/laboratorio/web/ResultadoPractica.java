package laboratorio.web;

import general.web.PaginaBase;

import java.io.Serializable;
import java.util.List;

import laboratorio.dominio.excepciones.ExcepcionResultadoInvalido;
import laboratorio.presenter.ResultadoPracticaPresenter;
import laboratorio.vo.ItemResultadoVO;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ResultadoPractica extends PaginaBase implements ResultadoPracticaVista {

	@SpringBean(name = "resultadoPracticaPresenter")
	private ResultadoPracticaPresenter presenter;
	
	private Integer codigoPractica;
	
	private String nombrePractica;
	
	private List<ItemResultadoVO> resultados;
	
	public ResultadoPractica() {
		this(null, null);
	}
	
	public ResultadoPractica(Integer codigoPractica, String nombrePractica) {
		this.codigoPractica = codigoPractica;
		this.nombrePractica = nombrePractica;
		this.presenter.setVista(this);
		this.presenter.inicializar();
	}
	
	public void inicializar() {
		this.add(new FeedbackPanel("feedback"));
		
		Form form = new Form("form");
		this.add(form);
		
		form.add(new Label("nombrePractica", this.nombrePractica));
		
		ListView listaResultados = crearResultadosListView();
		form.add(listaResultados);
		
		SubmitLink submitSubmitLink = crearSubmitSubmitLink(); 
		form.add(submitSubmitLink);
	}

	private SubmitLink crearSubmitSubmitLink() {
		SubmitLink submitSubmitLink = new SubmitLink("submit") {

			private static final long serialVersionUID = -6224818851728643177L;

			@Override
			public void onSubmit() {
				super.onSubmit();
				
				try {
					presenter.grabar();
					setResponsePage(RegistrarResultados.class);
				} catch (ExcepcionResultadoInvalido e) {
					for (String resultado: e.getResultadosInvalidos().keySet()) {
						String[] parametros = new String[2];
						parametros[0] = resultado;
						parametros[1] = e.getResultadosInvalidos().get(resultado).getTipo();
						error(new StringResourceModel(e.getClave(), ResultadoPractica.this, null, parametros).getString());
					}
				}
			}
		};
		return submitSubmitLink;
	}

	private ListView crearResultadosListView() {
		ListView listaResultados = new ListView("listaResultados",new Model((Serializable)resultados)) {
		
			private static final long serialVersionUID = 3458682075090198444L;

			@Override
			protected void populateItem(ListItem item) {
				item.add(new Label("labelItem", ((ItemResultadoVO)item.getModelObject()).getClave()));
				
				item.add(new TextField("valorItem", new PropertyModel((ItemResultadoVO)item.getModelObject(), "valor")));
			}
		};
		return listaResultados;
	}

	public List<ItemResultadoVO> getResultados() {
		return resultados;
	}

	public void setResultados(List<ItemResultadoVO> resultados) {
		this.resultados = resultados;
	}

	public void setPresenter(ResultadoPracticaPresenter presenter) {
		this.presenter = presenter;
	}

	public Integer getCodigoPractica() {
		return codigoPractica;
	}
}
