package at.ppm.puppet.dal;

import java.util.ArrayList;

import at.ppm.puppet.dal.hibpojos.Assignment;
import at.ppm.puppet.dal.hibpojos.ModuleVersion;
import at.ppm.puppet.dal.hibpojos.Node;

public interface IModelRepository {
	
	ArrayList<String> getDependenciesFromModule(String checkBoxName);
	
	boolean create(Object object);
	
	Node getNode(int id);
	
	<T> ArrayList<T> getAll(Class<T> clazz);
	
	ArrayList<Assignment> getAssignmentsFromNode(Node node);
	
	void deleteAssignment(Assignment assignment);
	
	void deleteNodeAndAssignments(Node node);
	
	ModuleVersion getModuleVersion(int id);
	
	void addAssignmentToNode(Node node, Assignment assignment, ModuleVersion module);
	
	void update(Object obj);

}
