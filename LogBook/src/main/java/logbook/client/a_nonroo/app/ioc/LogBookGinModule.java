package logbook.client.a_nonroo.app.ioc;


import logbook.client.a_nonroo.app.request.LogBookRequestFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;


public class LogBookGinModule extends logbook.client.scaffold.ioc.ScaffoldModule {
	
	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		bind(LogBookRequestFactory.class).toProvider(RequestFactoryProvider.class).in(Singleton.class);
		bind(PlaceController.class).toProvider(PlaceControllerProvider.class).in(Singleton.class);
	}

	static class PlaceControllerProvider implements Provider<PlaceController> {

		private final PlaceController placeController;

		@Inject
		public PlaceControllerProvider(EventBus eventBus) {
			this.placeController = new PlaceController(eventBus);
		}

		public PlaceController get() {
			return placeController;
		}
	}

	static class RequestFactoryProvider implements Provider<LogBookRequestFactory> {

		private final LogBookRequestFactory requestFactory;

		@Inject
		public RequestFactoryProvider(EventBus eventBus) {
			requestFactory = GWT.create(LogBookRequestFactory.class);
			requestFactory.initialize(eventBus, new logbook.client.scaffold.request.EventSourceRequestTransport(
					eventBus));
		}

		public LogBookRequestFactory get() {
			return requestFactory;
		}
	}

}
