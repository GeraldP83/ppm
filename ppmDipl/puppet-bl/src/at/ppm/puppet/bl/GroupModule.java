package at.ppm.puppet.bl;

public class GroupModule {
	private String name;
	private String version;
	
	public GroupModule(String name, String version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSoftwareVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getNameAndVersion() {
		return name+version;
	}

}
