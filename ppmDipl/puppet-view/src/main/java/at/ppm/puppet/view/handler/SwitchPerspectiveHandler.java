package at.ppm.puppet.view.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SwitchPerspectiveHandler {
	private final static String MAINPERSPECTIVE = "puppet-view.perspective.main";
	private final static String GROUPPERSPECTIVE = "puppet-view.perspective.group";

	@Execute
	public void execute(MWindow window, MApplication app, EPartService partService,
			EModelService modelService) {
		MPerspective element = (MPerspective) modelService.find(GROUPPERSPECTIVE, app);
		MPerspective activePerspective = modelService.getActivePerspective(window);
		if (activePerspective.getElementId().equalsIgnoreCase(MAINPERSPECTIVE)) {
			element = (MPerspective) modelService.find(GROUPPERSPECTIVE, app);
			partService.switchPerspective(element);
		}
		else {
			element = (MPerspective) modelService.find(MAINPERSPECTIVE, app);
			partService.switchPerspective(element);
	
		}
		// now switch perspective
	}

	
}
