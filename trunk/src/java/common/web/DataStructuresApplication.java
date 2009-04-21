package common.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadWebRequest;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class DataStructuresApplication extends WebApplication {

	public void init() {
		super.init();
		getRequestCycleSettings().setGatherExtendedBrowserInfo(true);
		addComponentInstantiationListener(new SpringComponentInjector(this));
		getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
		getApplicationSettings().setInternalErrorPage(ErrorPage.class);
	}
	
	/**
	 * Devuelve la home page 
	 */
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}
	

	public Session newSession(Request request, Response response) {
		return new DataStructuresSession(request);
	}
	
	protected WebRequest newWebRequest(HttpServletRequest servletRequest) {
        return new UploadWebRequest(servletRequest);
    }

}
