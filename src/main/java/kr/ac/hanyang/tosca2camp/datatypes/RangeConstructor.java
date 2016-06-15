package kr.ac.hanyang.tosca2camp.datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;


public class RangeConstructor extends Constructor {
    public RangeConstructor() {
        this.yamlConstructors.put(new Tag("!range"), new ConstructRange());
    }

    private class ConstructRange extends AbstractConstruct {
        public Object construct(Node node) {
            String val = (String) constructSequence((SequenceNode) node).toString();
            Pattern versionPattern = Pattern.compile("\\[ *(\\S+) *, *(\\S+) *\\]");
            Matcher matcher = versionPattern.matcher(val);
            try{
            	return new Range(Integer.parseInt(matcher.group(1)),Integer.parseInt(matcher.group(2)));
            }
            catch(Exception e){
            	System.out.println(e.getMessage()+" Upper lower is null");
            	return null;
            }
        }
    }
}