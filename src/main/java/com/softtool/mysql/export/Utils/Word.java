package com.softtool.mysql.export.Utils;


import com.softtool.mysql.export.dao.ExportMapper;
import com.softtool.mysql.export.entities.Export;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;


@Component
public class Word {

    @Autowired
    ExportMapper mapper;


    @Value("${export.tableSchema}")
    private String tableSchema;


    public  void save() throws Exception {
        XWPFDocument document = new XWPFDocument();

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File(tableSchema+".docx"));  // 下载路径/文件名称


        //添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        titleParagraph.setStyle("标题 2");
        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText(tableSchema);
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setFontSize(20);


        List<Map<String, String>> maps = mapper.selectTableName(tableSchema);
        System.out.println("tableSchema = " + tableSchema);
        System.out.println("maps = " + maps.size());
        for (int i = 0; i < maps.size(); i++) {
            Map<String, String> stringStringMap = maps.get(i);

            System.out.println("tableSchema  " + tableSchema+"--"+ stringStringMap.get("table_name"));
            List<Export> lists = mapper.selectTableCols(tableSchema, stringStringMap.get("table_name"));
            System.out.println("lists.size() = " + lists.size());


            //段落
            XWPFParagraph tableNameParagraph = document.createParagraph();
            tableNameParagraph.setStyle("标题 3");
            XWPFRun run = tableNameParagraph.createRun();
            run.setText(stringStringMap.get("table_name") + " " +stringStringMap.get("table_comment"));
            run.setColor("696969");
            run.setFontSize(16);

            //基本信息表格
            XWPFTable infoTable = document.createTable();

            //列宽自动分割
            CTTblWidth comTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
            comTableWidth.setType(STTblWidth.DXA);
            comTableWidth.setW(BigInteger.valueOf(9072));

            //表格第一行
            XWPFTableRow comTableRowOne = infoTable.getRow(0);
            comTableRowOne.getCell(0).setText("列名");
            comTableRowOne.addNewTableCell().setText("数据类型");
            comTableRowOne.addNewTableCell().setText("必输");
            comTableRowOne.addNewTableCell().setText("注释");


            for (int j = 0; j < lists.size(); j++) {
                Export export = lists.get(j);
                System.out.println("export.toString() = " + export.toString());

                //表格第二行
                XWPFTableRow comTableRowTwo = infoTable.createRow();
                comTableRowTwo.getCell(0).setText(export.getCOLUMN_NAME());
                comTableRowTwo.getCell(1).setText(export.getCOLUMN_TYPE());
                comTableRowTwo.getCell(2).setText(export.getIS_NULLABLE());
                comTableRowTwo.getCell(3).setText(export.getCOLUMN_COMMENT());


            }

        }
        document.write(out);
        out.close();
        System.out.println("create_table document written success.");
    }


}