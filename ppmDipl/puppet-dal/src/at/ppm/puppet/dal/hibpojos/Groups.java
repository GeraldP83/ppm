package at.ppm.puppet.dal.hibpojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
@SuppressWarnings("serial")
@Entity
public class Groups implements Serializable, Comparable<Groups> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
	@SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
	private int group_id;
	
	@OneToMany(mappedBy="group")
//	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<NodeIsInGroup> nodesInGroup = new ArrayList<NodeIsInGroup>();
	
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
//	@LazyCollection(LazyCollectionOption.FALSE) 
	private Collection<GroupHasModule> groupHasModule = new ArrayList<GroupHasModule>();
	
	private String name;
	
	

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public ArrayList<GroupHasModule> getGroupHasModule() {
		return new ArrayList<GroupHasModule>(groupHasModule);
		
	}

	public void setGroupHasModule(Collection<GroupHasModule> groupHasModule) {
		this.groupHasModule = groupHasModule;
	}

	public Collection<NodeIsInGroup> getNodesInGroup() {
		return nodesInGroup;
	}

	public void setNodesInGroup(Collection<NodeIsInGroup> nodesInGroup) {
		this.nodesInGroup = nodesInGroup;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Groups o) {
		return this.getName().compareTo(o.getName());
	}
	
	
	
	
	
}
