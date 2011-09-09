
package eu.choreos.vvws.storews.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "purchase", namespace = "http://storews.vvws.choreos.eu/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "purchase", namespace = "http://storews.vvws.choreos.eu/", propOrder = {
    "arg0",
    "arg1"
})
public class Purchase {

    @XmlElement(name = "arg0", namespace = "")
    private eu.choreos.vvws.common.CD arg0;
    @XmlElement(name = "arg1", namespace = "")
    private eu.choreos.vvws.common.Customer arg1;

    /**
     * 
     * @return
     *     returns CD
     */
    public eu.choreos.vvws.common.CD getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(eu.choreos.vvws.common.CD arg0) {
        this.arg0 = arg0;
    }

    /**
     * 
     * @return
     *     returns Customer
     */
    public eu.choreos.vvws.common.Customer getArg1() {
        return this.arg1;
    }

    /**
     * 
     * @param arg1
     *     the value for the arg1 property
     */
    public void setArg1(eu.choreos.vvws.common.Customer arg1) {
        this.arg1 = arg1;
    }

}
