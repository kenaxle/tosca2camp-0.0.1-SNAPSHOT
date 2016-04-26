package kr.ac.hanyang.tosca2camp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import kr.ac.hanyang.tosca2camp.assignments.AttributeAs;
import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;
import kr.ac.hanyang.tosca2camp.assignments.NodeTemplate;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RelationshipTemplate;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.AdminEndpointCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.AttachmentCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.BindableNetworkCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.ContainerCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.DatabaseEndpointCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.EndpointCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.NodeCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.OperatingSystemCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.PublicEndpointCapability;
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.ScalableCapability;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.ApplicationContainerNode;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.BlockStorageNode;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.ComputeNode;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.DBMSNode;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.DatabaseNode;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.LoadBalancerNode;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.ObjectStorageNode;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.RootNode;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.RuntimeContainerNode;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.SoftwareComponentNode;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.WebApplicationNode;
import kr.ac.hanyang.tosca2camp.datatypes.nodes.WebServerNode;
import kr.ac.hanyang.tosca2camp.definitiontypes.AttributeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.CapabilityDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef.Builder;
import kr.ac.hanyang.tosca2camp.definitiontypes.PropertyDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RequirementDef;
import kr.ac.hanyang.tosca2camp.toscaTypes.ListEntry;


/**
 * Hello world!
 *
 */
public class App{
	
	private final String FILEPATH = "C:/Users/Kena/git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/definitions/";
	
	//hardcode the names of the definition files only for testing
	private String[] nodeDefFileNames = {"tosca.nodes.Root.yml","tosca.nodes.BlockStorage.yml","tosca.nodes.Compute.yml",
			"tosca.nodes.Container.Application.yml","tosca.nodes.Container.Runtime.yml","tosca.nodes.Database.yml",
			"tosca.nodes.DBMS.yml","tosca.nodes.LoadBalancer.yml","tosca.nodes.ObjectStorage.yml",
			"tosca.nodes.SoftwareComponent.yml","tosca.nodes.WebApplication.yml","tosca.nodes.WebServer.yml"};
	
	private String[] capDefFileNames = {"tosca.capabilities.Root.yml","tosca.capabilities.Scalable.yml","tosca.capabilities.OperatingSystem.yml",
			 "tosca.capabilities.Node.yml","tosca.capabilities.Endpoint.yml","tosca.capabilities.Endpoint.Database.yml",
			 "tosca.capabilities.Endpoint.Admin.yml","tosca.capabilities.Container.yml","tosca.capabilities.Bindable.yml",
			 "tosca.capabilities.Attachment.yml"};
	
	private String[] relDefFileNames = {"tosca.relationships.Root.yml","tosca.relationships.ConnectsTo.yml","tosca.relationships.DependsOn.yml",
			 "tosca.relationships.HostedOn.yml","tosca.relationships.RoutesTo.yml","tosca.relationships.AttachesTo.yml"};
	
	private Map<String, NodeDef> nodeDefinitions = new LinkedHashMap<String, NodeDef>();
	private Map<String, CapabilityDef> capDefinitions = new LinkedHashMap<String, CapabilityDef>(); 
	private Map<String, RelationshipDef> relDefinitions = new LinkedHashMap<String, RelationshipDef>();
	
	private Map<String, NodeTemplate> nodeTemplates = new LinkedHashMap<String, NodeTemplate>();
	private Map<String, RelationshipTemplate> relTemplates = new LinkedHashMap<String, RelationshipTemplate>();
	
	//load the Normative type definitions
	//-------------------------------------------------------------------------------
	@SuppressWarnings({ "unchecked" })
	private void loadDefinition(String fileName) throws FileNotFoundException{
			Yaml yaml = new Yaml();
			Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File(fileName)));
			for(String defName:map.keySet())			
				nodeDefinitions.put(defName,parseNodeDef(defName,(Map<String, Object>)map.get(defName)));
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private void loadCapability(String fileName) throws FileNotFoundException{
		Yaml yaml = new Yaml();
		Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File(fileName)));
		for(String defName:map.keySet())			
			 capDefinitions.put(defName,parseCapDef(defName,(Map<String, Object>)map.get(defName)));
	}
	
	@SuppressWarnings("unchecked")
	private void loadRelationship(String fileName) throws FileNotFoundException{
		Yaml yaml = new Yaml();
		Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File(fileName)));
		for(String defName:map.keySet())			
			 relDefinitions.put(defName,parseRelDef(defName,(Map<String, Object>)map.get(defName)));
	}
	
	
	
	private <T> NodeDef parseNodeDef(String name, Map<String, Object>nodeMap){
		String type = (String) nodeMap.get("type");
		String parentDef = (String) nodeMap.get("derived_from");
		
		NodeDef.Builder nodeDefBuilder;
		NodeDef returnNode;
		// if not null then copy the nodeDef
		//TODO find the def in the list if already loaded othewise load the def
		//Builder nDef;
		if (parentDef!=null){
			NodeDef parent = (NodeDef) nodeDefinitions.get(parentDef);
			if(parent !=null){
				returnNode = NodeDef.clone(parent); //copy the parent and then get a builder to add new functionality
				nodeDefBuilder = returnNode.getBuilder(name,type); 
			}else{
				//try to load the parent definition
				try{
					loadDefinition(FILEPATH+parentDef+".yml");
				}catch(Exception e){
					System.out.println("The definition "+FILEPATH+parentDef+" does not exist. \n Will build incomplete def");
				}
				nodeDefBuilder = new NodeDef.Builder(name, type);
			}
		}else{
			nodeDefBuilder = new NodeDef.Builder(name, type); 
		}
		//continue parsing the definition.
		for(String key: nodeMap.keySet()){
			switch(key){
			case "properties":
				Map<String,Object> propDefMap = (Map<String,Object>)nodeMap.get(key);
				for(String propName:propDefMap.keySet()){
					nodeDefBuilder.addProperty(parsePropDef(propName,(Map<String, Object>)propDefMap.get(propName)));
				}
				break;
			case "attributes":
				Map<String,Object> attrDefMap = (Map<String,Object>)nodeMap.get(key);
				for(String attrName:attrDefMap.keySet()){
					nodeDefBuilder.addAttribute(parseAttrDef(attrName,(Map<String, Object>)attrDefMap.get(attrName)));
				}
				break;
			case "requirements":
				List<Map<String,Object>> reqDefList = (List<Map<String,Object>>)nodeMap.get(key);
				for(Map<String, Object> reqMap:reqDefList){
					//nodeDefBuilder.addRequirement(parseReqDef(reqName,(Map<String, Object>)reqDefMap.get(reqName)));
					
					String reqName = reqMap.keySet().iterator().next();
					nodeDefBuilder.addRequirement(parseReqDef(reqName,(Map<String, Object>)reqMap.get(reqName)));
					//propBuilder.addConstraint(new ListEntry.Builder<>(key,constraint.get(key)).build());
				}
				break;
			case "capabilities":
				Map<String,Object> capDefMap = (Map<String,Object>)nodeMap.get(key);
				for(String capName:capDefMap.keySet()){
					nodeDefBuilder.addCapabilitiy(parseCapDef(capName,(Map<String, Object>)capDefMap.get(capName)));
				}
				break;
			case "interfaces":
//				Map<String,Object> interDefMap = (Map<String,Object>)nodeMap.get(key);
//				for(String ifaceName:interDefMap.keySet()){
//					nodeDefBuilder.addInterface(parseInterDef(ifaceName,(Map<String, Object>)interDefMap.get(ifaceName)));
//				}
				break;
			case "artifacts":
//				Map<String,Object> artiFactDefMap = (Map<String,Object>)nodeMap.get(key);
//				for(String artifactName:artiFactDefMap.keySet()){
//					nodeDefBuilder.addArtifact(parseArtifactDef(artifactName,(Map<String, Object>)artiFactDefMap.get(artifactName)));
//				}
				break;
			default:
				break;
			}
		}		
		returnNode = nodeDefBuilder.build();
		//nodeDefinitions.put(name,rNode);
		return returnNode;
	}
	
	public PropertyDef parsePropDef(String name, Map<String, Object> propMap){
		String type = (String) propMap.get("type");
		PropertyDef.Builder propBuilder = new PropertyDef.Builder(name, type);
		for(String mapItem:propMap.keySet()){
			switch(mapItem){
			case "description":
				propBuilder.description((String)propMap.get(mapItem));
				break;
			case "required":
				propBuilder.required((boolean)propMap.get(mapItem));
				break;
			case "default_value":
				propBuilder.defaultVal((String)propMap.get(mapItem));
				break;
			case "status":
				propBuilder.status((String)propMap.get(mapItem));
				break;
			case "constraints":
				@SuppressWarnings("unchecked")
				List<Map<String,Object>> conList = (List<Map<String, Object>>) propMap.get(mapItem);
				for(Map<String,Object> constraint:conList){
					String key = constraint.keySet().iterator().next();
					propBuilder.addConstraint(new ListEntry.Builder<>(key,constraint.get(key)).build());	
				}
				break;
			case "entry_schema":
				propBuilder.entry_schema((String)propMap.get(mapItem));
				break;
			}
		}
		return propBuilder.build();
	}
	
	public AttributeDef parseAttrDef(String name, Map<String, Object> attrMap){
		String type = (String) attrMap.get("type");
		AttributeDef.Builder attrBuilder = new AttributeDef.Builder(name, type);
		for(String mapItem:attrMap.keySet()){
			switch(mapItem){
			case "description":
				attrBuilder.description((String)attrMap.get(mapItem));
				break;
			case "default_value":
				attrBuilder.defaultVal((String)attrMap.get(mapItem));
				break;
			case "status":
				attrBuilder.status((String)attrMap.get(mapItem));
				break;
			case "entry_schema":
//				attrBuilder.entry_schema((String)attrMap.get(mapItem));
				break;
			}
		}
		return attrBuilder.build();
	}
	
	@SuppressWarnings({ "rawtypes" })
	public RequirementDef parseReqDef(String name, Map<String, Object> reqMap){
		String capability = (String) reqMap.get("capability");	
		RequirementDef.Builder reqBuilder = new RequirementDef.Builder(name, capability);
		for(String mapItem:reqMap.keySet()){
			switch(mapItem){
			case "node":
				reqBuilder.node((String)reqMap.get(mapItem));
				break;
			case "relationship":
				reqBuilder.relationship((String)reqMap.get(mapItem));
				break;
			case "occurence":
				reqBuilder.occurence((String)reqMap.get(mapItem));
				break;
			}
		}
		return reqBuilder.build();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public CapabilityDef parseCapDef(String name, Map<String, Object> capMap){
		String type;
		//capability is normative
		if (Arrays.asList(capDefFileNames).contains(name))
			type =  name.substring(name.lastIndexOf(".") + 1);
		else
			type = (String) capMap.get("type");
		String parentDef = (String) capMap.get("derived_from");
		
		CapabilityDef.Builder capBuilder;
		CapabilityDef returnCapability;
		// if not null then copy the nodeDef
		//TODO find the def in the list if already loaded othewise load the def
		//Builder nDef;
		if (parentDef!=null){
			CapabilityDef parent = (CapabilityDef) capDefinitions.get(parentDef);
			if(parent !=null){
				returnCapability = CapabilityDef.clone(parent); //copy the parent and then get a builder to add new functionality
				capBuilder = returnCapability.getBuilder(name,type); 
			}else{
				//try to load the parent definition
				try{
					loadCapability(FILEPATH+parentDef+".yml");
				}catch(Exception e){
					System.out.println("The definition "+FILEPATH+parentDef+" does not exist. \n Will build incomplete def");
				}
				capBuilder = new CapabilityDef.Builder(name, type);
			}
		}else{
			capBuilder = new CapabilityDef.Builder(name, type); 
		}
		
		for(String mapItem:capMap.keySet()){
			switch(mapItem){
			case "description":
				capBuilder.description((String)capMap.get(mapItem));
				break;
//			case "derived_from":
//				capBuilder.derived_from((String)capMap.get(mapItem));
//				break;
			case "properties":
				Map<String, Object> propMap = (Map<String, Object>) capMap.get(mapItem);
				for(String propName:propMap.keySet()){
					capBuilder.addProperty(parsePropDef(propName,(Map<String, Object>)propMap.get(propName)));	
				}
				break;
			case "attributes":
				Map<String, Object> attrMap = (Map<String, Object>) capMap.get(mapItem);
				for(String attrName:attrMap.keySet()){
					capBuilder.addAttribute(parseAttrDef(attrName,(Map<String, Object>)attrMap.get(attrName)));	
				}
				break;
			case "valid_source_types":
				List<String> sourcesList = (List<String>) capMap.get(mapItem);
				for(String attrName:sourcesList){
					capBuilder.addValid_source_types(attrName);	
				}
				break;
			}
		}
		return capBuilder.build();
	}
	
	public RelationshipDef parseRelDef(String name, Map<String, Object> relMap){
		String parentDef = (String) relMap.get("derived_from");
		
		RelationshipDef.Builder relBuilder;
		RelationshipDef returnRel;
		// if not null then copy the nodeDef
		//TODO find the def in the list if already loaded othewise load the def
		//Builder nDef;
		if (parentDef!=null){
			RelationshipDef parent = (RelationshipDef) relDefinitions.get(parentDef);
			if(parent !=null){
				returnRel = RelationshipDef.clone(parent); //copy the parent and then get a builder to add new functionality
				relBuilder = returnRel.getBuilder(name); 
			}else{
				//try to load the parent definition
				try{
					loadDefinition(FILEPATH+parentDef+".yml");
				}catch(Exception e){
					System.out.println("The definition "+FILEPATH+parentDef+" does not exist. \n Will build incomplete def");
				}
				relBuilder = new RelationshipDef.Builder(name);
			}
		}else{
			relBuilder = new RelationshipDef.Builder(name); 
		}
		
		for(String mapItem:relMap.keySet()){
			switch(mapItem){
			case "description":
				relBuilder.description((String)relMap.get(mapItem));
				break;
//			case "derived_from":
//				relBuilder.derived_from((String)relMap.get(mapItem));
//				break;
			case "properties":
				Map<String, Object> propMap = (Map<String, Object>) relMap.get(mapItem);
				for(String propName:propMap.keySet()){
					relBuilder.addProperty(parsePropDef(propName,(Map<String, Object>)propMap.get(propName)));	
				}
				break;
			case "attributes":
				Map<String, Object> attrMap = (Map<String, Object>) relMap.get(mapItem);
				for(String attrName:attrMap.keySet()){
					relBuilder.addAttribute(parseAttrDef(attrName,(Map<String, Object>)attrMap.get(attrName)));	
				}
				break;
			case "valid_target_types":
				List<String> sourcesList = (List<String>) relMap.get(mapItem);
				for(String attrName:sourcesList){
					relBuilder.addValid_target_types(attrName);	
				}
				break;
			case "interfaces":
			    // handle this case here
				break;
			}
		}
		return relBuilder.build();
	}
	
	//-------------------------------------------------------------------------------

	private List<String> getBuildStack(NodeDef nodeDef){
		List<String> retList = null;
		if (nodeDef.getDerived_from() == null){
			retList =  new ArrayList();
			retList.add(nodeDef.getType());
		}else 
		{
			retList = getBuildStack(nodeDefinitions.get(nodeDef.getDerived_from()));
			retList.add(nodeDef.getType());
		}
		return retList;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <V> boolean parseNode(String name, Map<String, Object>nodeMap){
		boolean valid;
		String type = (String) nodeMap.get("type");
		NodeTemplate.Builder<V> nodeBuilder = new NodeTemplate.Builder<>(name, type);
		String typeName = "";
		
		switch(type){
		case "Root": typeName = "tosca.nodes.Root"; break;
		case "Compute": typeName = "tosca.nodes.Compute"; break;
		case "SoftwareComponent": typeName = "tosca.nodes.SoftwareComponent"; break;
		case "WebServer": typeName = "tosca.nodes.WebServer"; break;
		case "WebApplication": typeName = "tosca.nodes.WebApplication"; break;
		case "DBMS": typeName = "tosca.nodes.DBMS"; break;
		case "Database": typeName = "tosca.nodes.Database"; break;
		case "ObjectStorage": typeName = "tosca.nodes.ObjectStorage"; break;
		case "BlockStorage": typeName = "tosca.nodes.BlockStorage"; break;
		case "Container.Runtime": typeName = "tosca.nodes.Container.Runtime"; break;
		case "Container.Application": typeName = "tosca.nodes.Container.Application"; break;
		case "LoadBalancer": typeName = "tosca.nodes.LoadBalancer"; break;
		default: if(!type.isEmpty())
					typeName = type;//test if the type is not empty then its long type
		break; //use the empty string
		}
		
		//try to get the definition or try to load it if its normative
		NodeDef myDefinition = (NodeDef) nodeDefinitions.get(typeName);
		
			//returnNode = NodeDef.clone(parent); //copy the parent and then get a builder to add new functionality
			//nodeDefBuilder = returnNode.getBuilder(name,type); 
		if(myDefinition == null){
			//try to load the parent definition
			try{
				loadDefinition(FILEPATH+typeName+".yml");
				myDefinition = (NodeDef) nodeDefinitions.get(type);
			}catch(Exception e){
				System.out.println("The definition "+FILEPATH+typeName+" does not exist. \n Will build incomplete def");
				return false;
			}
			//nodeDefBuilder = new NodeDef.Builder<Builder>(name, type);
		}
		
		nodeBuilder.description((String) nodeMap.get("description"));
		//nodeBuilder.directives(directives); TODO
		Map<String,Object> propMap = ((Map<String,Object>) nodeMap.get("properties"));
		if (propMap != null){
			for(String propertyName:propMap.keySet()){
				nodeBuilder.addProperty(new PropertyAs.Builder<V>(propertyName,(V)propMap.get(propertyName)).build());
			}
		}
		
		Map<String,Object> attrMap = ((Map<String,Object>) nodeMap.get("attributes"));
		if (attrMap != null){
			for(String attributeName:propMap.keySet()){
				nodeBuilder.addAttribute(new AttributeAs.Builder<V>(attributeName,(V)propMap.get(attributeName)).build());
			}
		}
		
		Map<String,Object> capMap = ((Map<String,Object>) nodeMap.get("capabilities"));
		if (capMap != null){
			for(String capName:capMap.keySet()){
				nodeBuilder.addCapability(parseCapability(capName,(Map<String, Object>)capMap.get(capName)));
			}
		}
		
		List<Map<String,Object>> reqList = ((List<Map<String,Object>>) nodeMap.get("requirements"));
		if (reqList != null){
			for(Map<String,Object> reqMap:reqList){ 
				String reqName = reqMap.keySet().iterator().next();
				nodeBuilder.addRequirement(parseRequirement(reqName,(Map<String, Object>)reqMap.get(reqName)));
			}
		}
		
		NodeTemplate node = nodeBuilder.build();
		valid = myDefinition.validate(node);
		if (valid){
			nodeTemplates.put(node.getName(), node);
			return valid;
		}
		return false;
	}
	
	public boolean loadRelationship(String name, Map<String,Object>relMap){
		boolean valid;
		String type = (String) relMap.get("type");
		RelationshipDef myDefinition = (RelationshipDef) relDefinitions.get(type);
		if(myDefinition == null){
			//try to load the definition
			try{
				loadRelationship(FILEPATH+type+".yml");
				myDefinition = (RelationshipDef) relDefinitions.get(type);
			}catch(Exception e){
				System.out.println("The definition "+FILEPATH+type+" does not exist. \n Will build incomplete def");
				return false;
			}
		}
		RelationshipTemplate relTemplate = parseRelationship(type,(Map<String, Object>) relMap.get("properties"));
		valid = myDefinition.validate(relTemplate);
		if (valid){
			relTemplates.put(relTemplate.getName(), relTemplate);
			return valid;
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CapabilityAs parseCapability(String name, Map<String, Object> capMap){
		CapabilityAs.Builder capBuilder = new CapabilityAs.Builder(name);
		
		String type;
		switch(name){
		case "host": type = "tosca.capabilities.Container"; break;
		case "endpoint": type = "tosca.capabilities.Endpoint"; break;
		case "public_endpoint": type = "tosca.capabilities.Endpoint.Public"; break;
		case "admin_endpoint": type = "tosca.capabilities.Endpoint.Admin"; break;
		case "database_endpoint": type = "tosca.capabilities.Endpoint.Database"; break;
		case "attachment": type = "tosca.capabilities.Attachment"; break;
		case "os": type = "tosca.capabilities.OperatingSystem"; break;
		case "scalable": type = "tosca.capabilities.Scalable"; break;
		case "bindable": type = "tosca.capabilities.Bindable"; break;
		default: type = "tosca.capabilities.Root"; break;
		}
		
		CapabilityDef myDefinition = (CapabilityDef) capDefinitions.get(type);
		if(myDefinition == null){
			//try to load the definition
			try{
				loadCapability(FILEPATH+type+".yml");
				myDefinition = (CapabilityDef) capDefinitions.get(type);
			}catch(Exception e){
				System.out.println("The definition "+FILEPATH+type+" does not exist. \n Will build incomplete def");
			}
		}
		
		Map<String,Object> propMap = ((Map<String,Object>) capMap.get("properties"));
		for(String propertyName:propMap.keySet()){
			capBuilder.addProperty(new PropertyAs.Builder(propertyName,propMap.get(propertyName)).build());
		}
		
		Map<String,Object> attrMap = ((Map<String,Object>) capMap.get("attributes"));
		for(String attributeName:propMap.keySet()){
			capBuilder.addAttribute(new AttributeAs.Builder(attributeName,propMap.get(attributeName)).build());
		}
		//do validation
		return capBuilder.build();
	}
	
	//TODO p.g. 261 should be able to parse extended grammar with properties
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <V> RelationshipTemplate parseRelationship(String type, Map<String, Object> property){
		RelationshipTemplate.Builder builder = new RelationshipTemplate.Builder(type,"desc");
		for (String key:property.keySet()){
			builder.addProperties(new PropertyAs.Builder<V>(key,(V)property.get(key)).build());
		}
		return builder.build();
	}	
	
	//TODO p.g. 260 - 261 this should be able to parse short or extended form 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T, U, V> RequirementAs parseRequirement(String name, Map<String, Object> requirement){
		RequirementAs.Builder reqBuilder = new RequirementAs.Builder(name);
		for (String key:requirement.keySet()){
			switch (key){
				case "capability":
					Map<String, Object> capMap = (Map<String, Object>) requirement.get(key);
					for(String key2:capMap.keySet()){
						String capType = (String) capMap.get(key2); // get the type of capability
						Map<String, Object> capProperties = (Map<String, Object>) capMap.get("properties");
						reqBuilder.capability(parseCapability(capType, capProperties));
					}
					break;
				case "node":
					reqBuilder.node(new NodeTemplate.Builder<>((String) requirement.get(key), "tosca.nodes.Root").build());//(parseNode((String) requirement.get(key), null)); //create an empty node with the name. this will be a root node
					break;
				case "relationship":
					Map<String, Object> relationshipMap = (Map<String, Object>) requirement.get(key);
					String relType = (String) relationshipMap.get("type"); // get the type of relationship
					Map<String, Object> relProperties = (Map<String, Object>) relationshipMap.get("properties");
					reqBuilder.relationship(parseRelationship(relType, relProperties));
					break;
				
				default:
					break;
			}
		}
		return reqBuilder.build();
	}
	
	
	public void parseTosca(Map<String, Object> toscaMap){
		for (String key:toscaMap.keySet()){
			switch (key){
			case "tosca_definitions_version":
					
				break;
			case "description":
				
				break;
			case "topology_template":
				Map<String, Object> topologyTemplateMap = (Map<String,Object>) toscaMap.get(key);
				for (String topologyItem:topologyTemplateMap.keySet()){
					switch (topologyItem){
					case "inputs":break;
					case "node_templates":
						Map<String, Object> nodeTemplateMap = (Map<String, Object>) topologyTemplateMap.get(topologyItem);
						for(String nodeTemplate:nodeTemplateMap.keySet()){
							System.out.println(parseNode(nodeTemplate,(Map<String,Object>)nodeTemplateMap.get(nodeTemplate)));
							//Map<String, Object> propObj = (Map<String, Object>) capObj.get(key2);
						}
						break;
					case "outputs":	break;
					default:
						break;
					}
				}
				break;
			default:
				break;
		
			}
		}
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public static void main( String[] args ) throws Exception{
		
		// load the Normative definitions
		//-----------------------------------------------
		App app = new App();
		for(String fileName: app.nodeDefFileNames){
			app.loadDefinition(app.FILEPATH+fileName);
		}
		for(String fileName: app.capDefFileNames){
			app.loadCapability(app.FILEPATH+fileName);
		}
		for(String fileName: app.relDefFileNames){
			app.loadRelationship(app.FILEPATH+fileName);
		}
		//-----------------------------------------------
		
		// Parse the Yaml plan
		//-----------------------------------------------
	    Yaml yaml = new Yaml();
		Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File("C:/Users/Kena/Git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/Sample1.yml")));
		app.parseTosca(map);
			
//			for(String key:map.keySet()){
//				
//				switch (key){
//				case "node_templates":
//					Map<String, Object> nodeTempMap = (Map<String, Object>) map.get(key);
//					for(String nodeType:nodeTempMap.keySet()){
//						System.out.println(parseNode(nodeType,(Map<String,Object>)nodeTempMap.get(nodeType)));
//						//Map<String, Object> propObj = (Map<String, Object>) capObj.get(key2);
//					}
//					break;
//				default:
//					break;
//			
//				}
//			}
	}
}
				
				
				
				

	
					
