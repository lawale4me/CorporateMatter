/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ahmed
 */
@Entity
@Table(name = "branch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Branch.findAll", query = "SELECT b FROM Branch b"),
    @NamedQuery(name = "Branch.findByBranchid", query = "SELECT b FROM Branch b WHERE b.branchid = :branchid"),
    @NamedQuery(name = "Branch.findByName", query = "SELECT b FROM Branch b WHERE b.name = :name"),
    @NamedQuery(name = "Branch.findByCode", query = "SELECT b FROM Branch b WHERE b.code = :code")})
public class Branch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "branchid")
    private Integer branchid;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @OneToMany(mappedBy = "branchid")
    private Collection<Users> usersCollection;

    public Branch() {
    }

    public Branch(Integer branchid) {
        this.branchid = branchid;
    }

    public Integer getBranchid() {
        return branchid;
    }

    public void setBranchid(Integer branchid) {
        this.branchid = branchid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (branchid != null ? branchid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Branch)) {
            return false;
        }
        Branch other = (Branch) object;
        if ((this.branchid == null && other.branchid != null) || (this.branchid != null && !this.branchid.equals(other.branchid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cm.entities.Branch[ branchid=" + branchid + " ]";
    }
    
}
