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
import kr.ac.hanyang.tosca2camp.datatypes.capabilities.ContainerCapability;
import kr.ac.hanyang.tosca2camp.toscaTypes.MapType;


/**
 * Hello world!
 *
 */
public class App{
    
	public CapabilityAs parseCap(String type, Map<String, Object> map){
		
		switch (type){
		case "host":
			ContainerCapability host = ContainerCapability.Builder("").build();
			break;
		case "endpoint":
			break;
		case "public_endpoint":
			break;
		case "admin_endpoint":
			break;
		case "database_endpoint":
			break;
		case "attachment":
			break;
		case "os":
			break;
		case "scalable":
			break;
		case "bindable":
			break;
		default: // this will build the base Node capability.
			break;
		}
		for(String key:map.keySet()){
			Map<String, Object> capObj = (Map<String, Object>) map.get(key);
			System.out.println(key);
			for(String key2:capObj.keySet()){
				System.out.println(key2);
				//Map<String, Object> capObj = (Map<String, Object>) map.get(key);
				
			}
		}
		
		return cap;
	}
	
	
	public static void main( String[] args ) throws FileNotFoundException{

		
//        @SuppressWarnings("rawtypes")
//		RootNode rNode = new RootNode.Builder("tosca.nodes.root","RN001122","my_web_app_tier_1","Ready").build();
//        ComputeNode cNode = new ComputeNode.Builder("CN002211", "my_compute_1", "Ready").build();
//        SoftwareComponentNode sNode = new SoftwareComponentNode.Builder("SC001122","my_software_1","Ready").build();
//        WebServerNode wNode = new WebServerNode.Builder("WS002211", "my_webserver_1", "Ready").build();
//        WebApplicationNode wANode = new WebApplicationNode.Builder("WA001122", "my_webApp_1", "Ready").build();
//        DBMSNode DBNode = new DBMSNode.Builder("DB001122", "my_DB_1", "Ready").build();
//        DatabaseNode DBaseNode = new DatabaseNode.Builder("DB001122", "my_DB_1", "Ready","productsdb").build();
//        System.out.println(rNode);
//        System.out.println(cNode);
//        System.out.println(sNode);
//        System.out.println(wNode);
//        System.out.println(wANode);
//        System.out.println(DBNode);
//        System.out.println(DBaseNode);
			@SuppressWarnings("unused")
			//Constructor constructor = new Constructor(CapabilityAs.Builder.class);
			//TypeDescription capDescription = new TypeDescription(CapabilityAs.Builder.class);
			//capDescription.putListPropertyType("properties", PropertyAs.Builder.class);
			//constructor.addTypeDescription(capDescription);
			Yaml yaml = new Yaml();
			Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File("c:/Users/Kena/workspace/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/Sample1.yml")));
			for(String key:map.keySet()){
				Map<String, Object> capObj = (Map<String, Object>) map.get(key);
				System.out.println(key);
				for(String key2:capObj.keySet()){
					System.out.println(key2);
					//Map<String, Object> capObj = (Map<String, Object>) map.get(key);
					
				}
			}
			//System.out.println(map.get("capabilities"));
			//Map<String, List<PropertyAs>> str = (Map<String, List<PropertyAs>>) yaml.load(new FileInputStream(new File("c:/Users/Kena/workspace/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/Sample1.yml")));
			
			//System.out.println(str);
			//System.out.println(((Map)((Map)str.get("node_templates")).get("my_server")).get("type"));
		
	
	}
}
