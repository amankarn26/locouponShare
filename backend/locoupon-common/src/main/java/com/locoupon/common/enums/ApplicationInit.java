package com.locoupon.common.enums;

//import org.glassfish.jersey.media.multipart.MultiPartFeature;
//import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.stereotype.Service;

//import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
//import com.inouvelle.supply.util.SystemHelper;
//import com.wordnik.swagger.jersey.listing.ApiListingResourceJSON;

@Service
public class ApplicationInit extends Resource {

	public static String tomcatpath;

	public ApplicationInit() {
//		register(RequestContextFilter.class);
//		register(JacksonFeatures.class);
//		register(MultiPartFeature.class);
//		register(CORSResponseFilter.class);
		tomcatpath = System.getenv("CATALINA_HOME");
		System.out.println("Application tomcatpath:==" + tomcatpath);

//		SystemHelper list = new SystemHelper();
//		for (Class c : list.clist) {
//			register(c);
//		}
//		register(ApiListingResourceJSON.class);
//		register(ResourceHttpRequestHandler.class);
	}
}