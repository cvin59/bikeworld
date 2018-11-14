/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team17.bikeworld.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author asus
 */
@Entity
@Table(name = "account", catalog = "bikeworld", schema = "")
@XmlRootElement
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 255)
    private String username;
    @Basic(optional = false)
    @Expose
    @Column(name = "password", nullable = false, length = 50)
    private String password;
    @Expose
    @Column(name = "isActive")
    private Short isActive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountUsename")
    private Collection<Product> productCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountUsename")
    private Collection<ProductRating> productRatingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountUsename")
    private Collection<EventRating> eventRatingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountUsename")
    private Collection<Participant> participantCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountUsename")
    private Collection<ProposalEvent> proposalEventCollection;
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Role roleId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountUsername")
    private Collection<Profile> profileCollection;
    @JoinColumn(name = "profileId", referencedColumnName = "id")
    @ManyToOne
    @Expose
    private Profile profileId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyerUsername")
    private Collection<Order> order1Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sellerUsername")
    private Collection<Order> order2Collection;

    public Account() {
    }

    public Account(String username) {
        this.username = username;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @XmlTransient
    public Collection<ProductRating> getProductRatingCollection() {
        return productRatingCollection;
    }

    public void setProductRatingCollection(Collection<ProductRating> productRatingCollection) {
        this.productRatingCollection = productRatingCollection;
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
    public Collection<ProposalEvent> getProposalEventCollection() {
        return proposalEventCollection;
    }

    public void setProposalEventCollection(Collection<ProposalEvent> proposalEventCollection) {
        this.proposalEventCollection = proposalEventCollection;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    public Profile getProfileId() {
        return profileId;
    }

    public void setProfileId(Profile profileId) {
        this.profileId = profileId;
    }

    @XmlTransient
    public Collection<Order> getOrder1Collection() {
        return order1Collection;
    }

    public void setOrder1Collection(Collection<Order> order1Collection) {
        this.order1Collection = order1Collection;
    }

    @XmlTransient
    public Collection<Order> getOrder2Collection() {
        return order2Collection;
    }

    public void setOrder2Collection(Collection<Order> order2Collection) {
        this.order2Collection = order2Collection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
