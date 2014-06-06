package at.ppm.puppet.dal.hibpojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
@SuppressWarnings("serial")
@Entity
public class Groups implements Serializable, Comparable<Groups> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
	@SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
	private int group_id;
	
	@OneToMany(mappedBy="group")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<NodeIsInGroup> nodesInGroup = new ArrayList<NodeIsInGroup>();
	
	private String name;

	public int getGropu_id() {
		return group_id;
	}

	public void setGropu_id(int gropu_id) {
		this.group_id = gropu_id;
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
