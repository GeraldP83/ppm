package at.ppm.puppet.bl;

import java.util.ArrayList;

public class SoftwareModule {
	
	private String name;
	private ArrayList<String> versions;
	

	public SoftwareModule(String moduleName, ArrayList<String> versions) {
		this.name = moduleName;
		if (!(versions.size() > 0)) {
			this.versions = null;
		}
		this.versions = versions;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<String> getVersions() {
		return versions;
	}

	
	

}
