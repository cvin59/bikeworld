/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team17.bikeworld.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "productrating", catalog = "bikeworld", schema = "")
@XmlRootElement
public class ProductRating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Expose
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "isActive")
    private Short isActive;
    @Expose
    @Column(name = "content", length = 255)
    private String content;
    @Expose
    @Column(name = "rateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rateDate;
    @Expose
    @Column(name = "point")
    private Integer point;
    @Expose
    @JoinColumn(name = "account_usename", referencedColumnName = "username", nullable = false)
    @ManyToOne(optional = false)
    private Account accountUsename;
    @Expose
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Product productId;

    public ProductRating() {
    }

    public ProductRating(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Date getRateDate() {
        return rateDate;
    }

    public void setRateDate(Date rateDate) {
        this.rateDate = rateDate;
    }

    public Account getAccountUsename() {
        return accountUsename;
    }

    public void setAccountUsename(Account accountUsename) {
        this.accountUsename = accountUsename;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
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
        if (!(object instanceof ProductRating)) {
            return false;
        }
        ProductRating other = (ProductRating) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductRating[ id=" + id + " ]";
    }
    
}
