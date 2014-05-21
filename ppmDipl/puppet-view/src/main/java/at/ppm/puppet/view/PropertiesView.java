package at.ppm.puppet.view;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;

import at.ppm.puppet.bl.DeploymentConfigService;
import at.ppm.puppet.bl.PuppetModule;
import at.ppm.puppet.dal.hibpojos.Module;
import at.ppm.puppet.dal.hibpojos.Node;
import at.ppm.view.util.Events;

public class PropertiesView {

	private Table table;
	private TableViewer tableViewer;

	public PropertiesView() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		tableViewer = new TableViewer(composite, SWT.BORDER
				| SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setFont(SWTResourceManager.getFont("Lucida Grande", 13,
				SWT.NORMAL));
		table.setHeaderVisible(true);

		TableViewerColumn softwareViewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		softwareViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getText(Object element) {
				PuppetModule prop = (PuppetModule) element;
				return prop == null ? "" : prop.getSoftwareName();
			}
		});
		TableColumn tblclmnNewColumn = softwareViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(150);
		tblclmnNewColumn.setText("Software");

		TableViewerColumn versionViewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		versionViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getText(Object element) {
				PuppetModule prop = (PuppetModule) element;
				return prop == null ? "" : prop.getSoftwareVersion();
			}
		});
		TableColumn tblclmnNewColumn_1 = versionViewerColumn.getColumn();
		tblclmnNewColumn_1.setWidth(90);
		tblclmnNewColumn_1.setText("Version");
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				return null;
			}
			public String getText(Object element) {
				PuppetModule prop = (PuppetModule) element;
				return prop == null ? "" : prop.getState();
			}
		});
		TableColumn tblclmnState = tableViewerColumn.getColumn();
		tblclmnState.setWidth(100);
		tblclmnState.setText("State");

		TableViewerColumn dateViewerColumn = new TableViewerColumn(tableViewer,
				SWT.NONE);
		dateViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getText(Object element) {
				PuppetModule prop = (PuppetModule) element;
				return prop == null ? "" : prop.getDate();
			}
		});
		TableColumn tblclmnNewColumn_2 = dateViewerColumn.getColumn();
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("Installation Date");
		tableViewer.setContentProvider(new ArrayContentProvider());

	}

	@Inject
	@Optional
	private void getNotified(@UIEventTopic(Events.NODESELECTED) Node node) {
		if (!(node instanceof Node)) {
			tableViewer.setInput(null);			
		}
		else {
			ArrayList<PuppetModule> prop = DeploymentConfigService.getPuppetModules(node);
			if (prop.size() == 0) {
				prop.add(new PuppetModule("Nothing Installed", null, null, null));
			}
			tableViewer.setInput(prop);			
		}
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO Set the focus to control
	}
}
