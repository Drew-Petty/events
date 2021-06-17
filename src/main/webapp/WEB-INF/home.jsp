<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Events</title>
<link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
	<div class="row justify-content-between">
	<h1 class="col-auto">Welcome <c:out value="${user.firstName}"/></h1>
	<a class="col-auto" href="/logout">Log Out</a>
	</div>
	<h3>Here are some events in your state:</h3>
	<table class="table w-50">
		<tr>
			<th>Name</th>
			<th>Date</th>
			<th>Location</th>
			<th>Host</th>
			<th>Action/Status</th>
		</tr>
		<c:forEach items="${inState}" var="event">
			<tr>
				<td><a href="/event/${event.id}"><c:out value="${event.name}"/></a></td>
				<td><fmt:formatDate value="${event.date}" type="date"/></td>
				<td><c:out value="${event.location}"/></td>
				<td><c:out value="${event.host.firstName}"/></td>
				<td>
					<c:if test="${event.host.id ==user.id }">
						<a href="/edit/${event.id}">Edit</a>
						<a href="/delete/${event.id}">Delete</a>
					</c:if>
					<c:if test="${event.host.id != user.id}">
						<c:set var="contains" value="false"/>
						<c:forEach var="attendee" items="${event.users}" >
							<c:if test="${attendee.id == user.id}">
								<c:set var="contains" value="true"/>
							</c:if>
						</c:forEach>
						<c:if test="${contains == false}">
							<a href="/join/${event.id}">Join</a>
						</c:if>
					<c:if test="${contains == true}">
						<a href="/cancel/${event.id}">Cancel</a>
					</c:if>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<h3>Here are some of the events in other states:</h3>
	<table class="table w-50">
		<tr>
			<th>Name</th>
			<th>Date</th>
			<th>Location</th>
			<th>State</th>
			<th>Host</th>
			<th>Action</th>
		</tr>
		<c:forEach items="${outOfState}" var="event">
			<tr>
				<td><a href="/event/${event.id}"><c:out value="${event.name}"/></a></td>
				<td><fmt:formatDate value="${event.date}" type="date"/></td>
				<td><c:out value="${event.location}"/></td>
				<td><c:out value="${event.state}"/></td>
				<td><c:out value="${event.host.firstName}"/></td>
				<td>
					<c:if test="${event.host.id ==user.id }">
						<a href="/edit/${event.id}">Edit</a>
						<a href="/delete/${event.id}">Delete</a>
					</c:if>
					<c:if test="${event.host.id != user.id}">
					<c:set var="contains" value="false"/>
					<c:forEach var="attendee" items="${event.users}" >
						<c:if test="${attendee.id == user.id}">
							<c:set var="contains" value="true"/>
						</c:if>
					</c:forEach>
				
					<c:if test="${contains == false}">
						<a href="/join/${event.id}">Join</a>
					</c:if>
					<c:if test="${contains == true}">
						<a href="/cancel/${event.id}">Cancel</a>
					</c:if>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<form:form class="w-50" action="/createEvent" method="post" modelAttribute="event">
		<form:hidden value="${user.id}" path="host"/>
		<fieldset>
			<legend>Create an Event</legend>
			<div class="row">
				<form:label class="col" path="name">Name:</form:label>
				<form:input class="col form-control" path="name"/>
			</div>
			<div class="row">
				<form:label class="col" path="date">Date:</form:label>
				<form:input type="date" class="col form-control" path="date"/>
			</div>
			<div class="row">
				<form:label class="col" path="location">Location:</form:label>
				<div class="col container">
					<div class="row">
					<form:input class="col form-control" path="location" placeholder="City"/>
					<form:select class="col form-control" path="state">
					<c:forEach items="${states}" var="state">
						<form:option value="${state}"><c:out value="${state}"/></form:option>
					</c:forEach>
					</form:select>
					</div>
				</div>
			</div>
			<div class="row justify-content-end">
				<input class="col-auto btn btn-primary" type="submit" value="Create Event">
			</div>
		</fieldset>
		<p class="text-danger"><form:errors path="*"/></p>
	</form:form>
</body>
</html>