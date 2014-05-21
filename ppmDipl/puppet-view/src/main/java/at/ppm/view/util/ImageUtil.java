package at.ppm.view.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import at.ppm.puppet.dal.utils.FileUtil;

public class ImageUtil {
	
	public static Image create(String path) {
			Image image;
			File file = FileUtil.getWorkspaceDirectory();
			URL filee = null;
			try {
				filee = new File(file.getAbsoluteFile() + File.separator + path).toURL();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ImageDescriptor img = ImageDescriptor.createFromURL(filee);
			image = img.createImage();
			return image;
		
	}

}
