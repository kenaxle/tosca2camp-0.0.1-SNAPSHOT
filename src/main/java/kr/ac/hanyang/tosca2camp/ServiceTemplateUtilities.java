package kr.ac.hanyang.tosca2camp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.ac.hanyang.tosca2camp.definitiontypes.CapabilityDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PolicyDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PolicyDef.Builder;
import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef;


public class ServiceTemplateUtilities {
	
	private static final Logger log = LoggerFactory.getLogger(ServiceTemplateUtilities.class);
	
	public static String normalizeTypeName(String shortTypeName, String type){
		switch(type){
		case "node":
			switch(shortTypeName){
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
			default: return shortTypeName;//test if the type is not empty then its long type
			}
		case "capability":
			switch(shortTypeName){
			case "Attachment": return "tosca.capabilities.Attachment"; 
			case "Bindable": return "tosca.capabilities.Bindable";
			case "Container": return "tosca.capabilities.Container"; 
			case "Endpoint.Admin": return "tosca.capabilities.Endpoint.Admin";
			case "Endpoint.Database": return "tosca.capabilities.Endpoint.Database"; 
			case "Endpoint.Public": return "tosca.capabilities.Endpoint.Public";
			case "Endpoint": return "tosca.capabilities.Endpoint"; 
			case "Node": return "tosca.capabilities.Node"; 
			case "OperatingSystem": return "tosca.capabilities.OperatingSystem";
			case "Root": return "tosca.capabilities.Root"; 
			case "Scalable": return "tosca.capabilities.Scalable";
			default: return shortTypeName;//test if the type is not empty then its long type
			}
		case "relationship":
			switch(shortTypeName){
			case "Root": return "tosca.relationships.Root"; 
			case "HostedOn": return "tosca.relationships.HostedOn"; 
			case "DependsOn": return "tosca.relationships.DependsOn"; 
			case "ConnectsTo": return "tosca.relationships.ConnectsTo"; 
			case "AttachesTo": return "tosca.relationships.AttachesTo"; 
			case "RoutesTo": return "tosca.relationships.RoutesTo"; 
			default: return shortTypeName;//test if the type is not empty then its long type
			}
		case "datatype":
			switch(shortTypeName){
			case "boolean": return "tosca.datatypes.boolean"; 
			case "credential": return "tosca.datatypes.credential";
			case "float": return "tosca.datatypes.float"; 
			case "integer": return "tosca.datatypes.integer"; 
			case "list": return "tosca.datatypes.list"; 
			case "map": return "tosca.datatypes.map"; 
			case "network.NetworkInfo": return "tosca.datatypes.network.NetworkInfo"; 
			case "network.PortDef": return "tosca.datatypes.network.PortDef"; 
			case "network.PortSpec": return "tosca.datatypes.network.PortSpec"; 
			case "range": return "tosca.datatypes.range";  
			case "scalar-unit.frequency": return "tosca.datatypes.scalar-unit.frequency"; 
			case "scalar-unit.size": return "tosca.datatypes.scalar-unit.size"; 
			case "scalar-unit.time": return "tosca.datatypes.scalar-unit.time"; 
			case "scalar-unit": return "tosca.datatypes.scalar-unit"; 
			case "string": return "tosca.datatypes.string"; 
			case "timestamp": return "tosca.datatypes.timestamp"; 
			case "version": return "tosca.datatypes.version"; 
			default: return shortTypeName;//test if the type is not empty then its long type
			}
		case "policy":
			switch(shortTypeName){
			case "root": return "tosca.policies.root"; 
			case "placement": return "tosca.policies.placement";
			default: return shortTypeName;//test if the type is not empty then its long type
			}
		default: return shortTypeName;
		}
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
		
		
//		@SuppressWarnings("unchecked")
//		public static ServiceTemplateUtilities getServiceTemplate(File file, Tosca2CampPlatform platform){
//			try{
//				Yaml yaml = new Yaml();
//				Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(file));
//				ServiceTemplateUtilities template = new ServiceTemplateUtilities(platform);
//				template.parseServiceTemplate(map);
//				return template;
//			}catch(Exception e){
//				System.out.println(e);
//				return null;
//			}
//		}
		
		//public String getId(){ return id;}
		
		
		//-------------------------- Template Parsers--------------------------------------
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static NodeDef parseNodeTemplate(String name, Map<String, Object>nodeMap, NodeDef nodeDefinition){
			
			String type = (String) nodeMap.get("type");
			

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
								//nodeDefinition.
								nodeDefinition.getCapability(capName).parseCapTemplate((Map<String, Object>)capMap.get(capName));
							}
						}
						break;
					default:
						break;
					}
				}
				return nodeDefinition.getBuilder(type).name(name).build();
				//nodeTemplates.put(name, nodeDefinition);
//			}else
//				System.out.println("No definition exists for the nodetype "+type+". Unable to parse the template.");	
			
			//return nodeDefinition;
		}
		
		
		public static RelationshipDef parseRelTemplate(String name, Map<String,Object>relMap, RelationshipDef relDefinition){
			String type = (String) relMap.get("type");
//			RelationshipDef relDefinition = relDef;
//			if(relDefinition == null){
//				relDefinition = platform.getRelationshipDef(type);
//			}
			//if(relDefinition != null){
				relDefinition.parseRelationshipTemplate(relMap);
				relDefinition = relDefinition.getBuilder(type).name(name).build();
				return relDefinition;
//			}else{
//				System.out.println("No definition exists for the RelationshipType "+type+". Unable to parse the template.");
//				return null;
//			}
		}
		
		public CapabilityDef parseCapTemplate(Map<String, Object>capMap, CapabilityDef capDef){

			Map<String,Object> propMap = ((Map<String,Object>) capMap.get("properties"));
			for(String propertyName:propMap.keySet()){
				capDef.getProperty(propertyName).setPropertyValue(propMap.get(propertyName));
			}
			return capDef;
		}
		
		
//		@SuppressWarnings("unchecked")
//		public static void parseRequirements(NodeDef nodeDefinition, List<Map<String, Object>> reqList){
//			
//			if (reqList != null){
//				for(Map<String, Object> reqMap:reqList){
//					String reqName = reqMap.keySet().iterator().next();
//					Map<String, Object> innerMap = (Map<String, Object>) reqMap.get(reqMap.keySet().iterator().next());
//					// get the requirement definition from the node definition. if it does not exist then we have a 
//					// custom requirement. most likely using a user defined relationship.
//					RequirementDef toEdit = nodeDefinition.getRequirement(reqName);
//					if (toEdit == null){
//						//using a custom requirement so simply add the requirement and link
//						toEdit = new RequirementDef.Builder(reqName)
//									 .node(nodeTemplates.get(innerMap.get("node")))
//									 .relationship(relTemplates.get(innerMap.get("relationship")))
//									 .build();
//					}else{						
//						toEdit = toEdit.getBuilder().node(nodeTemplates.get(innerMap.get("node")))
//										            .relationship(relTemplates.get(innerMap.get("relationship"))/*parseRelTemplate("",(Map<String,Object>) innerMap.get("relationship"))*/)
//										            .build();
//					}
//					nodeDefinition = nodeDefinition.getBuilder(nodeDefinition.getTypeName()).addRequirement(toEdit).build();
//				}
//			}		
//		}
		
		public static PolicyDef parsePolicyTemplate(String name, Map<String, Object>policyMap, PolicyDef policyDefinition){
			String type = (String) policyMap.get("type");
	
//			PolicyDef policyDefinition = polDef;
//			if(policyDefinition == null){
//				policyDefinition = platform.getPolicyDef(type);
//			}
			//if(policyDefinition != null){
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
				return policyDefinition.getBuilder(type).name(name).build();
				//policies.put(name, policyDefinition);
//			}else
//				System.out.println("No definition exists for the nodetype "+type+". Unable to parse the template.");	
//			return policyDefinition;
		}
		

		
		
		@SuppressWarnings("unchecked")
		public static ServiceTemplate parseServiceTemplate(Map<String, Object> toscaMap, Tosca2CampPlatform platform){
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
						RelationshipDef relDefinition = platform.parseRelDef(relTypeName,(Map<String,Object>) relTypesMap.get(relTypeName));
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
						PolicyDef polDefinition = platform.parsePolicyTypeDef(polTypeName,(Map<String,Object>) polTypesMap.get(polTypeName));
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
								String nodeType = (String)((Map<String, Object>) nodeTemplateMap.get(nodeTemplate)).get("type");
								NodeDef nodeDefinition = platform.getNodeDef(nodeType);
								if (nodeDefinition == null)
									nodeDefinition = serviceTemplateBuilder.peek().getNodeType(nodeType);
								if (nodeDefinition != null)
									topologyBuilder.addNode(parseNodeTemplate(nodeTemplate,(Map<String,Object>)nodeTemplateMap.get(nodeTemplate), nodeDefinition));
								else
									System.out.println("ERROR");
							}
							break;
						case "relationship_templates":
							Map<String, Object> relTemplateMap = (Map<String, Object>) topologyTemplateMap.get(topologyItem);
							for(String relTemplate:relTemplateMap.keySet()){
								String relType = (String)((Map<String, Object>) relTemplateMap.get(relTemplate)).get("type");
								RelationshipDef relDefinition = platform.getRelationshipDef(relType);
								if (relDefinition == null)
									relDefinition = serviceTemplateBuilder.peek().getRelationshipType(relType);
								if (relDefinition != null)
									topologyBuilder.addRelationship(parseRelTemplate(relTemplate,(Map<String,Object>)relTemplateMap.get(relTemplate), relDefinition));
								else
									log.error("Unable to load the type definition "+ relType);
							}
							break;
						case "groups":
							break;
						case "policies":
							Map<String, Object> policyTemplateMap = (Map<String, Object>) topologyTemplateMap.get(topologyItem);
							for(String policyTemplate:policyTemplateMap.keySet()){
								String polType = (String)((Map<String, Object>) policyTemplateMap.get(policyTemplate)).get("type");
								PolicyDef polDefinition = serviceTemplateBuilder.peek().getPolicyType(polType);
								if (polDefinition != null)
								parsePolicyTemplate(policyTemplate,(Map<String,Object>)policyTemplateMap.get(policyTemplate), polDefinition);
							}
							break;
						case "outputs":	break;
						default:
							break;
						}
					}
					// parse Requirements here. and link the service template
//					Map<String, Object> nodeTemplateMap = (Map<String, Object>) topologyTemplateMap.get("node_templates");
//					for(String nodeTemplate:nodeTemplateMap.keySet()){
//						NodeDef node = nodeTemplates.get(nodeTemplate);
//						List<Map<String,Object>> reqList = (List<Map<String,Object>>)((Map<String, Object>)nodeTemplateMap.get(nodeTemplate)).get("requirements");
//						parseRequirements(node, reqList);
//					}
					serviceTemplateBuilder.addTopologyTemplate(topologyBuilder.build());
					break;
				default:
					break;
			
				}
			}
			return serviceTemplateBuilder.build();
		}
		
//		//-------------------------------------------------------------------------------------------
//		
//		public static PolicyDef parsePolicyTypeDef(String name, Map<String, Object> dataMap, Tosca2CampPlatform platform){
//			PolicyDef.Builder policyDefBuilder;
//			PolicyDef returnDef;
//			policyDefBuilder = new PolicyDef.Builder(name); 
//			if (dataMap != null){
//				for(String key: dataMap.keySet()){
//					switch(key){
//					case "properties":
//						Map<String,Object> propDefMap = (Map<String,Object>)dataMap.get(key);
//						for(String propName:propDefMap.keySet()){
//							policyDefBuilder.addProperty(platform.parsePropDef(propName,(Map<String, Object>)propDefMap.get(propName)));
//						}
//						break;
//					case "targets":
//						List<String> tList = (List<String>) dataMap.get(key);
//						for(String target:tList){
//							policyDefBuilder.addTargets(target);	
//						}
//						break;	
//					default: 
//						break;
//					}
//				}
//			}
//			return policyDefBuilder.build();
//		}
//		
//		public static RelationshipDef parseRelTypeDef(String typeName, Map<String, Object> relMap, Tosca2CampPlatform platform) throws Exception{
//			String parentType = (String) relMap.get("derived_from");
//			RelationshipDef.Builder relBuilder;
//			RelationshipDef returnRel;
//
//			if (parentType!=null){
//				RelationshipDef parent = platform.getRelationshipDef(parentType);
//				if(parent !=null){
//					returnRel = (RelationshipDef) parent.clone(); //copy the parent and then get a builder to add new functionality
//					relBuilder = returnRel.getBuilder(typeName); 
//				}else{
//					throw new Exception("unable to find parent type "+parentType);
//				}
//			}else{
//				relBuilder = new RelationshipDef.Builder(typeName);
//			}	
//			for(String mapItem:relMap.keySet()){
//				switch(mapItem){
//				case "description":
//					relBuilder.description((String)relMap.get(mapItem));
//					break;
//				case "properties":
//					Map<String, Object> propMap = (Map<String, Object>) relMap.get(mapItem);
//					for(String propName:propMap.keySet()){
//						relBuilder.addProperty(platform.parsePropDef(propName,(Map<String, Object>)propMap.get(propName)));	
//					}
//					break;
//				case "attributes":
//					Map<String, Object> attrMap = (Map<String, Object>) relMap.get(mapItem);
//					for(String attrName:attrMap.keySet()){
//						relBuilder.addAttribute(platform.parseAttrDef(attrName,(Map<String, Object>)attrMap.get(attrName)));	
//					}
//					break;
//				case "valid_target_types":
//					List<String> sourcesList = (List<String>) relMap.get(mapItem);
//					for(String attrName:sourcesList){
//						relBuilder.addValid_target_types(attrName);	
//					}
//					break;
//				case "interfaces":
//				    // handle this case here
//					break;
//				}
//			}
//			return relBuilder.build();
//			
//		}
//		
		
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
