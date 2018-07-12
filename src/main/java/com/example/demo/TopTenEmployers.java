package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TopTenEmployers")
public class TopTenEmployers implements Serializable{

    @Id
    @Column(name = "companyid")
    private long companyid;

    @Column(name = "companyname")
    private String companyname;

    @Column(name = "total_employees")
    private long totalemployees;

    public long getCompanyid() {
        return companyid;
    }

    public void setCompanyid(long companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public long getTotalemployees() {
        return totalemployees;
    }

    public void setTotalemployees(long totalemployees) {
        this.totalemployees = totalemployees;
    }
}
