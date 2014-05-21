//package handlerTests;
//
//import java.util.ArrayList;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import testUtil.TestConstants;
//import at.ppm.puppet.bl.DeploymentConfigService;
//import at.ppm.puppet.dal.hibpojos.Node;
//
//public class CreateNodeException {
//	
//	private final String NODENAME = TestConstants.NODENAME;
//
//
//	@Before
//	public void before() {
//		cleanDb();
//		DeploymentConfigService.createNode(NODENAME);
//	}
//	
//	@Test(expected=IllegalArgumentException.class)
//	public void createExistingNode() {
//		DeploymentConfigService.createNode(NODENAME);
//	}
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
