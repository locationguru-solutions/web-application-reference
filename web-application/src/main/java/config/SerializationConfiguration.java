package config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

/**
 * Provides configuration of JSON and XML serializers and respective message converters.
 */
@Configuration
public class SerializationConfiguration
{
	private static final Logger logger = LogManager.getLogger(SerializationConfiguration.class);

	/**
	 * Configures instance of {@link ObjectMapper} for JSON serialization.
	 * <p>
	 * The configuration includes -
	 * - Serialization of NULL values
	 * - Formatting of output values
	 * - Required modules
	 *
	 * @return fully configured instance of {@link ObjectMapper}.
	 */
	@Bean(name = { "objectMapper", "jsonMapper" })
	public ObjectMapper jsonMapper()
	{
		final ObjectMapper objectMapper = new ObjectMapper();

		// Configuring JSON mapper
		configureMapper(objectMapper);

		return objectMapper;
	}

	/**
	 * Configures instance of {@link ObjectMapper} for XML serialization.
	 * <p>
	 * The configuration includes -
	 * - Serialization of NULL values
	 * - Formatting of output values
	 * - Required modules
	 *
	 * @return fully configured instance of {@link ObjectMapper}.
	 */
	@Bean(name = "xmlMapper")
	public XmlMapper xmlMapper()
	{
		final XmlMapper xmlMapper = new XmlMapper();

		// Configuring XML mapper
		configureMapper(xmlMapper);

		return xmlMapper;
	}

	/**
	 * Configures {@link ObjectMapper} with application defaults.
	 *
	 * @param objectMapper instance to be configured.
	 */
	private static void configureMapper(final ObjectMapper objectMapper)
	{
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // Disabling failure generation for unknown properties

		objectMapper.disable(SerializationFeature.INDENT_OUTPUT); // Disabling output formatting
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Disabling generating dates as millisecond values

		objectMapper.registerModule(new JacksonXmlModule()); // Enables serialization to XML
		objectMapper.registerModule(new JaxbAnnotationModule()); // Enables usage of JAXB annotations for XML and JSON serialization
		objectMapper.registerModule(new AfterburnerModule()); // Enables optimization of serialization keys during compile time

		objectMapper.registerModules(new Hibernate5Module()); // Enables support for serializing lazy-loaded hibernate entities
		objectMapper.registerModules(new Jdk8Module()); // Enables support for JDK 8 data types e.g. Optional
		objectMapper.registerModule(new JavaTimeModule()); // Enables serialization for Java 8 timestamps
	}

	/**
	 * Provides message converter for JSON.
	 *
	 * @return instance of message converter for JSON.
	 */
	@Bean(name = "mappingJackson2HttpMessageConverter")
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter()
	{
		final ObjectMapper jsonMapper = new ObjectMapper();

		// Configuring JSON mapper
		configureMapper(jsonMapper);

		return new MappingJackson2HttpMessageConverter(jsonMapper);
	}

	/**
	 * Provides message converter for XML.
	 *
	 * @return instance of message converter for XML.
	 */
	@Bean("mappingJackson2XmlHttpMessageConverter")
	public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter()
	{
		final XmlMapper xmlMapper = new XmlMapper();

		// Configuring XML mapper
		configureMapper(xmlMapper);

		return new MappingJackson2XmlHttpMessageConverter(xmlMapper);
	}
}
