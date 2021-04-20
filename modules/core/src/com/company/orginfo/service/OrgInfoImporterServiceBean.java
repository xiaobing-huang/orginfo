package com.company.orginfo.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.BaseGenericIdEntity;
import com.haulmont.cuba.core.global.PersistenceHelper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service(OrgInfoImporterService.NAME)
public class OrgInfoImporterServiceBean implements OrgInfoImporterService {


    @Inject
    Persistence persistence;

//    @Inject
//    private Metadata metadata;

    public void doImport(List<BaseGenericIdEntity> orgInfoList) {
        orgInfoList.forEach(this::persistEntity);

    }

    protected void persistEntity(BaseGenericIdEntity orgInfo) {
        try (Transaction tx = persistence.getTransaction()) {
            EntityManager entityManager = persistence.getEntityManager();
            if (PersistenceHelper.isNew(orgInfo)) {
                entityManager.persist(orgInfo);
            } else {
                entityManager.merge(orgInfo);
            }
            tx.commit();
        }
    }

}