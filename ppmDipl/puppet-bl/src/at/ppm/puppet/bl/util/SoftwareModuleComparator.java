package at.ppm.puppet.bl.util;

import java.util.Comparator;

import at.ppm.puppet.bl.SoftwareModule;

public class SoftwareModuleComparator implements Comparator<SoftwareModule> {

	@Override
	public int compare(SoftwareModule o1, SoftwareModule o2) {
		if (o1.getVersions().size() == o2.getVersions().size()) {
			return o1.getName().compareTo(o2.getName());
		}
		return o1.getVersions().size() > o2.getVersions().size() ? 1 : -1;
	}

}
