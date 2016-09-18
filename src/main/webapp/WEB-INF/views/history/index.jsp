<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<title>批处理系统 - 跑批历史</title>
	<tags:html_head/>
</head>
<body>
	<tags:top_nav active="history"/>
	
    <div class="az-page-header">
		<div class="container-full">
			<h3><a href="#">跑批历史</a></h3>
		</div>
    </div>

    <div class="container-full">
		<tags:alert_msg/>
		<table id="executingJobs" class="table table-striped table-bordered table-hover table-condensed executions-table">
			<thead>
				<tr>
					<th class="execid">批次号</th>
					<th class="flowid">类型</th>
					<th>会计日</th>
					<th class="user">开始时间</th>
					<th class="date">结束时间</th>
					<th class="elapse">用时</th>
					<th class="status">状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="jobExec" items="${pageInfo.list}">
				<tr>
					<td>${jobExec.id}</td>
					<td>${jobExec.flowId}</td>
					<td>${jobExec.accountingDate}</td>
					<td>${jobExec.startTime}</td>
					<td>${jobExec.endTime}</td>
					<td>${jobExec.totalTime}</td>
					<td>${jobExec.status}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<tags:pagination url="/history" page="${pageInfo}"/>
	</div><!-- /container-full -->
</body>
</html>