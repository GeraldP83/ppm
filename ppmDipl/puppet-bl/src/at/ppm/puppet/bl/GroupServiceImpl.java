package at.ppm.puppet.bl;

import java.util.ArrayList;

import at.ppm.puppet.bl.Interfaces.IGroupService;
import at.ppm.puppet.dal.IModelRepository;
import at.ppm.puppet.dal.ModelRepositoryImpl;
import at.ppm.puppet.dal.hibpojos.Groups;
import at.ppm.puppet.dal.hibpojos.Node;

public class GroupServiceImpl implements IGroupService {
	
	private final IModelRepository repo = new ModelRepositoryImpl();

	@Override
	public void createGroup(String groupName) {
		Groups group = new Groups();
		group.setName(groupName);
		repo.create(group);
	}

	@Override
	public boolean groupExists(String value) {
		if (repo.getGroup(value) == null) return false;
		return true;
	}

	@Override
	public void deleteGroup(String name) {
		Groups group = repo.getGroup(name);
		if (group != null) {
			repo.delete(group);
		}
		
	}

	@Override
	public ArrayList<Groups> getAllGroups() {
	return repo.getAll(Groups.class);
	}





}
