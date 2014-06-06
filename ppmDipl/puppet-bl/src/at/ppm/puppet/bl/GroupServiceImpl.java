package at.ppm.puppet.bl;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import at.ppm.puppet.bl.Interfaces.IGroupService;
import at.ppm.puppet.dal.IModelRepository;
import at.ppm.puppet.dal.ModelRepositoryImpl;
import at.ppm.puppet.dal.hibpojos.GroupHasModule;
import at.ppm.puppet.dal.hibpojos.Groups;
import at.ppm.puppet.dal.hibpojos.ModuleVersion;
import at.ppm.puppet.dal.utils.HibernateSession;

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
		if (repo.getGroup(value) == null)
			return false;
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

	@Override
	public ArrayList<GroupModule> getGroupModules(Groups lastSelectedGroup) {
		Session session = HibernateSession.getInstance();
		Transaction tx = null;
			tx = session.beginTransaction();
		
			session.update(lastSelectedGroup);
			tx.commit();
		
		ArrayList<GroupHasModule> groupHasModules = lastSelectedGroup.getGroupHasModule();
		ArrayList<GroupModule> groupModules = new ArrayList<GroupModule>(groupHasModules.size());
		for (GroupHasModule groupModule : groupHasModules) {
			groupModules.add(new GroupModule(groupModule.getModuleVersion().getModule().getName(), groupModule.getModuleVersion().getVersion()));
		}
		return groupModules;

	}

}
