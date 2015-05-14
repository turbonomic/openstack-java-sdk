package com.woorea.openstack.cinder.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * This interface is for the action implementation in Cinder.
 */
public interface VolumeAction extends Serializable {

	/**
	 * This class is for the implementation of the action: volume migration.
	 *
	 * There are three parameters in the JSON:
	 * "Parameter": {
	 *      "host":"Parameter-host",
	 *      "volume":"Parameter-volume"
	 *      "force_host_copy":"Parameter-forceHostCopy"
	 * }
	 *
	 * The force_host_copy is a parameter to enables or disables generic host-based
	 * force-migration, which bypasses driver optimizations. Here, a false value of
	 * force_host_copy is used.
	 */
	@JsonRootName("os-migrate_volume")
	public static final class Migrate implements VolumeAction {
		@JsonProperty("volume")
		private String volume;

		@JsonProperty("host")
		private String host;

		@JsonProperty("force_host_copy")
		private boolean forceHostCopy;

		public Migrate() {
			this.forceHostCopy = false;
		}

		/**
		 * @return the hostName
		 */
		public String getHost() {
			return host;
		}

		/**
		 * @param hostName the hostName to set
		 */
		public void setHost(String host) {
			this.host = host;
		}

		/**
		 * @return the volume ID
		 */
		public String getVolume() {
			return volume;
		}

		/**
		 * @param volume the volume ID to set
		 */
		public void setVolume(String volume) {
			this.volume = volume;
		}

		/**
		 * @return the forceHostCopy
		 */
		public boolean getForceHostCopy() {
			return forceHostCopy;
		}

		/**
		 * @param forceHostCopy the forceHostCopy to set
		 */
		public void setForceHostCopy(boolean forceHostCopy) {
			this.forceHostCopy = forceHostCopy;
		}
	}
}
