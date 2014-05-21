//package handlerTests;
//
//import java.util.ArrayList;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import testUtil.TestConstants;
//import at.ppm.puppet.bl.DeploymentConfigService;
//import at.ppm.puppet.bl.util.PropertyConfig;
//import at.ppm.puppet.bl.util.PropertyConfig.PropertyFile;
//import at.ppm.puppet.dal.hibpojos.Node;
//
//public class AddExistingAssignmentException {
//	
//	private final String NODENAME = TestConstants.NODENAME;
//	private final String JAVA = TestConstants.JAVA;
//	
//	@Before
//	public void before() {
//		cleanDb();
//		DeploymentConfigService.createNode(NODENAME);
//		DeploymentConfigService.addAssignmentToNode(NODENAME, JAVA);
//	}
//
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void addOneNode(){
//		DeploymentConfigService.addAssignmentToNode(NODENAME, JAVA);
//	}
//	
//	
//	
//	
//	
//	private void cleanDb() {
//		ArrayList<Node> allNodes = DeploymentConfigService.getAllNodes();
//		for (Node node : allNodes) {
//			DeploymentConfigService.deleteNodeAndAssignments(node.getName());
//		}
//	}
//	
//	@After
//	public void after() {
//		cleanDb();
//	}
//
//}
