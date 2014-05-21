package at.ppm.puppet.dal.velocity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


/**
 * @author gerald
 *
 */
public class Velocity {
	
	private static final String PUPPET_TEMPLATE_Nodes = "puppetTemplateNodes.vm";
	private static final String PUPPET_TEMPLATE_INIT = "puppetTemplateInit.vm";

	public static void writeNodeFilesForPuppet(PuppetParamsForVelocity velocityObject) {
		
		
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("resource.loader", "class");
		velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityEngine.init();

		        /*  first, get and initialize an engine  */
		        /*  next, get the Template  */
		        org.apache.velocity.Template t = velocityEngine.getTemplate(PUPPET_TEMPLATE_Nodes);
		        /*  create a context and add data */
		        VelocityContext context = new VelocityContext();
		        context.put("name", velocityObject.getNode());
		        context.put("list", velocityObject.getModules());
		        /* now render the template into a StringWriter */
		        StringWriter writer = new StringWriter();
		        t.merge( context, writer );
		        /* show the World */
		        File f = new File("/etc/puppet/manifests/" + velocityObject.getNode() + ".pp");
//		        String toWrite = writer.toString();
		        try {
					FileWriter fw = new FileWriter(f);
					BufferedWriter bfw = new BufferedWriter(fw);
					bfw.write(writer.toString());
					bfw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        System.out.println( writer.toString() );     
		    
	}

	/**
	 * writes the import statements for the init.pp
	 * @param nodeNames
	 */
	public static void writePuppetSiteFile(ArrayList<String> nodeNames) {
		
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("resource.loader", "class");
		velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityEngine.init();

		   
		        org.apache.velocity.Template t = velocityEngine.getTemplate(PUPPET_TEMPLATE_INIT);
		        VelocityContext context = new VelocityContext();
		        context.put("list", nodeNames);
		        /* now render the template into a StringWriter */
		        StringWriter writer = new StringWriter();
		        t.merge( context, writer );
		        /* show the World */
		        File f = new File("/etc/puppet/manifests/site.pp");
//		        String toWrite = writer.toString();
		        try {
					FileWriter fw = new FileWriter(f);
					BufferedWriter bfw = new BufferedWriter(fw);
					bfw.write(writer.toString());
					bfw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        System.out.println( writer.toString() );  
		
	}
	
}
