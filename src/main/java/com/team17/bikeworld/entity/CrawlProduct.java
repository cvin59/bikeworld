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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author asus
 */
@Entity
@Table(name="crawlproduct",catalog = "bikeworld", schema = "")
@XmlRootElement
public class CrawlProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    @Expose
    private Integer id;
    @Column(length = 255)
    @Expose
    private String name;
    @Column(length = 255)
    @Expose
    private String url;
    @Column(length = 45)
    @Expose
    private String price;
    @Lob
    @Column(length = 2147483647)
    @Expose
    private String description;
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @ManyToOne
    @Expose
    private Brand brandId;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne
    @Expose
    private Category categoryId;
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    @ManyToOne
    @Expose
    private CrawlSite siteId;
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne
    @Expose
    private CrawlStatus status;

    @OneToMany(mappedBy = "crawlProductid")
    private Collection<CrawlProductImage> crawlProductImageCollection;
    @Column(length = 255)
    @Expose
    private String hash;



    public CrawlProduct() {
    }

    public CrawlProduct(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Brand getBrandId() {
        return brandId;
    }

    public void setBrandId(Brand brandId) {
        this.brandId = brandId;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public CrawlSite getSiteId() {
        return siteId;
    }

    public void setSiteId(CrawlSite siteId) {
        this.siteId = siteId;
    }

    public CrawlStatus getStatus() {
        return status;
    }

    public void setStatus(CrawlStatus status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<CrawlProductImage> getCrawlProductImageCollection() {
        return crawlProductImageCollection;
    }

    public void setCrawlProductImageCollection(Collection<CrawlProductImage> crawlProductImageCollection) {
        this.crawlProductImageCollection = crawlProductImageCollection;
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
        if (!(object instanceof CrawlProduct)) {
            return false;
        }
        CrawlProduct other = (CrawlProduct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CrawlProduct[ id=" + id + " ]";
    }

}
