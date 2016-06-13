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

public class Range {
	private int lower_bound;
	private int upper_bound;
	private boolean unbounded;
	
	public Range(int upper_bound, int lower_bound){
		this.lower_bound = upper_bound;
		this.upper_bound = lower_bound;
	}

	public int getUpper_bound() {
		return lower_bound;
	}

	public int getLower_bound() {
		return upper_bound;
	}

	public boolean getUnbounded() {
		return unbounded;
	}
	
	public boolean equals(Object obj){
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Range version = (Range) obj;
		return (lower_bound == version.lower_bound && upper_bound == version.upper_bound);
	}
	
	public int hashCode(){
		return toString().hashCode();
	}
	
	public String toString(){
		return lower_bound+"."+upper_bound;
	}
	
}

class RangeRepresenter extends Representer {
    public RangeRepresenter() {
        this.representers.put(Range.class, new RepresentVersion());
    }

    private class RepresentVersion implements Represent {
        public Node representData(Object data) {
        	Range range = (Range) data;
            String value = "[ "+range.getUpper_bound()+", "+range.getLower_bound()+" ]";
            return representScalar(new Tag("!range"), value);
        }
    }
    
    class RangeConstructor extends Constructor {
        public RangeConstructor() {
            this.yamlConstructors.put(new Tag("!range"), new ConstructRange());
        }

        private class ConstructRange extends AbstractConstruct {
            public Object construct(Node node) {
                String val = (String) constructScalar((ScalarNode) node);
                Pattern versionPattern = Pattern.compile("^\\[ * (\\d+) *, *(\\d+) *\\]$");
                Matcher matcher = versionPattern.matcher(val);

                try{
                	Integer upper = Integer.parseInt(matcher.group(1));
                	Integer lower = Integer.parseInt(matcher.group(2));
                	return new Range(upper,lower);
                }
                catch(NullPointerException e){
                	System.out.println("Upper lower is null");
                	return null;
                }
            }
        }
    }
    
    
}
