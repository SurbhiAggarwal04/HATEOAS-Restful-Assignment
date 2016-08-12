package edu.asu.surbhi.assignment.entryPoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import edu.asu.surbhi.assignment.exceptions.AppealDeletionException;
import edu.asu.surbhi.assignment.exceptions.NoSuchAppealException;
import edu.asu.surbhi.assignment.exceptions.UpdateException;
import edu.asu.surbhi.assignment.representations.AppealRepresentation;
import edu.asu.surbhi.assignment.representations.RestbucksUri;
import edu.asu.surbhi.assignment.services.CreateAppealActivity;
import edu.asu.surbhi.assignment.services.ReadAppealActivity;
import edu.asu.surbhi.assignment.services.RemoveAppealActivity;
import edu.asu.surbhi.assignment.services.UpdateAppealActivity;

@Path("/appeals")
public class AppealResource {
	   private @Context UriInfo uriInfo;
	    @POST
	    @Consumes("application/vnd.cse564-appeals+xml")
	    @Produces("application/vnd.cse564-appeals+xml")
	    public Response createAppeals(String xmlRepresentation) {
	        Response response = null;
	        
	        try {
	        	
	            AppealRepresentation responseRepresentation = new CreateAppealActivity().create(AppealRepresentation.fromXmlString(xmlRepresentation).getItems(), new RestbucksUri (uriInfo.getRequestUri()));
	            response = Response.status(201).entity(responseRepresentation).build();
	        } catch (Exception ex) {
	           response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	        }
	        
	        
	        return response;
	    }
	    
	    @GET
	    @Path("/{appealId}")
	    @Produces("application/vnd.cse564-appeals+xml")
	    public Response getAppeals() {
	      
	        Response response;
	        
	        try {
	            AppealRepresentation responseRepresentation = new ReadAppealActivity().retrieveByUri(new RestbucksUri(uriInfo.getRequestUri()));
	            response = Response.status(Status.OK).entity(responseRepresentation).build();
	        } catch(NoSuchAppealException nsoe) {
	            response = Response.status(Status.NOT_FOUND).build();
	        } catch (Exception ex) {
	            response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	        }
	        
	        return response;
	    }
	    
	    @GET
	    @Path("/resolved/{appealId}")
	    @Produces("application/vnd.cse564-appeals+xml")
	    public Response getResolvedAppeal() {
	      
	        Response response;
	        
	        try {
	            AppealRepresentation responseRepresentation = new ReadAppealActivity().retrievResolvedeByUri(new RestbucksUri(uriInfo.getRequestUri()));
	            response = Response.status(Status.OK).entity(responseRepresentation).build();
	        } catch(NoSuchAppealException nsoe) {
	            response = Response.status(Status.NOT_FOUND).build();
	        } catch (Exception ex) {
	            response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	        }
	        
	        return response;
	    }
	    
	    @POST
	    @Path("/{appealId}")
	    @Consumes("application/vnd.cse564-appeals+xml")
	    @Produces("application/vnd.cse564-appeals+xml")
	    public Response updateAppeals(String appealRepresentation) {
	        Response response;
	        
	        try {
	        	AppealRepresentation responseRepresentation = new UpdateAppealActivity().update(AppealRepresentation.fromXmlString(appealRepresentation).getItems(), new RestbucksUri(uriInfo.getRequestUri()));
	            response = Response.status(Status.OK).entity(responseRepresentation).build();
	        } catch (NoSuchAppealException nsoe) {
	            response = Response.status(Status.NOT_FOUND).build();
	        } catch(UpdateException ue) {
	            response = Response.status(Status.CONFLICT).build();
	        } catch (Exception ex) {
	            response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	        } 
	        
	        return response;
	     }
	    @POST
	    @Path("/submit/{appealId}")
	    @Produces("application/vnd.cse564-appeals+xml")
	    public Response updateAppeals() {
	        Response response;
	        
	        try {
	        	AppealRepresentation responseRepresentation = new UpdateAppealActivity().updateReady(new RestbucksUri(uriInfo.getRequestUri()));
	            response = Response.status(Status.OK).entity(responseRepresentation).build();
	        } catch (NoSuchAppealException nsoe) {
	            response = Response.status(Status.NOT_FOUND).build();
	        } catch(UpdateException ue) {
	            response = Response.status(Status.CONFLICT).build();
	        } catch (Exception ex) {
	            response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	        } 
	        
	        return response;
	     }
	    
	    @POST
	    @Path("/followUp/{appealId}")
	    @Produces("application/vnd.cse564-appeals+xml")
	    public Response updateFollowUpAppeals() {
	        Response response;
	        
	        try {
	        	AppealRepresentation responseRepresentation = new UpdateAppealActivity().updateFollowUp(new RestbucksUri(uriInfo.getRequestUri()));
	            response = Response.status(Status.OK).entity(responseRepresentation).build();
	        } catch (NoSuchAppealException nsoe) {
	            response = Response.status(Status.NOT_FOUND).build();
	        } catch(UpdateException ue) {
	            response = Response.status(Status.CONFLICT).build();
	        } catch (Exception ex) {
	            response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	        } 
	        
	        return response;
	     }
	    
	    @DELETE
	    @Path("/{appealId}")
	    @Produces("application/vnd.cse564-appeals+xml")
	    public Response removeAppeals() {
	        Response response;
	        
	        try {
	        	AppealRepresentation removedOrder = new RemoveAppealActivity().delete(new RestbucksUri(uriInfo.getRequestUri()));
	            response = Response.status(Status.OK).entity(removedOrder).build();
	        } catch (NoSuchAppealException nsoe) {
	            response = Response.status(Status.NOT_FOUND).build();
	        } catch(AppealDeletionException ode) {
	            response = Response.status(Status.BAD_REQUEST).build();
	        } catch (Exception ex) {
	            response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	        }
	        
	        return response;
	    }
	    
}
