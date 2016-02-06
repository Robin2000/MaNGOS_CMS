package com.ficus.menu;

import java.io.Serializable;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.ficus.util.Util;

public final class TreeMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private MenuNode root=new MenuNode("Home", "Home");

    public TreeMenu(){
    	init();
    }
    private boolean init() {
        String config=Util.loadConfig(this.getClass().getClassLoader(),"treemenu.xml");
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
    
 
}