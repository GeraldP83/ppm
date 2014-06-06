package at.ppm.puppet.bl.Interfaces;

import java.util.ArrayList;

import at.ppm.puppet.bl.GroupModule;
import at.ppm.puppet.bl.PuppetModule;
import at.ppm.puppet.dal.hibpojos.Groups;

public interface IGroupService {
	
	void createGroup(String groupName);

	boolean groupExists(String value);

	void deleteGroup(String name);

	ArrayList<Groups> getAllGroups();

	ArrayList<GroupModule> getGroupModules(Groups lastSelectedGroup);

}
