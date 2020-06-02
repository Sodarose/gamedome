package com.game.gameserver.util;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

/**
 * @author xuewenkang
 * @date 2020/6/1 11:32
 */
public class BlobTypeHandle extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ByteArrayInputStream bis;
        // 把String转化成byte流
        bis = new ByteArrayInputStream(parameter.getBytes(StandardCharsets.UTF_8));
        ps.setBinaryStream(i, bis, parameter.length());
    }

    /**
     * @param rs
     * @param columnName Colunm name, when configuration <code>useColumnLabel</code> is <code>false</code>
     */
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Blob blob = rs.getBlob(columnName);
        if(blob==null){
            return null;
        }
        return new String(blob.getBytes(1,
                (int) blob.length()), StandardCharsets.UTF_8);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Blob blob = rs.getBlob(columnIndex);
        if(blob==null){
            return null;
        }
        return new String(blob.getBytes(1,
                (int) blob.length()), StandardCharsets.UTF_8);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Blob blob = cs.getBlob(columnIndex);
        if(blob==null){
            return null;
        }
        return new String(blob.getBytes(1,
                (int) blob.length()), StandardCharsets.UTF_8);
    }
}
