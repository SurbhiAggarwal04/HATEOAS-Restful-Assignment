package edu.asu.surbhi.assignment.services;

import edu.asu.surbhi.assignment.exceptions.NoSuchAppealException;
import edu.asu.surbhi.assignment.model.AppealStatus;
import edu.asu.surbhi.assignment.model.Identifier;
import edu.asu.surbhi.assignment.model.Items;
import edu.asu.surbhi.assignment.repositories.AppealRepository;
import edu.asu.surbhi.assignment.representations.AppealRepresentation;
import edu.asu.surbhi.assignment.representations.RestbucksUri;

public class ReadAppealActivity {
	   public AppealRepresentation retrieveByUri(RestbucksUri orderUri) {
	        Identifier identifier  = orderUri.getId();
	        
	        Items items = AppealRepository.current().get(identifier);
	        
	        if(items == null) {
	            throw new NoSuchAppealException();
	        }
	        
	        return AppealRepresentation.createAppealRepresentation(items, orderUri);
	    }
	 
	   public AppealRepresentation retrievResolvedeByUri(RestbucksUri orderUri) {
	        Identifier identifier  = orderUri.getId();
	        Items items = AppealRepository.current().get(identifier);
	        if(items == null) {
	            throw new NoSuchAppealException();
	        }
	        AppealRepository.current().remove(identifier);
	        items.setStatus(AppealStatus.RESOLVED);
	        AppealRepository.current().store(identifier, items);
	       
	        
	        return AppealRepresentation.createAppealRepresentation(items, orderUri);
	    }
}
