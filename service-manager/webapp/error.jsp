<%@ page contentType="application/json" pageEncoding="UTF-8"%>
{
    status:<%=request.getAttribute("javax.servlet.error.status_code") %>,
    reason:<%=request.getAttribute("javax.servlet.error.message") %>
}
<html>
<body>
	status:<%=request.getAttribute("javax.servlet.error.status_code") %><br/>
	reason:<%=request.getAttribute("javax.servlet.error.message") %>
</body>
</html>