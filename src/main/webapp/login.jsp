<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
	<center>
		<h4>login page</h4>
		<h5>${message }</h5>
		<form action="login" method="post">
			username:<input type="text" name="name" /> <br /> password:<input type="password" name="password" /><br /> <input type="submit" value="submit" />
		</form>
	</center>

</body>
</html>