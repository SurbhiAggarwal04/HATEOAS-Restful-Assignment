package edu.asu.surbhi.assignment.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.asu.surbhi.assignment.representations.Representation;

@XmlRootElement
public class Appeal implements Serializable{
	private static final long serialVersionUID = 1L;
 //   @XmlElement(namespace = Representation.RESTBUCKS_NAMESPACE)
    private String subject;
//    @XmlElement(namespace = Representation.RESTBUCKS_NAMESPACE)

    private String content;
 //   @XmlElement(namespace = Representation.RESTBUCKS_NAMESPACE)

   // private AppealStatus status;    
    public Appeal()
    {
    	
    }
    
    public Appeal(String id, String subject, String content,AppealStatus status)
    {
    	this.subject=subject;
    	this.content=content;
    	//this.status=status;
    }
    
/*	public AppealStatus getStatus() {
		return status;
	}*/
	/*@XmlElement(name = "appeal_status", namespace = Representation.RESTBUCKS_NAMESPACE)
	public void setStatus(AppealStatus status) {
		this.status = status;
	}*/


	public String getSubject() {
		return subject;
	}
	@XmlElement(name = "appeal_subject", namespace = Representation.RESTBUCKS_NAMESPACE)
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	@XmlElement(name = "appeal_content", namespace = Representation.RESTBUCKS_NAMESPACE)
	public void setContent(String content) {
		this.content = content;
	}
	
	public String toString()
	{
		String result="SUBJECT:"+this.subject +",CONTENT:"+this.content;
		return result;
		
	}
    
    /**
     * For JAXB :-(
     */
 
}