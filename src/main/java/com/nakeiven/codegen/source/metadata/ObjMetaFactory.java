///*
// * 文件名称: ObjMetaFactory.java
// * 版权信息: Copyright 2001-2012 ZheJiang Collaboration Data System Co., LTD. All right reserved.
// * ----------------------------------------------------------------------------------------------
// * 修改历史:
// * ----------------------------------------------------------------------------------------------
// * 修改原因: 新增
// * 修改人员: huangwb
// * 修改日期: 2012-3-1
// * 修改内容: 
// */
//package com.manfen.codegen.source.metadata;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.cds.dmg.mdm.DbColumn;
//import com.cds.dmg.mdm.MetaData;
//import com.cds.dmg.mdm.MetaDataQueryCondition;
//import com.cds.dmg.mdm.service.MetaDataService;
//import com.cds.dmg.mdm.service.RelationalMetaDataService;
//import com.manfen.codegen.bean.ArgInfo;
//import com.manfen.codegen.bean.Column;
//import com.manfen.codegen.bean.Obj;
//import com.manfen.codegen.source.DefaultObjFactory;
//import com.manfen.codegen.source.ObjFactory;
//
//
///**
// * 
// * @author <a href="mailto:huangwb@zjcds.com">huangwb</a> created on 2012-3-1
// * @since DE6.0
// */
//public class ObjMetaFactory implements ObjFactory {
//    
//    @Autowired
//    private MetaDataService metaDataService;
//    
//    @Autowired
//    private RelationalMetaDataService relationalMetaDataService;
//    
//    @Override
//    public Obj getObj(ArgInfo argInfo) {
//        String strTableName = argInfo.getTableName();
//        List<DbColumn> columns = getColumns(strTableName);
//        return new DefaultObjFactory().genObj(argInfo, conDbColumn2Column(columns));
//    }
//    
//    private List<Column> conDbColumn2Column(List<DbColumn> dbColumns) {
//        List<Column> columns = new ArrayList<Column>();
//        Integer isPrimary = null;
//        Boolean bIsPrimary = false;
//        for (DbColumn dbColumn : dbColumns) {
//            isPrimary = dbColumn.getIsPrimary();
//            if(isPrimary == 1) {
//                
//            }
//            columns.add(new Column(dbColumn.getMetaDataName(),dbColumn.getJdbcType(),bIsPrimary));
//        }
//        return columns;
//    }
//    
//    /**
//     * get Columns by tableName
//     * @param strTableName
//     * @author huangwb created on 2012-2-16 
//     * @since DE6.0
//     */
//    private List<DbColumn> getColumns(String strTableName) {
//        MetaDataQueryCondition condition = new MetaDataQueryCondition();
//        condition.setMetaDataName(strTableName);
//        List<MetaData> captureMetaDatas = metaDataService.queryMetaData(condition);
//        MetaData tableMD = null;
//        if (null != captureMetaDatas && captureMetaDatas.size() > 0) {
//            tableMD = captureMetaDatas.get(0);
//        } 
//        else {
//            tableMD = null;
//        }
//        List<DbColumn> columns = new ArrayList<DbColumn>();
//        if (tableMD != null) {
//            String tableId = tableMD.getMetaDataId();
//            List<String> columnIds = relationalMetaDataService.getChildrenIds(tableId);
//            columns = new ArrayList<DbColumn>();
//            for (String dbColumnMetaDataId : columnIds) {
//                columns.add(relationalMetaDataService.getDbColumnMetaData(dbColumnMetaDataId));
//            }
//        }
//        return columns;
//    }
//    
//}
