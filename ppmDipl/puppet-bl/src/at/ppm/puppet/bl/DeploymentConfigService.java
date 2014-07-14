package at.ppm.puppet.bl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javafx.scene.control.CheckBox;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import at.ppm.puppet.bl.Interfaces.INodeService;
import at.ppm.puppet.bl.Interfaces.INodeService.AssignmentState;
import at.ppm.puppet.bl.util.PropertyConfig;
import at.ppm.puppet.bl.util.PropertyConfig.PropertyFile;
import at.ppm.puppet.bl.util.SoftwareModuleComparator;
import at.ppm.puppet.dal.IModelRepository;
import at.ppm.puppet.dal.ModelRepositoryImpl;
import at.ppm.puppet.dal.hibpojos.Assignment;
import at.ppm.puppet.dal.hibpojos.Module;
import at.ppm.puppet.dal.hibpojos.ModuleVersion;
import at.ppm.puppet.dal.hibpojos.Node;
import at.ppm.puppet.dal.velocity.PuppetParamsForVelocity;
import at.ppm.puppet.dal.velocity.Velocity;

public class DeploymentConfigService {
	private final static IModelRepository repo = new ModelRepositoryImpl();
	private final static PropertyConfig modulConfig = new PropertyConfig(
			PropertyFile.MODUL);
	private static PuppetParamsForVelocity velocityObject;
	private static INodeService nodeservice = DeploymentServiceFactoryImpl
			.getInstance().createNodeService();

	private DeploymentConfigService() {
	}

	// public static <T> ArrayList<T> getAllFromTable(Class<T> c) {
	// ArrayList<T> all = repo.getAll(c);
	// return all;
	// }

	public static ArrayList<PuppetModule> getPuppetModules(Node node) {
		ArrayList<Assignment> assignments = node.getAssignment();
		ArrayList<PuppetModule> puppetModules = new ArrayList<PuppetModule>(
				assignments.size());
		for (Assignment assignment : assignments) {
			puppetModules.add(new PuppetModule(assignment.getModuleVersion()
					.getVersion(), assignment.getModuleVersion().getModule()
					.getName(), assignment.getDate(), assignment.getState()));
		}
		return puppetModules;
	}

	public static ArrayList<String> getSoftwareVersionsFromPropFile(
			String software) {
		ArrayList<String> foundVersion = new ArrayList<>();
		Properties props = modulConfig.getAll();
		Set<Object> keySet = props.keySet();
		Iterator<Object> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String element = (String) iterator.next();
			if (element.toLowerCase().contains(software.toLowerCase())) {
				foundVersion.add(element);
			}
		}
		return foundVersion;
	}

	public static ArrayList<SoftwareModule> createTreeStructureForJavaFx() {
		ArrayList<SoftwareModule> treeStructure = new ArrayList<>();
		ArrayList<Module> modules = repo.getAll(Module.class);
		ArrayList<String> versions = new ArrayList<>();
		String moduleName;
		for (Module module : modules) {
			moduleName = module.getName();
			versions = getSoftwareVersionsFromPropFile(module.getName());
			treeStructure.add(new SoftwareModule(moduleName, versions));
		}
		Collections.sort(treeStructure, new SoftwareModuleComparator());
		return treeStructure;
	}

	// TODO add puppet code here?
	public static void setInstallingStateToAssignments(
			ArrayList<CheckBox> boxes, Node node) {

		for (CheckBox box : boxes) {
			String nodeName = node.getName();
			String moduleName = box.getText();
			if (box.isSelected()) {
				if (!(nodeservice.nodeContainsSoftware(nodeName, moduleName))) {
					nodeservice.addAssignmentToNode(nodeName, moduleName);
				} else if ((nodeservice.getAssignmentFromNode(node, moduleName)
						.getState().equalsIgnoreCase(AssignmentState.UNINSTALL
						.toString()))) {
					Assignment assignment = nodeservice.getAssignmentFromNode(
							node, moduleName);
					nodeservice.changeAssignmentState(node, assignment,
							AssignmentState.INSTALLING);
				}
			} else {
				Assignment assignment = nodeservice.getAssignmentFromNode(node,
						moduleName);
				if ((nodeservice.nodeContainsSoftware(nodeName, moduleName))
						&& !(assignment.getState()
								.equalsIgnoreCase(AssignmentState.UNINSTALL
										.toString()))) {
					nodeservice.changeAssignmentState(node, assignment,
							AssignmentState.UNINSTALL);
				}
			}
		}
		Node n = repo.getNode(node.getNode_id());
		addAssignmentsToVelocityObject(n);

	}

	public static void addAssignmentsToVelocityObject(Node node) {
		velocityObject = new PuppetParamsForVelocity();
		velocityObject.setNode(node.getName());
		ArrayList<Assignment> assignments = node.getAssignment();
		for (Assignment assignment : assignments) {
			String moduleNameConvertedForPupped = assignment.getModuleVersion()
					.getModule().getName().toLowerCase()
					+ assignment.getModuleVersion().getVersion()
							.replaceAll("[\\W]", "");
			if (assignment.getState().equalsIgnoreCase(
					AssignmentState.INSTALLING.toString())) {
				velocityObject
						.addModule((moduleNameConvertedForPupped + AssignmentState.INSTALLING
								.toString()).toLowerCase());
			} else if (assignment.getState().equalsIgnoreCase(
					AssignmentState.UNINSTALL.toString())) {
				velocityObject
						.addModule((moduleNameConvertedForPupped + "UNINSTALLING")
								.toLowerCase());
			}

		}
		// File f = new File("Users/gerald/testing");
		//
		// System.out.println(f.getPath());

		Velocity.writeNodeFilesForPuppet(velocityObject);
	}

	public static void writePuppetInitFile() {
		ArrayList<Node> allNodes = nodeservice.getAllNodes();
		ArrayList<String> nodeNames = new ArrayList<String>();
		for (Node node : allNodes) {
			nodeNames.add(node.getName());
		}
		Velocity.writePuppetSiteFile(nodeNames);
	}

}
