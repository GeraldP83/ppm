package at.ppm.puppet.bl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.ArrayList;
import java.util.List;

import at.ppm.puppet.bl.Interfaces.ILogFileListener;
import at.ppm.puppet.bl.Interfaces.INodeService;
import at.ppm.puppet.bl.Interfaces.INodeService.AssignmentState;
import at.ppm.puppet.bl.Interfaces.IWatchService;
import at.ppm.puppet.dal.hibpojos.Assignment;
import at.ppm.puppet.dal.hibpojos.Node;

/**
 * @author gerald
 *overwatches the logs directory. If puppet writes a new logfile the service will pass the name of 
 *the new, or updated file to the handler. Only saved files will be sent, no .swp files.
 */
public class WatchServiceImpl extends Thread implements IWatchService {

	private static final String WATCH_PATH = "/etc/puppet/logs";
	private ArrayList<ILogFileListener> listeners;
	private INodeService nodeService; 
	
	
	public WatchServiceImpl() {
		nodeService = DeploymentServiceFactoryImpl.getInstance().createNodeService();
		listeners = new ArrayList<ILogFileListener>();
		this.start();
	}
	/*
	 * (non-Javadoc)
	 */
	@Override
	public void run() {
		try {
			Path path = Paths.get(WATCH_PATH);
			for(;;) {
				java.nio.file.WatchService watcher = FileSystems.getDefault().newWatchService();
				path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
				WatchKey key = watcher.take();
				List<WatchEvent<?>> events = key.pollEvents();
				for (WatchEvent event : events) {
					System.out.println(event.context().toString());
					if (!(event.context().toString().contains(".swp"))) {
						handleWatcherEvent(event.context().toString());
					}
				}	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // end run

	private void handleWatcherEvent(String nodeNameLogFile) {
		String subString = nodeNameLogFile.substring(0,	nodeNameLogFile.lastIndexOf("."));
		System.out.println(subString);
		Node node = nodeService.getNode(subString);
		ArrayList<Assignment> assignments = node.getAssignment();
		File f = new File("/etc/puppet/logs/" + nodeNameLogFile);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			while (line != null) {
				if (line.startsWith("status")) {
					String state = (line.substring(7));
					if (state.equals("changed")) {
						for (Assignment assignment : assignments) {
							if (assignment.getState().equalsIgnoreCase(AssignmentState.INSTALLING.toString())) {
								nodeService.changeAssignmentState(node, assignment,AssignmentState.INSTALLED);
							} else if (assignment.getState().equalsIgnoreCase(AssignmentState.UNINSTALL.toString())) {
								nodeService.removeAssignment(assignment);
							}
						}
					}
					//check for bugs!!
					if (state.equals("unchanged")) {
						for (Assignment assignment : assignments) {
							if (assignment.getState().equalsIgnoreCase(AssignmentState.UNINSTALL.toString())) {
								nodeService.removeAssignment(assignment);
							}
						}
					}

				}
				line = br.readLine();
			}
			node = null;
			fireChangedEvent();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void registerListener(ILogFileListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
		
	}

	@Override
	public void unregisterListener(ILogFileListener listener) {
		if (listeners.contains(listener)) {
			int index = listeners.indexOf(listener);
			listeners.remove(index);
		}
		
	}

	
	private void fireChangedEvent() {
		if (listeners.size() > 0) {
			for (ILogFileListener listener : listeners) {
				listener.logFileChanged();
			}
		}
		
	}
}


