package com.woorea.openstack.nova.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName("server_group")
public class ServerGroup implements Serializable {
	
	private String id;
	
	private String name;
	
	private List<String> policies;
	
	private List<String> members;
	
	private Map<String, String> metadata;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the policies
	 */
	public List<String> getPolicies() {
		return policies;
	}

	/**
	 * @return the members
	 */
	public List<String> getMembers() {
		return members;
	}
	
	/**
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServerGroup [id=" + id + ", name=" + name + ", policies="
				+ policies + ", members=" + members + ", metadata=" + metadata
				+ "]";
	}
	
}
