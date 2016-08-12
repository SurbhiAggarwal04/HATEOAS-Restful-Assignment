package edu.asu.surbhi.assignment.services;

import java.net.URI;

import edu.asu.surbhi.assignment.model.AppealStatus;
import edu.asu.surbhi.assignment.model.Identifier;
import edu.asu.surbhi.assignment.model.Items;
import edu.asu.surbhi.assignment.repositories.AppealRepository;
import edu.asu.surbhi.assignment.representations.AppealRepresentation;
import edu.asu.surbhi.assignment.representations.Link;
import edu.asu.surbhi.assignment.representations.Representation;
import edu.asu.surbhi.assignment.representations.RestbucksUri;

public class CreateAppealActivity {
    public AppealRepresentation create(Items items, RestbucksUri requestUri) {
    	
        items.setStatus(AppealStatus.PREPARING);
    
        Identifier identifier = AppealRepository.current().store(items);
        RestbucksUri appealUri = new RestbucksUri(requestUri.getBaseUri() + "/" + identifier.toString());
        RestbucksUri submitUri = new RestbucksUri(requestUri.getBaseUri() + "/submit/" + identifier.toString());
        RestbucksUri followUpUri = new RestbucksUri(requestUri.getBaseUri() + "/followUp/" + identifier.toString());

        AppealRepresentation result= new AppealRepresentation(items, 
                new Link(Representation.RELATIONS_URI + "cancel", appealUri), 
                new Link(Representation.RELATIONS_URI + "update", appealUri),
                new Link(Representation.RELATIONS_URI + "submit", submitUri),
                new Link(Representation.RELATIONS_URI + "followUp", followUpUri),
                new Link(Representation.SELF_REL_VALUE, appealUri));
        return result;
    }
}
