package at.ppm.puppet.bl.Interfaces;

public interface IWatchService {
	
	public void registerListener(ILogFileListener listener);
	public void unregisterListener(ILogFileListener listener);

}
