<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><c:out value="${event.name}"></c:out></title>
<link rel="stylesheet" href="/css/bootstrap.css">
</head>
<body>
<h1><c:out value="${event.name}"/></h1>
<div class="row">
	<div class="col">
		<h3>Host: <c:out value="${event.host.firstName}"/> <c:out value="${event.host.lastName}"/></h3>
		<h3>Date: <fmt:formatDate value="${event.date}" type="date"/></h3>
		<h3>Location: <c:out value="${event.location}"/>, <c:out value="${event.state}"/></h3>
		<h3>People who are attending this event: <c:out value="${event.users.size()}"></c:out></h3>
		<table>
			<tr>
				<th>Name</th>
				<th>Location</th>
			</tr>
			<c:forEach items="${event.users}" var="attendee">
				<tr>
					<td>
						<c:out value="${attendee.firstName}"/> <c:out value="${attendee.lastName}"/>
					</td>
					<td>
						<c:out value="${attendee.location}"/>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="col">
		<h2>Messages</h2>
		<div class="container border h-25 overflow-auto">
			<c:forEach items="${event.messages}" var="message">
				<p><c:out value="${message}"></c:out></p>
				<p>----------------------------------------</p>
			</c:forEach>
		</div>
		<form class="container mt-2" action="/message/${event.id}">
			<fieldset>
			<legend>Add Comment:</legend>
			<textarea class="w-100" name="comment"></textarea>
			<input type="submit" value="add comment">
			</fieldset>
			<p class="text-danger"><c:out value="${error}"/></p>
		</form>
	</div>
</div>
</body>
</html>