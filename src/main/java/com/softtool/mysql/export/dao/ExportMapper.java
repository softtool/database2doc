package com.softtool.mysql.export.dao;


import com.softtool.mysql.export.entities.Export;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExportMapper {

//    @Select("SELECT table_name , table_comment FROM information_schema.TABLES WHERE table_schema  = #{tableSchema}")
    @Select("SELECT table_name , table_comment FROM information_schema.TABLES WHERE table_schema = #{tableSchema}  and TABLE_NAME like 'fg_aggr_%' ")
    List<Map<String,String>> selectTableName(String tableSchema);

    @Select("SELECT   TABLE_NAME,COLUMN_NAME,COLUMN_DEFAULT,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,COLUMN_TYPE,COLUMN_KEY,COLUMN_COMMENT FROM  INFORMATION_SCHEMA.COLUMNS " +
            " where table_schema = #{tableSchema}   and table_name =  #{tableName} ")
    List<Export> selectTableCols(@Param("tableSchema") String tableSchema,@Param("tableName") String tableName);


}