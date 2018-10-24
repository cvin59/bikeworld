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
@Table(name = "product", catalog = "bikeworld", schema = "")
@XmlRootElement
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "name", length = 255)
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Expose
    @Column(name = "price", precision = 22)
    private Double price;
    @Expose
    @Column(name = "description", length = 255)
    private String description;
    @Expose
    @Column(name = "longitude", precision = 22)
    private Double longitude;
    @Expose
    @Column(name = "latitude", precision = 22)
    private Double latitude;
    @Expose
    @Column(name = "address", length = 255)
    private String address;
    @Expose
    @Column(name = "postDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;
    @Expose
    @Column(name = "totalRatePoint", precision = 22)
    private Double totalRatePoint;
    @Expose
    @Column(name = "totalRates")
    private Integer totalRates;
    @Expose
    @Column(name = "status")
    private Integer status;
    @Expose
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_usename")
    private Account accountUsename;
    @Expose
    @JoinColumn(name = "brand_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Brand brandId;
    @Expose
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Category categoryId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<ProductRating> productRatingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<ProductImage> productImageCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<Order> order1Collection;

    public Product() {
    }

    public Product(Integer id) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Double getTotalRatePoint() {
        return totalRatePoint;
    }

    public void setTotalRatePoint(Double totalRatePoint) {
        this.totalRatePoint = totalRatePoint;
    }

    public Integer getTotalRates() {
        return totalRates;
    }

    public void setTotalRates(Integer totalRates) {
        this.totalRates = totalRates;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Account getAccountUsename() {
        return accountUsename;
    }

    public void setAccountUsename(Account accountUsename) {
        this.accountUsename = accountUsename;
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

    @XmlTransient
    public Collection<ProductRating> getProductRatingCollection() {
        return productRatingCollection;
    }

    public void setProductRatingCollection(Collection<ProductRating> productRatingCollection) {
        this.productRatingCollection = productRatingCollection;
    }

    @XmlTransient
    public Collection<ProductImage> getProductImageCollection() {
        return productImageCollection;
    }

    public void setProductImageCollection(Collection<ProductImage> productImageCollection) {
        this.productImageCollection = productImageCollection;
    }

    @XmlTransient
    public Collection<Order> getOrder1Collection() {
        return order1Collection;
    }

    public void setOrder1Collection(Collection<Order> order1Collection) {
        this.order1Collection = order1Collection;
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Product[ id=" + id + " ]";
    }
    
}
