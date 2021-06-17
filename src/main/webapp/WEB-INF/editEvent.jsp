<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/css/bootstrap.css">
<title>Events</title>
</head>
<body>
<h1><c:out value="${event.name}"/></h1>
<form:form class="w-50" action="/update/${event.id}" method="POST" modelAttribute="event">
	<input type="hidden" name="_method" value="put">
	<form:hidden value="${event.host.id}" path="host"/>
	<fieldset>
	<legend>Edit Event</legend>
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
		<input class="col-auto btn btn-primary" type="submit" value="Edit">
	</div>
	</fieldset>
	<p class="text-danger"><form:errors path="*"/></p>
</form:form>
</body>
</html>