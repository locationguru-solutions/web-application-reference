package config;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HazelcastConfigurationTest
{
	@Test
	public void testClusterMemberConfiguration()
	{
		final HazelcastProperties.Server server = new HazelcastProperties.Server();

		server.setPrimaryAddress("127.0.0.1");
		server.setPort(5701);
		server.getCluster().setMembers(List.of("127.0.0.1:5701", "127.0.0.1:[5702;5706]", "127.0.0.1:[5703-5705]"));

		Assertions.assertEquals(List.of("127.0.0.1:5701", "127.0.0.1:5702"),
				HazelcastConfiguration.createMembers("127.0.0.1", new String[]{ "5701", "5702" }));

		Assertions.assertEquals(List.of("127.0.0.1:5701", "127.0.0.1:5702", "127.0.0.1:5703"),
				HazelcastConfiguration.createMembers("127.0.0.1:[5701;5702;5703]"));

		Assertions.assertEquals(List.of("127.0.0.1:5701", "127.0.0.1:5702", "127.0.0.1:5703"),
				HazelcastConfiguration.createMembers("127.0.0.1:[5701-5703]"));

		Assertions.assertEquals(List.of("127.0.0.1:5701"),
				HazelcastConfiguration.createMembers("127.0.0.1", "5701"));

		Assertions.assertEquals(List.of("127.0.0.1:5701", "127.0.0.1:5702"),
				HazelcastConfiguration.createMembers("127.0.0.1", "5701-5702"));

		Assertions.assertEquals(List.of("127.0.0.1:5701", "127.0.0.1:5702", "127.0.0.1:5703", "127.0.0.1:5704", "127.0.0.1:5705"),
				HazelcastConfiguration.createMembers("127.0.0.1:[5701;5702-5704;5705]"));

		final List<String> members = HazelcastConfiguration.createMembers(server);

		Assertions.assertEquals(List.of("127.0.0.1:5702", "127.0.0.1:5706", "127.0.0.1:5703", "127.0.0.1:5704", "127.0.0.1:5705"), members);
	}
}