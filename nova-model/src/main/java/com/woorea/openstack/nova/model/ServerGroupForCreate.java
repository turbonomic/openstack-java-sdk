package com.woorea.openstack.nova.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName("server_group")
public class ServerGroupForCreate implements Serializable {
	
	private String name;
	
	private List<String> policies;

	public ServerGroupForCreate() {
		super();
	}
	
	public ServerGroupForCreate(String name) {
		this.name = name;
	}

	public ServerGroupForCreate(String name, List<String> policies) {
		this(name);
		this.policies = policies;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public List<String> getPolicies() {
		return policies;
	}

	/**
	 * @param description the description to set
	 */
	public void setPolicies(List<String> policies) {
		this.policies = policies;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServerGroupForCreate [name=" + name + ", policies="
				+ policies + "]";
	}
	
}
