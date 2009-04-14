package general.web;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

public class SesionClinica extends WebSession {

	private static final long serialVersionUID = 6734792360963047538L;
	
	public SesionClinica(Request request) {
		super(request);
	}

}
