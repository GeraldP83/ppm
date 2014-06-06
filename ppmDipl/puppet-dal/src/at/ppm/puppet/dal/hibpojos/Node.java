package at.ppm.puppet.dal.hibpojos;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
@SuppressWarnings("serial")
@Entity
public class Node implements Serializable, Comparable<Node>{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
	@SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
	private int node_id;
	
	private String name;
	private String os;



	@OneToMany(mappedBy="node",fetch=FetchType.EAGER)
	private Collection<Assignment> assignment = new ArrayList<Assignment>();
	
	@OneToMany(mappedBy="node")
	private Collection<NodeIsInGroup> nodeIsInGroup = new ArrayList<NodeIsInGroup>();
	
	public void addAssignment(Assignment a) {
		assignment.add(a);
	}

	public int getNode_id() {
		return node_id;
	}

	public void setNode_id(int node_id) {
		this.node_id = node_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public ArrayList<Assignment> getAssignment() {
		return new ArrayList<>(assignment);
	}

	public void setAssignment(ArrayList<Assignment> assignment) {
		this.assignment = assignment;
	}

	@Transient
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assignment == null) ? 0 : assignment.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + node_id;
		result = prime * result + ((os == null) ? 0 : os.hashCode());
		return result;
	}

	@Transient
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (node_id != other.node_id)
			return false;
		return true;
	}


	@Override
	public int compareTo(Node o) {
		return this.getName().compareTo(o.getName());
	}
	
	

	
}
