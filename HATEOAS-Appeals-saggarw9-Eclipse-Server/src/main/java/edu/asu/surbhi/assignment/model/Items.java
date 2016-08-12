package edu.asu.surbhi.assignment.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.asu.surbhi.assignment.representations.Representation;
@XmlRootElement(name = "items", namespace = Representation.RESTBUCKS_NAMESPACE)
public class Items {
	 
	    private List<Appeal> appeals;
	  
	    private String email;
	   
	    private AppealStatus status=AppealStatus.PREPARING;
	    
	    public Items()
	    {
	    	
	    }
	    public Items(List<Appeal> appeals,String email,AppealStatus status)
	    {
	    	this.appeals=appeals;
	    	this.email=email;
	    	this.status=status;
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
	    public String toString()
	    {
	    	String result="";
	    	for(Appeal appeal:this.appeals )
	    	{
	    		result=result+"\n"+appeal;
	    	}
	    	result=result+"\n"+this.email;
	    	result+="\n"+this.status;
			return result;
	    	
	    }
	    

}
