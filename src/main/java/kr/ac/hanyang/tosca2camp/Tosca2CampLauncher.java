package kr.ac.hanyang.tosca2camp;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Tosca2CampLauncher {
	@SuppressWarnings("unchecked")
	public static void main( String[] args ) throws Exception{
		
//		// load the Normative definitions
//		//-----------------------------------------------
		AppContext app = new AppContext();
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
		Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream(new File("C:/Users/Kena/Git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/Sample4.yml")));
		
		app.parseServiceTemplate(map);
		System.out.println("---------------------Relationship Types----------------------------");
		
		for(String defName: app.customRelDefinitions.keySet()){
			System.out.println(app.customRelDefinitions.get(defName));
		}
		System.out.println("-----------------------Node Templates------------------------------");
		for(String defName: app.nodeTemplates.keySet()){
			System.out.println(app.nodeTemplates.get(defName));
		}
		System.out.println("-------------------Relationship Templates--------------------------");
		for(String defName: app.relTemplates.keySet()){
			System.out.println(app.relTemplates.get(defName));
		}

	}
}
}
