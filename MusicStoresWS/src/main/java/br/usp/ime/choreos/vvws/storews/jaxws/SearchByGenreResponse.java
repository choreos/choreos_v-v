
package br.usp.ime.choreos.vvws.storews.jaxws;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "searchByGenreResponse", namespace = "http://storews.vvws.choreos.ime.usp.br/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchByGenreResponse", namespace = "http://storews.vvws.choreos.ime.usp.br/")
public class SearchByGenreResponse {

    @XmlElement(name = "return", namespace = "")
    private List<br.usp.ime.choreos.vvws.common.CD> _return;

    /**
     * 
     * @return
     *     returns List<CD>
     */
    public List<br.usp.ime.choreos.vvws.common.CD> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(List<br.usp.ime.choreos.vvws.common.CD> _return) {
        this._return = _return;
    }

}
