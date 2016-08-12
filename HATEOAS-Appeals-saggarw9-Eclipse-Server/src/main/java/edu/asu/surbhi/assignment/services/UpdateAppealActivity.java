package edu.asu.surbhi.assignment.services;

import edu.asu.surbhi.assignment.exceptions.NoSuchAppealException;
import edu.asu.surbhi.assignment.exceptions.UpdateException;
import edu.asu.surbhi.assignment.model.AppealStatus;
import edu.asu.surbhi.assignment.model.Identifier;
import edu.asu.surbhi.assignment.model.Items;
import edu.asu.surbhi.assignment.repositories.AppealRepository;
import edu.asu.surbhi.assignment.representations.AppealRepresentation;
import edu.asu.surbhi.assignment.representations.RestbucksUri;

public class UpdateAppealActivity {
    public AppealRepresentation update(Items items, RestbucksUri appealUri) {
        Identifier appealIdentifier = appealUri.getId();

        AppealRepository repository = AppealRepository.current();
        if (!AppealRepository.current().appealPlaced(appealIdentifier)) { 
            throw new NoSuchAppealException();
        }

        if (!appealCanBeChanged(appealIdentifier)) {
            throw new UpdateException();
        }

        repository.remove(appealIdentifier);
        repository.store(appealIdentifier,items);
        
        return AppealRepresentation.createAppealRepresentation(items, appealUri); 
    }
    public AppealRepresentation updateReady(RestbucksUri appealUri) {
        Identifier appealIdentifier = appealUri.getId();

        AppealRepository repository = AppealRepository.current();
        if (!AppealRepository.current().appealPlaced(appealIdentifier)) { 
            throw new NoSuchAppealException();
        }

        if (!appealCanBeChanged(appealIdentifier)) {
            throw new UpdateException();
        }
        Items storedOrder = repository.get(appealIdentifier);
        storedOrder.setStatus(AppealStatus.READY);
        repository.remove(appealIdentifier);
        repository.store(appealIdentifier,storedOrder);
        return AppealRepresentation.createAppealRepresentation(storedOrder, appealUri); 
    }

    public AppealRepresentation updateFollowUp(RestbucksUri appealUri) {
        Identifier appealIdentifier = appealUri.getId();

        AppealRepository repository = AppealRepository.current();
        if (!AppealRepository.current().appealPlaced(appealIdentifier)) { 
            throw new NoSuchAppealException();
        }

        if (!followUpAppealCanBeChanged(appealIdentifier)) {
            throw new UpdateException();
        }
        Items storedOrder = repository.get(appealIdentifier);
        storedOrder.setStatus(AppealStatus.FOLLOWUP);
        repository.remove(appealIdentifier);
        repository.store(appealIdentifier,storedOrder);
        return AppealRepresentation.createAppealRepresentation(storedOrder, appealUri); 
    }
    private boolean followUpAppealCanBeChanged(Identifier identifier) {
        return AppealRepository.current().get(identifier).getStatus() == AppealStatus.PREPARING || AppealRepository.current().get(identifier).getStatus() == AppealStatus.READY;
    }
    private boolean appealCanBeChanged(Identifier identifier) {
        return AppealRepository.current().get(identifier).getStatus() == AppealStatus.PREPARING;
    }
}
