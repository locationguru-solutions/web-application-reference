package com.locationguru.csf.model;

import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

import com.locationguru.csf.model.support.AccessScope;
import com.locationguru.csf.model.support.RoleType;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

@Entity
@Table(name = "roles")
public class Role
{
	@Id
	@XmlTransient
	@Column(name = "id")
	@SequenceGenerator(name = "roles_id_seq", sequenceName = "roles_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_seq")
	private Long id;

	@XmlElement
	@Generated(event = EventType.INSERT)
	@Column(name = "uid", updatable = false, insertable = false)
	private UUID uid;

	@NotNull
	@XmlElement
	@Column(name = "access_scope_id")
	private AccessScope accessScope;

	@NotNull
	@XmlElement
	@Column(name = "type_id")
	private RoleType type;

	@NotEmpty
	@XmlElement
	@Column(name = "identity")
	private String identity;

	@NotEmpty
	@XmlElement
	@Column(name = "name")
	private String name;

	@NotNull
	@XmlElement
	@Column(name = "description")
	private String description;

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

	public AccessScope getAccessScope()
	{
		return this.accessScope;
	}

	public void setAccessScope(final AccessScope accessScope)
	{
		this.accessScope = accessScope;
	}

	public RoleType getType()
	{
		return this.type;
	}

	public void setType(final RoleType type)
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

	public String getName()
	{
		return this.name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}
}

