<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include   file="/page/inc.jsp"%>
<jsp:useBean id="questBean" scope="application" class="com.ficus.quest.QuestBean"/>
<%@ page import="com.ficus.db.TableBean" %>
<%@ page import="com.ficus.db.Column" %>
<%
	String table=request.getParameter("t");
	
 %>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=utf-8">
    <title><%=table %> List</title>
       
    <!-- DataTables JavaScript -->
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>



    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
                responsive: true,
                serverSide: true,
                ajax: {
			        url: '<%=basePath%>/tabledata?t=<%=table%>&bean=questBean',
			        type: 'POST'
			    },
			    "columnDefs": [
            	{
	                "render": function ( data, type, row ) {
	                    return "<a href=chain.jsp?questid="+data+">"+data+"</a>";
	                },
	                "targets": 0
            	}]
        });
    });
    </script>
</head>
<body>
               <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <%=table %> List
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                        <%for(Column col:questBean.getCols(table)){ %>
                                            <th><%=col.getName() %></th>
                                         <%}%>   
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                            <div class="well">
                                
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
</body>
</html>