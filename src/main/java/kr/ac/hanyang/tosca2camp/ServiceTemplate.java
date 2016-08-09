package kr.ac.hanyang.tosca2camp;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PolicyDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.PolicyDef.Builder;
import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RequirementDef;

public class ServiceTemplate {

	
		//need a list for inputs
		private Map<String, RelationshipDef> customRelDefinitions;
		private Map<String, PolicyDef> customPolicies;
		private Map<String, NodeDef> nodeTemplates;
		private Map<String, RelationshipDef> relTemplates;
		private AppContext appContext;
		//need a list for outputs
		
		
		public ServiceTemplate(AppContext appContext) {
			customRelDefinitions = new LinkedHashMap<String, RelationshipDef>();
			customPolicies = new LinkedHashMap<String, PolicyDef>();
			nodeTemplates = new LinkedHashMap<String, NodeDef>();
			relTemplates = new LinkedHashMap<String, RelationshipDef>();
			this.appContext = appContext;
		}
		
		
		public static ServiceTemplate getServiceTemplate(Map<String, Object> map, AppContext appContext){
			ServiceTemplate template = new ServiceTemplate(appContext);
			template.parseServiceTemplate(map);
			return template;
		}
		
		
		@SuppressWarnings("unchecked")
		public static ServiceTemplate getServiceTemplate(File file, AppContext appContext){
			try{
				Yaml yaml = new Yaml();
				Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(file));
				ServiceTemplate template = new ServiceTemplate(appContext);
				template.parseServiceTemplate(map);
				return template;
			}catch(Exception e){
				System.out.println(e);
				return null;
			}
		}
		
//		private NodeDef getNodeDef(String typeName){
//			NodeDef nodeDefinition = nodeTemplates.get(typeName);
//			
//		}
//		
		
		
		
		//-------------------------- Template Parsers--------------------------------------
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void parseNodeTemplate(String name, Map<String, Object>nodeMap){
			String type = (String) nodeMap.get("type");
			
			//try to get the definition or try to load it if its normative		
			
			NodeDef nodeDefinition = nodeTemplates.get(type);
			if (nodeDefinition == null){
				nodeDefinition = appContext.getNodeDef(type);
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
		}
		
		
		public RelationshipDef parseRelTemplate(String name, Map<String,Object>relMap){
			String type = (String) relMap.get("type");
			RelationshipDef relDefinition = customRelDefinitions.get(type);
			if(relDefinition == null){
				relDefinition = appContext.getRelationshipDef(type);
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
		public void parseRequirements(NodeDef nodeDefinition, List<Map<String, Object>> reqList){
			//List<Map<String,Object>> reqList = (List<Map<String,Object>>)nodeMap.get(key);
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
										            .relationship(parseRelTemplate("",(Map<String, Object>) innerMap.get("relationship")))
										            .build();
					}
					nodeDefinition = nodeDefinition.getBuilder(nodeDefinition.getTypeName()).addRequirement(toEdit).build();
				}
			}		
		}
		
		public void parsePolicyTemplate(String name, Map<String, Object>policyMap){
			String type = (String) policyMap.get("type");
			
			//try to get the definition or try to load it if its normative		
			PolicyDef policyDefinition = customPolicies.get(type);
			if(policyDefinition == null){
				policyDefinition = appContext.getPolicyDef(type);
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
				//policyTemplates.put(name, nodeDefinition);
			}else
				System.out.println("No definition exists for the nodetype "+type+". Unable to parse the template.");	
		}
		
		
		@SuppressWarnings("unchecked")
		public void parseServiceTemplate(Map<String, Object> toscaMap){
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
						RelationshipDef relDefinition = appContext.parseRelDef(relTypeName,(Map<String,Object>) relTypesMap.get(relTypeName));
						if (relDefinition != null){
							//relDefinition = relDefinition.getBuilder(relTypeName).name(relTypeName).build();
							customRelDefinitions.put(relTypeName,relDefinition);
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
						PolicyDef polDefinition = appContext.parsePolicyTypeDef(polTypeName,(Map<String,Object>) polTypesMap.get(polTypeName));
						if (polDefinition != null){
							//relDefinition = relDefinition.getBuilder(relTypeName).name(relTypeName).build();
							customPolicies.put(polTypeName,polDefinition);
						}
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
								relTemplates.put(relTemplate, parseRelTemplate(relTemplate,(Map<String,Object>)relTemplateMap.get(relTemplate)));
							}
							break;
						case "groups":
							break;
						case "policies":
							Map<String, Object> policyTemplateMap = (Map<String, Object>) topologyTemplateMap.get(topologyItem);
							for(String policyTemplate:policyTemplateMap.keySet()){
								parsePolicyTemplate(policyTemplate,(Map<String,Object>)policyTemplateMap.get(policyTemplate));
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
					break;
				default:
					break;
			
				}
			}
		}
		
		public String toString(){
			String toReturn = "---------------------Relationship Types----------------------------\n";
			for(String defName: customRelDefinitions.keySet()){
				toReturn += customRelDefinitions.get(defName);
			}
			toReturn += "-----------------------Node Templates------------------------------\n";
			for(String defName: nodeTemplates.keySet()){
				toReturn += nodeTemplates.get(defName);
			}
			toReturn += "-------------------Relationship Templates--------------------------\n";
			for(String defName: relTemplates.keySet()){
				toReturn += relTemplates.get(defName);
			}
			return toReturn;
		}
}
