package com.dp.events.models;

import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="events")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(min=1, message="please enter a valid event name")
	private String name;
	@NotNull(message = "event needs a date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	@NotNull
	@Size(min=1,message="please enter a valid location")
	private String location;
	@NotNull
	private String state;
	
	private ArrayList<String> messages = new ArrayList<String>();
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="host_id")
	private User host;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "users_events",
			joinColumns = @JoinColumn(name="event_id"),
			inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User>users;
//	======================================
	public Event() {
	}
	public Event(@NotNull @Size(min = 1, message = "please enter a valid event name") String name, @NotNull Date date,
			@NotNull @Size(min = 1, message = "please enter a valid location") String location, @NotNull String state) {
		this.name = name;
		this.date = date;
		this.location = location;
		this.state = state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public ArrayList<String> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
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
	public User getHost() {
		return host;
	}
	public void setHost(User host) {
		this.host = host;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
