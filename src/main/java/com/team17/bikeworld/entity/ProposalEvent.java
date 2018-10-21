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
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "proposalevent")
@XmlRootElement
public class ProposalEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
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
    @Expose
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Expose
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Expose
    @Column(name = "description", length = 1000)
    private String description;

    @Expose
    @Column(name = "status")
    private Integer status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proposalEventid")
    private Collection<ProposalEventImage> proposaleventimageCollection;

    @Expose
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_usename")
    private Account accountUsename;

    public ProposalEvent() {
    }

    public ProposalEvent(Integer id) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<ProposalEventImage> getProposaleventimageCollection() {
        return proposaleventimageCollection;
    }

    public void setProposaleventimageCollection(Collection<ProposalEventImage> proposaleventimageCollection) {
        this.proposaleventimageCollection = proposaleventimageCollection;
    }

    public Account getAccountUsename() {
        return accountUsename;
    }

    public void setAccountUsename(Account accountUsename) {
        this.accountUsename = accountUsename;
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
        if (!(object instanceof ProposalEvent)) {
            return false;
        }
        ProposalEvent other = (ProposalEvent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProposalEvent[ id=" + id + " ]";
    }
    
}
