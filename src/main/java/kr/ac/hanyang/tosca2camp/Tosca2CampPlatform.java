package kr.ac.hanyang.tosca2camp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import kr.ac.hanyang.tosca2camp.definitiontypes.AttributeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.CapabilityDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.ConstraintTypeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.DataTypeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.EntrySchemaDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PolicyDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PropertyDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RequirementDef;


public class Tosca2CampPlatform{
	
	private final Logger log = LoggerFactory.getLogger(Tosca2CampPlatform.class);
	
	private Map<String, NodeDef> nodeDefinitions;
	private Map<String, CapabilityDef> capDefinitions; 
	private Map<String, RelationshipDef> relDefinitions;
	private Map<String, DataTypeDef> dataDefinitions;
	private Map<String, PolicyDef> policyDefinitions;
	private Map<String, ServiceTemplate> serviceTemplates;

	
	public Tosca2CampPlatform(){
		nodeDefinitions = new LinkedHashMap<String, NodeDef>();
		capDefinitions = new LinkedHashMap<String, CapabilityDef>(); 
		relDefinitions = new LinkedHashMap<String, RelationshipDef>();
		dataDefinitions = new LinkedHashMap<String, DataTypeDef>();
		policyDefinitions = new LinkedHashMap<String, PolicyDef>();
		serviceTemplates =new LinkedHashMap<String, ServiceTemplate>();
		
	}
	
	public static Tosca2CampPlatform newPlatform(){
		
		try{
			Tosca2CampPlatform app = new Tosca2CampPlatform();
			// load the datatypes
			for(String fileName: ToscaConstants.DTYPEDEF_FILENAMES){
				app.loadDataTypes(ToscaConstants.FILEPATH+fileName);
			}
			//load the capability types
			for(String fileName: ToscaConstants.CAPDEF_FILENAMES){
				app.loadCapability(ToscaConstants.FILEPATH+fileName);
			}
			//load the relationship types
			for(String fileName: ToscaConstants.RELDEF_FILENAMES){
				app.loadRelationship(ToscaConstants.FILEPATH+fileName);
			}
			//load the nodetypes
			for(String fileName: ToscaConstants.NODEDEF_FILENAMES){
				app.loadDefinition(ToscaConstants.FILEPATH+fileName);
			}
			//load policytypes
			for(String fileName: ToscaConstants.PTYPEDEF_FILENAMES){
				app.loadPolicy(ToscaConstants.FILEPATH+fileName);
			}
			return app;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
			
	}
	
	

	//---------------------Type Definition Parsers---------------------------------
	@SuppressWarnings({ "unchecked" })
	private void loadDefinition(String fileName) throws FileNotFoundException{
			Yaml yaml = new Yaml();
			Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File(fileName)));
			for(String defName:map.keySet())			
				nodeDefinitions.put(defName,parseNodeDef(defName,(Map<String, Object>)map.get(defName)));
	}
	
	@SuppressWarnings({ "unchecked" })
	private void loadCapability(String fileName) throws FileNotFoundException{
		Yaml yaml = new Yaml();
		Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File(fileName)));
		for(String defType:map.keySet()){			
			CapabilityDef capDef = parseCapDef(defType,(Map<String, Object>)map.get(defType));
			capDefinitions.put(capDef.getType(),capDef);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadRelationship(String fileName) throws FileNotFoundException{
		Yaml yaml = new Yaml();
		Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File(fileName)));
		for(String defTypeName:map.keySet())			
			 relDefinitions.put(defTypeName,parseRelDef(defTypeName,(Map<String, Object>)map.get(defTypeName)));
	}
	
	private void loadDataTypes(String fileName) throws FileNotFoundException{
		Yaml yaml = new Yaml();
		Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File(fileName)));
		for(String defName:map.keySet())			
			 dataDefinitions.put(defName,parseDataTypeDef(defName,(Map<String, Object>)map.get(defName)));
	}
	
	private void loadPolicy(String fileName) throws FileNotFoundException{
		Yaml yaml = new Yaml();
		Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File(fileName)));
		for(String defName:map.keySet())			
			 policyDefinitions.put(defName,parsePolicyTypeDef(defName,(Map<String, Object>)map.get(defName)));
	}
	
	
	
	private NodeDef parseNodeDef(String typeName, Map<String, Object>nodeMap){
		
		if (nodeMap == null) 
			return new NodeDef.Builder(typeName).build();

		NodeDef thisNode = (NodeDef) nodeDefinitions.get(typeName);
		if(thisNode !=null){
			return (NodeDef) thisNode.clone(); //copy the node
		}else{

		String parentDef = (String) nodeMap.get("derived_from");
		NodeDef.Builder nodeDefBuilder;
		NodeDef returnNode;
		// if not null then copy the nodeDef
		//TODO find the def in the list if already loaded othewise load the def
		//Builder nDef;
		if (parentDef!=null){
			NodeDef parent = (NodeDef) nodeDefinitions.get(parentDef);
			if(parent !=null){
				returnNode = (NodeDef) parent.clone(); //copy the parent and then get a builder to add new functionality. maybe dont clone.
				nodeDefBuilder = returnNode.getBuilder(typeName); 
				nodeDefBuilder.derived_from(returnNode); //add the parent
			}else{
				//try to load the parent definition
				try{
					loadDefinition(ToscaConstants.FILEPATH+ServiceTemplateUtilities.normalizeTypeName(parentDef,"node")+".yml");
					//clone and get builder here also
				}catch(Exception e){
					System.out.println(e.getMessage());
					System.out.println("The definition "+ToscaConstants.FILEPATH+ServiceTemplateUtilities.normalizeTypeName(parentDef,"node")+".yml does not exist. \n Will build incomplete def");
				}
				nodeDefBuilder = new NodeDef.Builder(typeName); // may have to go in the try
			}
		}else{
			nodeDefBuilder = new NodeDef.Builder(typeName); 
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
					String reqName = reqMap.keySet().iterator().next();
					nodeDefBuilder.addRequirement(parseReqDef(reqName,(Map<String, Object>)reqMap.get(reqName)));
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
		return returnNode;
	}
}
	
	public EntrySchemaDef parseEntrySchema(Map<String, Object> propMap){
		String type = (String) propMap.get("type");
		DataTypeDef dataDef = dataDefinitions.get(ToscaConstants.TYPEPREFIX+type); //TODO I may have to clone 
		if (dataDef == null){
			try{
				loadDataTypes(ToscaConstants.FILEPATH+ToscaConstants.TYPEPREFIX+type+".yml");
				dataDef = dataDefinitions.get(ToscaConstants.TYPEPREFIX+type);
			}
			catch(Exception e){
				return null; // dont have the definition
			}
		}
		EntrySchemaDef.Builder entrySchemaBuilder = new EntrySchemaDef.Builder(dataDef); //assume we will find the type
		for(String mapItem:propMap.keySet()){
			switch(mapItem){
			case "description":
				entrySchemaBuilder.description((String)propMap.get(mapItem));
				break;
			case "constraints":
				List<Map<String,Object>> conList = (List<Map<String, Object>>) propMap.get(mapItem);
				for(Map<String,Object> constraint:conList){
					String key = constraint.keySet().iterator().next();
					entrySchemaBuilder.addConstraint(new ConstraintTypeDef.Builder(key).value(constraint.get(key)).build());	
				}
				break;
			}
		}
		return entrySchemaBuilder.build();
	}
		
	public PropertyDef parsePropDef(String name, Map<String, Object> propMap){
		
		PropertyDef.Builder propBuilder = new PropertyDef.Builder(name);
		for(String mapItem:propMap.keySet()){
			switch(mapItem){
			case "type":
				String type = (String) propMap.get("type");
				// load the definition from the list
				DataTypeDef dataDef = dataDefinitions.get(ServiceTemplateUtilities.normalizeTypeName(type,"datatype")); //TODO I may have to clone 
				if (dataDef == null){
					try{
						loadDataTypes(ToscaConstants.FILEPATH+ServiceTemplateUtilities.normalizeTypeName(type,"datatype")+".yml");
						dataDef = dataDefinitions.get(ServiceTemplateUtilities.normalizeTypeName(type,"datatype"));
					}
					catch(Exception e){
						return null; // dont have the definition
					}
				}
				propBuilder.type(dataDef); 
				break;
			case "description":
				propBuilder.description((String)propMap.get(mapItem));
				break;
			case "required":
				propBuilder.required((Boolean)propMap.get(mapItem));
				break;
			case "default_value":
				propBuilder.defaultVal((String)propMap.get(mapItem)); //TODO change to actual type
				break;
			case "status":
				propBuilder.status((String)propMap.get(mapItem));
				break;
			case "constraints":
				List<Map<String,Object>> conList = (List<Map<String, Object>>) propMap.get(mapItem);
				for(Map<String,Object> constraint:conList){
					String key = constraint.keySet().iterator().next();
					propBuilder.addConstraint(new ConstraintTypeDef.Builder(key).value(constraint.get(key)).build());	
				}
				break;
			case "entry_schema":
				propBuilder.entry_schema(parseEntrySchema((Map<String, Object>)propMap.get(mapItem)));
				break;
			}
		}
		return propBuilder.build();
	}
	
	public AttributeDef parseAttrDef(String name, Map<String, Object> attrMap){
		String type = (String) attrMap.get("type");
		AttributeDef.Builder attrBuilder = new AttributeDef.Builder(name);
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
	
	public RequirementDef parseReqDef(String name, Map<String, Object> reqMap){
		RequirementDef.Builder reqBuilder = new RequirementDef.Builder(name);
		for(String mapItem:reqMap.keySet()){
			switch(mapItem){
			case "node":		
				reqBuilder.node((String)reqMap.get(mapItem));
				break;
			case "relationship":		
				reqBuilder.relationship((String)reqMap.get(mapItem));
				break;
			case "capability":			
				reqBuilder.capability((String)reqMap.get(mapItem));
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
		if (capMap == null) {
			type = name;
			return new CapabilityDef.Builder(name, type).build();
		}
		type = (String) capMap.get("type");		
		//capability is normative
		if (type == null) type = name; 

		CapabilityDef thisCap = (CapabilityDef) capDefinitions.get(type);
		if(thisCap !=null){
			//set the name here.
			CapabilityDef toReturn = (CapabilityDef) thisCap.clone(); //copy the node
			toReturn = toReturn.getBuilder(type).name(name).build();
			return toReturn;
		}else{		
		
		String parentDef = (String) capMap.get("derived_from");
		
		CapabilityDef.Builder capBuilder;
		CapabilityDef returnCapability;
		// if not null then copy the nodeDef
		//TODO find the def in the list if already loaded othewise load the def
		//Builder nDef;
		if (parentDef!=null){
			CapabilityDef parent = (CapabilityDef) capDefinitions.get(parentDef);
			if(parent !=null){
				returnCapability = (CapabilityDef) parent.clone(); //copy the parent and then get a builder to add new functionality
				capBuilder = returnCapability.getBuilder(type); 
			}else{
				//try to load the parent definition
				try{
					loadCapability(ToscaConstants.FILEPATH+parentDef+".yml");
				}catch(Exception e){
					System.out.println("The definition "+ToscaConstants.FILEPATH+parentDef+" does not exist. \n Will build incomplete def");
				}
				capBuilder = new CapabilityDef.Builder(type);
			}
		}else{
			capBuilder = new CapabilityDef.Builder(type); 
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
}
	
	
	public RelationshipDef parseRelDef(String typeName, Map<String, Object> relMap){

		RelationshipDef thisNode = (RelationshipDef) relDefinitions.get(typeName);
		if(thisNode !=null){
			return (RelationshipDef) thisNode.clone(); //copy the node
		}else{
		
		if (relMap == null) 
			return new RelationshipDef.Builder(typeName).build();
		
		String parentDef = (String) relMap.get("derived_from");
		RelationshipDef.Builder relBuilder;
		RelationshipDef returnRel;
		// if not null then copy the nodeDef
		//TODO find the def in the list if already loaded othewise load the def
		//Builder nDef;
		if (parentDef!=null){
			RelationshipDef parent = (RelationshipDef) relDefinitions.get(parentDef);
			if(parent !=null){
				returnRel = (RelationshipDef) parent.clone(); //copy the parent and then get a builder to add new functionality
				relBuilder = returnRel.getBuilder(typeName); 
			}else{
				//try to load the parent definition
				try{
					loadDefinition(ToscaConstants.FILEPATH+ServiceTemplateUtilities.normalizeTypeName(parentDef,"relationship")+".yml");
				}catch(Exception e){
					System.out.println("The definition "+ToscaConstants.FILEPATH+ServiceTemplateUtilities.normalizeTypeName(parentDef,"relationship")+" does not exist. \n Will build incomplete def");
				}
				relBuilder = new RelationshipDef.Builder(typeName);
			}
		}else{
			relBuilder = new RelationshipDef.Builder(typeName); 
		}	
			for(String mapItem:relMap.keySet()){
				switch(mapItem){
				case "description":
					relBuilder.description((String)relMap.get(mapItem));
					break;
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
	}
		
	public DataTypeDef parseDataTypeDef(String name, Map<String, Object> dataMap){
		DataTypeDef.Builder dataDefBuilder;
		DataTypeDef returnDef;
		
		dataDefBuilder = new DataTypeDef.Builder(name); 
		if (dataMap != null){
			for(String key: dataMap.keySet()){
				switch(key){
				case "properties":
					Map<String,Object> propDefMap = (Map<String,Object>)dataMap.get(key);
					for(String propName:propDefMap.keySet()){
						dataDefBuilder.addProperty(parsePropDef(propName,(Map<String, Object>)propDefMap.get(propName)));
					}
					break;
				case "constraints":
					List<Map<String,Object>> conList = (List<Map<String, Object>>) dataMap.get(key);
					for(Map<String,Object> constraint:conList){
						String operator = constraint.keySet().iterator().next();
						dataDefBuilder.addConstraint(new ConstraintTypeDef.Builder(operator).value(constraint.get(operator)).build());	
					}
					break;	
				default: 
					break;
				}
			}
		}
		return dataDefBuilder.build();
	}
	
	public PolicyDef parsePolicyTypeDef(String name, Map<String, Object> dataMap){
		PolicyDef.Builder policyDefBuilder;
		PolicyDef returnDef;
		policyDefBuilder = new PolicyDef.Builder(name); 
		if (dataMap != null){
			for(String key: dataMap.keySet()){
				switch(key){
				case "properties":
					Map<String,Object> propDefMap = (Map<String,Object>)dataMap.get(key);
					for(String propName:propDefMap.keySet()){
						policyDefBuilder.addProperty(parsePropDef(propName,(Map<String, Object>)propDefMap.get(propName)));
					}
					break;
				case "targets":
					List<String> tList = (List<String>) dataMap.get(key);
					for(String target:tList){
						policyDefBuilder.addTargets(target);	
					}
					break;	
				default: 
					break;
				}
			}
		}
		return policyDefBuilder.build();
	}
	
	public NodeDef getNodeDef(String typeName){
		NodeDef nodeDefinition = (NodeDef) nodeDefinitions.get(ServiceTemplateUtilities.normalizeTypeName(typeName,"node"));
		if(nodeDefinition == null){
			//try to load the definition
			try{
				loadDefinition(ServiceTemplateUtilities.normalizeTypeName(typeName,"node")+".yml");
				nodeDefinition = (NodeDef) nodeDefinitions.get(typeName);
			}catch(Exception e){
				System.out.println("The Node definition "+typeName+" does not exist.");
				return null;
			}
		}
		return nodeDefinition;
	}
	
	public RelationshipDef getRelationshipDef(String typeName){
		RelationshipDef relDefinition = (RelationshipDef) relDefinitions.get(ServiceTemplateUtilities.normalizeTypeName(typeName,"relationship"));
			if(relDefinition == null){
				//try to load the definition
				try{
					loadRelationship(ServiceTemplateUtilities.normalizeTypeName(typeName,"relationship")+".yml");
					relDefinition = (RelationshipDef) relDefinitions.get(typeName);
				}catch(Exception e){
					log.debug("The relationship definition "+typeName+" is not normative.");
					return null;
				}
			}
		return relDefinition;
	}
	
	
	public CapabilityDef getCapabilityDef(String typeName){
		CapabilityDef capDefinition = (CapabilityDef) capDefinitions.get(ServiceTemplateUtilities.normalizeTypeName(typeName,"capability"));
		if(capDefinition == null){
			//try to load the definition
			try{
				loadCapability(ServiceTemplateUtilities.normalizeTypeName(typeName,"capability")+".yml");
				capDefinition = (CapabilityDef) capDefinitions.get(typeName);
			}catch(Exception e){
				System.out.println("The capability definition "+typeName+" does not exist.");
				return null;
			}
		}
		return capDefinition;
	}
	
	public DataTypeDef getDataTypeDef(String typeName){
		DataTypeDef dataDefinition = (DataTypeDef) dataDefinitions.get(ServiceTemplateUtilities.normalizeTypeName(typeName,"data"));
		if(dataDefinition == null){
			//try to load the definition
			try{
				loadCapability(ServiceTemplateUtilities.normalizeTypeName(typeName,"data")+".yml");
				dataDefinition = (DataTypeDef) dataDefinitions.get(typeName); //FIXME maybe normalize
			}catch(Exception e){
				System.out.println("The data definition "+typeName+" does not exist.");
				return null;
			}
		}
		return dataDefinition;
	}
	
	public PolicyDef getPolicyDef(String typeName){
		PolicyDef policyDefinition = (PolicyDef) policyDefinitions.get(ServiceTemplateUtilities.normalizeTypeName(typeName,"policies"));
		if(policyDefinition == null){
			//try to load the definition
			try{
				loadPolicy(ServiceTemplateUtilities.normalizeTypeName(typeName,"policies")+".yml");
				policyDefinition = (PolicyDef) policyDefinitions.get(typeName); //FIXME maybe normalize
			}catch(Exception e){
				System.out.println("The policy definition "+typeName+" does not exist.");
				return null;
			}
		}
		return policyDefinition;
	}
	
	public Map<String, NodeDef> getNodeDefs(){
		return nodeDefinitions;
	}
	
	public Map<String, RelationshipDef> getRelationshipDefs(){
		return relDefinitions;
	}
	
	public Map<String, CapabilityDef> getCapabilityDefs(){
		return capDefinitions;
	}
	
	public Map<String, DataTypeDef> getDatatypeDefs(){
		return dataDefinitions;
	}

//	public ServiceTemplate createServiceTemplate(Map<String, Object> toscaMap){
//		ServiceTemplate st = ServiceTemplateUtilities.getServiceTemplate(toscaMap, this);
//		serviceTemplates.put(st.getId(), st);
//		return st;
//	}
//	
	public ServiceTemplate createServiceTemplate(File file){
		Yaml yaml = new Yaml();
        Map<String, Object> map;
		try {
			map = (Map<String,Object>) yaml.load(new FileInputStream(file));
			ServiceTemplate st = ServiceTemplateUtilities.parseServiceTemplate(map, this);
			serviceTemplates.put(st.getId(), st);
			return st;
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		return null;
		
	}
//	
//	
//	public List<ServiceTemplateUtilities> getServiceTemplates(){
//		return (List<ServiceTemplateUtilities>) serviceTemplates.values();
//	}
	
}
//Yaml yaml = new Yaml(new RangeConstructor(),new RangeRepresenter(), new DumperOptions());
//	    yaml.addImplicitResolver(new Tag("!range"), Pattern.compile("^\\[ * (\\d+) *, *(\\d+) *\\]$"), "[");
//		Map<String, Range> map = (Map<String,Range>) yaml.load(new FileInputStream(new File("C:/Users/Kena/Git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/Sample.yml")));
//		System.out.println(map.get("tosca_definitions_version"));		
