package config;

import java.time.Duration;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.cache.hazelcast")
public class HazelcastProperties
{
	private static final Logger logger = LogManager.getLogger(HazelcastProperties.class);

	/**
	 * Whether to configure Hazelcast instance in client or server mode.
	 */
	private String mode = "server";

	private String clusterName;
	private String instanceName;

	private final Client client = new Client();
	private final Server server = new Server();

	public String getMode()
	{
		return mode;
	}

	public void setMode(final String mode)
	{
		this.mode = mode;
	}

	public String getClusterName()
	{
		return clusterName;
	}

	public void setClusterName(final String clusterName)
	{
		this.clusterName = clusterName;
	}

	public String getInstanceName()
	{
		return instanceName;
	}

	public void setInstanceName(final String instanceName)
	{
		this.instanceName = instanceName;
	}

	public Client getClient()
	{
		return client;
	}

	public Server getServer()
	{
		return server;
	}

	public static class Client
	{
		private final List<String> serverAddresses = new ArrayList<>();
		private SmartRouting smartRouting = new SmartRouting();
		private Integer connectionTimeout = 5000;

		public List<String> getServerAddresses()
		{
			return serverAddresses;
		}

		public SmartRouting getSmartRouting()
		{
			return smartRouting;
		}

		public void setSmartRouting(final SmartRouting smartRouting)
		{
			this.smartRouting = smartRouting;
		}

		public Integer getConnectionTimeout()
		{
			return connectionTimeout;
		}

		public void setConnectionTimeout(final Integer connectionTimeout)
		{
			this.connectionTimeout = connectionTimeout;
		}

		public static class SmartRouting
		{
			private Boolean enabled = true;

			public Boolean getEnabled()
			{
				return enabled;
			}

			public void setEnabled(final Boolean enabled)
			{
				this.enabled = enabled;
			}
		}
	}

	public static class Server
	{
		private Integer port = 5701;
		private String primaryAddress = "127.0.0.1";
		private final List<String> secondaryAddresses = new ArrayList<>();

		private final PortAutoIncrement portAutoIncrement = new PortAutoIncrement();
		private final Cluster cluster = new Cluster();
		private final Multicast multicast = new Multicast();

		public Integer getPort()
		{
			return port;
		}

		public void setPort(final Integer port)
		{
			this.port = port;
		}

		public String getPrimaryAddress()
		{
			return primaryAddress;
		}

		public void setPrimaryAddress(final String primaryAddress)
		{
			this.primaryAddress = primaryAddress;
		}

		public List<String> getSecondaryAddresses()
		{
			return secondaryAddresses;
		}

		public PortAutoIncrement getPortAutoIncrement()
		{
			return portAutoIncrement;
		}

		public Cluster getCluster()
		{
			return cluster;
		}

		public Multicast getMulticast()
		{
			return multicast;
		}

		public static class PortAutoIncrement
		{

			private Boolean enabled = false;

			public Boolean getEnabled()
			{
				return enabled;
			}

			public void setEnabled(final Boolean enabled)
			{
				this.enabled = enabled;
			}
		}

		public static class Multicast
		{
			private Boolean enabled = false;
			private String groupName = "multicastGroup";
			private Integer port = 5710;
			private Set<String> trustedInterfaces = new HashSet<>();
			private Duration timeout = Duration.ofSeconds(5);
			private Integer timeToLive = 32;

			public Boolean getEnabled()
			{
				return enabled;
			}

			public void setEnabled(final Boolean enabled)
			{
				this.enabled = enabled;
			}

			public String getGroupName()
			{
				return groupName;
			}

			public void setGroupName(final String groupName)
			{
				this.groupName = groupName;
			}

			public Integer getPort()
			{
				return port;
			}

			public void setPort(final Integer port)
			{
				this.port = port;
			}

			public Set<String> getTrustedInterfaces()
			{
				return trustedInterfaces;
			}

			public void setTrustedInterfaces(final Set<String> trustedInterfaces)
			{
				this.trustedInterfaces = trustedInterfaces;
			}

			public Duration getTimeout()
			{
				return timeout;
			}

			public void setTimeout(final Duration timeout)
			{
				this.timeout = timeout;
			}

			public Integer getTimeToLive()
			{
				return timeToLive;
			}

			public void setTimeToLive(final Integer timeToLive)
			{
				this.timeToLive = timeToLive;
			}
		}

		public static class Cluster
		{
			private Boolean enabled = false;
			private List<String> members = new ArrayList<>();

			public Boolean getEnabled()
			{
				return enabled;
			}

			public void setEnabled(final Boolean enabled)
			{
				this.enabled = enabled;
			}

			public List<String> getMembers()
			{
				return members;
			}

			public void setMembers(final List<String> members)
			{
				this.members = members;
			}
		}
	}


}
