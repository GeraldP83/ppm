package at.ppm.puppet.bl.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.IllegalSelectorException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import at.ppm.puppet.dal.utils.FileUtil;

public class PropertyConfig {
	
	public enum PropertyFile {
		NODE("node.config"),
		MODUL("module.config");
		
		private String fileName;
		
		private PropertyFile(String fileName) {
			this.fileName = fileName;
		}
		
		private Properties load() {
			File file = getFileFromBundle();
			try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))){
				Properties p = new Properties();
				p.load(in);
				return p;
				
			} catch (Exception e) {
				
			}
			return null;
		}
		
		private void save(Properties props) {
			File file = getFileFromBundle();
			try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))){
				props.store(out, null);	
			} catch (Exception e) {
				throw new IllegalStateException("Can not save node to prop file.");
				
			}
		}

		private File getFileFromBundle() {
			File file = FileUtil.getWorkspaceDirectory();	
			file = new File(file.getAbsoluteFile() + File.separator + fileName);
//			System.out.println(file.getAbsolutePath());
//			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//			URL url = classLoader.getResource("./"+fileName);
//			try {
//				if (url.toString().startsWith("f")) {
//					file = new File(url.toURI());
//				}
//				else {
//					file = new File(FileLocator.resolve(url).toURI());				
//				}
//			} catch (URISyntaxException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return file;
		}
		
		public String getFileName() {
			return fileName;
		}

		private void delete(String key) {
			Properties props = this.load();
			props.remove(key);
			this.save(props);
		}
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	private PropertyFile file;
	
	public PropertyConfig(PropertyFile configFile) {
		this.file = configFile;
	}
	
	
	public void set(String key, int value) {
		Properties props = file.load();
		//TODO check if key already exists
		props.setProperty(key, Integer.toString(value));
		file.save(props);
	
	}
	
	public int get(String key) {
		Properties props = file.load();
		if (!props.containsKey(key)) {throw new IllegalArgumentException();}
		return Integer.valueOf(props.getProperty(key));
	}
	
	public boolean containsNode(String nodeName) {
		try {
			get(nodeName);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public void deleteEntry(String key) {
		file.delete(key);
	}


	public Properties getAll() {
		return file.load();
	}
}
