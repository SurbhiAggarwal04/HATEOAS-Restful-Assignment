package edu.asu.surbhi.assignment.representations;

import java.net.URI;
import java.net.URISyntaxException;

import edu.asu.surbhi.assignment.model.Identifier;

public class RestbucksUri {
    private URI uri;
    
    public RestbucksUri(String uri) {
        try {
            this.uri = new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    
    public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public RestbucksUri(URI uri) {
        this(uri.toString());
    }

    public RestbucksUri(URI uri, Identifier identifier) {
        this(uri.toString() + "/" + identifier.toString());
    }

    public Identifier getId() {
        String path = uri.getPath();
        return new Identifier(path.substring(path.lastIndexOf("/") + 1, path.length()));
    }
    public String getRestPath()
    {
    	String path = uri.toString();
    	String restPath=path.substring(0,path.lastIndexOf("/"));
    	return restPath;
    }
    public URI getFullUri() {
        return uri;
    }
    
    @Override
    public String toString() {
        return uri.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RestbucksUri) {
            return ((RestbucksUri)obj).uri.equals(uri);
        }
        return false;
    }

    public String getBaseUri() {
       
        String uriString = uri.toString();
        return uriString;
    }
}
