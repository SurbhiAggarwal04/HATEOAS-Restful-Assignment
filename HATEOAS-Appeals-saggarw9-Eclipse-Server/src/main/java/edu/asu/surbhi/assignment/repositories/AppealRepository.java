package edu.asu.surbhi.assignment.repositories;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.surbhi.assignment.model.Identifier;
import edu.asu.surbhi.assignment.model.Items;

public class AppealRepository {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealRepository.class);

    private static final AppealRepository theRepository = new AppealRepository();
    private HashMap<String, Items> backingStore = new HashMap<String, Items>(); // Default implementation, not suitable for production!

    public static AppealRepository current() {
        return theRepository;
    }
    private AppealRepository(){
      
    }
    public Items get(Identifier identifier) {
        return backingStore.get(identifier.toString());
     }
    

    public Identifier store(Items items) {

        Identifier id = new Identifier();
        backingStore.put(id.toString(), items);
        return id;
    }
    
    public void store(Identifier identifier, Items items) {
        backingStore.put(identifier.toString(), items);
    }

    public boolean has(Identifier identifier) {
        boolean result =  backingStore.containsKey(identifier.toString());
        return result;
    }

    public void remove(Identifier identifier) {
        LOG.debug("Removing from storage the Order object with id", identifier);
        backingStore.remove(identifier.toString());
    }
    

    

    public synchronized void clear() {
        backingStore = new HashMap<String, Items>();
    }

    public int size() {
        return backingStore.size();
    }
    
    public boolean appealPlaced(Identifier identifier) {
        return AppealRepository.current().has(identifier);
    }
}
