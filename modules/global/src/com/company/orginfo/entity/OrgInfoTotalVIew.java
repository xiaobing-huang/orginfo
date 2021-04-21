package com.company.orginfo.entity;

import com.haulmont.cuba.core.entity.BaseStringIdEntity;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.DesignSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@DesignSupport("{'dbView': true, 'generatedDdl': false}")
@Table(name = "ORG_TYPE_TOTAL_VIEW")
@Entity(name = "orginfo_OrgInfoTotalView")
public class OrgInfoTotalVIew extends BaseStringIdEntity {
    private static final long serialVersionUID = -9027919661156664151L;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Column(name = "total_count")
    protected Integer totalCount;

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Id
    @Column(name = "org_type")
    protected String orgType;

    @Override
    public String getId() {
        return orgType;
    }

    @Override
    public void setId(String id) {
        this.orgType = id;
    }
}