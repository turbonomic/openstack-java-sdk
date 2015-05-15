package com.woorea.openstack.nova.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServerGroups implements Iterable<ServerGroup>, Serializable {

	@JsonProperty("server_groups")
	private List<ServerGroup> list;

	/**
	 * @return the list
	 */
	public List<ServerGroup> getList() {
		return list;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServerGroups [list=" + list + "]";
	}

	@Override
	public Iterator<ServerGroup> iterator() {
		return list.iterator();
	}
	
}
