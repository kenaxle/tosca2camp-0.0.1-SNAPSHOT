package kr.ac.hanyang.tosca2camp;

import java.io.File;

public class Tosca2CampUtils {
	
	public static ServiceTemplateUtilities createServiceTemplate( Tosca2CampPlatform platform, File file){
		return platform.createServiceTemplate(file);
	}
	
}
