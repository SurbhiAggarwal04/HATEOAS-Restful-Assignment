package edu.asu.surbhi.assignment.representations;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = Representation.DAP_NAMESPACE)
public class Link implements Serializable{
	private static final long serialVersionUID = 1L;
	private String rel;

	private URI uri;

	private String mediaType;

	/**
	 * For JAXB :-(
	 */

	Link() {

	}

	public String getRel() {
		return rel;
	}

	@XmlAttribute(name = "rel")
	public void setRel(String rel) {
		this.rel = rel;
	}

	@XmlAttribute(name = "uri")
	public void setUri(URI uri) {
		this.uri = uri;
	}

	@XmlAttribute(name = "mediaType")
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public Link(String name, RestbucksUri uri, String mediaType) {

		this.rel = name;
		this.uri = uri.getUri();
		this.mediaType = mediaType;

	}

	public Link(String name, RestbucksUri uri) {
		this(name, uri, Representation.RESTBUCKS_MEDIA_TYPE);
	}

	public String getRelValue() {
		return rel;
	}

	public URI getUri() {
			return uri;
	}

	public String getMediaType() {
		return mediaType;
	}
}
