package com.locationguru.csf.model;

import java.sql.Timestamp;
import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

import com.locationguru.csf.model.support.BaseEntity;
import com.locationguru.csf.model.support.UserStatus;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

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
	@Generated(event = EventType.INSERT)
	@Column(name = "uid", unique = true, updatable = false, insertable = false)
	private UUID uid;

	@NotNull
	@XmlTransient
	@Column(name = "customer_id")
	private Long customerId;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;

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

	public Role getRole()
	{
		return this.role;
	}

	public void setRole(final Role role)
	{
		this.role = role;
	}

	public String getIdentity()
	{
		return this.identity;
	}

	public void setIdentity(final String identity)
	{
		this.identity = identity;
	}

	public String getFirstName()
	{
		return this.firstName;
	}

	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return this.lastName;
	}

	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}

	public void setDisplayName(final String displayName)
	{
		this.displayName = displayName;
	}

	public String getContactNumber()
	{
		return this.contactNumber;
	}

	public void setContactNumber(final String contactNumber)
	{
		this.contactNumber = contactNumber;
	}

	public String getEmail_address()
	{
		return this.email_address;
	}

	public void setEmail_address(final String contactEmail)
	{
		this.email_address = contactEmail;
	}

	public Timestamp getBirthDate()
	{
		return this.birthDate;
	}

	public void setBirthDate(final Timestamp birthDate)
	{
		this.birthDate = birthDate;
	}

	public Integer getGender()
	{
		return this.gender;
	}

	public void setGender(final Integer gender)
	{
		this.gender = gender;
	}

	public UserStatus getStatus()
	{
		return this.status;
	}

	public void setStatus(final UserStatus status)
	{
		this.status = status;
	}
}
