package config;

import java.util.List;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spi.properties.ClusterProperty;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration
{
	private static final Logger logger = LogManager.getLogger(HazelcastConfiguration.class);

	private static volatile HazelcastInstance hazelcastInstance;

	/**
	 * Creates an server instance of {@link HazelcastInstance} for caching.
	 * This instance will be used by cache manager application level caching.
	 *
	 * @param hazelcastProperties for configuring {@link HazelcastInstance}.
	 * @return {@link HazelcastInstance} instance customized according to {@link HazelcastProperties}.
	 */
	@Bean(name = "hazelcastInstance", destroyMethod = "shutdown")
	public HazelcastInstance hazelcastServerInstance(final HazelcastProperties hazelcastProperties)
	{
		if (hazelcastInstance != null)
		{
			return hazelcastInstance;
		}

		System.setProperty("hazelcast.phone.home.enabled", "false");

		final HazelcastProperties.Server properties = hazelcastProperties.getServer();
		final Config config = new Config();

		config.setClusterName(hazelcastProperties.getClusterName()); // Configuring network wide cluster name
		config.setInstanceName(hazelcastProperties.getInstanceName()); // Configuring cluster wide instance name
		config.setProperty(ClusterProperty.LOGGING_TYPE.getName(), "slf4j"); // Configuring Hazelcast to use SLF4J logging

		// Configuration network properties
		final NetworkConfig networkConfig = config.getNetworkConfig();
		final List<String> secondaryAddresses = properties.getSecondaryAddresses();

		networkConfig.setPort(properties.getPort()) // Configuring server port
					 .setPublicAddress(properties.getPrimaryAddress())
					 .setPortAutoIncrement(properties.getPortAutoIncrement().getEnabled()) // Configuring port auto-increment
					 .getInterfaces()
					 .setEnabled(!secondaryAddresses.isEmpty()) // Enabling server interfaces
					 .setInterfaces(secondaryAddresses); // Configuring secondary addresses to bind to.

		final JoinConfig join = networkConfig.getJoin();

		// Configuring multicast properties
		final HazelcastProperties.Server.Multicast multicast = properties.getMulticast();

		join.getMulticastConfig()
			.setEnabled(multicast.getEnabled()) // Enabling / disabling clustering mode
			.setMulticastGroup(multicast.getGroupName())
			.setMulticastPort(multicast.getPort())
			.setTrustedInterfaces(multicast.getTrustedInterfaces())
			.setMulticastTimeoutSeconds((int) multicast.getTimeout().toSeconds())
			.setMulticastTimeToLive(multicast.getTimeToLive());

		// Configuring clustering properties
		final HazelcastProperties.Server.Cluster cluster = properties.getCluster();

		join.getTcpIpConfig()
			.setEnabled(cluster.getEnabled()) // Enabling support for well-known members, if specified
			.setMembers(cluster.getMembers()); // Setting well-known members of the cluster

		hazelcastInstance = Hazelcast.newHazelcastInstance(config);

		logger.info("Hazelcast server instance created : {}", hazelcastInstance.getName());

		return hazelcastInstance;
	}

	/**
	 * Creates an instance of {@link CacheManager} for caching.
	 *
	 * @param hazelcastInstance pre-configured instance of {@link HazelcastInstance}.
	 * @return Customized instance of {@link CacheManager}.
	 */
	@Bean(name = "cacheManager")
	public CacheManager cacheManager(final @Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance)
	{
		return new HazelcastCacheManager(hazelcastInstance);
	}


}
