package common.web;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

public class DataStructuresSession extends WebSession {

	private static final long serialVersionUID = 6734792360963047538L;
	
	public DataStructuresSession(Request request) {
		super(request);
	}

}
