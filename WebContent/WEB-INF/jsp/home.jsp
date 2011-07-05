<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="tags.jsp" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Sweet HOME</title>
</head>
<body>
	Hi, this is home page! <br/><br/>
	your login is <b><c:out value="${login}"></c:out></b> <br/>	
	your displayname is <b><c:out value="${displayname}"></c:out></b> <br/>
	your roles are: <b><c:out value="${rolesStr}"></c:out></b> <br/>
	<br/>
	<br/>
	<a href="<c:url value="/j_spring_security_logout"/>"> exit </a>
	
</body>
</html>