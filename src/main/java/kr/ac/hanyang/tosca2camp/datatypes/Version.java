package kr.ac.hanyang.tosca2camp.datatypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

public class Version {
	private int major_version;
	private int minor_version;
	private int fix_version;
	private String qualifier;
	private int build_version;
	
	public Version(int major_version, int minor_version){
		this.major_version = major_version;
		this.minor_version = minor_version;
	}

	public int getMajor_version() {
		return major_version;
	}

	public int getMinor_version() {
		return minor_version;
	}

	public int getFix_version() {
		return fix_version;
	}

	public void setFix_version(int fin_version) {
		this.fix_version = fin_version;
	}

	public String getQualifier() {
		return qualifier;
	}

	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}

	public int getBuild_version() {
		return build_version;
	}

	public void setBuild_version(int build_version) {
		this.build_version = build_version;
	}
	
	public boolean equals(Object obj){
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Version version = (Version) obj;
		return (major_version == version.major_version && minor_version == version.minor_version);
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	public String toString(){
		return major_version+"."+minor_version;
	}
	
}

class VersionRepresenter extends Representer {
    public VersionRepresenter() {
        this.representers.put(Version.class, new RepresentVersion());
    }

    private class RepresentVersion implements Represent {
        public Node representData(Object data) {
        	Version version = (Version) data;
            String value = version.getMajor_version()+"."+version.getMinor_version()+version.getFix_version()+"."+version.getQualifier()+"-"+version.getBuild_version();
            return representScalar(new Tag("!version"), value);
        }
    }
    
    class VersionConstructor extends Constructor {
        public VersionConstructor() {
            this.yamlConstructors.put(new Tag("!version"), new ConstructVersion());
        }

        private class ConstructVersion extends AbstractConstruct {
            public Object construct(Node node) {
                String val = (String) constructScalar((ScalarNode) node);
                Pattern versionPattern = Pattern.compile("^(\\d+)\\.(\\d+)(\\.(\\d+))?(\\.([a-z]*))?(\\-(\\d+))?$");
                Matcher matcher = versionPattern.matcher(val);
                Version toReturn;
                
                try{
                	Integer major = Integer.parseInt(matcher.group(1));
                	Integer minor = Integer.parseInt(matcher.group(2));
                	toReturn = new Version(major,minor);
                }
                catch(NullPointerException e){
                	System.out.println("Major minor version null");
                	return null;
                }
                if (matcher.group(3) != null){    	
                	if (matcher.group(5) != null){               		
                		if (matcher.group(7) != null){
                			Integer fixVer = Integer.parseInt(matcher.group(4));
                			String qualifier = matcher.group(6);
                			Integer buildVer = Integer.parseInt(matcher.group(8));
                			toReturn.setFix_version(fixVer);
                			toReturn.setQualifier(qualifier);
                			toReturn.setBuild_version(buildVer);
                		}
                	}
                }
                return toReturn;
            }
        }
    }
    
    
}
