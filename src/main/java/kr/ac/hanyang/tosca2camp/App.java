package kr.ac.hanyang.tosca2camp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
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
import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef;
import kr.ac.hanyang.tosca2camp.definitiontypes.NodeDef.Builder;


/**
 * Hello world!
 *
 */
public class App{
	
	//hardcode the names of the definition files only for testing
	private String[] nodeDefFileNames = {"nodes.BlockStorage.yml"};
	private Map<String, NodeDef> nodeDefinitions = new LinkedHashMap<String, NodeDef>();
	
    
	@SuppressWarnings({ "unused", "unchecked" })
	private void loadDefinitions() throws FileNotFoundException{
		for(String fileName: nodeDefFileNames){
			Yaml yaml = new Yaml();
			Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File("C:/Users/Kena/Git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/definitions/"+fileName)));
			for(String defName:map.keySet())
				System.out.println(parseNodeDef(defName,(Map<String, Object>)map.get(defName)));
		}
	}
	
	
	private <T> NodeDef parseNodeDef(String name, Map<String, Object>nodeMap){
		String type = (String) nodeMap.get("type");
		NodeDef parent = (NodeDef) nodeDefinitions.get(nodeMap.get("derived_from"));
		// if not null then copy the nodeDef
		//TODO find the def in the list if already loaded othewise load the def
		if (parent != null)
			NodeDef.Builder<Builder> nodeDefBuilder = parent.getBuilder(); // use this builder to copy the parent info
		else
			NodeDef.Builder<Builder> nodeDefBuilder = new NodeDef.Builder<Builder>(name, type); 
		
		for(String )
		
		
		return nodeDefBuilder.build();
	}
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> NodeTemplate parseNode(String name, Map<String, Object>nodeMap){
		//NodeTemplate.Builder<V, Builder<?,?>> nodeBuilder;
		//try{
		if (nodeMap != null){
			switch((String) nodeMap.get("type")){
			case "Compute": 
				ComputeNode.Builder compNodeBuilder = new ComputeNode.Builder("toscaID",name,"status");
				for (String key:nodeMap.keySet()){
					switch(key){
					case "capabilities":
						Map<String,Object> capMap = (Map<String,Object>) nodeMap.get(key);
						for(String capabilityType:capMap.keySet()){
							switch(capabilityType){
							case "host":	
								compNodeBuilder.addContainerCapability((ContainerCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							case "Endpoint.Admin":
								compNodeBuilder.addEndpointCapability((AdminEndpointCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							case "os":	
								compNodeBuilder.addOSCapability((OperatingSystemCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							case "Scalable":
								compNodeBuilder.addScalableCapability((ScalableCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							case "network.Bindable":
								compNodeBuilder.addBindableCapability((BindableNetworkCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								compNodeBuilder.addCapability(parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							}
						}
						break;
					case "requirements":
						List<Object> reqList = (List<Object>) nodeMap.get(key);
						for(Object listItem:reqList){
							Map<String,Object> reqInnerMap = (Map<String,Object>) listItem;
							for(String requirementType:reqInnerMap.keySet())
								compNodeBuilder.addRequirement(parseRequirement(requirementType,(Map<String, Object>)reqInnerMap.get(requirementType)));
						}
						break;
					}
				}
				return compNodeBuilder.build();
			case "SoftwareComponent":
				SoftwareComponentNode.Builder softCompBuilder = new SoftwareComponentNode.Builder("toscaID",name,"status");
				for (String key:nodeMap.keySet()){
					switch(key){
					case "properties":
						Map<String,Object> propMap = (Map<String,Object>) nodeMap.get(key);
						for(String propertyItem:propMap.keySet()){
							switch(propertyItem){
							case "component_version":	
								softCompBuilder.componentVersion((String) propMap.get(propertyItem));
								break;
							case "admin_credential":
								softCompBuilder.adminCreds((String) propMap.get(propertyItem));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								softCompBuilder.addProperty(new PropertyAs.Builder<V>(propertyItem, (V) propMap.get(propertyItem)).build());
								break;
							}
						}
						break;
					case "requirements":
						List<Object> reqList = (List<Object>) nodeMap.get(key);
						for(Object listItem:reqList){
							Map<String,Object> reqInnerMap = (Map<String,Object>) listItem;
							for(String requirementType:reqInnerMap.keySet())
								softCompBuilder.addRequirement(parseRequirement(requirementType,(Map<String, Object>)reqInnerMap.get(requirementType)));
						}
						break;
					}
				}
				return softCompBuilder.build();
			case "WebServer":
				WebServerNode.Builder webNodeBuilder = new WebServerNode.Builder("toscaID",name,"status");
				for (String key:nodeMap.keySet()){
					switch(key){
					case "capabilities":
						Map<String,Object> capMap = (Map<String,Object>) nodeMap.get(key);
						for(String capabilityType:capMap.keySet()){
							switch(capabilityType){
							case "data_endpoint":	
								webNodeBuilder.addDataEndPtCapability((EndpointCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							case "admin_endpoint":
								webNodeBuilder.addAdminEndPtCapability((AdminEndpointCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							case "host":	
								webNodeBuilder.addHostCapability((ContainerCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								webNodeBuilder.addCapability(parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							}
						}
						break;
					case "requirements":
						List<Object> reqList = (List<Object>) nodeMap.get(key);
						for(Object listItem:reqList){
							Map<String,Object> reqInnerMap = (Map<String,Object>) listItem;
							for(String requirementType:reqInnerMap.keySet())
								webNodeBuilder.addRequirement(parseRequirement(requirementType,(Map<String, Object>)reqInnerMap.get(requirementType)));
						}
						break;
					}
				}
				return webNodeBuilder.build();
			case "WebApplication":
				WebApplicationNode.Builder webAppBuilder = new WebApplicationNode.Builder("toscaID",name,"status");
				for (String key:nodeMap.keySet()){
					switch(key){
					case "properties":
						Map<String,Object> propMap = (Map<String,Object>) nodeMap.get(key);
						for(String propertyItem:propMap.keySet()){
							switch(propertyItem){
							case "context_root":	
								webAppBuilder.contextRoot((String) propMap.get(propertyItem));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								webAppBuilder.addProperty(new PropertyAs.Builder<V>(propertyItem, (V) propMap.get(propertyItem)).build());
								break;
							}
						}
						break;
					case "capabilities":
						Map<String,Object> capMap = (Map<String,Object>) nodeMap.get(key);
						for(String capabilityType:capMap.keySet()){
							switch(capabilityType){
							case "app_endpoint":	
								webAppBuilder.addAppEndpointCapability((EndpointCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								webAppBuilder.addCapability(parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							}
						}
						break;
					case "requirements":
						List<Object> reqList = (List<Object>) nodeMap.get(key);
						for(Object listItem:reqList){
							Map<String,Object> reqInnerMap = (Map<String,Object>) listItem;
							for(String requirementType:reqInnerMap.keySet())
								webAppBuilder.addRequirement(parseRequirement(requirementType,(Map<String, Object>)reqInnerMap.get(requirementType)));
						}
						break;
					}
				}
				return webAppBuilder.build();
			case "DBMS":
				DBMSNode.Builder dbmsBuilder = new DBMSNode.Builder("toscaID",name,"status");
				for (String key:nodeMap.keySet()){
					switch(key){
					case "properties":
						Map<String,Object> propMap = (Map<String,Object>) nodeMap.get(key);
						for(String propertyItem:propMap.keySet()){
							switch(propertyItem){
							case "root_password":	
								dbmsBuilder.rootPassword((String) propMap.get(propertyItem));
								break;
							case "port":	
								dbmsBuilder.port(((Integer) propMap.get(propertyItem)).intValue()); //this may have to be the wrapper
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								dbmsBuilder.addProperty(new PropertyAs.Builder<V>(propertyItem, (V) propMap.get(propertyItem)).build());
								break;
							}
						}
						break;
					case "capabilities":
						Map<String,Object> capMap = (Map<String,Object>) nodeMap.get(key);
						for(String capabilityType:capMap.keySet()){
							switch(capabilityType){
							case "host":	
								dbmsBuilder.addHostCapability((ContainerCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								dbmsBuilder.addCapability(parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							}
						}
						break;
					}
				}
				return dbmsBuilder.build();
			case "Database":
				DatabaseNode.Builder databaseBuilder = new DatabaseNode.Builder("toscaID",name,"status",(String)nodeMap.get("name")); 
				for (String key:nodeMap.keySet()){
					switch(key){
					case "properties":
						Map<String,Object> propMap = (Map<String,Object>) nodeMap.get(key);
						for(String propertyItem:propMap.keySet()){
							switch(propertyItem){
							case "port":	
								databaseBuilder.port(((Integer) propMap.get(propertyItem)).intValue());
								break;
							case "user":	
								databaseBuilder.user((String) propMap.get(propertyItem));
								break;
							case "password":	
								databaseBuilder.password((String) propMap.get(propertyItem));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								databaseBuilder.addProperty(new PropertyAs.Builder<V>(propertyItem, (V) propMap.get(propertyItem)).build());
								break;
							}
						}
						break;
					case "capabilities":
						Map<String,Object> capMap = (Map<String,Object>) nodeMap.get(key);
						for(String capabilityType:capMap.keySet()){
							switch(capabilityType){
							case "database_endpoint":	
								databaseBuilder.addDatabaseEndpointCapability((DatabaseEndpointCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								databaseBuilder.addCapability(parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							}
						}
						break;
					case "requirements":
						List<Object> reqList = (List<Object>) nodeMap.get(key);
						for(Object listItem:reqList){
							Map<String,Object> reqInnerMap = (Map<String,Object>) listItem;
							for(String requirementType:reqInnerMap.keySet())
								databaseBuilder.addRequirement(parseRequirement(requirementType,(Map<String, Object>)reqInnerMap.get(requirementType)));
						}
						break;
					}
				}
				return databaseBuilder.build();
			case "ObjectStorage":
				ObjectStorageNode.Builder objStorageBuilder = new ObjectStorageNode.Builder("toscaID",name,"status",(String)nodeMap.get("name"));
				for (String key:nodeMap.keySet()){
					switch(key){
					case "properties":
						Map<String,Object> propMap = (Map<String,Object>) nodeMap.get(key);
						for(String propertyItem:propMap.keySet()){
							switch(propertyItem){
							case "size":	
								objStorageBuilder.size((String) propMap.get(propertyItem));
								break;
							case "maxsize":	
								objStorageBuilder.maxSize((String) propMap.get(propertyItem));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								objStorageBuilder.addProperty(new PropertyAs.Builder<V>(propertyItem, (V) propMap.get(propertyItem)).build());
								break;
							}
						}
						break;
					case "capabilities":
						Map<String,Object> capMap = (Map<String,Object>) nodeMap.get(key);
						for(String capabilityType:capMap.keySet()){
							switch(capabilityType){
							case "storage_endpoint":	
								objStorageBuilder.addStorageEndpointCapability((EndpointCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								objStorageBuilder.addCapability(parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							}
						}
						break;
					}
				}
				return objStorageBuilder.build();
			case "BlockStorage":
				BlockStorageNode.Builder blkStorageBuilder = new BlockStorageNode.Builder("toscaID",name,"status",(String)nodeMap.get("size"));
				for (String key:nodeMap.keySet()){
					switch(key){
					case "properties":
						Map<String,Object> propMap = (Map<String,Object>) nodeMap.get(key);
						for(String propertyItem:propMap.keySet()){
							switch(propertyItem){
							case "volume_id":	
								blkStorageBuilder.volumeId((String) propMap.get(propertyItem));
								break;
							case "snapshot_id":	
								blkStorageBuilder.snapshotId((String) propMap.get(propertyItem));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								blkStorageBuilder.addProperty(new PropertyAs.Builder<V>(propertyItem, (V) propMap.get(propertyItem)).build());
								break;
							}
						}
						break;
					case "capabilities":
						Map<String,Object> capMap = (Map<String,Object>) nodeMap.get(key);
						for(String capabilityType:capMap.keySet()){
							switch(capabilityType){
							case "attachment":	
								blkStorageBuilder.addAttachmentCapability((AttachmentCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								blkStorageBuilder.addCapability(parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							}
						}
						break;
					}
				}
				return blkStorageBuilder.build();
			case "Container.Runtime":
				RuntimeContainerNode.Builder runtimeBuilder = new RuntimeContainerNode.Builder("toscaID",name,"status"); 
				for (String key:nodeMap.keySet()){
					switch(key){
					case "capabilities":
						Map<String,Object> capMap = (Map<String,Object>) nodeMap.get(key);
						for(String capabilityType:capMap.keySet()){
							switch(capabilityType){
							case "host":	
								runtimeBuilder.addHostCapability((ContainerCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							case "scalable":
								runtimeBuilder.addScalableCapability((ScalableCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								runtimeBuilder.addCapability(parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							}
						}
						break;
					}
				}
				return runtimeBuilder.build();
			case "Container.Application":
				ApplicationContainerNode.Builder contBuilder = new ApplicationContainerNode.Builder("toscaID",name,"status");
				for (String key:nodeMap.keySet()){
					switch(key){
					case "requirements":
						List<Object> reqList = (List<Object>) nodeMap.get(key);
						for(Object listItem:reqList){
							Map<String,Object> reqInnerMap = (Map<String,Object>) listItem;
							for(String requirementType:reqInnerMap.keySet())
								contBuilder.addRequirement(parseRequirement(requirementType,(Map<String, Object>)reqInnerMap.get(requirementType)));
						}
						break;
					}
				}
				return contBuilder.build();
			case "LoadBalancer":
				LoadBalancerNode.Builder loadBuilder = new LoadBalancerNode.Builder("toscaID",name,"status");
				for (String key:nodeMap.keySet()){
					switch(key){
					case "properties":
						Map<String,Object> propMap = (Map<String,Object>) nodeMap.get(key);
						for(String propertyItem:propMap.keySet()){
							switch(propertyItem){
							case "algorithm":	
								loadBuilder.algorithm((String) propMap.get(propertyItem));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								loadBuilder.addProperty(new PropertyAs.Builder<V>(propertyItem, (V) propMap.get(propertyItem)).build());
								break;
							}
						}
						break;
					case "capabilities":
						Map<String,Object> capMap = (Map<String,Object>) nodeMap.get(key);
						for(String capabilityType:capMap.keySet()){
							switch(capabilityType){
							case "client":	
								loadBuilder.addClientCapability((PublicEndpointCapability)parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							default:
								//add code to add a property that may not be in the spec
								//this allows future expansion;
								//TODO this could instead throw an exception
								loadBuilder.addCapability(parseCapability(capabilityType,(Map<String, Object>)capMap.get(capabilityType)));
								break;
							}
						}
						break;
					case "requirements":
						List<Object> reqList = (List<Object>) nodeMap.get(key);
						for(Object listItem:reqList){
							Map<String,Object> reqInnerMap = (Map<String,Object>) listItem;
							for(String requirementType:reqInnerMap.keySet())
								loadBuilder.addRequirement(parseRequirement(requirementType,(Map<String, Object>)reqInnerMap.get(requirementType)));
						}
						break;
					}
				}
				return loadBuilder.build();
				//break;
			default:
				//we dont have a type specified so we should build the root node
				return new RootNode.Builder("toscaID",name,"status","storage_size").build();
			}	
		}else{
			//System.out.println(e.getMessage());
			return new RootNode.Builder<V>( "get_tosca_ID", name, "get_tosca_status").build();
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static CapabilityAs parseCapability(String type, Map<String, Object> property){
		Map<String, Object> propertyMap = (Map<String, Object>) property.get("properties");
		CapabilityAs returnCap;
		switch (type){
		case "host":
			ContainerCapability.Builder builder = new ContainerCapability.Builder("desc");
			for (String key:propertyMap.keySet()){
				switch (key){
					case "cpu_frequency":builder.addCpu_frequency(((Integer) propertyMap.get(key)).intValue());
						break;
					case "disk_size":builder.addDisk_size(((Integer) propertyMap.get(key)).intValue());
						break;
					case "num_cpus":builder.addNumCpu(((Integer) propertyMap.get(key)).intValue());
						break;
					case "mem_size":builder.addMem_size(((Integer) propertyMap.get(key)).intValue());
						break;
					default:
						break;
				}
			}
			returnCap = builder.build();
			break;
		case "endpoint":
			EndpointCapability.Builder epBuilder = new EndpointCapability.Builder("desc",(String)propertyMap.get("protocol"),"IP Addr");
			for (String key:propertyMap.keySet()){
				switch (key){
					case "port":epBuilder.addPort(((Integer) propertyMap.get(key)).intValue());
						break;
					case "secure":epBuilder.addSecure(((Boolean) propertyMap.get(key)).booleanValue());
						break;
					case "url_path":epBuilder.addUrlPath_size(((String)propertyMap.get(key)).toString());
						break;
					case "port_name":epBuilder.addPortname(((String)propertyMap.get(key)).toString());
						break;
					case "network_name":epBuilder.addNetworkName(((String)propertyMap.get(key)).toString());
						break;
					case "initiator":epBuilder.addInitiator(((String)propertyMap.get(key)).toString());
						break;
					case "ports":epBuilder.addPorts(((String)propertyMap.get(key)).toString());
						break;
					default:
						break;
				}
			}
			returnCap = epBuilder.build();
			break;
		case "public_endpoint":
			PublicEndpointCapability.Builder pepBuilder = new PublicEndpointCapability.Builder("desc",(String)propertyMap.get("protocol"),"IP Addr");
			returnCap = pepBuilder.build();
			break;
		case "admin_endpoint":
			AdminEndpointCapability.Builder adepBuilder = new AdminEndpointCapability.Builder("desc",(String)propertyMap.get("protocol"),"IP Addr");
			returnCap = adepBuilder.build();
			break;
		case "database_endpoint":
			DatabaseEndpointCapability.Builder dbepBuilder = new DatabaseEndpointCapability.Builder("desc",(String)propertyMap.get("protocol"),"IP Addr");
			returnCap = dbepBuilder.build();
			break;
		case "attachment":
			AttachmentCapability.Builder attachBuilder = new AttachmentCapability.Builder("desc");
			returnCap = attachBuilder.build();
			break;
		case "os":
			OperatingSystemCapability.Builder osBuilder = new OperatingSystemCapability.Builder("");
			for (String key:propertyMap.keySet()){
				switch (key){
					case "architecture":osBuilder.addArchitecture((String)propertyMap.get(key));
						break;
					case "type":osBuilder.addType((String)propertyMap.get(key));
						break;
					case "distribution":osBuilder.addDistribution((String)propertyMap.get(key));
						break;
					case "version":osBuilder.addVersion((String)propertyMap.get(key));
						break;
					default:
						break;
				}
			}
			returnCap = osBuilder.build();
			break;
		case "scalable":
			ScalableCapability.Builder scalBuilder = new ScalableCapability.Builder("desc");
			for (String key:propertyMap.keySet()){
				switch (key){
					case "min_instances":scalBuilder.addMinInstances(((Integer) propertyMap.get(key)).intValue());
						break;
					case "max_instances":scalBuilder.addMaxInstances(((Integer) propertyMap.get(key)).intValue());
						break;
					case "default_instances":scalBuilder.addDefaultInstances(((Integer) propertyMap.get(key)).intValue());
						break;
					default:
						break;	
				}
			}
			returnCap = scalBuilder.build();
			break;
		case "bindable":
			BindableNetworkCapability.Builder bindNetBuilder = new BindableNetworkCapability.Builder("desc");
			returnCap = bindNetBuilder.build();
			break;
		default: // this will build the base Node capability.
			NodeCapability.Builder nodeBuilder = new NodeCapability.Builder("desc");
			returnCap = nodeBuilder.build();
		}
		return returnCap;
		
	}
	
	//TODO p.g. 261 should be able to parse extended grammar with properties
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> RelationshipTemplate parseRelationship(String name, Map<String, Object> property){
		//Map<String, Object> propertyMap = (Map<String, Object>) property.get("properties");
		//RelationshipTemplate relationShip;
		RelationshipTemplate.Builder builder = new RelationshipTemplate.Builder(name,"desc");
		for (String key:property.keySet()){
			builder.addProperties(new PropertyAs.Builder<V>(key,(V)property.get(key)).build());
		}
		return builder.build();
	}
	
	
	//TODO p.g. 260 - 261 this should be able to parse short or extended form 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T, U, V> RequirementAs parseRequirement(String name, Map<String, Object> requirement){
		RequirementAs.Builder<T, U, V> reqBuilder = new RequirementAs.Builder<T, U, V>(name);
		for (String key:requirement.keySet()){
			switch (key){
				case "capability":
					Map<String, Object> capMap = (Map<String, Object>) requirement.get(key);
					for(String key2:capMap.keySet()){
						String capType = (String) capMap.get(key2); // get the type of capability
						Map<String, Object> capProperties = (Map<String, Object>) capMap.get("properties");
						reqBuilder.capability((T)parseCapability(capType, capProperties));
					}
					break;
				case "node":
					reqBuilder.node((U)parseNode((String) requirement.get(key), null)); //create an empty node with the name. this will be a root node
					break;
				case "relationship":
					Map<String, Object> relationshipMap = (Map<String, Object>) requirement.get(key);
					String relType = (String) relationshipMap.get("type"); // get the type of relationship
					Map<String, Object> relProperties = (Map<String, Object>) relationshipMap.get("properties");
					reqBuilder.relationship((V)parseRelationship(relType, relProperties));
					break;
				
				default:
					break;
			}
		}
		return reqBuilder.build();
	}
	
	
	public static void parseTosca(Map<String, Object> toscaMap){
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

		App app = new App();
		app.loadDefinitions();
		
		
			//Yaml yaml = new Yaml();
			//Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File("C:/Users/Kena/Git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/Sample1.yml")));
			//parseTosca(map);
			
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
				
				
				
				

	
					
