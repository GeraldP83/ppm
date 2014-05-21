package at.ppm.puppet.dal.velocity;

import java.util.ArrayList;
import java.util.List;

public class PuppetParamsForVelocity {
	
	private String node;
	private List<String> modules = new ArrayList<>();
	
	public void setNode(String node) {
		this.node = node;
	}
	
	public String getNode() {
		return node;
	}
	
	public void addModule(String moduleName) {
		modules.add(moduleName);
	}
	public List<String> getModules() {
		return modules;
	}

	
}