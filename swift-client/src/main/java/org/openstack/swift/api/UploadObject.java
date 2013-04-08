package org.openstack.swift.api;

import javax.xml.ws.Response;

import org.openstack.base.client.OpenStackClientConnector;
import org.openstack.base.client.OpenStackRequest;
import org.openstack.swift.SwiftCommand;
import org.openstack.swift.model.ObjectForUpload;

public class UploadObject implements SwiftCommand<Response>{

	private ObjectForUpload objectForUpload;
	
	public UploadObject(ObjectForUpload objectForUpload) {
		this.objectForUpload = objectForUpload;
	}
	
	@Override
	public Response execute(OpenStackClientConnector connector, OpenStackRequest request) {
//		Invocation.Builder invocationBuilder = target.path(objectForUpload.getContainer()).path(objectForUpload.getName()).request(MediaType.APPLICATION_JSON);
//		for(String key : objectForUpload.getProperties().keySet()) {
//			invocationBuilder.header("x-object-meta-" + key, objectForUpload.getProperties().get(key));
//		}
//		return invocationBuilder.put(Entity.entity(objectForUpload.getInputStream(), MediaType.APPLICATION_OCTET_STREAM), Response.class);
		return null;
	}

}