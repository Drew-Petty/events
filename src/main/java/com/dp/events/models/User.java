package com.dp.events.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.dp.events.annotation.MatchPassword;
import com.dp.events.annotation.UniqueEmail;

@Entity
@Table(name="users")
@MatchPassword(message = "password confirmation must match password")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min=1,message = "Please enter a valid first name")
	private String firstName;
	@NotNull
	@Size(min=1,message = "Please enter a valid last name")
	private String lastName;
	@NotNull
	@Email(message = "Email must be valid")
	@Size(min=1, message = "please enter a valid email")
	@UniqueEmail(message="Email already registered")
	private String email;
	@NotNull
	@Size(min=1, message = "please enter a valid location")
	private String location;
	@NotNull
	private String state;
	@NotNull
	@Size(min=8, message = "password must be at least 8 characters")
	private String password;
	@Transient
	private String passwordConfirm;
//	======================================
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
//	======================================
	@OneToMany(mappedBy = "host",fetch = FetchType.LAZY)
	private List<Event> hostedEvents;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "users_events",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="event_id"))
	private List<Event>events;
//	======================================
	public User() {
	}
	
	public User(@NotNull @Size(min = 1, message = "Please enter a valid first name") String firstName,
			@NotNull @Size(min = 1, message = "Please enter a valid last name") String lastName,
			@NotNull @Email(message = "Email must be valid") @Size(min = 1, message = "please enter a valid email") String email,
			@NotNull @Size(min = 1, message = "please enter a valid location") String location, @NotNull String state,
			@NotNull @Size(min = 8, message = "password must be at least 8 characters") String password,
			String passwordConfirm) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.location = location;
		this.state = state;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}
	//	======================================
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<Event> getHostedEvents() {
		return hostedEvents;
	}
	public void setHostedEvents(List<Event> hostedEvents) {
		this.hostedEvents = hostedEvents;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
}
