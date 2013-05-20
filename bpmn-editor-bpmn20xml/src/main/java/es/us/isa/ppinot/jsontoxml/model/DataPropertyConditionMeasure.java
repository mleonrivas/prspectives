package es.us.isa.ppinot.jsontoxml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//import org.oryxeditor.server.diagram.Shape;
//import org.oryxeditor.server.diagram.StencilType;


/**
 * <p>Java class for tTask complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tTask">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.omg.org/bpmn20}tActivity">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "dataPropertyConditionMeasure", namespace="http://www.isa.us.es/ppinot")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDataPropertyConditionMeasure")
public class DataPropertyConditionMeasure  
    extends CountMeasure
{
    
    @XmlAttribute
    protected String statesconsidered;

	/**
	 * Default constructor
	 */
	public DataPropertyConditionMeasure() {
		
	}
	
	public String getStatesconsidered() {
		return statesconsidered;
	}

	public void setStatesconsidered(String statesconsidered) {
		this.statesconsidered = statesconsidered;
	}

}
