/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ahmed
 */
@Entity
@Table(name = "documents")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documents.findAll", query = "SELECT d FROM Documents d"),
    @NamedQuery(name = "Documents.findByDocumentID", query = "SELECT d FROM Documents d WHERE d.documentID = :documentID"),
    @NamedQuery(name = "Documents.findByUploaduser", query = "SELECT d FROM Documents d WHERE d.uploaduser = :uploaduser"),
    @NamedQuery(name = "Documents.findByDocname", query = "SELECT d FROM Documents d WHERE d.documentname = :documentname"),
    @NamedQuery(name = "Documents.findByDescription", query = "SELECT d FROM Documents d WHERE d.description = :description")})
public class Documents implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "documentID")
    private Integer documentID;
    @Lob
    @Column(name = "documentname")
    private String documentname;
    @Column(name = "uploaduser")
    private Integer uploaduser;
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "caseID", referencedColumnName = "caseID")
    @ManyToOne(optional = false)
    private Cases caseID;

    public Documents() {
    }

    public Documents(Integer documentID) {
        this.documentID = documentID;
    }

    public Integer getDocumentID() {
        return documentID;
    }

    public void setDocumentID(Integer documentID) {
        this.documentID = documentID;
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    public Integer getUploaduser() {
        return uploaduser;
    }

    public void setUploaduser(Integer uploaduser) {
        this.uploaduser = uploaduser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cases getCaseID() {
        return caseID;
    }

    public void setCaseID(Cases caseID) {
        this.caseID = caseID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentID != null ? documentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documents)) {
            return false;
        }
        Documents other = (Documents) object;
        if ((this.documentID == null && other.documentID != null) || (this.documentID != null && !this.documentID.equals(other.documentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cm.entities.Documents[ documentID=" + documentID + " ]";
    }
    
}
