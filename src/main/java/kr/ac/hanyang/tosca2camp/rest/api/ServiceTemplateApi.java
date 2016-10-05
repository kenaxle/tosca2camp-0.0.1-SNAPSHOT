package kr.ac.hanyang.tosca2camp.rest.api;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import kr.ac.hanyang.tosca2camp.ServiceTemplateUtilities;

@Path("/servicetemplates")
public interface ServiceTemplateApi {
	
//	@GET
//	@Path("/<add method name here>")
//    @Produces(MediaType.TEXT_PLAIN)
//	public ServiceTemplate createServiceTemplate(@WebParam(name = "arg0") File file);
//	
//	@GET
//	@Path("/<add method name here>")
//    @Produces(MediaType.TEXT_PLAIN)
//	public ServiceTemplate createServiceTemplate(@WebParam(name = "arg0") Map<String,Object> map);
	
	@GET
	@Path("/List")
    @Produces(MediaType.TEXT_PLAIN)
	public List<ServiceTemplateUtilities> getServiceTemplates();
}
