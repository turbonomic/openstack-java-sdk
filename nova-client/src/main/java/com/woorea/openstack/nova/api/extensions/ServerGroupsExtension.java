package com.woorea.openstack.nova.api.extensions;

import com.woorea.openstack.base.client.Entity;
import com.woorea.openstack.base.client.HttpMethod;
import com.woorea.openstack.base.client.OpenStackClient;
import com.woorea.openstack.base.client.OpenStackRequest;
import com.woorea.openstack.nova.model.ServerGroup;
import com.woorea.openstack.nova.model.ServerGroupForCreate;
import com.woorea.openstack.nova.model.ServerGroups;

public class ServerGroupsExtension {
	
	private final OpenStackClient CLIENT;
	
	public ServerGroupsExtension(OpenStackClient client) {
		CLIENT = client;
	}

	public class List extends OpenStackRequest<ServerGroups> {

		public List() {
			super(CLIENT, HttpMethod.GET, "/os-server-groups", null, ServerGroups.class);
		}

	}

	public class Create extends OpenStackRequest<ServerGroup> {

		private ServerGroupForCreate serverGroupForCreate;

		public Create(ServerGroupForCreate serverGroupForCreate) {
			super(CLIENT, HttpMethod.POST, "/os-server-groups", Entity.json(serverGroupForCreate), ServerGroup.class);
			this.serverGroupForCreate = serverGroupForCreate;
		}

	}

	public class Show extends OpenStackRequest<ServerGroup> {

		public Show(String id) {
			super(CLIENT, HttpMethod.GET, new StringBuilder("/os-server-groups/").append(id).toString(), null, ServerGroup.class);
		}

	}

	public class Delete extends OpenStackRequest<Void> {

		public Delete(String id) {
			super(CLIENT, HttpMethod.DELETE, new StringBuilder("/os-server-groups/").append(id).toString(), null, Void.class);
		}

	}

	public List list() {
		return new List();
	}

	public Create create(String name, java.util.List<String> policies) {
		return new Create(new ServerGroupForCreate(name, policies));
	}

	public Create create(String name) {
		return create(name, null);
	}

	public Show show(String id) {
		return new Show(id);
	}

	public Delete delete(String id) {
		return new Delete(id);
	}

}
