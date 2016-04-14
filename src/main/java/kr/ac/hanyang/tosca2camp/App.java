package kr.ac.hanyang.tosca2camp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import kr.ac.hanyang.tosca2camp.assignments.CapabilityAs;
import kr.ac.hanyang.tosca2camp.assignments.NodeTemplate;
import kr.ac.hanyang.tosca2camp.assignments.PropertyAs;
import kr.ac.hanyang.tosca2camp.assignments.RelationshipTemplate;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs;
import kr.ac.hanyang.tosca2camp.assignments.RequirementAs.Builder;
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
import kr.ac.hanyang.tosca2camp.datatypes.nodes.RootNode;
import kr.ac.hanyang.tosca2camp.toscaTypes.MapType;


/**
 * Hello world!
 *
 */
public class App{
    
	@SuppressWarnings("rawtypes")
	public static <V> NodeTemplate parseNode(String type, Map<String, Object> nodeMap){
		return new RootNode.Builder<V>("Root", type, "ToscaID", "status").build();
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static CapabilityAs parseCap(String type, Map<String, Object> property){
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> RelationshipTemplate parseRelationship(String name, Map<String, Object> property){
		Map<String, Object> propertyMap = (Map<String, Object>) property.get("properties");
		//RelationshipTemplate relationShip;
		RelationshipTemplate.Builder builder = new RelationshipTemplate.Builder(name,"desc");
		for (String key:propertyMap.keySet()){
			builder.addProperties(new PropertyAs.Builder<V>(key,(V)propertyMap.get(key)).build());
		}
		return builder.build();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T, U, V> RequirementAs parseRequirement(String type, Map<String, Object> requirement){
		RequirementAs.Builder<T, U, V> reqBuilder = new RequirementAs.Builder<T, U, V>(type);
		for (String key:requirement.keySet()){
			switch (key){
				case "node":
					reqBuilder.node((U)parseNode(key, (Map)requirement.get(key)));
					break;
				case "relationship":
					Map<String, Object> relationshipMap = (Map<String, Object>) requirement.get(key);
					String relType = (String) relationshipMap.get("type"); // get the type of relationship
					Map<String, Object> relProperties = (Map<String, Object>) relationshipMap.get("properties");
					reqBuilder.relationship((V)parseRelationship(relType, relProperties));
					break;
				case "capabilities":
					//parse the capabilities
					//builder.addNumCpu(((Integer) propertyMap.get(key)).intValue());
					break;
				default:
					break;
			}
		}
		return reqBuilder.build();
	}
	
	public static void main( String[] args ) throws Exception{

			Yaml yaml = new Yaml();
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File("C:/Users/Kena/Git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/Sample1.yml")));
			for(String key:map.keySet()){
				@SuppressWarnings("unchecked")
				Map<String, Object> capObj = (Map<String, Object>) map.get(key);
				System.out.println(key);
				
				
				for(String key2:capObj.keySet()){
					@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
					Map<String, Object> propObj = (Map<String, Object>) capObj.get(key2);
					//System.out.println(key2);
					
					//parse the capability here.
					
					CapabilityAs myCap = parseCap(key2, propObj);
					System.out.println(myCap);
					
//					for(String key3:propObj.keySet()){
//						@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
//						Map <String, Object> propList = (Map<String, Object>) propObj.get(key3);
//						System.out.println(key3);
//						
//						for(String key4 :propList.keySet()){
//							try{
//								int prop = ((Integer) propList.get(key4)).intValue();
//								System.out.println(prop);
//							}catch(Exception e){	}
//							try{
//								String prop = (String)propList.get(key4);
//								System.out.println(prop);
//							}catch(Exception e){ }
//							try{
//								double prop = ((Double) propList.get(key4)).doubleValue();
//								System.out.println(prop);
//							}catch(Exception e){ }
//							
//						}
//						
//					}
					
	
					
				}
			}
	
		
	
	}
}
