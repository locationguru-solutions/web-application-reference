package config;

import java.util.List;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spi.properties.ClusterProperty;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ)
public class HazelcastConfiguration
{
	private static final Logger logger = LogManager.getLogger(HazelcastConfiguration.class);

	/**
	 * Creates an server instance of {@link HazelcastInstance} for caching.
	 * This instance will be used by cache manager application level caching.
	 *
	 * @param hazelcastProperties for configuring {@link HazelcastInstance}.
	 * @return {@link HazelcastInstance} instance customized according to {@link HazelcastProperties}.
	 */
	@Bean(name = "hazelcastInstance", destroyMethod = "shutdown")
	@ConditionalOnProperty(prefix = "application.cache.hazelcast", name = "mode", havingValue = "client")
	public HazelcastInstance hazelcastClientInstance(final HazelcastProperties hazelcastProperties)
	{
		System.setProperty("hazelcast.phone.home.enabled", "false");

		final HazelcastProperties.Client properties = hazelcastProperties.getClient();
		final ClientConfig config = new ClientConfig();

		config.setClusterName(hazelcastProperties.getClusterName()); // Configuring name of the cluster to which client will connect
		config.setInstanceName(hazelcastProperties.getInstanceName()); // Configuring name of the instance to which client will connect
		config.setProperty(ClusterProperty.LOGGING_TYPE.getName(), "slf4j"); // Configuring Hazelcast to use SLF4J logging

		// Configuring client connection retry properties
		config.getConnectionStrategyConfig()
			  .getConnectionRetryConfig()
			  .setClusterConnectTimeoutMillis(Long.MAX_VALUE); // Retry connecting to cluster indefinitely

		// Configuration network properties
		final ClientNetworkConfig networkConfig = config.getNetworkConfig();

		networkConfig.setAddresses(properties.getServerAddresses()) // Configuring addresses of servers in cluster
					 .setSmartRouting(properties.getSmartRouting().getEnabled()) // Enabling / disabling smart routing of requests to nearest server
					 .setConnectionTimeout(properties.getConnectionTimeout()); // Configuring client connection timeout

		final HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(config);

		logger.info("Hazelcast client instance created : {}", hazelcastInstance.getName());

		return hazelcastInstance;
	}

	/**
	 * Creates an server instance of {@link HazelcastInstance} for caching.
	 * This instance will be used by cache manager application level caching.
	 *
	 * @param hazelcastProperties for configuring {@link HazelcastInstance}.
	 * @return {@link HazelcastInstance} instance customized according to {@link HazelcastProperties}.
	 */
	@Bean(name = "hazelcastInstance", destroyMethod = "shutdown")
	@ConditionalOnProperty(prefix = "application.cache.hazelcast", name = "mode", havingValue = "server", matchIfMissing = true)
	public HazelcastInstance hazelcastServerInstance(final HazelcastProperties hazelcastProperties)
	{
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

		final HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);

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
