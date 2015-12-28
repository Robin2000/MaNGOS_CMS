<%
	String docRoot=request.getServletContext().getRealPath("/");
	String path=request.getContextPath();
	String basePath=new StringBuilder(request.getScheme()).append("://").append(request.getServerName()).append(':').append(request.getServerPort()+request.getContextPath()).toString();
	request.setAttribute("basePath",basePath);
%>