/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team17.bikeworld.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "order", catalog = "bikeworld", schema = "")
@XmlRootElement
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Expose
    @Column(name = "id", nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total", precision = 22)
    @Expose
    private Double total;
    @Expose
    @Column(name = "quantity")
    private Integer quantity;
    @Expose
    @Column(name = "addressDelivery", length = 255)
    private String addressDelivery;
    @Expose
    @Column(name = "phoneContact")
    private String phoneContact;
    @Expose
    @Column(name = "orderDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Expose
    @Column(name = "receiverName", length = 255)
    private String receiverName;
    @Expose
    @JoinColumn(name = "buyer_username", referencedColumnName = "username", nullable = false)
    @ManyToOne(optional = false)
    private Account buyerUsername;
    @Expose
    @JoinColumn(name = "seller_username", referencedColumnName = "username", nullable = false)
    @ManyToOne(optional = false)
    private Account sellerUsername;
    @Expose
    @JoinColumn(name = "orderStatus_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private OrderStatus orderStatusid;
    @Expose
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Product productId;

    public Order() {
    }

    public Order(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getAddressDelivery() {
        return addressDelivery;
    }

    public void setAddressDelivery(String addressDelivery) {
        this.addressDelivery = addressDelivery;
    }

    public String getPhoneContact() {
        return phoneContact;
    }

    public void setPhoneContact(String phoneContact) {
        this.phoneContact = phoneContact;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Account getBuyerUsername() {
        return buyerUsername;
    }

    public void setBuyerUsername(Account buyerUsername) {
        this.buyerUsername = buyerUsername;
    }

    public Account getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(Account sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public OrderStatus getOrderStatusid() {
        return orderStatusid;
    }

    public void setOrderStatusid(OrderStatus orderStatusid) {
        this.orderStatusid = orderStatusid;
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
        if (!(object instanceof Order)) {
            return false;
        }
        Order other = (Order) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Order[ id=" + id + " ]";
    }
    
}
