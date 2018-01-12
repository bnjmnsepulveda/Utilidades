/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Roberto
 */
@WebListener
public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
    	System.out.println("Session creada:: ID="+sessionEvent.getSession().getId());
       
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    	System.out.println("Session destruida:: ID="+sessionEvent.getSession().getId());
    }
    
}
