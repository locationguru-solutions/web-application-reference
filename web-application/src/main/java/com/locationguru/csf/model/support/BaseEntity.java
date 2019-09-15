package com.locationguru.csf.model.support;

import java.sql.Timestamp;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class BaseEntity
{
	private static final Logger logger = LogManager.getLogger(BaseEntity.class);

	@XmlTransient
	@Column(name = "is_active")
	private Boolean isActive;

	@XmlTransient
	@Column(name = "created_by")
	private Long createdBy;

	@XmlTransient
	@Column(name = "updated_by")
	private Long updatedBy;

	@XmlTransient
	@Column(name = "deleted_by")
	private Long deletedBy;

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

	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive(final Boolean active)
	{
		isActive = active;
	}

	public Long getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(final Long createdBy)
	{
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(final Long updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public Long getDeletedBy()
	{
		return deletedBy;
	}

	public void setDeletedBy(final Long deletedBy)
	{
		this.deletedBy = deletedBy;
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
