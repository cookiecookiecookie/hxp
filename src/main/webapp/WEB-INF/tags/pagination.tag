<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="page" type="com.github.pagehelper.PageInfo" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="queryString" type="java.lang.String" required="false"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false"%>
<c:if test="${page.pages gt 0}">
	<c:set var="qs" value="${fn:substring(queryString, 0, fn:indexOf(queryString, '&p='))}"></c:set>
	<c:choose>
		<c:when test='${fn:indexOf(url, "?") lt 0}'>
			<c:set var="url" value="${url }?${qs}&"/>
		</c:when>
		<c:otherwise>
			<c:set var="url" value="${url }&${qs}&"/>
		</c:otherwise>
	</c:choose>
	<ul class="pagination pagination-sm no-margin ${cssClass }">
		<c:choose>
			<c:when test="${page.hasPreviousPage}">
				<li><a href="${url }p=${page.prePage }"><i class="glyphicon glyphicon-chevron-left"></i></a></li>
			</c:when>
			<c:otherwise>
				<li class="disabled"><a href="#"><i class="glyphicon glyphicon-chevron-left"></i></a></li>
			</c:otherwise>
		</c:choose>
		<c:forEach var="idx"  items="${page.navigatepageNums }">
			<c:choose>
				<c:when test="${idx eq page.pageNum }">
					<li class="active"><a href="#">${idx}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="${url }p=${idx }">${idx}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:choose>
			<c:when test="${page.hasNextPage}">
				<li><a href="${url }p=${page.nextPage }"><i class="glyphicon glyphicon-chevron-right"></i></a></li>
			</c:when>
			<c:otherwise>
				<li class="disabled"><a href="#"><i class="glyphicon glyphicon-chevron-right"></i></a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</c:if>
