package kr.ac.hanyang.tosca2camp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kr.ac.hanyang.tosca2camp.definitiontypes.ArtifactDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.CapabilityDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.DataTypeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.GroupDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.InterfaceDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PolicyDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.VersionDef;

public class ServiceTemplate implements Cloneable{
	private VersionDef tosca_definitions_version; //Node Template name
	private Map<String,String> metaData;
	private String description; // description are treated as their own type but for now they will be string
	private List<String> directives; //Node template item
	//imports
	private List<ArtifactDef> artifactTypes;
	private List<DataTypeDef> dataTypes; 
	private List<CapabilityDef> capabilityTypes;
	private List<InterfaceDef> interfaceTypes;
	private List<RelationshipDef> relationshipTypes;
	private List<NodeDef> nodeTypes;
	private List<GroupDef> groupTypes;
	private List<PolicyDef> policyTypes;
	private TopologyTemplate topologyTemplate;
	
	
	public static class Builder {
		private VersionDef tosca_definitions_version;
		private Map<String,String> metaData;
		private String description; // description are treated as their own type but for now they will be string
		private List<String> directives;
		private List<ArtifactDef> artifactTypes = new ArrayList<ArtifactDef>();
		private List<DataTypeDef> dataTypes = new ArrayList<DataTypeDef>(); 
		private List<CapabilityDef> capabilityTypes = new ArrayList<CapabilityDef>();
		private List<InterfaceDef> interfaceTypes = new ArrayList<InterfaceDef>();
		private List<RelationshipDef> relationshipTypes = new ArrayList<RelationshipDef>();
		private List<NodeDef> nodeTypes = new ArrayList<NodeDef>();
		private List<GroupDef> groupTypes = new ArrayList<GroupDef>();
		private List<PolicyDef> policyTypes = new ArrayList<PolicyDef>();
		private TopologyTemplate topologyTemplate;
		
		//used to builde a type Definition
		public Builder(){
			this.tosca_definitions_version = tosca_definitions_version;
			//UUID.randomUUID().toString();
		}
		
		public Builder(VersionDef tosca_definitions_version){
			this.tosca_definitions_version = tosca_definitions_version;
		}
		
		public Builder metaData(Map<String,String> metaData){
			this.metaData = metaData;
			return  this;
		}
		
		public Builder description(String description){
			this.description = description;
			return  this;
		}
		
		public Builder directive(String directive){
			this.directives.add(directive);
			return this;
		}
		
		public Builder addArtifactType(ArtifactDef artifactType){
			this.artifactTypes.add(artifactType);
			return this;
		}
		
		public Builder addDataType(DataTypeDef dataType){
			this.dataTypes.add(dataType);
			return this;
		}
		
		public Builder addCapabilityType(CapabilityDef capabilityType){
			this.capabilityTypes.add(capabilityType);
			return this;
		}
		
		public Builder addInterfaceType(InterfaceDef interfaceType){
			this.interfaceTypes.add(interfaceType);
			return this;
		}
		
		public Builder addRelationshipType(RelationshipDef relationshipType){
			this.relationshipTypes.add(relationshipType);
			return this;
		}
		
		public Builder addNodeType(NodeDef nodeType){
			this.nodeTypes.add(nodeType);
			return this;
		}
		
		public Builder addGroupType(GroupDef groupType){
			this.groupTypes.add(groupType);
			return this;
		}
		
		public Builder addPolicyType(PolicyDef policyType){
			this.policyTypes.add(policyType);
			return this;
		}
		
		public Builder addTopologyTemplate(TopologyTemplate topologyTemplate){
			this.topologyTemplate = topologyTemplate;
			return this;
		}
		
		public ServiceTemplate build(){
			return new ServiceTemplate(this);
		}
	}
	
	
	
	protected ServiceTemplate(Builder builder){
		this.tosca_definitions_version = builder.tosca_definitions_version;
		this.metaData = builder.metaData;
		this.description = builder.description;
		this.directives = builder.directives;
		this.artifactTypes = builder.artifactTypes;
		this.dataTypes = builder.dataTypes;
		this.capabilityTypes = builder.capabilityTypes;
		this.interfaceTypes = builder.interfaceTypes;
		this.relationshipTypes = builder.relationshipTypes;
		this.nodeTypes = builder.nodeTypes;
		this.groupTypes = builder.groupTypes;
		this.policyTypes = builder.policyTypes;
		this.topologyTemplate = builder.topologyTemplate;
	}
	
	public Builder getBuilder(VersionDef tosca_definitions_version){
		Builder builder = new Builder(tosca_definitions_version);
		builder.metaData = this.metaData;
		builder.description = this.description;
		builder.directives = this.directives;
		builder.artifactTypes = this.artifactTypes;
		builder.dataTypes = this.dataTypes;
		builder.capabilityTypes = this.capabilityTypes;
		builder.interfaceTypes = this.interfaceTypes;
		builder.relationshipTypes = this.relationshipTypes;
		builder.nodeTypes = this.nodeTypes;
		builder.groupTypes = this.groupTypes;
		builder.policyTypes = this.policyTypes;
		builder.topologyTemplate = this.topologyTemplate;
		return builder;
	}	
	

	public VersionDef getVersion() {return tosca_definitions_version;}
	
	public Map<String, String> getMetaData() {return metaData;}
	
    public String getDescription() {return description;}

	public List<String> getDirectives() {return directives;}
	
	public List<ArtifactDef> getArtifacts() {return artifactTypes;}
	
	public List<DataTypeDef> getDataTypes() {return dataTypes;}
	
	public List<CapabilityDef> getCapabilityTypes() {return capabilityTypes;}
	
	public List<InterfaceDef> getInterfaceTypes() {return interfaceTypes;}
	
	public List<RelationshipDef> getRelationshipTypes() {return relationshipTypes;}
	
	public List<NodeDef> getNodeTypes() {return nodeTypes;}
	
	public List<GroupDef> getGroupTypes() {return groupTypes;}
	
	public List<PolicyDef> getPolicyTypes() {return policyTypes;}
	
	public TopologyTemplate getTopologyTemplate() {return topologyTemplate;}
	
}
