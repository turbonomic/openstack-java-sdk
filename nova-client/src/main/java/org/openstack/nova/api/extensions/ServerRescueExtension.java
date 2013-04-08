package org.openstack.nova.api.extensions;

import org.openstack.base.client.OpenStackClientConnector;
import org.openstack.base.client.OpenStackRequest;
import org.openstack.nova.NovaCommand;
import org.openstack.nova.model.ServerAction.Rescue;
import org.openstack.nova.model.ServerAction.Unrescue;

public class ServerRescueExtension {

	public class RescueServer implements NovaCommand<Void> {
		
		private Rescue action;

		private String id;
		
		public RescueServer(String id, Rescue action) {
			this.id = id;
			this.action = action;
		}

		@Override
		public Void execute(OpenStackClientConnector connector, OpenStackRequest request) {
			request.method("POST");
		    request.path("/servers/").path(id).path("/action");
		    request.header("Accept", "application/json");
		    request.json(action);
		    connector.execute(request);
			return null;
		}

	}

	public class UnrescueServer implements NovaCommand<Void> {
		
		private Unrescue action;

		private String id;
		
		public UnrescueServer(String id) {
			this.id = id;
			this.action = new Unrescue();
		}

		@Override
		public Void execute(OpenStackClientConnector connector, OpenStackRequest request) {
			request.method("POST");
		    request.path("/servers/").path(id).path("/action");
		    request.header("Accept", "application/json");
		    request.json(action);
		    connector.execute(request);
			return null;
		}

	}
	
	public RescueServer rescue(String serverId, String adminPass) {
		Rescue action = new Rescue(adminPass);
		return new RescueServer(serverId, action);
	}
	
	public UnrescueServer unrescue(String serverId) {
		return new UnrescueServer(serverId);
	}
}