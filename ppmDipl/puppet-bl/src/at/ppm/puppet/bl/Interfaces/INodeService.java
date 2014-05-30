package at.ppm.puppet.bl.Interfaces;

import java.util.ArrayList;

import at.ppm.puppet.bl.PuppetModule;
import at.ppm.puppet.dal.hibpojos.Assignment;
import at.ppm.puppet.dal.hibpojos.Node;

public interface INodeService {
	public enum AssignmentState {
		INSTALLING,
		INSTALLED,
		FAILED,
		UNINSTALL
	}
	
	ArrayList<Node> getAllNodes();
	Node getNode(String nodeName);
	void changeAssignmentState(Node node, Assignment assignment, AssignmentState state);
//	void removeAssignmentFromNode(String nodeName,String software);
	void removeAssignment(Assignment assignment);
	void createNode(String nodeName);
	boolean nodeExists(String nodeName);
	void deleteNodeAndAssignments(String nodeName);
	void addAssignmentToNode(String nodeName, String software);
	Assignment getAssignmentFromNode(Node node, String software);
	String getNodeState(ArrayList<Assignment> assignments);
	boolean nodeContainsSoftware(String nodeName, String software);
	void deleteNodeLogFile(String name);
	ArrayList<PuppetModule> getPuppetModules(Node node);
	void writePuppetInitFile();

}
