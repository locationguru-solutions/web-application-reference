package com.locationguru.csf.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.locationguru.csf.model.support.TokenStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tokens")
public class Token
{
	@Id
	@XmlTransient
	@Column(name = "id")
	@SequenceGenerator(name = "tokens_id_seq", sequenceName = "tokens_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokens_id_seq")
	private Long id;

	@NotNull
	@XmlElement
	@Column(name = "uid", unique = true, updatable = false)
	private UUID uid;

	@NotNull
	@XmlTransient
	@Column(name = "customer_id")
	private Long customerId;

	@NotNull
	@XmlTransient
	@Column(name = "user_id")
	private Long userId;

	@NotNull
	@XmlTransient
	@Column(name = "authentication_id")
	private Long authenticationId;

	@NotEmpty
	@XmlElement
	@Column(name = "identity", columnDefinition = "TEXT")
	private String identity;

	@NotNull
	@XmlElement
	@Column(name = "expected_expiration_timestamp")
	private Timestamp expectedExpirationTimestamp;

	@XmlElement
	@Column(name = "expiration_timestamp")
	private Timestamp expirationTimestamp;

	@XmlElement
	@Column(name = "expiration_date")
	@Temporal(value = TemporalType.DATE)
	private Date expirationDate;

	@NotNull
	@XmlElement
	@Column(name = "status_id")
	private TokenStatus status;

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

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(final Long userId)
	{
		this.userId = userId;
	}

	public Long getAuthenticationId()
	{
		return authenticationId;
	}

	public void setAuthenticationId(final Long authenticationId)
	{
		this.authenticationId = authenticationId;
	}

	public String getIdentity()
	{
		return identity;
	}

	public void setIdentity(final String identity)
	{
		this.identity = identity;
	}

	public Timestamp getExpectedExpirationTimestamp()
	{
		return expectedExpirationTimestamp;
	}

	public void setExpectedExpirationTimestamp(final Timestamp expectedExpirationTimestamp)
	{
		this.expectedExpirationTimestamp = expectedExpirationTimestamp;
	}

	public Timestamp getExpirationTimestamp()
	{
		return expirationTimestamp;
	}

	public void setExpirationTimestamp(final Timestamp expirationTimestamp)
	{
		this.expirationTimestamp = expirationTimestamp;
	}

	public Date getExpirationDate()
	{
		return expirationDate;
	}

	public void setExpirationDate(final Date expirationDate)
	{
		this.expirationDate = expirationDate;
	}

	public TokenStatus getStatus()
	{
		return status;
	}

	public void setStatus(final TokenStatus status)
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

	@Override
	public String toString()
	{
		return "Token{" +
					   "id=" + id +
					   ", uid=" + uid +
					   ", customerId=" + customerId +
					   ", userId=" + userId +
					   ", authenticationId=" + authenticationId +
					   ", identity='" + identity + '\'' +
					   ", expirationTimestamp=" + expirationTimestamp +
					   ", expirationDate=" + expirationDate +
					   ", status=" + status +
					   ", isActive=" + isActive +
					   ", creationTimestamp=" + creationTimestamp +
					   ", updateTimestamp=" + updateTimestamp +
					   ", deletionTimestamp=" + deletionTimestamp +
					   '}';
	}
}
