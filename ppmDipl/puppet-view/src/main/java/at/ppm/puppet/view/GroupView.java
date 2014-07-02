package at.ppm.puppet.view;


import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
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

import at.ppm.puppet.bl.DeploymentServiceFactoryImpl;
import at.ppm.puppet.bl.Interfaces.IGroupService;
import at.ppm.puppet.dal.hibpojos.Groups;
import at.ppm.view.util.Events;

public class GroupView {
	
	private IGroupService groupService;
	private Table nodesViewTable;
	@Inject
	private IEventBroker eventbroker;
	private ArrayList<Groups> list = new ArrayList<Groups>();
	private Groups selectedGroup;
	private TableViewer tableViewerGroupsView;
	private int currentlySelectedGroup;
	private TableColumn nodeNameColumn;


	public GroupView() {
		groupService = DeploymentServiceFactoryImpl.getInstance().createGroupService();
	}

	/**
	 * Create contents of the view part.
	 */

	@PostConstruct
	public void createControls(final Composite parent) {
		
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

		tableViewerGroupsView = new TableViewer(TableComposite, SWT.BORDER | SWT.FULL_SELECTION);

		/**
		 * the eventbroker sends the selected node to the propView
		 */
		tableViewerGroupsView.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						IStructuredSelection selection = (IStructuredSelection) tableViewerGroupsView.getSelection();
						currentlySelectedGroup = tableViewerGroupsView.getTable().getSelectionIndex();
						if (!selection.isEmpty()) {
							selectedGroup = (Groups) selection.getFirstElement();
							eventbroker.post(Events.GROUPSELECTED, selectedGroup);
						} else {
							selectedGroup = null;
							eventbroker.post(Events.GROUPSELECTED, null);
						}
						updateView();
					}
				});
		nodesViewTable = tableViewerGroupsView.getTable();
		nodesViewTable.setFont(SWTResourceManager.getFont("Lucida Grande", 14, SWT.NORMAL));
		nodesViewTable.setHeaderVisible(true);
		nodesViewTable.setBounds(0, 0, 18, 81);

		TableViewerColumn groupNameTableViewerColumn = new TableViewerColumn(
				tableViewerGroupsView, SWT.NONE);
		groupNameTableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			public Image getImage(Object element) {
				// TODO Auto-generated method stub
				return null;
			}

			public String getText(Object element) {
				Groups group = (Groups) element;
				return group.getName();
			}
		});
		nodeNameColumn = groupNameTableViewerColumn.getColumn();
		nodeNameColumn.setWidth(150);
		nodeNameColumn.setText("Group Name");
		tableViewerGroupsView.setContentProvider(new ArrayContentProvider());

		Button addButton = new Button(ButtonComposite, SWT.NONE);
		addButton.setText("Add Group");
		addButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String groupName = createDialog();
				if (groupName == null) return;
				groupService.createGroup(groupName);
				updateView();
			}
		});

		Button deleteButton = new Button(ButtonComposite, SWT.NONE);
		/*
		 * 
		 */
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (selectedGroup == null) {
					return;
				}
				groupService.deleteGroup(selectedGroup.getName());
				updateView();
			}
		});
		deleteButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		deleteButton.setText("Delete Group");
		new Label(ButtonComposite, SWT.NONE);
		updateView();

	}

	protected String createDialog() {
		Shell shell = new Shell();
		InputDialog inputDialog = new InputDialog(shell);
		return inputDialog.open();
	}

	private void updateView() {
		list = groupService.getAllGroups();
		Collections.sort(list);
		tableViewerGroupsView.setInput(list);
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
			shell.setText("Create Group Dialog");
			shell.setLocation(50, 50);

			shell.setLayout(new GridLayout(2, true));

			Label label = new Label(shell, SWT.NULL);
			label.setText("Please enter a Groupname:");

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
						if (value.length() == 0 || groupService.groupExists(value)) {
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


	
	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO Set the focus to control
	}
}

