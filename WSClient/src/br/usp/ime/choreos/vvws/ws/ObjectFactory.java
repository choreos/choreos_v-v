
package br.usp.ime.choreos.vvws.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.usp.ime.choreos.vvws.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SearchByGenreResponse_QNAME = new QName("http://ws.vvws.choreos.ime.usp.br/", "searchByGenreResponse");
    private final static QName _SearchByTitleResponse_QNAME = new QName("http://ws.vvws.choreos.ime.usp.br/", "searchByTitleResponse");
    private final static QName _SearchByGenre_QNAME = new QName("http://ws.vvws.choreos.ime.usp.br/", "searchByGenre");
    private final static QName _Purchase_QNAME = new QName("http://ws.vvws.choreos.ime.usp.br/", "purchase");
    private final static QName _SearchByArtist_QNAME = new QName("http://ws.vvws.choreos.ime.usp.br/", "searchByArtist");
    private final static QName _PurchaseResponse_QNAME = new QName("http://ws.vvws.choreos.ime.usp.br/", "purchaseResponse");
    private final static QName _SearchByArtistResponse_QNAME = new QName("http://ws.vvws.choreos.ime.usp.br/", "searchByArtistResponse");
    private final static QName _SearchByTitle_QNAME = new QName("http://ws.vvws.choreos.ime.usp.br/", "searchByTitle");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.usp.ime.choreos.vvws.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchByArtistResponse }
     * 
     */
    public SearchByArtistResponse createSearchByArtistResponse() {
        return new SearchByArtistResponse();
    }

    /**
     * Create an instance of {@link SearchByTitle }
     * 
     */
    public SearchByTitle createSearchByTitle() {
        return new SearchByTitle();
    }

    /**
     * Create an instance of {@link SearchByGenre }
     * 
     */
    public SearchByGenre createSearchByGenre() {
        return new SearchByGenre();
    }

    /**
     * Create an instance of {@link SearchByGenreResponse }
     * 
     */
    public SearchByGenreResponse createSearchByGenreResponse() {
        return new SearchByGenreResponse();
    }

    /**
     * Create an instance of {@link PurchaseResponse }
     * 
     */
    public PurchaseResponse createPurchaseResponse() {
        return new PurchaseResponse();
    }

    /**
     * Create an instance of {@link Purchase }
     * 
     */
    public Purchase createPurchase() {
        return new Purchase();
    }

    /**
     * Create an instance of {@link SearchByTitleResponse }
     * 
     */
    public SearchByTitleResponse createSearchByTitleResponse() {
        return new SearchByTitleResponse();
    }

    /**
     * Create an instance of {@link SearchByArtist }
     * 
     */
    public SearchByArtist createSearchByArtist() {
        return new SearchByArtist();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchByGenreResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.vvws.choreos.ime.usp.br/", name = "searchByGenreResponse")
    public JAXBElement<SearchByGenreResponse> createSearchByGenreResponse(SearchByGenreResponse value) {
        return new JAXBElement<SearchByGenreResponse>(_SearchByGenreResponse_QNAME, SearchByGenreResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchByTitleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.vvws.choreos.ime.usp.br/", name = "searchByTitleResponse")
    public JAXBElement<SearchByTitleResponse> createSearchByTitleResponse(SearchByTitleResponse value) {
        return new JAXBElement<SearchByTitleResponse>(_SearchByTitleResponse_QNAME, SearchByTitleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchByGenre }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.vvws.choreos.ime.usp.br/", name = "searchByGenre")
    public JAXBElement<SearchByGenre> createSearchByGenre(SearchByGenre value) {
        return new JAXBElement<SearchByGenre>(_SearchByGenre_QNAME, SearchByGenre.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Purchase }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.vvws.choreos.ime.usp.br/", name = "purchase")
    public JAXBElement<Purchase> createPurchase(Purchase value) {
        return new JAXBElement<Purchase>(_Purchase_QNAME, Purchase.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchByArtist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.vvws.choreos.ime.usp.br/", name = "searchByArtist")
    public JAXBElement<SearchByArtist> createSearchByArtist(SearchByArtist value) {
        return new JAXBElement<SearchByArtist>(_SearchByArtist_QNAME, SearchByArtist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurchaseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.vvws.choreos.ime.usp.br/", name = "purchaseResponse")
    public JAXBElement<PurchaseResponse> createPurchaseResponse(PurchaseResponse value) {
        return new JAXBElement<PurchaseResponse>(_PurchaseResponse_QNAME, PurchaseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchByArtistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.vvws.choreos.ime.usp.br/", name = "searchByArtistResponse")
    public JAXBElement<SearchByArtistResponse> createSearchByArtistResponse(SearchByArtistResponse value) {
        return new JAXBElement<SearchByArtistResponse>(_SearchByArtistResponse_QNAME, SearchByArtistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchByTitle }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.vvws.choreos.ime.usp.br/", name = "searchByTitle")
    public JAXBElement<SearchByTitle> createSearchByTitle(SearchByTitle value) {
        return new JAXBElement<SearchByTitle>(_SearchByTitle_QNAME, SearchByTitle.class, null, value);
    }

}
