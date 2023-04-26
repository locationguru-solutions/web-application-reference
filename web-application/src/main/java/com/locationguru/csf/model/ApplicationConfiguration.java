package com.locationguru.csf.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlTransient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.*;
import org.hibernate.generator.EventType;

@Entity
@Table(name = "application_configurations")
public class ApplicationConfiguration
		implements Serializable
{
	private static final Logger logger = LogManager.getLogger(ApplicationConfiguration.class);

	@Id
	@XmlTransient
	@Column(name = "id")
	@SequenceGenerator(name = "application_configurations_id_seq", sequenceName = "application_configurations_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_configurations_id_seq")
	private Long id;

	@Generated(event = EventType.INSERT)
	@Column(name = "uid", insertable = false, updatable = false)
	private UUID uid;

	@NotNull
	@NotEmpty
	@Column(name = "property")
	private String property;

	@NotNull
	@NotEmpty
	@Column(name = "value")
	private String value;

	@XmlTransient
	@CreationTimestamp
	@Column(name = "creation_timestamp")
	private Timestamp creationTimestamp;

	@XmlTransient
	@UpdateTimestamp
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	@Version
	@XmlTransient
	@Column(name = "version")
	private Integer version;

	public Long getId()
	{
		return this.id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public UUID getUid()
	{
		return this.uid;
	}

	public void setUid(final UUID uid)
	{
		this.uid = uid;
	}

	public String getProperty()
	{
		return this.property;
	}

	public void setProperty(final String property)
	{
		this.property = property;
	}

	public String getValue()
	{
		return this.value;
	}

	public void setValue(final String value)
	{
		this.value = value;
	}

	public Integer getVersion()
	{
		return this.version;
	}

	public void setVersion(final Integer version)
	{
		this.version = version;
	}

	public Timestamp getCreationTimestamp()
	{
		return this.creationTimestamp;
	}

	public void setCreationTimestamp(final Timestamp creationTimestamp)
	{
		this.creationTimestamp = creationTimestamp;
	}

	public Timestamp getUpdateTimestamp()
	{
		return this.updateTimestamp;
	}

	public void setUpdateTimestamp(final Timestamp updateTimestamp)
	{
		this.updateTimestamp = updateTimestamp;
	}
}
