package at.ppm.puppet.dal.utils;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

public class FileUtil {
	public static File getWorkspaceDirectory() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		File file = workspace.getRoot().getLocation().toFile();
//		file = new File(file.getAbsoluteFile() + "\\medialibrary\\");
		return file;
	}
}