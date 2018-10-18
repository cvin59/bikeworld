/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team17.bikeworld.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "event", catalog = "bikeworld", schema = "")
@XmlRootElement
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Expose
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "title", length = 255)
    private String title;
    @Expose
    @Column(name = "address", length = 255)
    private String address;
    @Expose
    @Column(name = "location", length = 255)
    private String location;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Expose
    @Column(name = "latitude", precision = 22)
    private Double latitude;
    @Expose
    @Column(name = "longitude", precision = 22)
    private Double longitude;
    @Expose
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Expose
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Expose
    @Column(name = "startRegisterDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startRegisterDate;
    @Expose
    @Column(name = "endRegisterDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endRegisterDate;
    @Expose
    @Column(name = "fee", precision = 22)
    private Double fee;
    @Expose
    @Column(name = "totalRatesPoint", precision = 22)
    private Double totalRatesPoint;
    @Expose
    @Column(name = "totalRates")
    private Integer totalRates;
    @Expose
    @Column(name = "description", length = 4000)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<EventRating> eventRatingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<Participant> participantCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventId")
    private Collection<EventImage> eventImageCollection;
    @Expose
    @JoinColumn(name = "eventStauts_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EventStatus eventStautsid;

    public Event() {
    }

    public Event(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartRegisterDate() {
        return startRegisterDate;
    }

    public void setStartRegisterDate(Date startRegisterDate) {
        this.startRegisterDate = startRegisterDate;
    }

    public Date getEndRegisterDate() {
        return endRegisterDate;
    }

    public void setEndRegisterDate(Date endRegisterDate) {
        this.endRegisterDate = endRegisterDate;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getTotalRatesPoint() {
        return totalRatesPoint;
    }

    public void setTotalRatesPoint(Double totalRatesPoint) {
        this.totalRatesPoint = totalRatesPoint;
    }

    public Integer getTotalRates() {
        return totalRates;
    }

    public void setTotalRates(Integer totalRates) {
        this.totalRates = totalRates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<EventRating> getEventRatingCollection() {
        return eventRatingCollection;
    }

    public void setEventRatingCollection(Collection<EventRating> eventRatingCollection) {
        this.eventRatingCollection = eventRatingCollection;
    }

    @XmlTransient
    public Collection<Participant> getParticipantCollection() {
        return participantCollection;
    }

    public void setParticipantCollection(Collection<Participant> participantCollection) {
        this.participantCollection = participantCollection;
    }

    @XmlTransient
    public Collection<EventImage> getEventImageCollection() {
        return eventImageCollection;
    }

    public void setEventImageCollection(Collection<EventImage> eventImageCollection) {
        this.eventImageCollection = eventImageCollection;
    }

    public EventStatus getEventStautsid() {
        return eventStautsid;
    }

    public void setEventStautsid(EventStatus eventStautsid) {
        this.eventStautsid = eventStautsid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Event[ id=" + id + " ]";
    }
    
}
