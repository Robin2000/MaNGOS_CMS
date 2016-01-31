<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include   file="/page/inc.jsp"%>
<jsp:useBean id="questChain" scope="session" class="com.ficus.quest.QuestChain"/>
<%@ page import="com.ficus.db.TableBean" %>
<%@ page import="com.ficus.db.Column" %>
<%
	String questid=request.getParameter("questid");
	questChain.setQuestid(questid);
 %>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=utf-8">
    <title>Chain Of Quest: <%=questid %></title>
    <!-- DataTables JavaScript -->
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>



    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
                responsive: true,
                serverSide: false,
 			    "columnDefs": [
            	{
	                "render": function ( data, type, row ) {
	                    return "<a href=chain.jsp?questid="+data+">"+data+"</a>";
	                },
	                "targets": 0
            	}],
            	"order": [[ 3, "asc" ]],
            	 "lengthMenu": [[50,100, -1], [50,100, "All"]]
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
                            Chain Of Quest_<%=questid %> List
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                        <%for(Column col:questChain.getCols()){ %>
                                            <th><%=col.getName() %></th>
                                         <%}%>   
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <%for(Object[] row:questChain.getList()){ %>
                                        <tr>
                                        <%for(Object val:row){ %>
                                            <td><%=val%></td>
          								<%}%> 
                                        </tr>
                                   <%}%>        
                                    </tbody>
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