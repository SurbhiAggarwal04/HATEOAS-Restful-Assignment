package edu.asu.surbhi.assignment.services;

import edu.asu.surbhi.assignment.exceptions.AppealDeletionException;
import edu.asu.surbhi.assignment.exceptions.NoSuchAppealException;
import edu.asu.surbhi.assignment.model.AppealStatus;
import edu.asu.surbhi.assignment.model.Identifier;
import edu.asu.surbhi.assignment.model.Items;
import edu.asu.surbhi.assignment.repositories.AppealRepository;
import edu.asu.surbhi.assignment.representations.AppealRepresentation;
import edu.asu.surbhi.assignment.representations.RestbucksUri;

public class RemoveAppealActivity {
	   public AppealRepresentation delete(RestbucksUri orderUri) throws AppealDeletionException {
	        // Discover the URI of the order that has been cancelled
	        
	        Identifier identifier = orderUri.getId();

	        AppealRepository repository = AppealRepository.current();

	        if (!repository.appealPlaced(identifier)) {
	            throw new NoSuchAppealException();
	        }

	        Items items = repository.get(identifier);
            
	        // Can't delete a ready or preparing order
	        if ( items.getStatus() == AppealStatus.READY || items.getStatus() == AppealStatus.RESOLVED) {
	            throw new AppealDeletionException();
	        }
            repository.remove(identifier);
            items.setStatus(AppealStatus.ABANDONED);
	        return new AppealRepresentation(items);
	    }
}
