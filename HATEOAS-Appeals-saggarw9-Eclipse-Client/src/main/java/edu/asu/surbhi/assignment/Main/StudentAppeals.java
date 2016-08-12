package edu.asu.surbhi.assignment.Main;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.bind.v2.runtime.Location;

import edu.asu.surbhi.assignment.model.Appeal;
import edu.asu.surbhi.assignment.model.AppealStatus;
import edu.asu.surbhi.assignment.model.Items;
import edu.asu.surbhi.assignment.representations.Representation;

@XmlRootElement(name = "items", namespace = Representation.RESTBUCKS_NAMESPACE)
public class StudentAppeals {
    
    private static final Logger LOG = LoggerFactory.getLogger(StudentAppeals.class);
    
	@XmlElement(name = "appeals", namespace = Representation.RESTBUCKS_NAMESPACE)
	private List<Appeal> appeals;
	@XmlElement(name = "email", namespace = Representation.RESTBUCKS_NAMESPACE)
	private String email;
	@XmlElement(name = "status", namespace = Representation.RESTBUCKS_NAMESPACE)
	private AppealStatus status;
    
    private StudentAppeals(){}
    
    public StudentAppeals(Items items) {
        this.appeals=items.getAppeals();
        this.status=items.getStatus();
        this.email=items.getEmail();
    }
   
}