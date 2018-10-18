/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team17.bikeworld.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "proposaleventimage", catalog = "bikeworld", schema = "")
@XmlRootElement
public class ProposalEventImage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Expose
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "imageLink", length = 255)
    private String imageLink;
    @Expose
    @JoinColumn(name = "proposalEvent_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private ProposalEvent proposalEventid;

    public ProposalEventImage() {
    }

    public ProposalEventImage(Integer id) {
        this.id = id;
    }

    public ProposalEventImage(String imageLink) {
        this.imageLink = imageLink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public ProposalEvent getProposalEventid() {
        return proposalEventid;
    }

    public void setProposalEventid(ProposalEvent proposalEventid) {
        this.proposalEventid = proposalEventid;
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
        if (!(object instanceof ProposalEventImage)) {
            return false;
        }
        ProposalEventImage other = (ProposalEventImage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProposalEventImage[ id=" + id + " ]";
    }
    
}
