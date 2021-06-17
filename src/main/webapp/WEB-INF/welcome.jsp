<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome</title>
<link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
	<h1>Welcome</h1>
	<div class="row">
		<form:form class="col" action="/register" method="post" modelAttribute="user">
			<fieldset>
			<legend>Register</legend>
				<div class="row">
					<form:label class="col" path="firstName">First Name:</form:label>
					<form:input class="col form-control" path="firstName"/>
				</div>
				<div class="row">
					<form:label class="col" path="lastName">Last Name:</form:label>
					<form:input class="col form-control" path="lastName"/>
				</div>
				<div class="row">
					<form:label class="col" path="email">Email:</form:label>
					<form:input type="email" class="col form-control" path="email" placeholder="Enter Email"/>
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
				<div class="row">
					<form:label class="col" path="password">Password:</form:label>
					<form:input class="col form-control" type="password" path="password"/>				
				</div>
				<div class="row">
					<form:label class="col" path="passwordConfirm">Confirm PW:</form:label>
					<form:input class="col form-control" type="password" path="passwordConfirm"/>
				</div>
				<div class="row justify-content-end">
					<input class="col-auto btn btn-primary" type="submit" value="Register">
				</div>
			</fieldset>
			<p class="text-danger"><form:errors path="*"/></p>
		</form:form>
		<form action="/login" class="col">
			<fieldset>
			<legend>Login</legend>
			<div class="row">
				<label class="col">Email:</label>
				<input class="col form-control" type="text" name="email">
			</div>
			<div class="row">
				<label class="col">Password:</label>
				<input class="col form-control" type="password" name="password">
			</div>
			<div class="row justify-content-end">
				<input class="col-auto btn btn-primary" type="submit" value="Log In">
			</div>
			</fieldset>
			<p class="text-danger"><c:out value="${error}"></c:out></p>
		</form>
	</div>
</body>
</html>