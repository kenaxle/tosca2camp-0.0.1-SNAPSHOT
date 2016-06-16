package kr.ac.hanyang.tosca2camp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import kr.ac.hanyang.tosca2camp.datatypes.Range;
import kr.ac.hanyang.tosca2camp.definitiontypes.AttributeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.CapabilityDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.ConstraintTypeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.DataTypeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.EntrySchemaDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PropertyDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RequirementDef;
import kr.ac.hanyang.tosca2camp.datatypes.RangeConstructor;
import kr.ac.hanyang.tosca2camp.datatypes.RangeRepresenter;


/**
 * Hello world!
 *
 */
public class App{
	
	private final String FILEPATH = "C:\\Users\\Kena\\git\\tosca2camp-0.0.1-SNAPSHOT\\src\\main\\java\\kr\\ac\\hanyang\\tosca2camp\\definitions\\";
	private final String TYPEPREFIX = "tosca.datatypes.";
	
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
	
	private String[] dTypeDefFileNames = {"tosca.datatypes.credential.yml","tosca.datatypes.boolean.yml","tosca.datatypes.float.yml",
			"tosca.datatypes.integer.yml","tosca.datatypes.list.yml","tosca.datatypes.map.yml",
			"tosca.datatypes.network.NetworkInfo.yml","tosca.datatypes.network.PortInfo.yml","tosca.datatypes.network.PortSpec.yml",
			"tosca.datatypes.network.PortDef.yml","tosca.datatypes.range.yml","tosca.datatypes.scalar-unit.frequency.yml",
			"tosca.datatypes.scalar-unit.size.yml","tosca.datatypes.scalar-unit.time.yml","tosca.datatypes.string.yml",
			"tosca.datatypes.timestamp.yml","tosca.datatypes.version.yml",};
	
	
	private Map<String, NodeDef> nodeDefinitions = new LinkedHashMap<String, NodeDef>();
	private Map<String, CapabilityDef> capDefinitions = new LinkedHashMap<String, CapabilityDef>(); 
	private Map<String, RelationshipDef> relDefinitions = new LinkedHashMap<String, RelationshipDef>();
	private Map<String, DataTypeDef> dataDefinitions = new LinkedHashMap<String, DataTypeDef>();
	
	//need a list for inputs
	private Map<String ,RelationshipDef> customRelDefinitions = new LinkedHashMap<String, RelationshipDef>();
	private Map<String, NodeDef> nodeTemplates = new LinkedHashMap<String, NodeDef>();
	private Map<String, RelationshipDef> relTemplates = new LinkedHashMap<String, RelationshipDef>();
	//need a list for outputs
	
	private String normalizeType(String shortType){
		switch(shortType){
		case "Root": return "tosca.nodes.Root"; 
		case "Compute": return "tosca.nodes.Compute";
		case "SoftwareComponent": return "tosca.nodes.SoftwareComponent"; 
		case "WebServer": return "tosca.nodes.WebServer";
		case "WebApplication": return "tosca.nodes.WebApplication"; 
		case "DBMS": return "tosca.nodes.DBMS";
		case "Database": return "tosca.nodes.Database"; 
		case "ObjectStorage": return "tosca.nodes.ObjectStorage"; 
		case "BlockStorage": return "tosca.nodes.BlockStorage";
		case "Container.Runtime": return "tosca.nodes.Container.Runtime"; 
		case "Container.Application": return "tosca.nodes.Container.Application";
		case "LoadBalancer": return "tosca.nodes.LoadBalancer";
		default: return shortType;//test if the type is not empty then its long type
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
					loadDefinition(FILEPATH+parentDef+".yml");
					//clone and get builder here also
				}catch(Exception e){
					System.out.println(e.getMessage());
					System.out.println("The definition "+FILEPATH+parentDef+".yml does not exist. \n Will build incomplete def");
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
		DataTypeDef dataDef = dataDefinitions.get(TYPEPREFIX+type); //TODO I may have to clone 
		if (dataDef == null){
			try{
				loadDataTypes(FILEPATH+TYPEPREFIX+type+".yml");
				dataDef = dataDefinitions.get(TYPEPREFIX+type);
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
				DataTypeDef dataDef = dataDefinitions.get(TYPEPREFIX+type); //TODO I may have to clone 
				if (dataDef == null){
					try{
						loadDataTypes(FILEPATH+TYPEPREFIX+type+".yml");
						dataDef = dataDefinitions.get(TYPEPREFIX+type);
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
				propBuilder.required((boolean)propMap.get(mapItem));
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
					loadCapability(FILEPATH+parentDef+".yml");
				}catch(Exception e){
					System.out.println("The definition "+FILEPATH+parentDef+" does not exist. \n Will build incomplete def");
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
					loadDefinition(FILEPATH+parentDef+".yml");
				}catch(Exception e){
					System.out.println("The definition "+FILEPATH+parentDef+" does not exist. \n Will build incomplete def");
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
		
	//-------------------------------------------------------------------------------
	
	//-------------------------- Template Parsers--------------------------------------
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void parseNodeTemplate(String name, Map<String, Object>nodeMap){
		String type = (String) nodeMap.get("type");
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
		NodeDef nodeDefinition = (NodeDef) nodeDefinitions.get(typeName);
		if(nodeDefinition == null){
			//maybe the definition was not loaded.
			//try to load the definition.
			try{
				loadDefinition(FILEPATH+typeName+".yml");
				nodeDefinition = (NodeDef) nodeDefinitions.get(typeName);
			}catch(Exception e){
				System.out.println("The definition "+FILEPATH+typeName+" does not exist. Unable to parse the node ");
				return;
			}
		}
		
		NodeDef myDefinition = (NodeDef) nodeDefinition.clone();
		//clone the definition because we need to add real values.
		for(String key: nodeMap.keySet()){
			switch(key){
			case "properties":
				Map<String,Object> propMap = (Map<String,Object>)nodeMap.get(key);
				if (propMap != null){
					for(String propertyName:propMap.keySet()){
						Object value = propMap.get(propertyName);
						myDefinition.setPropertyValue(propertyName, value);
					}
				}
				break;
			case "capabilities":
				Map<String,Object> capMap = (Map<String,Object>)nodeMap.get(key);
				if (capMap != null){
					for(String capName:capMap.keySet()){
						myDefinition.getCapability(capName).parseCapTemplate((Map<String, Object>)capMap.get(capName));
					}
				}
				break;
			case "requirements":
				List<Map<String,Object>> reqList = (List<Map<String,Object>>)nodeMap.get(key);
				if (reqList != null){
					for(Map<String, Object> reqMap:reqList){
						String reqName = reqMap.keySet().iterator().next();
						RequirementDef toParse = myDefinition.getRequirement(reqName);
						if (toParse == null){
							//using a custom requirement so simply add the requirement
							NodeDef.Builder myDefBuilder = myDefinition.getBuilder(typeName);
							myDefBuilder.addRequirement(parseReqDef(reqName,(Map<String, Object>)reqMap.get(reqName))).build(); 
						}
						Object relItem = ((Map<String, Object>)reqMap.get(reqName)).get("relationship");
						myDefinition.getRequirement(reqName).parseRelationshipDef(relItem);
					}
				}
				break;			
			default:
				break;
			}
		}		
		nodeTemplates.put(name, myDefinition);
	}
	
	
	public void parseRelTemplate(String name, Map<String,Object>relMap){
		String type = (String) relMap.get("type");
		String typeName = "";
		
		switch(type){
		case "Root": typeName = "tosca.relationships.Root"; break;
		case "HostedOn": typeName = "tosca.relationships.HostedOn"; break;
		case "DependsOn": typeName = "tosca.relationships.DependsOn"; break;
		case "ConnectsTo": typeName = "tosca.relationships.ConnectsTo"; break;
		case "AttachesTo": typeName = "tosca.relationships.AttachesTo"; break;
		case "RoutesTo": typeName = "tosca.relationships.RoutesTo"; break;
		default: if(!type.isEmpty())
					typeName = type;//test if the type is not empty then its long type
		break; //use the empty string
		}
		RelationshipDef relDefinition = (RelationshipDef) customRelDefinitions.get(typeName);
		if(relDefinition == null){
			relDefinition = (RelationshipDef) relDefinitions.get(typeName);
			if(relDefinition == null){
				//try to load the definition
				try{
					loadRelationship(FILEPATH+typeName+".yml");
					relDefinition = (RelationshipDef) relDefinitions.get(typeName);
				}catch(Exception e){
					System.out.println("The relationship definition "+typeName+" does not exist.");
					//this is a custom relationship and need to parse it
					//relDefinition = p
					return;
				}
			}
		}
		RelationshipDef myRelDefinition = (RelationshipDef) relDefinition.clone();
		myRelDefinition.parseRelationshipTemplate(relMap);
		myRelDefinition = myRelDefinition.getBuilder(typeName).name(name).build();
		relTemplates.put(name, myRelDefinition);
		
	}
	
	
	public void parseServiceTemplate(Map<String, Object> toscaMap){
		for (String key:toscaMap.keySet()){
			switch (key){
			case "tosca_definitions_version":
					
				break;
			case "description":
				
				break;
			case "relationship_types":
				//load custom relationship types
				Map<String, Object> relTypesMap = (Map<String,Object>) toscaMap.get(key);
				for (String relTypeName:relTypesMap.keySet()){
					customRelDefinitions.put(relTypeName,parseRelDef(relTypeName,(Map<String,Object>) relTypesMap.get(relTypeName)));
				}
				break;
			case "topology_template":
				Map<String, Object> topologyTemplateMap = (Map<String,Object>) toscaMap.get(key);
				for (String topologyItem:topologyTemplateMap.keySet()){
					switch (topologyItem){
					case "inputs":break;
					case "node_templates":
						Map<String, Object> nodeTemplateMap = (Map<String, Object>) topologyTemplateMap.get(topologyItem);
						for(String nodeTemplate:nodeTemplateMap.keySet()){
							parseNodeTemplate(nodeTemplate,(Map<String,Object>)nodeTemplateMap.get(nodeTemplate));
						}
						break;
					case "relationship_templates":
						Map<String, Object> relTemplateMap = (Map<String, Object>) topologyTemplateMap.get(topologyItem);
						for(String relTemplate:relTemplateMap.keySet()){
							parseRelTemplate(relTemplate,(Map<String,Object>)relTemplateMap.get(relTemplate));
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
	
	//==================================================================================
	@SuppressWarnings("unchecked")
	public static void main( String[] args ) throws Exception{
		
//		// load the Normative definitions
//		//-----------------------------------------------
		App app = new App();
		// load the datatypes
		for(String fileName: app.dTypeDefFileNames){
			app.loadDataTypes(app.FILEPATH+fileName);
		}
		//load the capability types
		for(String fileName: app.capDefFileNames){
			app.loadCapability(app.FILEPATH+fileName);
		}
		//load the relationship types
		for(String fileName: app.relDefFileNames){
			app.loadRelationship(app.FILEPATH+fileName);
		}
		//load the nodetypes
		for(String fileName: app.nodeDefFileNames){
			app.loadDefinition(app.FILEPATH+fileName);
		}
		

		//-----------------------------------------------
		
		// Parse the Yaml plan
		//-----------------------------------------------
	    Yaml yaml = new Yaml();
		Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File("C:/Users/Kena/Git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/Sample2.yml")));
		
		app.parseServiceTemplate(map);
	
		for(String defName: app.nodeTemplates.keySet()){
			System.out.println(app.nodeTemplates.get(defName));
		}
		for(String defName: app.relTemplates.keySet()){
			System.out.println(app.relTemplates.get(defName));
		}

	}
}

//Yaml yaml = new Yaml(new RangeConstructor(),new RangeRepresenter(), new DumperOptions());
//	    yaml.addImplicitResolver(new Tag("!range"), Pattern.compile("^\\[ * (\\d+) *, *(\\d+) *\\]$"), "[");
//		Map<String, Range> map = (Map<String,Range>) yaml.load(new FileInputStream(new File("C:/Users/Kena/Git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/Sample.yml")));
//		System.out.println(map.get("tosca_definitions_version"));		
