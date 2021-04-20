package com.company.orginfo.web.screens.orginfo;

import com.haulmont.cuba.gui.screen.*;
import com.company.orginfo.entity.OrgInfo;

@UiController("orginfo_OrgInfo.edit")
@UiDescriptor("org-info-edit.xml")
@EditedEntityContainer("orgInfoDc")
@LoadDataBeforeShow
public class OrgInfoEdit extends StandardEditor<OrgInfo> {
}