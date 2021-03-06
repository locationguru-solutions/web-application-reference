package com.locationguru.csf.model;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.locationguru.csf.model.support.BaseEntity;
import com.locationguru.csf.model.support.UserStatus;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "users")
public class User
		extends BaseEntity
{
	@Id
	@XmlTransient
	@Column(name = "id")
	@SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	private Long id;

	@XmlElement
	@Generated(GenerationTime.INSERT)
	@Column(name = "uid", unique = true, updatable = false, insertable = false)
	private UUID uid;

	@NotNull
	@XmlTransient
	@Column(name = "customer_id")
	private Long customerId;

	@NotNull
	@Column(name = "role_id")
	private Long roleId;

	@NotEmpty
	@Column(name = "identity")
	private String identity;

	@NotEmpty
	@Column(name = "first_name")
	private String firstName;

	@NotEmpty
	@Column(name = "last_name")
	private String lastName;

	@NotEmpty
	@Column(name = "display_name")
	private String displayName;

	@NotNull
	@Column(name = "contact_number")
	private String contactNumber;

	@NotEmpty
	@Column(name = "email_address")
	private String email_address;

	@Column(name = "birth_date")
	private Timestamp birthDate;

	@NotNull
	@Column(name = "gender_id")
	private Integer gender;

	@Column(name = "status_id")
	private UserStatus status;

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

	public Long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(final Long roleId)
	{
		this.roleId = roleId;
	}

	public String getIdentity()
	{
		return identity;
	}

	public void setIdentity(final String identity)
	{
		this.identity = identity;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(final String displayName)
	{
		this.displayName = displayName;
	}

	public String getContactNumber()
	{
		return contactNumber;
	}

	public void setContactNumber(final String contactNumber)
	{
		this.contactNumber = contactNumber;
	}

	public String getEmail_address()
	{
		return email_address;
	}

	public void setEmail_address(final String contactEmail)
	{
		this.email_address = contactEmail;
	}

	public Timestamp getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(final Timestamp birthDate)
	{
		this.birthDate = birthDate;
	}

	public Integer getGender()
	{
		return gender;
	}

	public void setGender(final Integer gender)
	{
		this.gender = gender;
	}

	public UserStatus getStatus()
	{
		return status;
	}

	public void setStatus(final UserStatus status)
	{
		this.status = status;
	}
}
