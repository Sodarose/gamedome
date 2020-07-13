package com.game.gameserver.util.typehandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.*;


/**
 * Json
 *
 * @author xuewenkang
 * @date 2020/7/7 11:03
 */
public class JsonTypeHandler extends BaseTypeHandler<JSONObject> {
    private static final String DEFAULT_CHARSET = "UTF-8";
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONObject parameter, JdbcType jdbcType) throws SQLException {
        String jsonString = parameter.toJSONString();
        ByteArrayInputStream bis = null;
        try {
            bis = new ByteArrayInputStream(jsonString.getBytes(DEFAULT_CHARSET));
            ps.setBinaryStream(i,bis,jsonString.getBytes().length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param rs
     * @param columnName Colunm name, when configuration <code>useColumnLabel</code> is <code>false</code>
     */
    @Override
    public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Blob blob = rs.getBlob(columnName);
        byte[] value = null;
        if(blob==null){
            return null;
        }
        try {

            if(value==null){
                return null;
            }
            String result = new String(value, DEFAULT_CHARSET);
            return JSON.parseObject(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Blob blob = rs.getBlob(columnIndex);
        byte[] value = null;
        if(blob==null){
            return null;
        }
        try {
            if(value==null){
                return null;
            }
            String result = new String(value, DEFAULT_CHARSET);
            JSONObject jsonObject = JSON.parseObject(result);
            return jsonObject;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Blob blob = cs.getBlob(columnIndex);
        byte[] value = null;
        if(blob==null){
            return null;
        }
        try {
            if(value==null){
                return null;
            }
            String result = new String(value, DEFAULT_CHARSET);
            JSONObject jsonObject = JSON.parseObject(result);
            return jsonObject;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
