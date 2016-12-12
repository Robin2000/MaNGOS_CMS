<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include   file="/page/inc.jsp"%>
<jsp:useBean id="questChain" scope="session" class="com.ficus.quest.QuestChain"/>
<%@ page import="com.ficus.db.TableBean" %>
<%@ page import="com.ficus.db.Column" %>
<%
	String questid=request.getParameter("questid");
	questChain.setQuestid(questid);
 %>
    {
    "type": "force",
    "categories": [
        {
            "name": "HTMLElement",
            "keyword": {},
            "base": "HTMLElement",
            "itemStyle": {
                "normal": {
                    "brushType": "both",
                     "color": "#D0D102",
                     "strokeColor": "#5182ab",
                     "lineWidth": 1
                }
            }
        }
    ],
    "nodes": [
<%for(int i=0;i<questChain.getList().size();i++){ %>
<% Object[] row=questChain.getList().get(i);
	if(i>0){%>
		,
<%}%>
         {
         	"label": "<%=row[0] %>.<%=row[2] %>",
            "name": "<%=row[0] %>.<%=row[2] %>",
            "value": <%=i+1%>,
            "category": 0
         }
 <%}%>            
    ],
    "links": [
<%
int count=0;
for(int i=0;i<questChain.getList().size();i++){ %>
	<%Object[] row=questChain.getList().get(i);
	if(!"0".equals(row[3].toString())){ 
		if(count>0){%>,<%}%>{
	            "source": <%=questChain.getQuestPos(row[3].toString()) %>,
	            "target": <%=questChain.getQuestPos(row[0].toString()) %>
	       }     
	     <%count++;%> 
	 <%}%>           
 <%}%>
    ]
}