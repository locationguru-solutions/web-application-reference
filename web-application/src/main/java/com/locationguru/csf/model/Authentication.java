package com.locationguru.csf.model;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.locationguru.csf.model.support.AuthenticationStatus;
import com.locationguru.csf.model.support.AuthenticationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.*;

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
	@Generated(GenerationTime.INSERT)
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
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public UUID getUid()
	{
		return uid;
	}

	public void setUid(final UUID uid)
	{
		this.uid = uid;
	}

	public Long getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(final Long customerId)
	{
		this.customerId = customerId;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(final User user)
	{
		this.user = user;
	}

	public AuthenticationType getType()
	{
		return type;
	}

	public void setType(final AuthenticationType type)
	{
		this.type = type;
	}

	public String getIdentity()
	{
		return identity;
	}

	public void setIdentity(final String identity)
	{
		this.identity = identity;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(final String password)
	{
		this.password = password;
	}

	public Timestamp getExpirationTimestamp()
	{
		return expirationTimestamp;
	}

	public void setExpirationTimestamp(final Timestamp expirationTimestamp)
	{
		this.expirationTimestamp = expirationTimestamp;
	}

	public AuthenticationStatus getStatus()
	{
		return status;
	}

	public void setStatus(final AuthenticationStatus status)
	{
		this.status = status;
	}

	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive(final Boolean isActive)
	{
		this.isActive = isActive;
	}

	public Timestamp getCreationTimestamp()
	{
		return creationTimestamp;
	}

	public void setCreationTimestamp(final Timestamp creationTimestamp)
	{
		this.creationTimestamp = creationTimestamp;
	}

	public Timestamp getUpdateTimestamp()
	{
		return updateTimestamp;
	}

	public void setUpdateTimestamp(final Timestamp updateTimestamp)
	{
		this.updateTimestamp = updateTimestamp;
	}

	public Timestamp getDeletionTimestamp()
	{
		return deletionTimestamp;
	}

	public void setDeletionTimestamp(final Timestamp deletionTimestamp)
	{
		this.deletionTimestamp = deletionTimestamp;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(final Integer version)
	{
		this.version = version;
	}
}
