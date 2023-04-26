package com.locationguru.csf.model;

import java.sql.Timestamp;
import java.util.UUID;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

import com.locationguru.csf.model.support.AuthenticationStatus;
import com.locationguru.csf.model.support.AuthenticationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.*;
import org.hibernate.generator.EventType;

@Entity
@Table(name = "authentications")
public class Authentication
{
	private static final Logger logger = LogManager.getLogger(Authentication.class);

	@Id
	@XmlTransient
	@Column(name = "id")
	@SequenceGenerator(name = "authentications_id_seq", sequenceName = "authentications_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authentications_id_seq")
	private Long id;

	@XmlElement
	@Generated(event = EventType.INSERT)
	@Column(name = "uid", unique = true, updatable = false)
	private UUID uid;

	@NotNull
	@XmlTransient
	@Column(name = "customer_id")
	private Long customerId;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@Fetch(value = FetchMode.JOIN)
	private User user;

	@NotNull
	@Column(name = "type_id")
	private AuthenticationType type;

	@NotNull
	@Column(name = "identity")
	private String identity;

	@NotNull
	@Column(name = "password")
	private String password;

	@Column(name = "expiration_timestamp")
	private Timestamp expirationTimestamp;

	@NotNull
	@Column(name = "status_id")
	private AuthenticationStatus status;

	@NotNull
	@XmlTransient
	@Column(name = "is_active")
	private Boolean isActive;

	@XmlTransient
	@CreationTimestamp
	@Column(name = "creation_timestamp")
	private Timestamp creationTimestamp;

	@XmlTransient
	@UpdateTimestamp
	@Column(name = "update_timestamp")
	private Timestamp updateTimestamp;

	@XmlTransient
	@Column(name = "deletion_timestamp")
	private Timestamp deletionTimestamp;

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

	public Long getCustomerId()
	{
		return this.customerId;
	}

	public void setCustomerId(final Long customerId)
	{
		this.customerId = customerId;
	}

	public User getUser()
	{
		return this.user;
	}

	public void setUser(final User user)
	{
		this.user = user;
	}

	public AuthenticationType getType()
	{
		return this.type;
	}

	public void setType(final AuthenticationType type)
	{
		this.type = type;
	}

	public String getIdentity()
	{
		return this.identity;
	}

	public void setIdentity(final String identity)
	{
		this.identity = identity;
	}

	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(final String password)
	{
		this.password = password;
	}

	public Timestamp getExpirationTimestamp()
	{
		return this.expirationTimestamp;
	}

	public void setExpirationTimestamp(final Timestamp expirationTimestamp)
	{
		this.expirationTimestamp = expirationTimestamp;
	}

	public AuthenticationStatus getStatus()
	{
		return this.status;
	}

	public void setStatus(final AuthenticationStatus status)
	{
		this.status = status;
	}

	public Boolean getIsActive()
	{
		return this.isActive;
	}

	public void setIsActive(final Boolean isActive)
	{
		this.isActive = isActive;
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

	public Timestamp getDeletionTimestamp()
	{
		return this.deletionTimestamp;
	}

	public void setDeletionTimestamp(final Timestamp deletionTimestamp)
	{
		this.deletionTimestamp = deletionTimestamp;
	}

	public Integer getVersion()
	{
		return this.version;
	}

	public void setVersion(final Integer version)
	{
		this.version = version;
	}
}
