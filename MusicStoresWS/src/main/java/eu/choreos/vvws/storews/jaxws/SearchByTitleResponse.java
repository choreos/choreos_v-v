
package eu.choreos.vvws.storews.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "searchByTitleResponse", namespace = "http://storews.vvws.choreos.eu/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchByTitleResponse", namespace = "http://storews.vvws.choreos.eu/")
public class SearchByTitleResponse {

    @XmlElement(name = "return", namespace = "")
    private List<eu.choreos.vvws.common.CD> _return;

    /**
     * 
     * @return
     *     returns List<CD>
     */
    public List<eu.choreos.vvws.common.CD> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<eu.choreos.vvws.common.CD> _return) {
        this._return = _return;
    }

}
