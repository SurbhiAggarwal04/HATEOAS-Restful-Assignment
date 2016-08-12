package edu.asu.surbhi.assignment.model;

public class Identifier {
    private final String identifier;

    public Identifier(String identifier) {
        this.identifier = identifier;
    }
    
    public Identifier() {
        this(java.util.UUID.randomUUID().toString());
    }

    public String toString() {
        return identifier;
    }
}
