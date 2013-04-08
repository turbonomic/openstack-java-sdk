package org.openstack.quantum.api.networks;

import org.openstack.base.client.OpenStackClientConnector;
import org.openstack.base.client.OpenStackRequest;
import org.openstack.quantum.client.QuantumCommand;
import org.openstack.quantum.model.Network;

public class ShowNetwork implements QuantumCommand<Network> {
	
	private String id;
	
	public ShowNetwork(String id) {
		this.id = id;
	}
	
	public Network execute(OpenStackClientConnector connector, OpenStackRequest request) {
//		return target.path("v2.0").path("networks").path(id).request(MediaType.APPLICATION_JSON).get(Network.class);
		return null;
	}

}