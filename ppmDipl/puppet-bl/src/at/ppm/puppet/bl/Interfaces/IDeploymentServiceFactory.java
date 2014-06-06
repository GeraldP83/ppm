package at.ppm.puppet.bl.Interfaces;

public interface IDeploymentServiceFactory {

	INodeService createNodeService();
	IWatchService createWatchService();
	IGroupService createGroupService();
	
	
}
