package at.ppm.puppet.dal.hibpojos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



@SuppressWarnings("serial")
@Entity
public class GroupHasModule implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
	@SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
	private int groupHasModule_id;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Groups group;
	
	@ManyToOne
	@JoinColumn(name = "module_version_id")
	private ModuleVersion moduleVersion;

	public int getGroupHasModule_id() {
		return groupHasModule_id;
	}

	public void setGroupHasModule_id(int groupHasModule_id) {
		this.groupHasModule_id = groupHasModule_id;
	}

	public Groups getGroup() {
		return group;
	}

	public void setGroup(Groups group) {
		this.group = group;
	}

	public ModuleVersion getModuleVersion() {
		return moduleVersion;
	}

	public void setModuleVersion(ModuleVersion moduleVersion) {
		this.moduleVersion = moduleVersion;
	}
	
	
}
