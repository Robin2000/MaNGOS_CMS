package com.ficus.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ficus.app.AppKey;

/*线程上下文共享变量*/
public class SessionUtil {

	private static final Logger log = Logger.getLogger(SessionUtil.class);

    //创建线程局部变量session，用来保存Session
    public static final ThreadLocal session = new ThreadLocal();
 
    /**
     * 获取当前线程中的Session
     * @return Session
     * @throws HibernateException
     */
    public static HashMap<AppKey,Object> currentSession() {
    	HashMap<AppKey,Object> s = (HashMap<AppKey,Object>) session.get();
        // 如果Session还没有打开，则新开一个Session
        if (s == null) {
            s = new HashMap<AppKey,Object>();
            session.set(s);         //将新开的Session保存到线程局部变量中
        }
        return s;
    }
 
}
