package edu.asu.surbhi.assignment.representations;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public abstract class Representation implements Serializable{
	private static final long serialVersionUID = 1L;
    public static final String RELATIONS_URI = "http://relations.restbucks.com/";
    public static final String RESTBUCKS_NAMESPACE = "http://schemas.restbucks.com";
    public static final String DAP_NAMESPACE = RESTBUCKS_NAMESPACE + "/dap";
    public static final String RESTBUCKS_MEDIA_TYPE = "application/vnd.cse564-appeals+xml";
    public static final String SELF_REL_VALUE = "self";


    protected List<Link> links;

    public List<Link> getLinks() {
		return links;
	}
    @XmlElement(name = "link", namespace = DAP_NAMESPACE)
	public void setLinks(List<Link> links) {
		this.links = links;
	}

	protected Link getLinkByName(String uriName) {
        if (links == null) {
            return null;
        }

        for (Link l : links) {
        	
            if (l.getRelValue().toLowerCase().equals(uriName.toLowerCase())) {
            	 return l;
            }
          
        }
        return null;
    }
}
