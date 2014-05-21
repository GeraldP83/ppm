package at.ppm.puppet.bl;

import at.ppm.puppet.bl.Interfaces.IDeploymentServiceFactory;
import at.ppm.puppet.bl.Interfaces.INodeService;
import at.ppm.puppet.bl.Interfaces.IWatchService;

public class DeploymentServiceFactoryImpl implements IDeploymentServiceFactory{
	
	private  final static DeploymentServiceFactoryImpl instance = new DeploymentServiceFactoryImpl();
	
	private DeploymentServiceFactoryImpl(){}
	
	public static DeploymentServiceFactoryImpl getInstance() {
		return instance;
	}

	@Override
	public INodeService createNodeService() {
		return new NodeServiceImpl();
	}

	@Override
	public IWatchService createWatchService() {
		return new WatchServiceImpl();
	}

}
