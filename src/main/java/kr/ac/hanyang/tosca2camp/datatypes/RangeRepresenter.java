package kr.ac.hanyang.tosca2camp.datatypes;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

public class RangeRepresenter extends Representer {
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
}