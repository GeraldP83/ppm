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
//
//public class CreateNodeTest {
//	private final String NODENAME = TestConstants.NODENAME;
//
//	@Before
//	public void before() {
//		cleanDb();
//	}
//
//	
//	@Test
//	public void addOneNode(){
////		PropertyConfig propConf = new PropertyConfig(PropertyFile.NODE);
//		DeploymentConfigService.createNode(NODENAME);
//		
////		Assert.assertTrue(propConf.containsNode(NODENAME));
//		Assert.assertEquals(1, DeploymentConfigService.getAllNodes().size());
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
