<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="world" scope="application" class="com.ficus.tables.WorldInfo"/>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ficus.db.TableInfo" %>
<%@include   file="/page/inc.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<title>Table Info <%=world.getTables().size()%></title>
<script src="../dist/js/masonry.pkgd.3.3.2.min.js"></script>
</head>
<body>
<div class="row">
     <div class="col-lg-12">
     	<div class="grid js-masonry" data-masonry-options='{ "itemSelector": ".grid-item", "columnWidth": 200 }'>
			<%
				ArrayList<ArrayList<TableInfo>> tableGroup=world.getTablesGroup();
				for(int i=0;i<tableGroup.size()/3;i++){
			%>

            	<%for(int j=0;j<3&&i*j<tableGroup.size();j++){%>
            		<%
            		ArrayList<TableInfo> list=tableGroup.get(i*3+j);
            		Character groupName=list.get(0).getName().charAt(0);
            		%>
            		<div class="grid-item">
	                    <div class="panel panel-default">
	                        <div class="panel-heading">
	                            <%=groupName %>
	                        </div>
	                        <div class="panel-body">
	                      		<%for(TableInfo table:list){ %>
	                            <h5><a href='<%=table.getName()%>'><%=table.getName() %></a>
	                                <small>(<%=table.getCount() %>)</small>
	                            </h5>
	                            <%} %>
	                        </div>
	                        <!-- /.panel-body -->
	                    </div>
                    	<!-- /.panel -->
                    </div>	
     			<%} %>

            <%} %>
		</div>
	</div>
</div>
</body>

</html>
