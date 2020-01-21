package com.locationguru.csf.job;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import config.EpicMovieQuotesJobProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Sample job for demonstrating basic setup required for a Quartz job.
 * This job simply quotes epic lines from movies.
 */
public class EpicMovieQuotesJob
		extends QuartzJobBean
{
	private static final Logger logger = LogManager.getLogger(EpicMovieQuotesJob.class);

	private final EpicMovieQuotesJobProperties properties;
	private final Map<String, List<String>> quotes = new HashMap<>();

	public EpicMovieQuotesJob(final EpicMovieQuotesJobProperties properties)
	{
		this.properties = properties;

		// Initializing epic quotes
		this.initialize();
	}

	protected void initialize()
	{
		final URL url = EpicMovieQuotesJob.class.getClassLoader().getResource(properties.getSourceFile());

		Objects.requireNonNull(url, "Source file is required");

		try
		{
			final List<String> lines = Files.readAllLines(Paths.get(url.toURI()));

			lines.replaceAll(String::strip);
			lines.removeIf(String::isBlank);

			for (final String line : lines)
			{
				final int index = line.indexOf(":");

				if (index == -1)
				{
					continue;
				}

				quotes.computeIfAbsent(line.substring(0, index).strip(), key -> new ArrayList<>())
					  .add(line.substring(index + 1).strip().replace("\\\\", System.lineSeparator()));
			}
		}
		catch (final IOException | URISyntaxException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	protected void executeInternal(final JobExecutionContext jobExecutionContext)
	{
		final Random random = new Random(System.nanoTime());
		final String author = List.copyOf(quotes.keySet()).get(random.nextInt(quotes.size()));
		final List<String> authorsQuotes = quotes.get(author);

		logger.info("{}{}{}- {}", System.lineSeparator(), authorsQuotes.get(random.nextInt(authorsQuotes.size())), System.lineSeparator(), author);
	}
}
