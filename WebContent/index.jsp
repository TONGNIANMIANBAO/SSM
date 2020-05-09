<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<!-- Bootstrap -->
<link type="text/css" href="./bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" />

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="./bootstrap/3.3.7/js/jquery-1.12.4.min.js"></script>
<!-- jquery.validate插件 -->
<script src="./bootstrap/3.3.7/js/jquery.validate.min.js"></script>
<!-- jquery.validate中文提示 -->
<script src="./bootstrap/3.3.7/js/messages_zh.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="./bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';
</script>
<style>
.error {
	color: red;
}
</style>
</head>
<body>
	<!-- Modal -->
	<div class="modal fade" id="empModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">用戶添加</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="empSaveForm">
						<div class="form-group">
							<label for="inputEmpName" class="col-sm-2 control-label">Empname:</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="inputEmpName" placeholder="Name" name="lastName" />
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail" class="col-sm-2 control-label">Email:</label>
							<div class="col-sm-10">
								<input type="email" class="form-control" id="inputEmail" placeholder="Email" name="email" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Gender:</label>
							<div class="col-sm-10">
								<label class="radio-inline" > 
									<input type="radio" name="gender" id="genderRadio1" value="1" checked="checked"/> 男
								</label> 
								<label class="radio-inline"> 
									<input type="radio" name="gender" id="genderRadio2" value="0" /> 女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Department:</label>
							<div class="col-sm-8">
								<select class="form-control" name="dId">
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					<button type="button" class="btn btn-primary" id="empSave">保存</button>
					<button type="button" class="btn btn-primary" id="empUpdate">更新</button>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<h1>SSM</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-md-3 col-md-offset-9">
				<button type="button" class="btn btn-primary" id="empAddBtn">新增</button>
				<button type="button" class="btn btn-danger" id="empDelBtn">刪除</button>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emps-table">
					<thead>
						<tr>
							<th><input type="checkbox" id="checkAll"/></th>
							<th>#</th>
							<th>name</th>
							<th>email</th>
							<th>gender</th>
							<th>department</th>
							<th>operate</th>
						</tr>
					</thead>
					<tbody>

					</tbody>

				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4" id="page-info"></div>
			<div class="col-md-4" id="page-navigation"></div>
		</div>
	</div>
	<script type="text/javascript" src="./index.js" charset="utf-8">
		
	</script>
</body>
</html>