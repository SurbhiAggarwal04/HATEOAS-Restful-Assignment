package edu.asu.surbhi.assignment.Main;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import edu.asu.surbhi.assignment.model.AppealStatus;
import edu.asu.surbhi.assignment.model.Identifier;
import edu.asu.surbhi.assignment.model.ItemBuilder;
import edu.asu.surbhi.assignment.model.Items;
import edu.asu.surbhi.assignment.representations.AppealRepresentation;
import edu.asu.surbhi.assignment.representations.Link;
import edu.asu.surbhi.assignment.representations.RestbucksUri;

/**
 * Hello world!
 *
 */
public class Main 
{
    private static final String RESTBUCKS_MEDIA_TYPE = "application/vnd.cse564-appeals+xml";
    private static final long ONE_MINUTE = 60000; 
    
    private static final String ENTRY_POINT_URI = "http://localhost:8080/HATEOAS-Appeals-saggarw9-Eclipse-Server/appeals";

    public static void main( String[] args )
    {
        URI serviceUri = null;
		try {
			serviceUri = new URI(ENTRY_POINT_URI);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
        	System.out.println("***********************************************************************************************");
        	System.out.println("Starting happy path test where a student submits an appeal and appeal goes to Preparing state with uri: "+serviceUri);
			happyPathTest(serviceUri);
        	System.out.println("***********************************************************************************************");
        	System.out.println("Starting abandoned case test where a student creates an appeal and abandon it with uri: "+serviceUri);
            abandonPathTest(serviceUri);
        	System.out.println("***********************************************************************************************");
        	System.out.println("Starting forgotten case test where a student creates an appeal and does a follow-up on the appeal with uri: "+serviceUri);
            forgottenPathTest(serviceUri);
           	Link badLink=new Link("badLink",new RestbucksUri(serviceUri+"/badLink"), RESTBUCKS_MEDIA_TYPE);
        	System.out.println("***********************************************************************************************");
        	System.out.println("Starting Bad Start case test where a student tries to send appeals with wrong uri: "+badLink.toString());
			badStartPathTest(badLink.getUri());
        	System.out.println("***********************************************************************************************");
        	System.out.println("Starting Bad Id case test where a student creates an appeal and does a follow-up on the appeal with the wrong id");
            badIdPathTest(serviceUri);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    private static void badIdPathTest(URI serviceUri) throws Exception
    {
      	System.out.println("STEP-1: Creating Appeals to send to Professor");
    	Items items=ItemBuilder.itemBuilder().withRandomItems().build();
        System.out.println("Appeals created\n"+items);
        Client client = Client.create();
        System.out.println("Client created");
        AppealRepresentation appealRepresentation=null;
        ClientResponse response = client.resource(serviceUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(ClientResponse.class, items);
        if(response.getStatus()==201)
        {
            appealRepresentation=response.getEntity(AppealRepresentation.class);
            System.out.println("Appeals sent to the Professor");
            
            System.out.println("Access, update or abandon appeals using the uri: "+appealRepresentation.getSelfLink().getUri().toString());
            System.out.println("\n\nSTEP-2: Accessing appeals created");
            response=client.resource(appealRepresentation.getSelfLink().getUri().toString()).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).get(ClientResponse.class);
            if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
            	System.out.println("Appeals not found with uri: "+appealRepresentation.getSelfLink().getUri().toString());
            else if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            	System.out.println("Some error occurred");
            else
            {
            appealRepresentation=response.getEntity(AppealRepresentation.class);
            System.out.println("Appeals:\n");
            if(AppealRepresentation.toXmlString(appealRepresentation)!=null)
            System.out.println(AppealRepresentation.toXmlString(appealRepresentation));
            }    
            
            System.out.println("\n\nSTEP-3: Submitting appeals which will change the status from PREPARING TO READY with uri: "+appealRepresentation.getSubmitLink().getUri().toString());
            response=client.resource(appealRepresentation.getSubmitLink().getUri().toString()).accept(appealRepresentation.getSubmitLink().getMediaType()).type(appealRepresentation.getSubmitLink().getMediaType()).post(ClientResponse.class);
            if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            	System.out.println("Some error occurred");
            else if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
            	System.out.println("Appeals not found with uri: "+appealRepresentation.getSubmitLink().getUri().toString());
            else if(response.getStatus()==ClientResponse.Status.CONFLICT.getStatusCode())
            	System.out.println("Appeals cannot be updated with uri: "+appealRepresentation.getSubmitLink().getUri().toString());
            else
            {
            	 appealRepresentation=response.getEntity(AppealRepresentation.class);
                 System.out.println("Appeals:\n");
                 if(AppealRepresentation.toXmlString(appealRepresentation)!=null)
                 System.out.println(AppealRepresentation.toXmlString(appealRepresentation));
                        
            }
            
            System.out.println("\n\nSTEP-4:Follow-up on appeals can be done using the uri: "+appealRepresentation.getFollowUpLink().getUri().toString());
            System.out.println("However, user uses the wrong uri");
            String path=appealRepresentation.getFollowUpLink().getUri().toString();
            path=path.substring(0,path.lastIndexOf("/")+1);
            path+="fgiujklnbfdsrtgtuilkhj";
            System.out.println(path);
            response=client.resource(path).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(ClientResponse.class);
            if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            	System.out.println("Some error occurred");
            else if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
            	System.out.println("Result: Appeals not found with uri: "+appealRepresentation.getSelfLink().getUri().toString());
            else if(response.getStatus()==ClientResponse.Status.CONFLICT.getStatusCode())
            	System.out.println("Follow-up not possible on the appeals as it is already resolved with uri: "+appealRepresentation.getSelfLink().getUri().toString());
            else
            {
            	 appealRepresentation=response.getEntity(AppealRepresentation.class);
                 System.out.println("Follow-up on the following appeals is done:\n");
                 if(AppealRepresentation.toXmlString(appealRepresentation)!=null)
                 System.out.println(AppealRepresentation.toXmlString(appealRepresentation));
            }
        }
        else
        {
        	System.out.println("Some error occurred");
        }
        
        
    }
    private static void badStartPathTest(URI serviceUri) throws Exception
    {
    	System.out.println("STEP-1: Creating Appeals to send to Professor");
    	Items items=ItemBuilder.itemBuilder().withRandomItems().build();
        System.out.println("Appeals created\n"+items);
        Client client = Client.create();
        System.out.println("Client created");
        AppealRepresentation appealRepresentation=null;	
        ClientResponse response = client.resource(serviceUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(ClientResponse.class, items);
        if(response.getStatus()==404)
        {
        	System.out.println("Server cannot be reached with the uri: "+serviceUri.toString());
        	System.out.println("Hence, appeals could not be send to the Professor");
        }

    }
    private static void forgottenPathTest(URI serviceUri) throws Exception
    {
       	System.out.println("STEP-1: Creating Appeals to send to Professor");
    	Items items=ItemBuilder.itemBuilder().withRandomItems().build();
        System.out.println("Appeals created\n"+items);
        Client client = Client.create();
        System.out.println("Client created");
        AppealRepresentation appealRepresentation=null;
        ClientResponse response = client.resource(serviceUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(ClientResponse.class, items);
        if(response.getStatus()==201)
        {
            appealRepresentation=response.getEntity(AppealRepresentation.class);
            System.out.println("Appeals sent to the Professor");
            System.out.println("Access, update or abandon appeals using the uri: "+appealRepresentation.getSelfLink().getUri().toString());
            System.out.println("\n\nSTEP-2: Accessing appeals created");
            response=client.resource(appealRepresentation.getSelfLink().getUri().toString()).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).get(ClientResponse.class);
            if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
            	System.out.println("Appeals not found with uri: "+appealRepresentation.getSelfLink().getUri().toString());
            else if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            	System.out.println("Some error occurred");
            else
            {
            appealRepresentation=response.getEntity(AppealRepresentation.class);
            System.out.println("Appeals:\n");
            if(AppealRepresentation.toXmlString(appealRepresentation)!=null)
            System.out.println(AppealRepresentation.toXmlString(appealRepresentation));
            }    
            
            System.out.println("\n\nSTEP-3: Submitting appeals which will change the status from PREPARING TO READY with uri: "+appealRepresentation.getSubmitLink().getUri().toString());
            response=client.resource(appealRepresentation.getSubmitLink().getUri().toString()).accept(appealRepresentation.getSubmitLink().getMediaType()).type(appealRepresentation.getSubmitLink().getMediaType()).post(ClientResponse.class);
            if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            	System.out.println("Some error occurred");
            else if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
            	System.out.println("Appeals not found with uri: "+appealRepresentation.getSubmitLink().getUri().toString());
            else if(response.getStatus()==ClientResponse.Status.CONFLICT.getStatusCode())
            	System.out.println("Appeals cannot be updated with uri: "+appealRepresentation.getSubmitLink().getUri().toString());
            else
            {
            	 appealRepresentation=response.getEntity(AppealRepresentation.class);
                 System.out.println("Appeals:\n");
                 if(AppealRepresentation.toXmlString(appealRepresentation)!=null)
                 System.out.println(AppealRepresentation.toXmlString(appealRepresentation));
                        
            }
            
            System.out.println("\n\nSTEP-4: Follow-up on appeals can be done using the uri: "+appealRepresentation.getFollowUpLink().getUri().toString());
            response=client.resource(appealRepresentation.getFollowUpLink().getUri().toString()).accept(appealRepresentation.getFollowUpLink().getMediaType()).type(appealRepresentation.getFollowUpLink().getMediaType()).post(ClientResponse.class);
            if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            	System.out.println("Some error occurred");
            else if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
            	System.out.println("Appeals not found with uri: "+appealRepresentation.getSelfLink().getUri().toString());
            else if(response.getStatus()==ClientResponse.Status.CONFLICT.getStatusCode())
            	System.out.println("Follow-up not possible on the appeals as it is already resolved with uri: "+appealRepresentation.getSelfLink().getUri().toString());
            else
            {
            	 appealRepresentation=response.getEntity(AppealRepresentation.class);
                 System.out.println("Follow-up on the following appeals is done:\n");
                 if(AppealRepresentation.toXmlString(appealRepresentation)!=null)
                 System.out.println(AppealRepresentation.toXmlString(appealRepresentation));
            }
        }
        else
        {
        	System.out.println("Some error occurred");
        }
        
        
    }
    private static void abandonPathTest(URI serviceUri) throws Exception
    {
    	System.out.println("STEP-1: Creating Appeals to send to Professor");
    	Items items=ItemBuilder.itemBuilder().withRandomItems().build();
        System.out.println("Appeals created\n"+items);
        Client client = Client.create();
        System.out.println("Client created");
        AppealRepresentation appealRepresentation=null;
        ClientResponse response = client.resource(serviceUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(ClientResponse.class, items);
        if(response.getStatus()==201)
        {
            appealRepresentation=response.getEntity(AppealRepresentation.class);
            System.out.println("Appeals sent to the Professor");
            
            System.out.println("Access, update or abandon appeals using the uri: "+appealRepresentation.getSelfLink().getUri().toString());
            System.out.println("\n\nSTEP-2: Accessing appeals created");
            response=client.resource(appealRepresentation.getSelfLink().getUri().toString()).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).get(ClientResponse.class);
            if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
            	System.out.println("Appeals not found with uri: "+appealRepresentation.getSelfLink().getUri().toString());
            else if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            	System.out.println("Some error occurred");
            else
            {
            appealRepresentation=response.getEntity(AppealRepresentation.class);
            System.out.println("Appeals:\n");
            if(AppealRepresentation.toXmlString(appealRepresentation)!=null)
            System.out.println(AppealRepresentation.toXmlString(appealRepresentation));
            }    

            System.out.println("Appeals can be abandoned using the uri: "+appealRepresentation.getCancelLink().getUri().toString());
            
            System.out.println("\n\nSTEP-3: Appeals created above are abandoned by the student [Appeals can be abadoned before they are submitted, that is, when they are in PREPARING stage]  with uri: "+appealRepresentation.getSelfLink().getUri().toString());
            response=client.resource(appealRepresentation.getCancelLink().getUri().toString()).accept(appealRepresentation.getCancelLink().getMediaType()).type(appealRepresentation.getCancelLink().getMediaType()).delete(ClientResponse.class);
            if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            	System.out.println("Some error occurred");
            else if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
            	System.out.println("Appeals not found with uri: "+appealRepresentation.getSelfLink().getUri().toString());
            else if(response.getStatus()==ClientResponse.Status.BAD_REQUEST.getStatusCode())
            	System.out.println("Appeals cannot be deleted as it is already submitted or resolved with uri: "+appealRepresentation.getSelfLink().getUri().toString());
            else
            {
            	 appealRepresentation=response.getEntity(AppealRepresentation.class);
                 System.out.println("Abandoned Appeals:\n");
                 if(AppealRepresentation.toXmlString(appealRepresentation)!=null)
                 System.out.println(AppealRepresentation.toXmlString(appealRepresentation));
            }
        }
        else
        {
        	System.out.println("Some error occurred");
        }
        
        
        
    }
    private static void happyPathTest(URI serviceUri) throws Exception {
    	System.out.println("STEP-1: Creating Appeals to send to Professor");
    	Items items=ItemBuilder.itemBuilder().withRandomItems().build();
        System.out.println("Appeals created\n"+items);
        Client client = Client.create();
        System.out.println("Client created");
        AppealRepresentation appealRepresentation=null;
        ClientResponse response = client.resource(serviceUri).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).post(ClientResponse.class, items);
        if(response.getStatus()==201)
        {
        appealRepresentation=response.getEntity(AppealRepresentation.class);
        System.out.println("Appeals sent to the Professor");
        System.out.println("Access, update or abandon appeals using the uri: "+appealRepresentation.getSelfLink().getUri().toString());
        
        
        System.out.println("\n\nSTEP-2: Accessing appeals created");
        response=client.resource(appealRepresentation.getSelfLink().getUri().toString()).accept(RESTBUCKS_MEDIA_TYPE).type(RESTBUCKS_MEDIA_TYPE).get(ClientResponse.class);
        if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
        	System.out.println("Appeals not found with uri: "+appealRepresentation.getSelfLink().getUri().toString());
        else if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
        	System.out.println("Some error occurred");
        else
        {
        appealRepresentation=response.getEntity(AppealRepresentation.class);
        System.out.println("Appeals:\n");
        if(AppealRepresentation.toXmlString(appealRepresentation)!=null)
        System.out.println(AppealRepresentation.toXmlString(appealRepresentation));
        }        
        
        System.out.println("\n\nSTEP-3 Updating appeals with uri: "+appealRepresentation.getUpdateLink().getUri().toString());
       	System.out.println("Updating Appeals to send to Professor");
    	items=ItemBuilder.itemBuilder().withRandomItems().build(items);
    	System.out.println("Updated Appeals\n"+items);
        response=client.resource(appealRepresentation.getUpdateLink().getUri().toString()).accept(appealRepresentation.getUpdateLink().getMediaType()).type(appealRepresentation.getUpdateLink().getMediaType()).post(ClientResponse.class, items);
        if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
        	System.out.println("Some error occurred");
        else if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
        	System.out.println("Appeals not found with uri: "+appealRepresentation.getSelfLink().getUri().toString());
        else if(response.getStatus()==ClientResponse.Status.CONFLICT.getStatusCode())
        	System.out.println("Appeals cannot be updated with uri: "+appealRepresentation.getSelfLink().getUri().toString());
        else
        {
            appealRepresentation=response.getEntity(AppealRepresentation.class);
            System.out.println("Updated Appeals sent to the Professor");
            System.out.println("Access, update or abandon appeals using the uri: "+appealRepresentation.getSelfLink().getUri().toString());
        }
        
        System.out.println("\n\nSTEP-4: Submitting appeals which will change the status from PREPARING TO READY with uri: "+appealRepresentation.getSubmitLink().getUri().toString());
        response=client.resource(appealRepresentation.getSubmitLink().getUri().toString()).accept(appealRepresentation.getSubmitLink().getMediaType()).type(appealRepresentation.getSubmitLink().getMediaType()).post(ClientResponse.class);
        if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
        	System.out.println("Some error occurred");
        else if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
        	System.out.println("Appeals not found with uri: "+appealRepresentation.getSubmitLink().getUri().toString());
        else if(response.getStatus()==ClientResponse.Status.CONFLICT.getStatusCode())
        	System.out.println("Appeals cannot be updated with uri: "+appealRepresentation.getSubmitLink().getUri().toString());
        else
        {
        	 appealRepresentation=response.getEntity(AppealRepresentation.class);
             System.out.println("Appeals:\n");
             if(AppealRepresentation.toXmlString(appealRepresentation)!=null)
             System.out.println(AppealRepresentation.toXmlString(appealRepresentation));
                    
        }
        
        if(appealRepresentation.getStatus().name().equalsIgnoreCase(AppealStatus.READY.name()))
        {
        	System.out.println("\n\nSTEP-5: Getting the response from the Professor on submitted appeals");
        	String path = appealRepresentation.getSelfLink().getUri().toString();
            String id=path.substring(path.lastIndexOf("/") + 1, path.length());
            response=client.resource(serviceUri+"/resolved/"+id).accept(appealRepresentation.getSelfLink().getMediaType()).type(appealRepresentation.getSelfLink().getMediaType()).get(ClientResponse.class);
            if(response.getStatus()==ClientResponse.Status.NOT_FOUND.getStatusCode())
            	System.out.println("Appeals not found with uri: "+appealRepresentation.getSelfLink().getUri().toString());
            else if(response.getStatus()==ClientResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode())
            	System.out.println("Some error occurred");
            else
            {
            appealRepresentation=response.getEntity(AppealRepresentation.class);
            System.out.println("Appeals:\n");
            if(AppealRepresentation.toXmlString(appealRepresentation)!=null)
            System.out.println(AppealRepresentation.toXmlString(appealRepresentation));
            }      
        	
        }
        else
        {
        	System.out.println("Appeals are not resolved from the Professor side as they are not yet submitted by the student");
        	
        }
        
        }
        else
        {
        	System.out.println("Some error occurred");
        }
        
        
        
        
        
    }
}
