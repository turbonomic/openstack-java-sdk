package com.woorea.openstack.base.client;


public interface OpenStackClientConnector {

	public <T> OpenStackResponse request(OpenStackRequest<T> request);

	public void setHttpConnectionTimeout(int httpConnectionTimeout);

	public int getHttpConnectionTimeout();

	public void setHttpSocketTimeout(int httpSocketTimeout);

	public int getHttpSocketTimeout();
}
