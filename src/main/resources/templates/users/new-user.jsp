<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<meta charset="UTF-8">
		<title>Create new user</title>
	</head>
	<body>
		<form th:action="@{/auth/createuser}" th:method="PATCH" th:object="${newUser}">
			<label id="username">Username</label>
			<input type="text" th:field="*{username}" id="password">
			<label id="password">Password</label>
			<input type="text" th:field="*{password}" id="password">
			<input type="submit" value="Send">
		</form>
	</body>
</html>