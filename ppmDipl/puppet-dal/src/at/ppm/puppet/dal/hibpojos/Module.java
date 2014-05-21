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

@SuppressWarnings("serial")
@Entity
public class Module implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_seq")
	@SequenceGenerator(name="user_seq", sequenceName="users_id_seq")
	private int module_id;
	
	@OneToMany(mappedBy="nodes")
	private Collection<ModuleVersion> module_versions = new ArrayList<>();
	
	private String name;
	private String description;
	
	public int getModule_id() {
		return module_id;
	}
	public void setModule_id(int module_id) {
		this.module_id = module_id;
	}
	public Collection<ModuleVersion> getModule_versions() {
		return module_versions;
	}
	public void setModule_versions(Collection<ModuleVersion> module_versions) {
		this.module_versions = module_versions;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return name;
	}
	
	

}
