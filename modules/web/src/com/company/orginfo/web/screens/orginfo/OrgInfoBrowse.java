package com.company.orginfo.web.screens.orginfo;

import com.company.orginfo.entity.OrgInfoTotalVIew;
import com.company.orginfo.service.OrgInfoImporterService;
import com.haulmont.charts.gui.components.charts.Chart;
import com.haulmont.cuba.core.entity.BaseGenericIdEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.RemoveOperation;
import com.haulmont.cuba.gui.Screens;
import com.haulmont.cuba.gui.app.core.file.FileUploadDialog;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.Filter;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.orginfo.entity.OrgInfo;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.inject.Inject;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@UiController("orginfo_OrgInfo.browse")
@UiDescriptor("org-info-browse.xml")
@LookupComponent("orgInfoesTable")
@LoadDataBeforeShow
public class OrgInfoBrowse extends StandardLookup<OrgInfo> {
    @Inject
    private Dialogs dialogs;
    @Inject
    private Screens screens;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private DataManager dataManager;

    @Inject
    private OrgInfoImporterService orgInfoImporterService;

    @Inject
    private CollectionLoader<OrgInfo> orgInfoesDl;

    private List<BaseGenericIdEntity> orgInfoList = new ArrayList<>();
    private OrgInfo orgInfo;
    @Inject
    private Filter filter;
    @Inject
    private CollectionLoader<OrgInfoTotalVIew> orgInfoTotalLoader;


    @Subscribe("btnImport")
    public void onBtnImportClick(Button.ClickEvent event) {
//        dialogs.createMessageDialog().withCaption("import excel").withMessage("import data").show();
        FileUploadDialog dialog = (FileUploadDialog) screens.create("fileUploadDialog", OpenMode.DIALOG);
        dialog.addAfterCloseListener(afterCloseEvent -> {
            orgInfoList.clear();
            UUID fileId = dialog.getFileId();
            if (fileId != null) {
                String fileName = dialog.getFileName();
                String extension = FilenameUtils.getExtension(fileName);
                switch (extension) {
                    case "xlsx":
                        uploadExcel(fileId);
                        break;
                    case "json":
                        uploadJson(fileId);
                        break;
                }
            }
        });
        screens.show(dialog);
    }

    private void uploadJson(UUID fileId) {
        File file = fileUploadingAPI.getFile(fileId);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            JSONParser jsonParser = new JSONParser();
            Object object = jsonParser.parse(new InputStreamReader(fileInputStream));
            JSONArray jsonArray = (JSONArray) object;
            jsonArray.forEach(orginfo -> parseOrgInfo((JSONObject) orginfo));
            orgInfoImporterService.doImport(orgInfoList);
            orgInfoesDl.load();
            orgInfoTotalLoader.load();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseOrgInfo(JSONObject jsonObject){
        orgInfo = dataManager.create(OrgInfo.class);
        orgInfo.setOrgCode((String) jsonObject.get("orgCode"));
        orgInfo.setOrgName((String) jsonObject.get("orgName"));
        orgInfo.setOrgType((String) jsonObject.get("orgType"));
        orgInfo.setOrgHeadEmail((String) jsonObject.get("orgHeadEmail"));
        orgInfoList.add(orgInfo);
    }

    public void uploadExcel(UUID fileId){
        try {
            File file = fileUploadingAPI.getFile(fileId);
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                orgInfo = dataManager.create(OrgInfo.class);
                orgInfo.setOrgCode(row.getCell(0).getStringCellValue());
                orgInfo.setOrgName(row.getCell(1).getStringCellValue());
                orgInfo.setOrgType(row.getCell(2).getStringCellValue());
                orgInfo.setOrgHeadEmail(row.getCell(3).getStringCellValue());
                orgInfoList.add(orgInfo);

            }
            if(orgInfoList.size() > 0){
                orgInfoImporterService.doImport(orgInfoList);
                orgInfoesDl.load();
                orgInfoTotalLoader.load();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe("pieChart")
    public void onPieChartSlicePullOut(Chart.SlicePullOutEvent event) {
       String orgType = event.getDataItem().getValue("orgType").toString();
       orgInfoesDl.setParameter("orgType", orgType);
       orgInfoesDl.load();
    }

    @Subscribe("pieChart")
    public void onPieChartSlicePullIn(Chart.SlicePullInEvent event) {
       orgInfoesDl.removeParameter("orgType");
       orgInfoesDl.load();
    }

    @Install(to = "orgInfoesTable.remove", subject = "afterActionPerformedHandler")
    private void orgInfoesTableRemoveAfterActionPerformedHandler(RemoveOperation.AfterActionPerformedEvent<OrgInfo> afterActionPerformedEvent) {
       orgInfoTotalLoader.load();
    }

    @Install(to = "orgInfoesTable.edit", subject = "afterCommitHandler")
    private void orgInfoesTableEditAfterCommitHandler(OrgInfo orgInfo) {
        orgInfoTotalLoader.load();
    }

    @Install(to = "orgInfoesTable.create", subject = "afterCommitHandler")
    private void orgInfoesTableCreateAfterCommitHandler(OrgInfo orgInfo) {
       orgInfoTotalLoader.load();
    }

}