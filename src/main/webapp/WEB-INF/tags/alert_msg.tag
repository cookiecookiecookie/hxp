<%@tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${not empty error_msg }">
		<div class="alert alert-danger alert-dismissable">
	        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
	        ${error_msg}
		</div>
	</c:when>
	<c:when test="${not empty warn_msg}">
		<div class="alert alert-warning alert-dismissable">
	        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
	        ${warn_msg}
	    </div>
	</c:when>
	<c:when test="${not empty success_msg}">
		<div class="alert alert-success">
	        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
	        ${success_msg}
    	</div>
	</c:when>
</c:choose>
<%-- Alert message triggered by JavaScript. --%>
<div class="alert alert-dismissable alert-messaging" id="messaging">
	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
	<p id="messaging-message"></p>
</div>
