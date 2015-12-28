package com.ficus.menu;

import java.io.Serializable;
import java.util.ArrayList;

public final class MenuNode implements Serializable {
    	
		private static final long serialVersionUID = 1L;
    
		private String url;
        private String text;
        private String icon;
        private boolean expand;
        private MenuNode parent;
        private ArrayList<MenuNode> children=new ArrayList<MenuNode>();
        
        public ArrayList<MenuNode> getChildren() {
			return children;
		}
		public void setChildren(ArrayList<MenuNode> children) {
			this.children = children;
		}
		public MenuNode(String icon,String text)
        {
            this.icon=icon;
            this.text=text;
        }
        public MenuNode(String icon,String text,String url)
        {
            this.icon=icon;
            this.text=text;
            this.url="".equals(url)?null:url;
        }
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
	        public String getText() {
			return text;
		}
	
		public void setText(String text) {
			this.text = text;
		}

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
		public boolean isExpand() {
			return expand;
		}
		public void setExpand(boolean expand) {
			this.expand = expand;
		}
		public MenuNode getParent() {
			return parent;
		}
		public void setParent(MenuNode parent) {
			this.parent = parent;
		}
        public void addChildren(MenuNode node){
        	children.add(node);
        }
}
