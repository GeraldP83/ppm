package at.ppm.puppet.dal.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.osgi.framework.Bundle;


public class HibernateSession {
	
public static Session instance;
private static int opesSessions = 0;
private static SessionFactory sessionfactory = createFactory();
	
	private HibernateSession() {}
	
	/**
	 * @return a Session Object
	 * @see Session
	 */
	@SuppressWarnings("deprecation")
	public static Session getInstance() {
		if(instance != null && instance.isOpen()) return instance;
		instance = sessionfactory.openSession();
		instance.beginTransaction();
//		System.out.println("opend session");
//		System.out.println(++opesSessions);
		return instance;
	}

	private static SessionFactory createFactory() {
		File file = FileUtil.getWorkspaceDirectory();
		file = new File(file.getAbsoluteFile()+"/hibernate.cfg.xml");
		AnnotationConfiguration n = new AnnotationConfiguration();
		SessionFactory sessionfactory = n.configure(file).buildSessionFactory();
		return sessionfactory;
	}
	
	
	
	public static void closeSession() {
		instance.close();
//		System.out.println("closed session");
//		System.out.println(--opesSessions);
//		instance = null;
	}
}