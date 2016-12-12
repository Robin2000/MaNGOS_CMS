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
    <title>任务链: <%=questid %></title>
    <!-- DataTables JavaScript -->
    <script src="../bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
    <script src="../bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>



    <!-- Page-Level Demo Scripts - Tables - Use for reference -->
    <script>
    var table;
    $(document).ready(function() {
        table=$('#dataTables-example').DataTable({
                responsive: true,
                serverSide: false,
                language: {
                	url: "<%=basePath%>/bower_components/datatables/media/js/Chinese.json"
                },
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
         $('#dataTables-example').on( 'draw.dt', function (e, settings) {
        		
		     $.each(settings.json.queryFilters, function(i, queryItem){ 
		       $('#'+queryItem.id).prop('outerHTML', queryItem.html);
		     });
		} );        
        
        $("select").css("width","400px");
       $(".panel-heading").css("line-height","40px");
    });
    function reload(){
    	table.draw();
    }
    </script>      
	


</head>
<body>
               <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            任务_<%=questid %>的链式列表
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                            	<!-- 图 -->
                            	<div id="main" style="height:400px"></div>
                            	<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
                            	<script type="text/javascript">
							        // 路径配置
							        require.config({
							            paths: {
							                echarts: 'http://echarts.baidu.com/build/dist'
							            }
							        });
							    </script>
                            	<script>
                            	 // 使用
							        require(
							            [
							                'echarts',
							                'echarts/chart/force' // 使用柱状图就加载bar模块，按需加载
							            ],
							            function (ec) {
							                // 基于准备好的dom，初始化echarts图表
							                var myChart = ec.init(document.getElementById('main')); 
							                
							                var option = {
											    title : {
											        text: '任务链',
											        subtext: '数据来自游戏	',
											        x:'right',
											        y:'bottom'
											    },
											    tooltip : {
											        trigger: 'item',
											        formatter : "{b}"
											    },
											    toolbox: {
											        show : true,
											        feature : {
											            restore : {show: true},
											            magicType: {
											                show: true,
											                type: ['force', 'chord'],
											                option: {
											                    chord: {
											                        minRadius : 2,
											                        maxRadius : 10,
											                        ribbonType: false,
											                        itemStyle: {
											                            normal: {
											                                label: {
											                                    show: true,
											                                    rotate: true,
											                                    textStyle:{color:'#333,fontSize:14px'}
											                                },
											                                chordStyle: {
											                                    opacity: 0.2
											                                }
											                            }
											                        }
											                    },
											                    force: {
											                        minRadius : 5,
											                        maxRadius : 8,
											                        itemStyle : {
											                            normal : {
											                                label: {
											                                    show: true
											                                },
											                                linkStyle : {
											                                    opacity : 0.5
											                                }
											                            }
											                        }
											                    }
											                }
											            },
											            saveAsImage : {show: true}
											        }
											    },
											    legend : {
											        data : ['任务<%=questid%>'],
											        orient : 'vertical',
											        x : 'left'
											    },
											    noDataEffect: 'none',
											    series :[{
											        //FIXME No data
											        type: 'force',
											    }],
											};
											$.ajax({
											    url: 'treedata.jsp?questid=<%=questid%>',
											    dataType: 'json',
											    success: function (data) {
											        option.series[0] = {
											            type: 'force',
											            name: '任务',
											            itemStyle: {
											                normal : {
											                	label:{
											                		show: true,
											                		textStyle:{color:'#333'}
											                	},
											                    linkStyle : {
											                        opacity : 0.5
											                    }
											                }
											            },
											            categories: data.categories,
											            nodes: data.nodes,
											            links: data.links,
											            minRadius: 5,
											            maxRadius: 8,
											            gravity: 1.1,
											            scaling: 1.1,
											            steps: 20,
											            large: true,
											            useWorker: true,
											            coolDown: 0.995,
											            ribbonType: false
											        };
											
											        myChart.setOption(option);
											        myChart.hideLoading();
											    }
											});
							        
							                // 为echarts对象加载数据 
							                myChart.setOption(option); 
							            }
							        );
                            	</script>
                            	<!-- 图结束 -->
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