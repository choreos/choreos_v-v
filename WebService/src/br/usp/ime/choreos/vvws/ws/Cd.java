/**
 * Cd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package br.usp.ime.choreos.vvws.ws;

public class Cd  implements java.io.Serializable {
	private static final long serialVersionUID = -6795531741486092605L;

	private java.lang.String artist;

    private java.lang.String genre;

    private java.lang.Integer numberOfTracks;

    private java.lang.String title;

    public Cd() {
    }

    public Cd(
           java.lang.String artist,
           java.lang.String genre,
           java.lang.Integer numberOfTracks,
           java.lang.String title) {
           this.artist = artist;
           this.genre = genre;
           this.numberOfTracks = numberOfTracks;
           this.title = title;
    }


    /**
     * Gets the artist value for this Cd.
     * 
     * @return artist
     */
    public java.lang.String getArtist() {
        return artist;
    }


    /**
     * Sets the artist value for this Cd.
     * 
     * @param artist
     */
    public void setArtist(java.lang.String artist) {
        this.artist = artist;
    }


    /**
     * Gets the genre value for this Cd.
     * 
     * @return genre
     */
    public java.lang.String getGenre() {
        return genre;
    }


    /**
     * Sets the genre value for this Cd.
     * 
     * @param genre
     */
    public void setGenre(java.lang.String genre) {
        this.genre = genre;
    }


    /**
     * Gets the numberOfTracks value for this Cd.
     * 
     * @return numberOfTracks
     */
    public java.lang.Integer getNumberOfTracks() {
        return numberOfTracks;
    }


    /**
     * Sets the numberOfTracks value for this Cd.
     * 
     * @param numberOfTracks
     */
    public void setNumberOfTracks(java.lang.Integer numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
    }


    /**
     * Gets the title value for this Cd.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Cd.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Cd)) return false;
        Cd other = (Cd) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.artist==null && other.getArtist()==null) || 
             (this.artist!=null &&
              this.artist.equals(other.getArtist()))) &&
            ((this.genre==null && other.getGenre()==null) || 
             (this.genre!=null &&
              this.genre.equals(other.getGenre()))) &&
            ((this.numberOfTracks==null && other.getNumberOfTracks()==null) || 
             (this.numberOfTracks!=null &&
              this.numberOfTracks.equals(other.getNumberOfTracks()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getArtist() != null) {
            _hashCode += getArtist().hashCode();
        }
        if (getGenre() != null) {
            _hashCode += getGenre().hashCode();
        }
        if (getNumberOfTracks() != null) {
            _hashCode += getNumberOfTracks().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Cd.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.vvws.choreos.ime.usp.br/", "cd"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("artist");
        elemField.setXmlName(new javax.xml.namespace.QName("", "artist"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("genre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "genre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfTracks");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numberOfTracks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
