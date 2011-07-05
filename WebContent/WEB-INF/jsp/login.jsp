<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="tags.jsp" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title> Login</title>
<link rel="stylesheet" type="text/css" href="css/main.css" media="screen" />
<script>
	function setFocus(){
		var logInput = document.getElementById('j_username');
		logInput.focus();
	}
</script>
</head>
<body style="background-color: #D1E5F6" onload="setFocus()">
	<div align="center">
		<div id="container">

			<div id="content">
					
                    	<c:if test="${not empty error}">
                        	<span class="errorMsg" style="color: red; font-family: monospace;">
                            	${error}<br/><br/>
                        	</span>
                        </c:if>
                    
                    <!--  <img src="images/logo.png" alt="Logo" width="200"/> -->
					<div id="login" align="center" style="margin-top: 0.5em; background-color: white; width: 350px; padding: 1em; border: 1px solid #252175;">
                    	<form action="<c:url value="/j_spring_security_check"/>" method="post">
                        	<table>
                            	<tr>
                                	<td align="right" style="color: #252175; font-family: sans-serif;">login: </td>
                                    <td > <input id="j_username" name="j_username"/> </td>
                                </tr>
                                <tr>
                                	<td align="right" style="color: #252175; font-family: sans-serif;">password: </td>
                                    <td> <input name="j_password" type="password" /> </td>
                                </tr>
                                <tr>
                                	<td colspan="2" align="center"> <button type="submit">go</button> </td>
                               	</tr>
							</table>
                	</form>
				</div><!-- login -->
				
			</div><!-- content -->
				
		</div> <!-- container -->
	
	</div>	
</body>
</html>