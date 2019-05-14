package com.softtool.mysql.export;

import com.softtool.mysql.export.Utils.Markdown;
import com.softtool.mysql.export.Utils.Word;
import com.softtool.mysql.export.dao.ExportMapper;
import com.softtool.mysql.export.entities.Export;
import javafx.application.Application;
import org.apache.poi.xwpf.usermodel.*;
import org.mybatis.spring.annotation.MapperScan;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@MapperScan("com.softtool.SpiderMan.dao")
public class MysqlExportApplication {
    public static void main(String[] args) {
        System.exit(SpringApplication
                .exit(SpringApplication.run(MysqlExportApplication.class, args)));
    }

    @Autowired
    Word word;
    @Autowired
    Markdown markdown;

    @Bean
    public Integer starter() throws Exception {
//        word.save();
        markdown.save();
        return -1;
    }
}
