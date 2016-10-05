package kr.ac.hanyang.tosca2camp;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.yaml.snakeyaml.Yaml;

import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PolicyDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PolicyDef.Builder;
import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RequirementDef;

public class ServiceTemplateUtilities {

	
		//need a list for inputs
		private String id;
		private Map<String, RelationshipDef> customRelDefinitions;
		private Map<String, PolicyDef> customPolicies;
		private Map<String, NodeDef> nodeTemplates;
		private Map<String, RelationshipDef> relTemplates;
		private Map<String, PolicyDef> policies;
		private Tosca2CampPlatform platform;
		//need a list for outputs
		
		
		public ServiceTemplateUtilities(Tosca2CampPlatform platform) {
			id = UUID.randomUUID().toString();
			customRelDefinitions = new LinkedHashMap<String, RelationshipDef>();
			customPolicies = new LinkedHashMap<String, PolicyDef>();
			nodeTemplates = new LinkedHashMap<String, NodeDef>();
			relTemplates = new LinkedHashMap<String, RelationshipDef>();
			policies = new LinkedHashMap<String, PolicyDef>();
			this.platform = platform;
		}
		
		
//		public static ServiceTemplate getServiceTemplate(Map<String, Object> map, Tosca2CampPlatform platform){
//			ServiceTemplate template = new ServiceTemplateUtilities(platform);
//			template.parseServiceTemplate(map);
//			return template;
//		}
//		
//		public static ServiceTemplateUtilities getServiceTemplate(Map<String, Object> map, Tosca2CampPlatform platform){
//			ServiceTemplateUtilities template = new ServiceTemplateUtilities(platform);
//			template.parseServiceTemplate(map);
//			return template;
//		}
		
		
		@SuppressWarnings("unchecked")
		public static ServiceTemplateUtilities getServiceTemplate(File file, Tosca2CampPlatform platform){
			try{
				Yaml yaml = new Yaml();
				Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(file));
				ServiceTemplateUtilities template = new ServiceTemplateUtilities(platform);
				template.parseServiceTemplate(map);
				return template;
			}catch(Exception e){
				System.out.println(e);
				return null;
			}
		}
		
		public String getId(){ return id;}
		
		
		//-------------------------- Template Parsers--------------------------------------
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public NodeDef parseNodeTemplate(String name, Map<String, Object>nodeMap, NodeDef nodeDef){
			
			String type = (String) nodeMap.get("type");
			
			NodeDef nodeDefinition = nodeDef;
			if (nodeDefinition == null){
				nodeDefinition = platform.getNodeDef(type);
			}
			if (nodeDefinition != null){
				for(String key: nodeMap.keySet()){
					switch(key){
					case "properties":
						Map<String,Object> propMap = (Map<String,Object>)nodeMap.get(key);
						if (propMap != null){
							for(String propertyName:propMap.keySet()){
								Object value = propMap.get(propertyName);
								nodeDefinition.setPropertyValue(propertyName, value);
							}
						}
						break;
					case "capabilities":
						Map<String,Object> capMap = (Map<String,Object>)nodeMap.get(key);
						if (capMap != null){
							for(String capName:capMap.keySet()){
								nodeDefinition.getCapability(capName).parseCapTemplate((Map<String, Object>)capMap.get(capName));
							}
						}
						break;
					default:
						break;
					}
				}
				nodeDefinition = nodeDefinition.getBuilder(type).name(name).build();
				nodeTemplates.put(name, nodeDefinition);
			}else
				System.out.println("No definition exists for the nodetype "+type+". Unable to parse the template.");	
			
			return nodeDefinition;
		}
		
		
		public RelationshipDef parseRelTemplate(String name, Map<String,Object>relMap, RelationshipDef relDef){
			String type = (String) relMap.get("type");
			RelationshipDef relDefinition = relDef;
			if(relDefinition == null){
				relDefinition = platform.getRelationshipDef(type);
			}
			if(relDefinition != null){
				relDefinition.parseRelationshipTemplate(relMap);
				relDefinition = relDefinition.getBuilder(type).name(name).build();
				return relDefinition;
			}else{
				System.out.println("No definition exists for the RelationshipType "+type+". Unable to parse the template.");
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		public static void parseRequirements(NodeDef nodeDefinition, List<Map<String, Object>> reqList){
			
			if (reqList != null){
				for(Map<String, Object> reqMap:reqList){
					String reqName = reqMap.keySet().iterator().next();
					Map<String, Object> innerMap = (Map<String, Object>) reqMap.get(reqMap.keySet().iterator().next());
					// get the requirement definition from the node definition. if it does not exist then we have a 
					// custom requirement. most likely using a user defined relationship.
					RequirementDef toEdit = nodeDefinition.getRequirement(reqName);
					if (toEdit == null){
						//using a custom requirement so simply add the requirement and link
						toEdit = new RequirementDef.Builder(reqName)
									 .node(nodeTemplates.get(innerMap.get("node")))
									 .relationship(relTemplates.get(innerMap.get("relationship")))
									 .build();
					}else{						
						toEdit = toEdit.getBuilder().node(nodeTemplates.get(innerMap.get("node")))
										            .relationship(relTemplates.get(innerMap.get("relationship"))/*parseRelTemplate("",(Map<String,Object>) innerMap.get("relationship"))*/)
										            .build();
					}
					nodeDefinition = nodeDefinition.getBuilder(nodeDefinition.getTypeName()).addRequirement(toEdit).build();
				}
			}		
		}
		
		public static PolicyDef parsePolicyTemplate(String name, Map<String, Object>policyMap, PolicyDef polDef){
			String type = (String) policyMap.get("type");
	
			PolicyDef policyDefinition = polDef;
			if(policyDefinition == null){
				policyDefinition = platform.getPolicyDef(type);
			}
			if(policyDefinition != null){
				for(String key: policyMap.keySet()){
					switch(key){
					case "properties":
						Map<String,Object> propMap = (Map<String,Object>)policyMap.get(key);
						if (propMap != null){
							for(String propertyName:propMap.keySet()){
								Object value = propMap.get(propertyName);
								policyDefinition.setPropertyValue(propertyName, value);
							}
						}
						break;
					case "targets":
						List<String> targetMap = (ArrayList<String>)policyMap.get(key);
						if (targetMap != null){
							Builder policyBuilder = policyDefinition.getBuilder(type);
							for(String target:targetMap){
								policyBuilder.addTargets(target);
								//policyDefinition..getCapability(capName).parseCapTemplate((Map<String, Object>)capMap.get(capName));
							}
							policyBuilder.build();
						}
						break;
					default:
						break;
					}
				}
				policyDefinition = policyDefinition.getBuilder(type).name(name).build();
				policies.put(name, policyDefinition);
			}else
				System.out.println("No definition exists for the nodetype "+type+". Unable to parse the template.");	
			return policyDefinition;
		}
		

		
		
		@SuppressWarnings("unchecked")
		public static ServiceTemplate parseServiceTemplate(Map<String, Object> toscaMap){
			kr.ac.hanyang.tosca2camp.ServiceTemplate.Builder serviceTemplateBuilder = new ServiceTemplate.Builder();
			
			for (String key:toscaMap.keySet()){
				switch (key){
				case "tosca_definitions_version":
						
					break;
				case "description":
					
					break;
				
				case "imports":
					//TODO add detail here
					break;
					
				case "data_types":
					//TODO add detail here
					break;	
				
				case "capability_types":
					//TODO add detail here
					break;	
				
				case "interface_types":
					//TODO add detail here
					break;	
					
				case "relationship_types":
					//load custom relationship types
					Map<String, Object> relTypesMap = (Map<String,Object>) toscaMap.get(key);
					for (String relTypeName:relTypesMap.keySet()){
						RelationshipDef relDefinition = parseRelDef(relTypeName,(Map<String,Object>) relTypesMap.get(relTypeName));
						if (relDefinition != null){
							serviceTemplateBuilder.addRelationshipType(relDefinition);
						}
					}
					break;
				
				case "node_types":
					//TODO add detail here
					break;
					
				case "group_types":
					//TODO add detail here
					break;
					
				case "policy_types":
					//load custom policy types
					Map<String, Object> polTypesMap = (Map<String,Object>) toscaMap.get(key);
					for (String polTypeName:polTypesMap.keySet()){
						PolicyDef polDefinition = parsePolicyTypeDef(polTypeName,(Map<String,Object>) polTypesMap.get(polTypeName));
						if (polDefinition != null){
							serviceTemplateBuilder.addPolicyType(polDefinition);
						}
					}
					break;
					
				case "topology_template":
					kr.ac.hanyang.tosca2camp.TopologyTemplate.Builder topologyBuilder = new TopologyTemplate.Builder("topologyTemplate");
					Map<String, Object> topologyTemplateMap = (Map<String,Object>) toscaMap.get(key);
					for (String topologyItem:topologyTemplateMap.keySet()){
						switch (topologyItem){
						case "inputs":break;
						case "node_templates":
							Map<String, Object> nodeTemplateMap = (Map<String, Object>) topologyTemplateMap.get(topologyItem);
							for(String nodeTemplate:nodeTemplateMap.keySet()){
								topologyBuilder.addNode(parseNodeTemplate(nodeTemplate,(Map<String,Object>)nodeTemplateMap.get(nodeTemplate), ));
							}
							break;
						case "relationship_templates":
							Map<String, Object> relTemplateMap = (Map<String, Object>) topologyTemplateMap.get(topologyItem);
							for(String relTemplate:relTemplateMap.keySet()){
								topologyBuilder.addRelationship(relTemplates.put(relTemplate, parseRelTemplate(relTemplate,(Map<String,Object>)relTemplateMap.get(relTemplate), )));
							}
							break;
						case "groups":
							break;
						case "policies":
							Map<String, Object> policyTemplateMap = (Map<String, Object>) topologyTemplateMap.get(topologyItem);
							for(String policyTemplate:policyTemplateMap.keySet()){
								parsePolicyTemplate(policyTemplate,(Map<String,Object>)policyTemplateMap.get(policyTemplate), );
							}
							break;
						case "outputs":	break;
						default:
							break;
						}
					}
					// parse Requirements here. and link the service template
					Map<String, Object> nodeTemplateMap = (Map<String, Object>) topologyTemplateMap.get("node_templates");
					for(String nodeTemplate:nodeTemplateMap.keySet()){
						NodeDef node = nodeTemplates.get(nodeTemplate);
						List<Map<String,Object>> reqList = (List<Map<String,Object>>)((Map<String, Object>)nodeTemplateMap.get(nodeTemplate)).get("requirements");
						parseRequirements(node, reqList);
					}
					serviceTemplateBuilder.addTopologyTemplate(topologyBuilder.build());
					break;
				default:
					break;
			
				}
			}
		}
		
		//-------------------------------------------------------------------------------------------
		
		public static PolicyDef parsePolicyTypeDef(String name, Map<String, Object> dataMap){
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
		
		public static RelationshipDef parseRelDef(String typeName, Map<String, Object> relMap, RelationshipDef relDef){

			RelationshipDef thisNode = relDef;//(RelationshipDef) relDefinitions.get(typeName);
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
						loadDefinition(ToscaConstants.FILEPATH+normalizeTypeName(parentDef,"relationship")+".yml");
					}catch(Exception e){
						System.out.println("The definition "+ToscaConstants.FILEPATH+normalizeTypeName(parentDef,"relationship")+" does not exist. \n Will build incomplete def");
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
		
		
//		// just for testing
//		public String toString(){
//			String toReturn = "id: "+id+" \n";
//			toReturn += "---------------------Relationship Types----------------------------\n";
//			for(String defName: customRelDefinitions.keySet()){
//				toReturn += customRelDefinitions.get(defName);
//			}
//			toReturn += "-----------------------Node Templates------------------------------\n";
//			for(String defName: nodeTemplates.keySet()){
//				toReturn += nodeTemplates.get(defName);
//			}
//			toReturn += "-------------------Relationship Templates--------------------------\n";
//			for(String defName: relTemplates.keySet()){
//				toReturn += relTemplates.get(defName);
//			}
//			return toReturn;
//		}
}
