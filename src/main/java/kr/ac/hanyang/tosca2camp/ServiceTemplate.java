package kr.ac.hanyang.tosca2camp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RelationshipDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.RequirementDef;

public class ServiceTemplate {

	
		//need a list for inputs
		private Map<String ,RelationshipDef> customRelDefinitions;
		private Map<String, NodeDef> nodeTemplates;
		private Map<String, RelationshipDef> relTemplates;
		//need a list for outputs
		
		
		public ServiceTemplate() {
			customRelDefinitions = new LinkedHashMap<String, RelationshipDef>();
			nodeTemplates = new LinkedHashMap<String, NodeDef>();
			relTemplates = new LinkedHashMap<String, RelationshipDef>();
		}
		
		
		public static ServiceTemplate getServiceTemplate(Map<String, Object> map){
			ServiceTemplate template = new ServiceTemplate();
			template.parseServiceTemplate(map);
			return template;
		}
		
		
		//-------------------------- Template Parsers--------------------------------------
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void parseNodeTemplate(String name, Map<String, Object>nodeMap){
			String type = (String) nodeMap.get("type");
			String typeName = normalizeTypeName(type,"node");
			
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
							RequirementDef reqDef = myDefinition.getRequirement(reqName);//.parseRelationshipDef(relItem);
							if (reqDef.getRelDefTypeName() instanceof String){
								RelationshipDef relDef = parseRelDef((String) reqDef.getRelDefTypeName())
							}
						}
					}
					break;			
				default:
					break;
				}
			}
			myDefinition = myDefinition.getBuilder(typeName).name(name).build();
			nodeTemplates.put(name, myDefinition);
		}
		
		
		public void parseRelTemplate(String name, Map<String,Object>relMap){
			String type = (String) relMap.get("type");
			String typeName = normalizeTypeName(type,"relationship");

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
		
}
