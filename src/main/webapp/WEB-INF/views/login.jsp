<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<title>用户登录</title>
	<tags:html_head/>
</head>
<body>
	<tags:top_nav active=""/>
	<div class="container-full">
		<div class="login">
			<div class="well">
				<form id="login-form" role="form" action="${ctx}/login" method="post">
					<c:if test="${not empty error_msg}">
						<div class="alert alert-danger" id="error-msg">${error_msg}</div>
					</c:if>
					<fieldset>
						<legend>用户登录</legend>
						<input type="text" class="form-control" name="username" id="username" placeholder="用户名">
						<input type="password" class="form-control" name="password" id="password" placeholder="登录密码">
						<button type="submit" class="btn btn-primary btn-lg btn-block" id="login-submit">登录</button>
					</fieldset>
				</form>
			</div>
			<!-- /.well -->
		</div>
		<!-- /.login -->
	</div>
	<!-- /container -->
</body>
</html>