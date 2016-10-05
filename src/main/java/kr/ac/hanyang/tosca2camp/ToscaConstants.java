package kr.ac.hanyang.tosca2camp;

public class ToscaConstants {
	public static final String FILEPATH = "/Users/Kena/git/tosca2camp-0.0.1-SNAPSHOT/src/main/java/kr/ac/hanyang/tosca2camp/definitions/";
	public static final String TYPEPREFIX = "tosca.datatypes.";
	
	public static final String[] NODEDEF_FILENAMES = {"tosca.nodes.Root.yml","tosca.nodes.BlockStorage.yml","tosca.nodes.Compute.yml",
			"tosca.nodes.Container.Application.yml","tosca.nodes.Container.Runtime.yml","tosca.nodes.Database.yml",
			"tosca.nodes.DBMS.yml","tosca.nodes.LoadBalancer.yml","tosca.nodes.ObjectStorage.yml",
			"tosca.nodes.SoftwareComponent.yml","tosca.nodes.WebApplication.yml","tosca.nodes.WebServer.yml"};
	
	public static final String[] CAPDEF_FILENAMES = {"tosca.capabilities.Root.yml","tosca.capabilities.Scalable.yml","tosca.capabilities.OperatingSystem.yml",
			 "tosca.capabilities.Node.yml","tosca.capabilities.Endpoint.yml","tosca.capabilities.Endpoint.Database.yml",
			 "tosca.capabilities.Endpoint.Admin.yml","tosca.capabilities.Container.yml","tosca.capabilities.Bindable.yml",
			 "tosca.capabilities.Attachment.yml"};
	
	public static final String[] RELDEF_FILENAMES = {"tosca.relationships.Root.yml","tosca.relationships.ConnectsTo.yml","tosca.relationships.DependsOn.yml",
			 "tosca.relationships.HostedOn.yml","tosca.relationships.RoutesTo.yml","tosca.relationships.AttachesTo.yml"};
	
	public static final String[] DTYPEDEF_FILENAMES = {"tosca.datatypes.credential.yml","tosca.datatypes.boolean.yml","tosca.datatypes.float.yml",
			"tosca.datatypes.integer.yml","tosca.datatypes.list.yml","tosca.datatypes.map.yml",
			"tosca.datatypes.network.NetworkInfo.yml","tosca.datatypes.network.PortInfo.yml","tosca.datatypes.network.PortSpec.yml",
			"tosca.datatypes.network.PortDef.yml","tosca.datatypes.range.yml","tosca.datatypes.scalar-unit.frequency.yml",
			"tosca.datatypes.scalar-unit.size.yml","tosca.datatypes.scalar-unit.time.yml","tosca.datatypes.string.yml",
			"tosca.datatypes.timestamp.yml","tosca.datatypes.version.yml"};
	
	public static final String[] PTYPEDEF_FILENAMES = {"tosca.policies.root.yml","tosca.policies.placement.yml"};
}
