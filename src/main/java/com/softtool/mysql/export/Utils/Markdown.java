package com.softtool.mysql.export.Utils;


import com.softtool.mysql.export.dao.ExportMapper;
import com.softtool.mysql.export.entities.Export;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;


@Component
public class Markdown {

    @Autowired
    ExportMapper mapper;


    @Value("${export.tableSchema}")
    private String tableSchema;

    private String end="\n";


    public  void save() throws Exception {
        String string="";
        string="# " +tableSchema +end;


        List<Map<String, String>> maps = mapper.selectTableName(tableSchema);
        for (int i = 0; i < maps.size(); i++) {
            Map<String, String> stringStringMap = maps.get(i);
            string=string+ "## "+stringStringMap.get("table_name") +" "+stringStringMap.get("table_comment")+end;

            string=string+"| 列名 | 数据类型| 必输 | 注释 |"+end;
            string=string+"| -- | -- | -- | -- |"+end;
                    List<Export> lists = mapper.selectTableCols(tableSchema, stringStringMap.get("table_name"));
            for (int j = 0; j < lists.size(); j++) {
                Export export = lists.get(j);
                string=string+"|"+export.getCOLUMN_NAME()+"|"+export.getCOLUMN_TYPE()+"|"+
                        export.getIS_NULLABLE()+"|"+export.getCOLUMN_COMMENT()+"|"+end;
            }
        }
        FileUtils.writeStringToFile(new File(tableSchema+".md"),string,"gb2312");
    }


}