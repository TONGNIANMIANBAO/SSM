<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<!--  Bootstrap -->
<link type="text/css" href="./bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" />

<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="./bootstrap/3.3.7/js/1.12.4/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="./bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>

	<jsp:forward page="/emps"></jsp:forward>
	Standard button
	<button type="button" class="btn btn-default">（默认样式）Default</button>

	Provides extra visual weight and identifies the primary action in a set
	of buttons
	<button type="button" class="btn btn-primary">（首选项）Primary</button>

	Indicates a successful or positive action
	<button type="button" class="btn btn-success">（成功）Success</button>

	Contextual button for informational alert messages
	<button type="button" class="btn btn-info">（一般信息）Info</button>

	Indicates caution should be taken with this action
	<button type="button" class="btn btn-warning">（警告）Warning</button>

	Indicates a dangerous or potentially negative action
	<button type="button" class="btn btn-danger">（危险）Danger</button>

	Deemphasize a button by making it look like a link while maintaining
	button behavior
	<button type="button" class="btn btn-link">链接==Link</button>
</body>
</html>