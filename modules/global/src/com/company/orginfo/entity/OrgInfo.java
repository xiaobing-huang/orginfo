package com.company.orginfo.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "ORGINFO_ORG_INFO")
@Entity(name = "orginfo_OrgInfo")
public class OrgInfo extends StandardEntity {
    private static final long serialVersionUID = 2249159787347382814L;

    @NotNull
    @Column(name = "ORG_NAME", nullable = false, length = 50)
    protected String orgName;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @NotNull
    @Column(name = "ORG_CODE", nullable = false, length = 50)
    protected String orgCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @NotNull
    @Column(name = "ORG_TYPE", nullable = false, length = 50)
    protected String orgType;

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @NotNull
    @Column(name = "ORG_HEAD_EMAIL", nullable = false, length = 50)
    protected String orgHeadEmail;

    public String getOrgHeadEmail() {
        return orgHeadEmail;
    }

    public void setOrgHeadEmail(String orgHeadEmail) {
        this.orgHeadEmail = orgHeadEmail;

    }
}