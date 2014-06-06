package at.ppm.puppet.dal.hibpojos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@SuppressWarnings("serial")
@Entity
public class NodeIsInGroup implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
	@SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
	private int nodeIsInGroup_id;

	@ManyToOne
	@JoinColumn(name = "node_id")
	private Node node;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private Groups group;

	public int getNodesInGroup_id() {
		return nodeIsInGroup_id;
	}

	public void setNodesInGroup_id(int nodesInGroup_id) {
		this.nodeIsInGroup_id = nodesInGroup_id;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Groups getGroup() {
		return group;
	}

	public void setGroup(Groups group) {
		this.group = group;
	}

	
}
