package logbook.shared.scaffold;

import logbook.server.domain.Administrator;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

@Service(Administrator.class)
public interface AdministratorRequestNonRoo extends RequestContext {
	
	abstract Request<logbook.client.managed.proxy.AdministratorProxy> findAdministratorFromSession();
}
