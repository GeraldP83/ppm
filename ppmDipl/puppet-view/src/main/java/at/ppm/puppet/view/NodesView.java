package at.ppm.puppet.view;

import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import at.ppm.puppet.bl.DeploymentConfigService;
import at.ppm.puppet.bl.DeploymentServiceFactoryImpl;
import at.ppm.puppet.bl.Interfaces.ILogFileListener;
import at.ppm.puppet.bl.Interfaces.INodeService;
import at.ppm.puppet.bl.Interfaces.IWatchService;
import at.ppm.puppet.dal.hibpojos.Node;
import at.ppm.view.util.Events;
import at.ppm.view.util.ImageUtil;

public class NodesView implements ILogFileListener{
	
	private INodeService nodeService;
	private IWatchService watchService;

	private Table nodesViewTable;
	@Inject
	private IEventBroker eventbroker;
	private ArrayList<Node> list = new ArrayList<Node>();
	private Node selectedNode;
	private TableViewer tableViewerNodesView;
	private int currentlySelectedNode;
	private TableColumn nodeNameColumn;


	public NodesView() {
		nodeService = DeploymentServiceFactoryImpl.getInstance().createNodeService();
		watchService = DeploymentServiceFactoryImpl.getInstance().createWatchService();
	}

	/**
	 * Create contents of the view part.
	 */

	@PostConstruct
	public void createControls(final Composite parent) {
		
		watchService.registerListener(this);
		GridLayout gl_parent = new GridLayout(1, true);
		gl_parent.marginTop = 1;
		gl_parent.marginRight = 1;
		gl_parent.marginLeft = 1;
		gl_parent.marginHeight = 1;
		gl_parent.verticalSpacing = 1;
		gl_parent.marginWidth = 1;
		parent.setLayout(gl_parent);

		Composite TableComposite = new Composite(parent, SWT.NONE);
		Composite ButtonComposite = new Composite(parent, SWT.BORDER);
		ButtonComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1));
		ButtonComposite.setLayout(new GridLayout(3, true));
		TableComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		GridData gd_TableComposite = new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1);
		gd_TableComposite.heightHint = 374;
		TableComposite.setLayoutData(gd_TableComposite);

		tableViewerNodesView = new TableViewer(
				TableComposite, SWT.BORDER | SWT.FULL_SELECTION);

		/**
		 * the eventbroker sends the selected node to the propView
		 */
		tableViewerNodesView.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						IStructuredSelection selection = (IStructuredSelection) tableViewerNodesView.getSelection();
						currentlySelectedNode = tableViewerNodesView.getTable().getSelectionIndex();
						if (!selection.isEmpty()) {
							selectedNode = (Node) selection.getFirstElement();
							eventbroker.post(Events.NODESELECTED, selectedNode);
						} else {
							selectedNode = null;
							eventbroker.post(Events.NODESELECTED, null);
						}
						updateView(tableViewerNodesView);
					}
				});
		nodesViewTable = tableViewerNodesView.getTable();
		nodesViewTable.setFont(SWTResourceManager.getFont("Lucida Grande", 14,
				SWT.NORMAL));
		nodesViewTable.setHeaderVisible(true);
		nodesViewTable.setBounds(0, 0, 18, 81);

		TableViewerColumn statusTableViewerColumn = new TableViewerColumn(
				tableViewerNodesView, SWT.NONE);
		statusTableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			public Image getImage(Object element) {
				Node node = (Node)element;
				if (node.getAssignment().size() < 1) return null;
				String state = nodeService.getNodeState(node.getAssignment());
				Image image = ImageUtil.create(state);
				return image;
			}

			public String getText(Object element) {
				return null;
			}
		});
		TableColumn statusColumn = statusTableViewerColumn.getColumn();
		statusColumn.setWidth(50);
		statusColumn.setText("Status");

		TableViewerColumn nodeNameTableViewerColumn = new TableViewerColumn(
				tableViewerNodesView, SWT.NONE);
		nodeNameTableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getText(Object element) {
				Node node = (Node) element;
				return node.getName();
			}
		});
		nodeNameColumn = nodeNameTableViewerColumn.getColumn();
		nodeNameColumn.setWidth(150);
		nodeNameColumn.setText("Node Name");
		tableViewerNodesView.setContentProvider(new ArrayContentProvider());

		Button addButton = new Button(ButtonComposite, SWT.NONE);
		addButton.setText("Add Node");
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String nodeName = createDialog();
				if (nodeName == null) return;
				nodeService.createNode(nodeName);
				DeploymentConfigService.writePuppetInitFile();
				updateView(tableViewerNodesView);
			}
		});

		Button deleteButton = new Button(ButtonComposite, SWT.NONE);
		/*
		 * 
		 */
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (selectedNode == null) {
					return;
				}
				nodeService.deleteNodeLogFile(selectedNode.getName());
				nodeService.deleteNodeAndAssignments(selectedNode.getName());
				DeploymentConfigService.writePuppetInitFile();
				updateView(tableViewerNodesView);
			}
		});
		deleteButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		deleteButton.setText("Delete Node");
		new Label(ButtonComposite, SWT.NONE);
		updateView(tableViewerNodesView);

	}

	protected String createDialog() {
		Shell shell = new Shell();
		InputDialog inputDialog = new InputDialog(shell);
		return inputDialog.open();
	}

	private void updateView(TableViewer tableViewer) {
		list = nodeService.getAllNodes();
		Collections.sort(list);
		tableViewer.setInput(list);
	
	}

	private class InputDialog extends Dialog {
		String value;

		/**
		 * @param parent
		 */
		public InputDialog(Shell parent) {
			super(parent);
		}

		/**
		 * @param parent
		 * @param style
		 */
		public InputDialog(Shell parent, int style) {
			super(parent, style);
		}

		/**
		 * Makes the dialog visible.
		 * 
		 * @return
		 */
		public String open() {
			Shell parent = getParent();
			final Shell shell = new Shell(parent, SWT.TITLE | SWT.BORDER
					| SWT.APPLICATION_MODAL);
			shell.setText("Create Node Dialog");

			shell.setLayout(new GridLayout(2, true));

			Label label = new Label(shell, SWT.NULL);
			label.setText("Please enter a Nodename:");

			final Text text = new Text(shell, SWT.SINGLE | SWT.BORDER);

			final Button buttonOK = new Button(shell, SWT.PUSH);
			buttonOK.setText("Ok");
			buttonOK.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			Button buttonCancel = new Button(shell, SWT.PUSH);
			buttonCancel.setText("Cancel");

			text.addListener(SWT.Modify, new Listener() {
				public void handleEvent(Event event) {
					try {
						value = text.getText();
						if (value.length() == 0 || nodeService.nodeExists(value)) {
							buttonOK.setEnabled(false);
							return;
						}
						buttonOK.setEnabled(true);
					} catch (Exception e) {
						buttonOK.setEnabled(false);
					}
				}
			});

			buttonOK.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					// TODO validate!!! wirte method in bl
					shell.dispose();
				}
			});

			buttonCancel.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					value = null;
					shell.dispose();
				}
			});
			shell.addListener(SWT.Traverse, new Listener() {
				public void handleEvent(Event event) {
					if (event.detail == SWT.TRAVERSE_ESCAPE)
						event.doit = false;
				}
			});

			text.setText("");
			shell.pack();
			shell.open();

			Display display = parent.getDisplay();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}

			return value;
		}

	}

	
	@Inject
	@Optional
	private void getNotified(@UIEventTopic(Events.DEPLOINGSTARTED) Node node) {
		updateAndRefreshSelection();
	}

	private void updateAndRefreshSelection() {
		updateView(tableViewerNodesView);
		tableViewerNodesView.getTable().setSelection(currentlySelectedNode);	
		IStructuredSelection selection = (IStructuredSelection) tableViewerNodesView.getSelection();
		tableViewerNodesView.setSelection(selection);
	}

	
	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO Set the focus to control
	}

	@Override
	public void logFileChanged() {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				updateAndRefreshSelection();
				
			}
		});
		
		
	}
}
