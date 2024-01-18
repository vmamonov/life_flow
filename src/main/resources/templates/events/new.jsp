<!DOCTYPE html>
<html xmlns:th="https://thymeleaf.org">
	<head>
		<meta charset="UTF-8">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
		<title>Create new event</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-3">
					<form th:action="@{/events/create}" th:method="PATCH" th:object="${event}">
						<label for="title" class="form-label">Title</label>
						<input type="text" th:field="*{title}" id="title" class="form-control">
						<div th:if="${#fields.hasErrors('title')}" th:errors="*{title}" class="alert alert-danger" role="alert"></div>
						<label for="start" class="form-label">Start</label>
						<input type="datetime" th:field="*{start}" id="start" class="form-control">
						<div th:if="${#fields.hasErrors('start')}" th:errors="*{start}" class="alert alert-danger" role="alert"></div>
						<label for="end" class="form-label">End</label>
						<input type="datetime" th:field="*{end}" id="end" class="form-control">
						<div th:if="${#fields.hasErrors('end')}" th:errors="*{end}" class="alert alert-danger" role="alert"></div>
						<br>
						<input type="submit" class="btn btn-primary">
					</form>
				</div>
			</div>
		</div>
	</body>
</html>