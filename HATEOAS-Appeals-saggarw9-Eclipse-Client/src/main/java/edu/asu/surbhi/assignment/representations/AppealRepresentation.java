package edu.asu.surbhi.assignment.representations;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.asu.surbhi.assignment.model.Appeal;
import edu.asu.surbhi.assignment.model.AppealStatus;
import edu.asu.surbhi.assignment.model.Items;

@XmlRootElement(name = "items", namespace = Representation.RESTBUCKS_NAMESPACE)
public class AppealRepresentation extends Representation implements
		Serializable {

	private static final long serialVersionUID = 1L;
	private List<Appeal> appeals;

	private String email;

	private AppealStatus status;

	/**
	 * For JAXB :-(
	 */

	public AppealRepresentation() {

	}

	public List<Appeal> getAppeals() {
		return appeals;
	}

	@XmlElement(name = "appeal", namespace = Representation.RESTBUCKS_NAMESPACE)
	public void setAppeals(List<Appeal> appeals) {
		this.appeals = appeals;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement(name = "email", namespace = Representation.RESTBUCKS_NAMESPACE)
	public void setEmail(String email) {
		this.email = email;
	}

	public AppealStatus getStatus() {
		return status;
	}

	@XmlElement(name = "status", namespace = Representation.RESTBUCKS_NAMESPACE)
	public void setStatus(AppealStatus status) {
		this.status = status;
	}

	public static AppealRepresentation fromXmlString(String xmlRepresentation) {

		AppealRepresentation orderRepresentation = null;
		try {
			JAXBContext context = JAXBContext
					.newInstance(AppealRepresentation.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			orderRepresentation = (AppealRepresentation) unmarshaller
					.unmarshal(new ByteArrayInputStream(xmlRepresentation
							.getBytes()));
		} catch (Exception e) {
			// throw new InvalidOrderException(e);
		}

		return orderRepresentation;
	}
    public static String toXmlString(AppealRepresentation appealrepresentation)
    {
    	StringWriter swt=new StringWriter();
    	JAXBContext context;
		try {
			context = JAXBContext.newInstance(AppealRepresentation.class);
		
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		m.marshal(appealrepresentation,swt);	
		return swt.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

    }
	public AppealRepresentation(Items items, Link... links) {

		try {
			this.appeals = items.getAppeals();
			this.email = items.getEmail();
			this.status = items.getStatus();
			this.links = java.util.Arrays.asList(links);
		} catch (Exception ex) {

		}

	}

	public AppealRepresentation(Items items) {

		try {
			this.appeals = items.getAppeals();
			this.email = items.getEmail();
			this.status = items.getStatus();
		} catch (Exception ex) {

		}

	}

	public Link getUpdateLink() {
		return getLinkByName(RELATIONS_URI + "update");
	}

	public Items getItems() {
		Items items = new Items(appeals, email, status);
		return items;
	}

	public Link getSelfLink() {
		return getLinkByName("self");
	}

	public Link getCancelLink() {
		return getLinkByName(RELATIONS_URI + "cancel");
	}
	public Link getSubmitLink() {
		return getLinkByName(RELATIONS_URI + "submit");
	}
	public Link getFollowUpLink() {
		return getLinkByName(RELATIONS_URI + "followUp");
	}

	public static AppealRepresentation createAppealRepresentation(Items items,
			RestbucksUri uri) {
		AppealRepresentation appealRepresentation;
		RestbucksUri submitUri = new RestbucksUri(uri.getBaseUri() + "/submit/"
				+ uri.getId().toString());
		RestbucksUri followUpUri = new RestbucksUri(uri.getBaseUri()
				+ "/followUp/" + uri.getId().toString());
		if (items.getStatus() == AppealStatus.PREPARING) {

			appealRepresentation = new AppealRepresentation(items, new Link(
					RELATIONS_URI + "cancel", uri), new Link(RELATIONS_URI
					+ "update", uri), new Link(Representation.RELATIONS_URI
					+ "submit", submitUri), new Link(
					Representation.RELATIONS_URI + "followUp", followUpUri),
					new Link(Representation.SELF_REL_VALUE, uri));
		} else if (items.getStatus() == AppealStatus.READY
				|| items.getStatus() == AppealStatus.FOLLOWUP) {

			appealRepresentation = new AppealRepresentation(items, new Link(
					Representation.SELF_REL_VALUE, uri));
		} else {
			appealRepresentation = new AppealRepresentation(items);
		}

		return appealRepresentation;

	}
}
