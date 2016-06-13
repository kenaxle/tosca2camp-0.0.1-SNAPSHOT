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

public class ScalarUnit {
	private double scalar;
	private String unit;
	private boolean unbounded;
	
	public ScalarUnit(double scalar, String unit){
		this.scalar = scalar;
		this.unit = unit;
	}

	public double getScalar() {
		return scalar;
	}

	public String getUnit() {
		return unit;
	}
	
//	public boolean equals(Object obj){
//		if (obj == null) return false;
//		if (getClass() != obj.getClass()) return false;
//		ScalarUnit version = (ScalarUnit) obj;
//		return (lower_bound == version.lower_bound && upper_bound == version.upper_bound);
//	}
//	
//	public int hashCode(){
//		return toString().hashCode();
//	}
	
	public String toString(){
		return scalar+" "+unit;
	}
	
}

class ScalarSizeRepresenter extends Representer {
    public ScalarSizeRepresenter() {
        this.representers.put(ScalarUnit.class, new RepresentScalarSize());
    }

    private class RepresentScalarSize implements Represent {
        public Node representData(Object data) {
        	ScalarUnit scalar = (ScalarUnit) data;
            String value = scalar.getScalar()+" "+scalar.getUnit();
            return representScalar(new Tag("!scalar-unit_size"), value);
        }
    }
    
    class ScalarSizeConstructor extends Constructor {
        public ScalarSizeConstructor() {
            this.yamlConstructors.put(new Tag("!scalar-unit_size"), new ConstructScalarSize());
        }

        private class ConstructScalarSize extends AbstractConstruct {
            public Object construct(Node node) {
                String val = (String) constructScalar((ScalarNode) node);
                Pattern versionPattern = Pattern.compile("^(\\d+\\.?\\d?+) *(\\bB\\b|\\bkB\\b|\\bKiB\\b|\\bMB\\b|\\bMiB\\b|\\bGB\\b|\\bGiB\\b|\\bTB\\b|\\bTiB\\b)$");
                Matcher matcher = versionPattern.matcher(val);

                try{
                	Double scalar = Double.parseDouble(matcher.group(1));
                	String unit = matcher.group(2);
                	return new ScalarUnit(scalar,unit);
                }
                catch(NullPointerException e){
                	System.out.println("Upper lower is null");
                	return null;
                }
            }
        }
    }
     
}

class ScalarTimeRepresenter extends Representer {
    public ScalarTimeRepresenter() {
        this.representers.put(ScalarUnit.class, new RepresentScalarTime());
    }

    private class RepresentScalarTime implements Represent {
        public Node representData(Object data) {
        	ScalarUnit scalar = (ScalarUnit) data;
            String value = scalar.getScalar()+" "+scalar.getUnit();
            return representScalar(new Tag("!scalar-unit_time"), value);
        }
    }
    
    class ScalarTimeConstructor extends Constructor {
        public ScalarTimeConstructor() {
            this.yamlConstructors.put(new Tag("!scalar-unit_size"), new ConstructScalarTime());
        }

        private class ConstructScalarTime extends AbstractConstruct {
            public Object construct(Node node) {
                String val = (String) constructScalar((ScalarNode) node);
                Pattern versionPattern = Pattern.compile("^(\\d+\\.?\\d?+) *(\\bd\\b|\\bh\\b|\\bm\\b|\\bs\\b|\\bms\\b|\\bus\\b|\\bns\\b)$");
                Matcher matcher = versionPattern.matcher(val);

                try{
                	Double scalar = Double.parseDouble(matcher.group(1));
                	String unit = matcher.group(2);
                	return new ScalarUnit(scalar,unit);
                }
                catch(NullPointerException e){
                	System.out.println("Upper lower is null");
                	return null;
                }
            }
        }
    }
     
}

class ScalarFrequencyRepresenter extends Representer {
    public ScalarFrequencyRepresenter() {
        this.representers.put(ScalarUnit.class, new RepresentScalarFrequency());
    }

    private class RepresentScalarFrequency implements Represent {
        public Node representData(Object data) {
        	ScalarUnit scalar = (ScalarUnit) data;
            String value = scalar.getScalar()+" "+scalar.getUnit();
            return representScalar(new Tag("!scalar-unit_frequency"), value);
        }
    }
    
    class ScalarFrequencyConstructor extends Constructor {
        public ScalarFrequencyConstructor() {
            this.yamlConstructors.put(new Tag("!scalar-unit_frequency"), new ConstructScalarFrequency());
        }

        private class ConstructScalarFrequency extends AbstractConstruct {
            public Object construct(Node node) {
                String val = (String) constructScalar((ScalarNode) node);
                Pattern versionPattern = Pattern.compile("^(\\d+\\.?\\d?+) *(\\bHz\\b|\\bkHz\\b|\\bMHz\\b|\\bGHz\\b)$");
                Matcher matcher = versionPattern.matcher(val);

                try{
                	Double scalar = Double.parseDouble(matcher.group(1));
                	String unit = matcher.group(2);
                	return new ScalarUnit(scalar,unit);
                }
                catch(NullPointerException e){
                	System.out.println("Upper lower is null");
                	return null;
                }
            }
        }
    }
     
}
