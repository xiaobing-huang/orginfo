package com.company.orginfo.service;

import com.haulmont.cuba.core.entity.BaseGenericIdEntity;
import com.haulmont.cuba.core.global.PersistenceHelper;

import javax.inject.Inject;
import java.util.List;

public interface OrgInfoImporterService {
    String NAME = "orginfo_OrgInfoImporterService";


    void doImport(List<BaseGenericIdEntity> orgInfoList);
}