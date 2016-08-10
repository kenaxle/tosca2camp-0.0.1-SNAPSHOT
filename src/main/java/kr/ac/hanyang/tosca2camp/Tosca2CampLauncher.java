package kr.ac.hanyang.tosca2camp;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Tosca2CampLauncher {
	@SuppressWarnings("unchecked")
	public static void main( String[] args ) throws Exception{
		
		AppContext appContext = AppContext.newAppContext();
		File yamlFile = new File("C:/Users/Kena/Git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/Sample2.yml");
		ServiceTemplate st = ServiceTemplate.getServiceTemplate(yamlFile, appContext);
		System.out.println(st.toString());
		
		//System.out.println(platform.getNodeDefs());
        //Yaml yaml = new Yaml();
       // Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream();
        
		//-----------------------------------------------
		
		// Parse the Yaml plan
		//-----------------------------------------------
//	    
//		
//		
//		app.parseServiceTemplate(map);
//		System.out.println("---------------------Relationship Types----------------------------");
//		
//		for(String defName: app.customRelDefinitions.keySet()){
//			System.out.println(app.customRelDefinitions.get(defName));
//		}
//		System.out.println("-----------------------Node Templates------------------------------");
//		for(String defName: app.nodeTemplates.keySet()){
//			System.out.println(app.nodeTemplates.get(defName));
//		}
//		System.out.println("-------------------Relationship Templates--------------------------");
//		for(String defName: app.relTemplates.keySet()){
//			System.out.println(app.relTemplates.get(defName));
//		}
//
//	}
	}
}
