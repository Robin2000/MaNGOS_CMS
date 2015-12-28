package com.ficus.wiki;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ficus.app.Container;
import com.ficus.util.Util;

@WebServlet("/wiki/*")
public class WikiSevlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(WikiSevlet.class);
	
	private String contentPath;
	private String proxyBase;
	
    private String wikiIndex="https://github.com/cmangos/issues/wiki/mangosdb_struct/";
    private String wikiDetail="https://github.com/cmangos/issues/wiki/";
    private long ONE_WEEK=7*24*60*60*1000;
    
    public WikiSevlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri=request.getRequestURI(); /* /MaNGOSC/wiki/achievement_criteria_requirement.pro */
		String table=uri.substring(proxyBase.length());
		if(table.equals(""))
			table="index";

		String file1=new StringBuilder(Container.me.getAppProperty("FICUS_WEBROOT")).append("/wiki/").append(table).append(".html").toString();  /* E:/workspace.wow/MaNGOSC/WebContent/wiki/achievement_reward.html */
		String file2=new StringBuilder(request.getServletContext().getRealPath("/wiki")).append('/').append(table).append(".html").toString();/*E:/workspace.wow/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/MaNGOSC/wiki/achievement_reward.html*/
		
		String redirect=new StringBuilder(contentPath).append("/wiki/").append(table).append(".html").toString(); /* /MaNGOSC/wiwki/achievement_reward.html */

		
		String url=table.equals("index")?wikiIndex:new StringBuilder(wikiDetail).append(table).toString();  /* https://github.com/cmangos/issues/wiki/mangosdb_struct/achievement_reward.html */
		
		File f=new File(file1);
		if(!f.exists())
		{
			grap(file1,file2,url,table);
		}else
		{
			if(System.currentTimeMillis()-f.lastModified()>ONE_WEEK)
				grap(file1,file2,url,table);	
		}
		
		//RequestDispatcher dispatch = request.getRequestDispatcher(dispatchUrl);
		//dispatch.forward(request, response);
		
		//response.sendRedirect(new StringBuilder(contentPath).append("/page/wiki.jsp?url=").append(URLEncoder.encode(redirect,"UTF-8")).toString());
		//response.sendRedirect(new StringBuilder(contentPath).append("/page/wiki.jsp?table="+table).toString());
		
		
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			String s = Util.readFile(file1);
			response.getWriter().write(s);
		} catch (Exception e) {
			response.getWriter().write("file not found!"+file1);
		}
		
	}
	private void grap(String file1,String file2,String url,String table){
		
		Document doc;
		try {
			doc = Jsoup.parse(new URL(url), 10000);
			Elements list=doc.getElementsByClass("markdown-body");
			
			StringBuilder sb=new StringBuilder("<html><head><title>").append(table).append("</title></head><body>\n");
			sb.append("<div class='row'><div class='col-lg-12'>");
			for(Element e:list)
				sb.append(e.html());

			sb.append("</div></div>");
			sb.append("\n</body></html>");

			Util.writeFile(file1, sb.toString());
			Util.writeFile(file2, sb.toString());
		} catch (Exception e) {
			log.error("grap wiki url fail!"+url);
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		contentPath=config.getServletContext().getContextPath();  /* /MaNGOSC */
		proxyBase=contentPath+"/wiki/";
	}

}
