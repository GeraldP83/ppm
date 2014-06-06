package at.ppm.puppet.bl.Interfaces;

import java.util.ArrayList;

import at.ppm.puppet.dal.hibpojos.Groups;

public interface IGroupService {
	
	void createGroup(String groupName);

	boolean groupExists(String value);

	void deleteGroup(String name);

	ArrayList<Groups> getAllGroups();

}
