package kr.ac.hanyang.tosca2camp;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Tosca2CampLauncher {
	@SuppressWarnings("unchecked")
	public static void main( String[] args ) throws Exception{
		
		Tosca2CampPlatform platform = Tosca2CampPlatform.newPlatform();
		//resource_handler.
		//server.setHandler(new T2CHandler());
		//server.start();
		//server.join();
		File yamlFile = new File("/Users/Kena/git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/Sample2.yml");
		//ServiceTemplate st = platform.createServiceTemplate(yamlFile);
		//System.out.println(st.toString());
		
		//System.out.println(platform.getNodeDefs());
        //Yaml yaml = new Yaml();
       // Map<String, Object> map = (Map<String,Object>) yaml.load(new FileInputStream();
        
		
	}
}
