package com.ficus.menu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public final class TreeMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(TreeMenu.class);

    private MenuNode root=new MenuNode("Home", "Home");

    public TreeMenu(){
    	init();
    }
    private boolean init() {
        String config=loadConfig();
        if(config==null)
            return false;
        
        Document doc = Jsoup.parse(config);
        
		Elements r = doc.select("root");
		addChildren(root,r.get(0));
		
		return true;
    }
    
    private void addChildren(MenuNode parent,Node node)
    {
        List<Node> nodeList = node.childNodes();
        MenuNode curNode;

        for(Node elem:nodeList)
        {
            curNode=new MenuNode(elem.attr("icon"),elem.attr("text"),elem.attr("url"));
            curNode.setParent(parent);
            if("true".equals(elem.attr("isexpand")))
            	curNode.setExpand(true);
            parent.getChildren().add(curNode);
            addChildren(curNode,elem);
        }
    }
    private String loadConfig(){
       try(InputStream in=this.getClass().getClassLoader().getResourceAsStream("treemenu.xml");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));   
               ){
            StringBuilder txt = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) 
            	txt.append(line).append("\n");
            return txt.toString();
       }catch(Throwable e){
    	   log.error("config menu error",e);
          return null;
       }
    }
 
}