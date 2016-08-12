package edu.asu.surbhi.assignment.model;

import javax.xml.bind.annotation.XmlEnumValue;


public enum AppealStatus {
    @XmlEnumValue(value="resolved")
    RESOLVED,
    @XmlEnumValue(value="preparing")
    PREPARING, 
    @XmlEnumValue(value="ready")
    READY, 
    @XmlEnumValue(value="abandoned")
    ABANDONED,
    @XmlEnumValue(value="followUp")
    FOLLOWUP
  
}
