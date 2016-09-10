
package edu.hneu.studentsportal.pojo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="metadata">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="end-date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="has-three-last-pairs" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="has-weekend-pairs" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="start-date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="year" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                 &lt;attribute name="year-display-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="week">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="day" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="day" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                           &lt;attribute name="display-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="schedule-elements">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="schedule-element" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="subject" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                     &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="type" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="teacher" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="full-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="room" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="building" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                     &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="groups" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="group" maxOccurs="unbounded" minOccurs="0">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                 &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                               &lt;attribute name="all" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                               &lt;attribute name="main" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="schedule-element" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="subject">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                               &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="type">
 *                                         &lt;complexType>
 *                                           &lt;simpleContent>
 *                                             &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                               &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                               &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/extension>
 *                                           &lt;/simpleContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="teacher">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                               &lt;attribute name="full-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="room">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                               &lt;/sequence>
 *                                               &lt;attribute name="building" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                               &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                               &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                               &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="groups">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="group" maxOccurs="unbounded" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                         &lt;sequence>
 *                                                           &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                           &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                                         &lt;/sequence>
 *                                                         &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
 *                                                         &lt;attribute name="main" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                                       &lt;/restriction>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="break-end" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="break-end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                     &lt;attribute name="break-start" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="break-start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                     &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="day" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                     &lt;attribute name="end" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                     &lt;attribute name="first" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="is-one-of-multiple" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="pair" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                                     &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                                     &lt;attribute name="last" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="break-end" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="break-end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="break-start" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="break-start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="day" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                           &lt;attribute name="end" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="pair" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *                           &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="contains-multiple" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="time-zone" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metadata",
    "week",
    "scheduleElements"
})
@XmlRootElement(name = "schedule")
public class Schedule {

    @XmlElement(required = true)
    protected Schedule.Metadata metadata;
    @XmlElement(required = true)
    protected Schedule.Week week;
    @XmlElement(name = "schedule-elements", required = true)
    protected Schedule.ScheduleElements scheduleElements;
    @XmlAttribute(name = "time-zone")
    protected String timeZone;

    /**
     * Gets the value of the metadata property.
     * 
     * @return
     *     possible object is
     *     {@link Schedule.Metadata }
     *     
     */
    public Schedule.Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the value of the metadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link Schedule.Metadata }
     *     
     */
    public void setMetadata(Schedule.Metadata value) {
        this.metadata = value;
    }

    /**
     * Gets the value of the week property.
     * 
     * @return
     *     possible object is
     *     {@link Schedule.Week }
     *     
     */
    public Schedule.Week getWeek() {
        return week;
    }

    /**
     * Sets the value of the week property.
     * 
     * @param value
     *     allowed object is
     *     {@link Schedule.Week }
     *     
     */
    public void setWeek(Schedule.Week value) {
        this.week = value;
    }

    /**
     * Gets the value of the scheduleElements property.
     * 
     * @return
     *     possible object is
     *     {@link Schedule.ScheduleElements }
     *     
     */
    public Schedule.ScheduleElements getScheduleElements() {
        return scheduleElements;
    }

    /**
     * Sets the value of the scheduleElements property.
     * 
     * @param value
     *     allowed object is
     *     {@link Schedule.ScheduleElements }
     *     
     */
    public void setScheduleElements(Schedule.ScheduleElements value) {
        this.scheduleElements = value;
    }

    /**
     * Gets the value of the timeZone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the value of the timeZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeZone(String value) {
        this.timeZone = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="end-date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="has-three-last-pairs" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="has-weekend-pairs" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
     *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="start-date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="year" type="{http://www.w3.org/2001/XMLSchema}short" />
     *       &lt;attribute name="year-display-name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Metadata {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "description")
        protected String description;
        @XmlAttribute(name = "end-date")
        protected String endDate;
        @XmlAttribute(name = "has-three-last-pairs")
        protected String hasThreeLastPairs;
        @XmlAttribute(name = "has-weekend-pairs")
        protected String hasWeekendPairs;
        @XmlAttribute(name = "id")
        protected Short id;
        @XmlAttribute(name = "name")
        protected String name;
        @XmlAttribute(name = "short-name")
        protected String shortName;
        @XmlAttribute(name = "start-date")
        protected String startDate;
        @XmlAttribute(name = "type")
        protected String type;
        @XmlAttribute(name = "year")
        protected Short year;
        @XmlAttribute(name = "year-display-name")
        protected String yearDisplayName;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * Gets the value of the endDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEndDate() {
            return endDate;
        }

        /**
         * Sets the value of the endDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEndDate(String value) {
            this.endDate = value;
        }

        /**
         * Gets the value of the hasThreeLastPairs property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHasThreeLastPairs() {
            return hasThreeLastPairs;
        }

        /**
         * Sets the value of the hasThreeLastPairs property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHasThreeLastPairs(String value) {
            this.hasThreeLastPairs = value;
        }

        /**
         * Gets the value of the hasWeekendPairs property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHasWeekendPairs() {
            return hasWeekendPairs;
        }

        /**
         * Sets the value of the hasWeekendPairs property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHasWeekendPairs(String value) {
            this.hasWeekendPairs = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link Short }
         *     
         */
        public Short getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link Short }
         *     
         */
        public void setId(Short value) {
            this.id = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the shortName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getShortName() {
            return shortName;
        }

        /**
         * Sets the value of the shortName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setShortName(String value) {
            this.shortName = value;
        }

        /**
         * Gets the value of the startDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStartDate() {
            return startDate;
        }

        /**
         * Sets the value of the startDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStartDate(String value) {
            this.startDate = value;
        }

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Gets the value of the year property.
         * 
         * @return
         *     possible object is
         *     {@link Short }
         *     
         */
        public Short getYear() {
            return year;
        }

        /**
         * Sets the value of the year property.
         * 
         * @param value
         *     allowed object is
         *     {@link Short }
         *     
         */
        public void setYear(Short value) {
            this.year = value;
        }

        /**
         * Gets the value of the yearDisplayName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getYearDisplayName() {
            return yearDisplayName;
        }

        /**
         * Sets the value of the yearDisplayName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setYearDisplayName(String value) {
            this.yearDisplayName = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="schedule-element" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="subject" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
     *                           &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="type" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="teacher" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                           &lt;attribute name="full-name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="room" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                           &lt;/sequence>
     *                           &lt;attribute name="building" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
     *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="groups" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="group" maxOccurs="unbounded" minOccurs="0">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                       &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                     &lt;/sequence>
     *                                     &lt;attribute name="all" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
     *                                     &lt;attribute name="main" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="schedule-element" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="subject">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
     *                                     &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="type">
     *                               &lt;complexType>
     *                                 &lt;simpleContent>
     *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                                     &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/extension>
     *                                 &lt;/simpleContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="teacher">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                     &lt;/sequence>
     *                                     &lt;attribute name="full-name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="room">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                     &lt;/sequence>
     *                                     &lt;attribute name="building" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
     *                                     &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}short" />
     *                                     &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="groups">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="group" maxOccurs="unbounded" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                               &lt;sequence>
     *                                                 &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                                 &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                                               &lt;/sequence>
     *                                               &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
     *                                               &lt;attribute name="main" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                                             &lt;/restriction>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                           &lt;attribute name="break-end" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="break-end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                           &lt;attribute name="break-start" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="break-start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                           &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="day" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                           &lt;attribute name="end" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                           &lt;attribute name="first" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="is-one-of-multiple" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="pair" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                           &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                           &lt;attribute name="last" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="break-end" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="break-end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                 &lt;attribute name="break-start" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="break-start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                 &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="day" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                 &lt;attribute name="end" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                 &lt;attribute name="pair" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                 &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                 &lt;attribute name="contains-multiple" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "scheduleElement"
    })
    public static class ScheduleElements {

        @XmlElement(name = "schedule-element")
        protected List<Schedule.ScheduleElements.ScheduleElement> scheduleElement;

        /**
         * Gets the value of the scheduleElement property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the scheduleElement property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getScheduleElement().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Schedule.ScheduleElements.ScheduleElement }
         * 
         * 
         */
        public List<Schedule.ScheduleElements.ScheduleElement> getScheduleElement() {
            if (scheduleElement == null) {
                scheduleElement = new ArrayList<Schedule.ScheduleElements.ScheduleElement>();
            }
            return this.scheduleElement;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="subject" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
         *                 &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="type" minOccurs="0">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *                 &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="teacher" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *                 &lt;attribute name="full-name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="room" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                 &lt;/sequence>
         *                 &lt;attribute name="building" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
         *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="groups" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="group" maxOccurs="unbounded" minOccurs="0">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                             &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                           &lt;attribute name="all" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
         *                           &lt;attribute name="main" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="schedule-element" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="subject">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
         *                           &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="type">
         *                     &lt;complexType>
         *                       &lt;simpleContent>
         *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *                           &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/extension>
         *                       &lt;/simpleContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="teacher">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                           &lt;attribute name="full-name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="room">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                           &lt;/sequence>
         *                           &lt;attribute name="building" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
         *                           &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}short" />
         *                           &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="groups">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="group" maxOccurs="unbounded" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                                     &lt;sequence>
         *                                       &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                       &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                                     &lt;/sequence>
         *                                     &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
         *                                     &lt;attribute name="main" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                                   &lt;/restriction>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *                 &lt;attribute name="break-end" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="break-end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
         *                 &lt;attribute name="break-start" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="break-start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
         *                 &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="day" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *                 &lt;attribute name="end" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
         *                 &lt;attribute name="first" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="is-one-of-multiple" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="pair" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *                 &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
         *                 &lt;attribute name="last" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="break-end" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="break-end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
         *       &lt;attribute name="break-start" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="break-start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
         *       &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="day" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *       &lt;attribute name="end" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="end-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
         *       &lt;attribute name="pair" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *       &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="start-epoch" type="{http://www.w3.org/2001/XMLSchema}int" />
         *       &lt;attribute name="contains-multiple" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "subject",
            "type",
            "teacher",
            "room",
            "groups",
            "scheduleElement"
        })
        public static class ScheduleElement {

            protected Schedule.ScheduleElements.ScheduleElement.Subject subject;
            protected Schedule.ScheduleElements.ScheduleElement.Type type;
            protected Schedule.ScheduleElements.ScheduleElement.Teacher teacher;
            protected Schedule.ScheduleElements.ScheduleElement.Room room;
            protected Schedule.ScheduleElements.ScheduleElement.Groups groups;
            @XmlElement(name = "schedule-element")
            protected List<Schedule.ScheduleElements.ScheduleElement> scheduleElement;
            @XmlAttribute(name = "break-end")
            protected String breakEnd;
            @XmlAttribute(name = "break-end-epoch")
            protected Integer breakEndEpoch;
            @XmlAttribute(name = "break-start")
            protected String breakStart;
            @XmlAttribute(name = "break-start-epoch")
            protected Integer breakStartEpoch;
            @XmlAttribute(name = "date")
            protected String date;
            @XmlAttribute(name = "day")
            protected Byte day;
            @XmlAttribute(name = "end")
            protected String end;
            @XmlAttribute(name = "end-epoch")
            protected Integer endEpoch;
            @XmlAttribute(name = "pair")
            protected Byte pair;
            @XmlAttribute(name = "start")
            protected String start;
            @XmlAttribute(name = "start-epoch")
            protected Integer startEpoch;
            @XmlAttribute(name = "contains-multiple")
            protected String containsMultiple;

            /**
             * Gets the value of the subject property.
             * 
             * @return
             *     possible object is
             *     {@link Schedule.ScheduleElements.ScheduleElement.Subject }
             *     
             */
            public Schedule.ScheduleElements.ScheduleElement.Subject getSubject() {
                return subject;
            }

            /**
             * Sets the value of the subject property.
             * 
             * @param value
             *     allowed object is
             *     {@link Schedule.ScheduleElements.ScheduleElement.Subject }
             *     
             */
            public void setSubject(Schedule.ScheduleElements.ScheduleElement.Subject value) {
                this.subject = value;
            }

            /**
             * Gets the value of the type property.
             * 
             * @return
             *     possible object is
             *     {@link Schedule.ScheduleElements.ScheduleElement.Type }
             *     
             */
            public Schedule.ScheduleElements.ScheduleElement.Type getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             * 
             * @param value
             *     allowed object is
             *     {@link Schedule.ScheduleElements.ScheduleElement.Type }
             *     
             */
            public void setType(Schedule.ScheduleElements.ScheduleElement.Type value) {
                this.type = value;
            }

            /**
             * Gets the value of the teacher property.
             * 
             * @return
             *     possible object is
             *     {@link Schedule.ScheduleElements.ScheduleElement.Teacher }
             *     
             */
            public Schedule.ScheduleElements.ScheduleElement.Teacher getTeacher() {
                return teacher;
            }

            /**
             * Sets the value of the teacher property.
             * 
             * @param value
             *     allowed object is
             *     {@link Schedule.ScheduleElements.ScheduleElement.Teacher }
             *     
             */
            public void setTeacher(Schedule.ScheduleElements.ScheduleElement.Teacher value) {
                this.teacher = value;
            }

            /**
             * Gets the value of the room property.
             * 
             * @return
             *     possible object is
             *     {@link Schedule.ScheduleElements.ScheduleElement.Room }
             *     
             */
            public Schedule.ScheduleElements.ScheduleElement.Room getRoom() {
                return room;
            }

            /**
             * Sets the value of the room property.
             * 
             * @param value
             *     allowed object is
             *     {@link Schedule.ScheduleElements.ScheduleElement.Room }
             *     
             */
            public void setRoom(Schedule.ScheduleElements.ScheduleElement.Room value) {
                this.room = value;
            }

            /**
             * Gets the value of the groups property.
             * 
             * @return
             *     possible object is
             *     {@link Schedule.ScheduleElements.ScheduleElement.Groups }
             *     
             */
            public Schedule.ScheduleElements.ScheduleElement.Groups getGroups() {
                return groups;
            }

            /**
             * Sets the value of the groups property.
             * 
             * @param value
             *     allowed object is
             *     {@link Schedule.ScheduleElements.ScheduleElement.Groups }
             *     
             */
            public void setGroups(Schedule.ScheduleElements.ScheduleElement.Groups value) {
                this.groups = value;
            }

            /**
             * Gets the value of the scheduleElement property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the scheduleElement property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getScheduleElement().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Schedule.ScheduleElements.ScheduleElement }
             * 
             * 
             */
            public List<Schedule.ScheduleElements.ScheduleElement> getScheduleElement() {
                if (scheduleElement == null) {
                    scheduleElement = new ArrayList<Schedule.ScheduleElements.ScheduleElement>();
                }
                return this.scheduleElement;
            }

            /**
             * Gets the value of the breakEnd property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBreakEnd() {
                return breakEnd;
            }

            /**
             * Sets the value of the breakEnd property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBreakEnd(String value) {
                this.breakEnd = value;
            }

            /**
             * Gets the value of the breakEndEpoch property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getBreakEndEpoch() {
                return breakEndEpoch;
            }

            /**
             * Sets the value of the breakEndEpoch property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setBreakEndEpoch(Integer value) {
                this.breakEndEpoch = value;
            }

            /**
             * Gets the value of the breakStart property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBreakStart() {
                return breakStart;
            }

            /**
             * Sets the value of the breakStart property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBreakStart(String value) {
                this.breakStart = value;
            }

            /**
             * Gets the value of the breakStartEpoch property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getBreakStartEpoch() {
                return breakStartEpoch;
            }

            /**
             * Sets the value of the breakStartEpoch property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setBreakStartEpoch(Integer value) {
                this.breakStartEpoch = value;
            }

            /**
             * Gets the value of the date property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDate() {
                return date;
            }

            /**
             * Sets the value of the date property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDate(String value) {
                this.date = value;
            }

            /**
             * Gets the value of the day property.
             * 
             * @return
             *     possible object is
             *     {@link Byte }
             *     
             */
            public Byte getDay() {
                return day;
            }

            /**
             * Sets the value of the day property.
             * 
             * @param value
             *     allowed object is
             *     {@link Byte }
             *     
             */
            public void setDay(Byte value) {
                this.day = value;
            }

            /**
             * Gets the value of the end property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEnd() {
                return end;
            }

            /**
             * Sets the value of the end property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEnd(String value) {
                this.end = value;
            }

            /**
             * Gets the value of the endEpoch property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getEndEpoch() {
                return endEpoch;
            }

            /**
             * Sets the value of the endEpoch property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setEndEpoch(Integer value) {
                this.endEpoch = value;
            }

            /**
             * Gets the value of the pair property.
             * 
             * @return
             *     possible object is
             *     {@link Byte }
             *     
             */
            public Byte getPair() {
                return pair;
            }

            /**
             * Sets the value of the pair property.
             * 
             * @param value
             *     allowed object is
             *     {@link Byte }
             *     
             */
            public void setPair(Byte value) {
                this.pair = value;
            }

            /**
             * Gets the value of the start property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStart() {
                return start;
            }

            /**
             * Sets the value of the start property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStart(String value) {
                this.start = value;
            }

            /**
             * Gets the value of the startEpoch property.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getStartEpoch() {
                return startEpoch;
            }

            /**
             * Sets the value of the startEpoch property.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setStartEpoch(Integer value) {
                this.startEpoch = value;
            }

            /**
             * Gets the value of the containsMultiple property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getContainsMultiple() {
                return containsMultiple;
            }

            /**
             * Sets the value of the containsMultiple property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setContainsMultiple(String value) {
                this.containsMultiple = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="group" maxOccurs="unbounded" minOccurs="0">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                   &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *                 &lt;/sequence>
             *                 &lt;attribute name="all" type="{http://www.w3.org/2001/XMLSchema}string" />
             *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
             *                 &lt;attribute name="main" type="{http://www.w3.org/2001/XMLSchema}string" />
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *       &lt;/sequence>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "group"
            })
            public static class Groups {

                protected List<Schedule.ScheduleElements.ScheduleElement.Groups.Group> group;

                /**
                 * Gets the value of the group property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the group property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getGroup().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link Schedule.ScheduleElements.ScheduleElement.Groups.Group }
                 * 
                 * 
                 */
                public List<Schedule.ScheduleElements.ScheduleElement.Groups.Group> getGroup() {
                    if (group == null) {
                        group = new ArrayList<Schedule.ScheduleElements.ScheduleElement.Groups.Group>();
                    }
                    return this.group;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
                 *       &lt;sequence>
                 *         &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
                 *       &lt;/sequence>
                 *       &lt;attribute name="all" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
                 *       &lt;attribute name="main" type="{http://www.w3.org/2001/XMLSchema}string" />
                 *     &lt;/restriction>
                 *   &lt;/complexContent>
                 * &lt;/complexType>
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "displayName",
                    "title"
                })
                public static class Group {

                    @XmlElement(name = "display-name", required = true)
                    protected String displayName;
                    @XmlElement(required = true)
                    protected String title;
                    @XmlAttribute(name = "all")
                    protected String all;
                    @XmlAttribute(name = "id")
                    protected Short id;
                    @XmlAttribute(name = "main")
                    protected String main;

                    /**
                     * Gets the value of the displayName property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getDisplayName() {
                        return displayName;
                    }

                    /**
                     * Sets the value of the displayName property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setDisplayName(String value) {
                        this.displayName = value;
                    }

                    /**
                     * Gets the value of the title property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getTitle() {
                        return title;
                    }

                    /**
                     * Sets the value of the title property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setTitle(String value) {
                        this.title = value;
                    }

                    /**
                     * Gets the value of the all property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getAll() {
                        return all;
                    }

                    /**
                     * Sets the value of the all property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setAll(String value) {
                        this.all = value;
                    }

                    /**
                     * Gets the value of the id property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Short }
                     *     
                     */
                    public Short getId() {
                        return id;
                    }

                    /**
                     * Sets the value of the id property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Short }
                     *     
                     */
                    public void setId(Short value) {
                        this.id = value;
                    }

                    /**
                     * Gets the value of the main property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getMain() {
                        return main;
                    }

                    /**
                     * Sets the value of the main property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setMain(String value) {
                        this.main = value;
                    }

                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *       &lt;attribute name="building" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
             *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "displayName"
            })
            public static class Room {

                @XmlElement(name = "display-name", required = true)
                protected String displayName;
                @XmlAttribute(name = "building")
                protected String building;
                @XmlAttribute(name = "id")
                protected Short id;
                @XmlAttribute(name = "name")
                protected String name;
                @XmlAttribute(name = "short-name")
                protected String shortName;

                /**
                 * Gets the value of the displayName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDisplayName() {
                    return displayName;
                }

                /**
                 * Sets the value of the displayName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDisplayName(String value) {
                    this.displayName = value;
                }

                /**
                 * Gets the value of the building property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBuilding() {
                    return building;
                }

                /**
                 * Sets the value of the building property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBuilding(String value) {
                    this.building = value;
                }

                /**
                 * Gets the value of the id property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Short }
                 *     
                 */
                public Short getId() {
                    return id;
                }

                /**
                 * Sets the value of the id property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Short }
                 *     
                 */
                public void setId(Short value) {
                    this.id = value;
                }

                /**
                 * Gets the value of the name property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Sets the value of the name property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setName(String value) {
                    this.name = value;
                }

                /**
                 * Gets the value of the shortName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getShortName() {
                    return shortName;
                }

                /**
                 * Sets the value of the shortName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setShortName(String value) {
                    this.shortName = value;
                }

            }

            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}short" />
             *       &lt;attribute name="short-name" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Subject {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "id")
                protected Short id;
                @XmlAttribute(name = "short-name")
                protected String shortName;

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the id property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Short }
                 *     
                 */
                public Short getId() {
                    return id;
                }

                /**
                 * Sets the value of the id property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Short }
                 *     
                 */
                public void setId(Short value) {
                    this.id = value;
                }

                /**
                 * Gets the value of the shortName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getShortName() {
                    return shortName;
                }

                /**
                 * Sets the value of the shortName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setShortName(String value) {
                    this.shortName = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="display-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *       &lt;/sequence>
             *       &lt;attribute name="full-name" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "displayName"
            })
            public static class Teacher {

                @XmlElement(name = "display-name", required = true)
                protected String displayName;
                @XmlAttribute(name = "full-name")
                protected String fullName;
                @XmlAttribute(name = "id")
                protected Integer id;

                /**
                 * Gets the value of the displayName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDisplayName() {
                    return displayName;
                }

                /**
                 * Sets the value of the displayName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDisplayName(String value) {
                    this.displayName = value;
                }

                /**
                 * Gets the value of the fullName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFullName() {
                    return fullName;
                }

                /**
                 * Sets the value of the fullName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFullName(String value) {
                    this.fullName = value;
                }

                /**
                 * Gets the value of the id property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Integer }
                 *     
                 */
                public Integer getId() {
                    return id;
                }

                /**
                 * Sets the value of the id property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Integer }
                 *     
                 */
                public void setId(Integer value) {
                    this.id = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}byte" />
             *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class Type {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "id")
                protected Byte id;
                @XmlAttribute(name = "type")
                protected String type;

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the id property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Byte }
                 *     
                 */
                public Byte getId() {
                    return id;
                }

                /**
                 * Sets the value of the id property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Byte }
                 *     
                 */
                public void setId(Byte value) {
                    this.id = value;
                }

                /**
                 * Gets the value of the type property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Sets the value of the type property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setType(String value) {
                    this.type = value;
                }

            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="day" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="day" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                 &lt;attribute name="display-name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "day"
    })
    public static class Week {

        protected List<Schedule.Week.Day> day;
        @XmlAttribute(name = "number")
        protected Byte number;

        /**
         * Gets the value of the day property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the day property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDay().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Schedule.Week.Day }
         * 
         * 
         */
        public List<Schedule.Week.Day> getDay() {
            if (day == null) {
                day = new ArrayList<Schedule.Week.Day>();
            }
            return this.day;
        }

        /**
         * Gets the value of the number property.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public Byte getNumber() {
            return number;
        }

        /**
         * Sets the value of the number property.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setNumber(Byte value) {
            this.number = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="date" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="day" type="{http://www.w3.org/2001/XMLSchema}byte" />
         *       &lt;attribute name="display-name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Day {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "date")
            protected String date;
            @XmlAttribute(name = "day")
            protected Byte day;
            @XmlAttribute(name = "display-name")
            protected String displayName;
            @XmlAttribute(name = "name")
            protected String name;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the date property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDate() {
                return date;
            }

            /**
             * Sets the value of the date property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDate(String value) {
                this.date = value;
            }

            /**
             * Gets the value of the day property.
             * 
             * @return
             *     possible object is
             *     {@link Byte }
             *     
             */
            public Byte getDay() {
                return day;
            }

            /**
             * Sets the value of the day property.
             * 
             * @param value
             *     allowed object is
             *     {@link Byte }
             *     
             */
            public void setDay(Byte value) {
                this.day = value;
            }

            /**
             * Gets the value of the displayName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDisplayName() {
                return displayName;
            }

            /**
             * Sets the value of the displayName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDisplayName(String value) {
                this.displayName = value;
            }

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

        }

    }

}
