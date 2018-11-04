/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team17.bikeworld.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "profile", catalog = "bikeworld", schema = "")
@XmlRootElement
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Expose
    @Column(name = "id", nullable = false)
    private Integer id;
    @Expose
    @Column(name = "firstName", length = 255)
    private String firstName;
    @Expose
    @Column(name = "lastName", length = 255)
    private String lastName;
    @Expose
    @Column(name = "email", length = 255)
    private String email;
    @Expose
    @Column(name = "phone")
    private String phone;
    @Expose
    @Column(name = "address", length = 255)
    private String address;
    @Expose
    @Column(name = "avatarLink", length = 255)
    private String avatarLink;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profileId")
    private Collection<Account> accountCollection;

    public Profile() {
    }

    public Profile(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
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
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Profile[ id=" + id + " ]";
    }
    
}
