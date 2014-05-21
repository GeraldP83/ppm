package at.ppm.puppet.bl;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.ppm.puppet.bl.Interfaces.INodeService.AssignmentState;

public class PuppetModule {
	private String softwareName;
	private String softwareVersion;
	private String state;
	private Date installationDate;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	

	public PuppetModule(String version, String name, Date date, String state) {
		softwareVersion = version;
		softwareName = name;
		installationDate = date;
		setState(state);
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}
	
	public String getDate() {
		if (installationDate == null) {
			return "no date set";
		}
		return df.format(installationDate);
	}
	
	public String getNameAndVersion() {
		return softwareName+softwareVersion;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (state == null) {
			state = "";
			return;
		}
		if ((state.equalsIgnoreCase(AssignmentState.INSTALLING.toString())) || (state.equalsIgnoreCase(AssignmentState.INSTALLED.toString())) 
				|| (state.equalsIgnoreCase(AssignmentState.FAILED.toString()) || (state.equalsIgnoreCase(AssignmentState.UNINSTALL.toString())))) {
			this.state = state;
		}
		else {
			throw new IllegalArgumentException("Wrong state for " + this.getClass().getName() + " " + softwareName);
		}
	}

	
}
