<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<title>批处理系统 - 跑批</title>
	<tags:html_head/>
</head>
<body>
	<tags:top_nav active="schedule"/>
	
    <div class="az-page-header">
		<div class="container-full">
			<h3><a href="#">跑批</a></h3>
		</div>
    </div>

    <div class="container-full">
		<tags:alert_msg/>
		
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="col-md-8">
					<div>当前会计日：<span>${accountingDate}</span></div>
					<div>下个会计日：<span>${nextAccountingDate}</span></div>
					<div>状态码：<span id="exec-status">${jobExec.status}</span></div>
					<div>批次号：<span id="exec-id">${jobExec.id}</span></div>
				</div>
				<div class="col-md-4">
					<div class="pull-right">
						<c:if test="${empty jobExec || jobExec.status eq 'SUCCESS'}">
							<button id="runNextDayBtn" class="btn btn-default" onclick="switchingAccountingDate();">切换会计日</button>
						</c:if>
						<c:choose>
							<c:when test="${empty jobExec || jobExec.status ne 'SUCCESS'}">
								<button id="runBatchBtn" class="btn btn-default" onclick="execJobFlow('batch');">开始跑批</button>
							</c:when>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
		
		<!-- <ul class="nav nav-tabs nav-sm" id="header-tabs" role="tablist">
			<li role="presentation" id="currently-running-view-link" class="active">
				<a href="#currently-running-view" aria-controls="currently-running-view" role="tab" data-toggle="tab">可用任务流</a>
			</li>
		</ul> -->

		<div class="tab-content">
			<div role="tabpanel" class="tab-pane active" id="currently-running-view">
				<table id="executingJobs" class="table table-striped table-bordered table-hover table-condensed executions-table">
					<thead>
						<tr>
							<th>#序号</th>
							<th>任务ID</th>
							<th>描述</th>
							<th>状态</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>用时</th>
							<th class="action">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="job" items="${jobs}" varStatus="status">
						<tr>
							<td class="tb-name">${status.index + 1}</td>
							<td>${job.id }</td>
							<td>${job.description }</td>
							<td id="${job.id }-status">READLY</td>
							<td id="${job.id }-start-time"></td>
							<td id="${job.id }-end-time"></td>
							<td id="${job.id }-elapse"></td>
							<td>
								<c:if test="${job.type eq 'python' }">
									<a href="javascript:;" onclick="stdoutDownload('${job.id }');">日志下载</a>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- /row -->
		</div>
	</div><!-- /container-full -->
	<script type="text/javascript">
	var execId = "${jobExec.id}";
	var isClearInterval = false;
	function refreshStatus(execId) {
		$.ajax({
			type : 'POST',
			url : "${ctx}/schedule/list_exec_inst",
			data : {
				execId : execId
			},
			dataType : "JSON",
			success : function(data, status) {
				if(data.jobExec.status != "RUNNING") {
					isClearInterval = true;
				}
				if(data.jobExec.status == "FAIL") {
					$("#runBatchBtn").show();
				}
				$("#exec-status").text(data.jobExec.status);
				for(i in data.jobInsts) {
					var item = data.jobInsts[i];
					$("#" + item.jobId + "-status").text(item.status);
					$("#" + item.jobId + "-start-time").text(item.startTime || "");
					$("#" + item.jobId + "-end-time").text(item.endTime || "");
					$("#" + item.jobId + "-elapse").text(item.totalTime || "");
				}
			},
			error: function() {
				alert("error");
			}
		});
	}
	
	function execJobFlow(flowId) {
		if(!window.confirm('当前会计日：${accountingDate}，你确认开始跑批吗？')){
        	return false;
        }
		$.ajax({
			type : 'POST',
			url : "${ctx}/schedule/exec_job_flow",
			data : {
				flowId : flowId
			},
			dataType : "JSON",
			beforeSend: function(){
			     $("#runBatchBtn").hide();
			},
			success : function(data, status) {
				execId = data.execId;
				$("#exec-id").text(data.execId);
				isClearInterval = false;
				if(data.status == "RUNNING") {
					$("#runNextDayBtn").hide();
				}
				refreshStatus(data.execId);
				var interval = setInterval(function(){
					refreshStatus(data.execId);
					if (isClearInterval) {
						clearInterval(interval);
					}
				}, 1000);
			},
			error : function() {
				alert("error");
			}
		});
	}
	
	function switchingAccountingDate(){
		if(!window.confirm('当前会计日：${accountingDate}，你确认切换会计日吗？')){
        	return false;
        }
		$.ajax({
			type : 'POST',
			url : "${ctx}/schedule/switching_accounting_date",
			data : {
				execId : execId
			},
			dataType : "JSON",
			success : function(data, status) {
				alert(data.message);
				if(data.status != "SUCCESS") {
					return false;
				}
				location.reload();
			},
			error: function() {
				alert("error");
			}
		});
	}

	function stdoutDownload(jobId) {
		if (execId ==  "") {
			alert("请先执行批处理");
			return false;
		}
		location.href="${ctx}/download/stdout?execId=" + execId + "&jobId=" + jobId;
	}

	if ("${jobExec.id}" != "") {
		refreshStatus("${jobExec.id}");
	}
	</script>
</body>
</html>