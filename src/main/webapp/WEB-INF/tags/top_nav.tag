<%@tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="active" type="java.lang.String" required="true"%>
<script type="text/javascript">
	function navMenuClick(url) {
		window.location.href = url;
	}
</script>
<div class="navbar navbar-inverse navbar-static-top">
	<div class="container-full">
		<div class="navbar-header">
			<div class="navbar-logo">
				<a href="${ctx}/">批处理</a>
			</div>
		</div>
		<div class="navbar-left navbar-enviro">
			<div class="navbar-enviro-name">${batsys_name}</div>
			<div class="navbar-enviro-server">${batsys_label}</div>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li class="${active eq 'schedule' ? 'active' : ''}" onclick="navMenuClick('${ctx}/schedule')"><a href="${ctx}/schedule">跑批</a></li>
				<li class="${active eq 'reqfund' ? 'active' : ''}" onclick="navMenuClick('${ctx}/reqfund')"><a href="${ctx}/reqfund">请款</a></li>
				<li class="${active eq 'history' ? 'active' : ''}" onclick="navMenuClick('${ctx}/history')"><a href="${ctx}/history">历史</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:if test="${not empty user }">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">${user }<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/logout">退出</a></li>
						</ul>
					</li>
				</c:if>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</div>