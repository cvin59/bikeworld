/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team17.bikeworld.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asus
 */
@Entity
@Table(catalog = "bikeworld", schema = "")
@XmlRootElement
public class CrawlProductImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer id;
    @Column(length = 255)
    @Expose
    private String imageLink;
    @Expose
    @JoinColumn(name = "crawlProduct_id", referencedColumnName = "id")
    @ManyToOne
    private CrawlProduct crawlProductid;

    public CrawlProductImage() {
    }

    public CrawlProductImage(String imageLink, CrawlProduct crawlProductid) {
        this.imageLink = imageLink;
        this.crawlProductid = crawlProductid;
    }

    public CrawlProductImage(Integer id) {
        this.id = id;
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

    public CrawlProduct getCrawlProductid() {
        return crawlProductid;
    }

    public void setCrawlProductid(CrawlProduct crawlProductid) {
        this.crawlProductid = crawlProductid;
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
        if (!(object instanceof CrawlProductImage)) {
            return false;
        }
        CrawlProductImage other = (CrawlProductImage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CrawlProductImage[ id=" + id + " ]";
    }

}
