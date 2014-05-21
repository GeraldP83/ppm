package at.ppm.puppet.dal.hibpojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@SuppressWarnings("serial")
@Entity(name="module_version")
public class ModuleVersion implements Serializable {
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
	@SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
	private int module_version_id;
	
	@ManyToOne
	@JoinColumn(name="module_id")
	private Module module;
	

	private String version;
	private Date date;
	
	@OneToMany(mappedBy="module")
	private Collection<Assignment> nodes = new ArrayList<Assignment>();

	public Module getModule() {
		return module;
	}
	
	public void setModule(Module module) {
		this.module = module;
	}
	
	public int getModule_version_id() {
		return module_version_id;
	}

	public void setModule_version_id(int module_version_id) {
		this.module_version_id = module_version_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Collection<Assignment> getNodes() {
		return nodes;
	}

	public void setNodes(Collection<Assignment> nodes) {
		this.nodes = nodes;
	}

	
	
	

}
