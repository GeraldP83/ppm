package at.ppm.puppet.dal;

import java.security.acl.Group;
import java.util.ArrayList;

import at.ppm.puppet.dal.hibpojos.Assignment;
import at.ppm.puppet.dal.hibpojos.GroupHasModule;
import at.ppm.puppet.dal.hibpojos.Groups;
import at.ppm.puppet.dal.hibpojos.ModuleVersion;
import at.ppm.puppet.dal.hibpojos.Node;
import at.ppm.puppet.dal.hibpojos.NodeIsInGroup;

public interface IModelRepository {
	
	boolean create(Object object);
	
	Node getNode(int id);
	
	<T> ArrayList<T> getAll(Class<T> clazz);
	
	ArrayList<Assignment> getAssignmentsFromNode(Node node);
	
	void deleteAssignment(Assignment assignment);
	
	void deleteNodeAndAssignments(Node node);
	
	ModuleVersion getModuleVersion(int id);
	
	void addAssignmentToNode(Node node, Assignment assignment, ModuleVersion module);
	
	void update(Object obj);
	
	void addNodeToGroup(Node node, NodeIsInGroup nodeIsInGroup, Groups group);
	
	Groups getGroup(String name);

	boolean delete(Object object);

	void addGroupHasModuleToGroup(Groups group, GroupHasModule groupHasModule,
			ModuleVersion module);

	
	
	

}
