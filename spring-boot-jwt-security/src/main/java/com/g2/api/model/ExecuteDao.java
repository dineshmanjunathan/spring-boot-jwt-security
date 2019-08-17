package com.g2.api.model;

import com.g2.api.connection.DBConnection;
import com.google.gson.Gson;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

@Component
public class ExecuteDao {


    public User validateLogin(String username) {
        try (Connection connection = DBConnection.getConnection()) {

            //return prepareResponse(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
    }

    /**
    * Method to execute procedure
    * @param restRequest
    * return String response JSON
    * */
    public String executeProcedure(RestRequest restRequest) {
        try (Connection connection = DBConnection.getConnection()) {
            CallableStatement cstmt = connection.prepareCall(prepareQuery(restRequest));
            setParameters(restRequest, cstmt);
            ResultSet rs = cstmt.executeQuery();
            return prepareResponse(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to update values in procedure
     * @param restRequest
     *
     * */
    public String updateProcedure(RestRequest restRequest) {
        try (Connection connection = DBConnection.getConnection()) {
            CallableStatement cstmt = connection.prepareCall(prepareQuery(restRequest));
            setParameters(restRequest, cstmt);
            int updated = cstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *  Method to prepareQuery passed to DB
     * @param restRequest
     * return String query
     * */
    private String prepareQuery(RestRequest restRequest){
        StringBuilder query = new StringBuilder("CALL " + restRequest.getProcedureName() + "(");
        if (!CollectionUtils.isEmpty(restRequest.getParametersList())) {
            int size = restRequest.getParametersList().size();
            List<String> param = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                param.add("?");
            }
            query.append(String.join(",", param));
        }
        query.append(")");
        return query.toString();
    }

    private void setParameters(RestRequest restRequest, CallableStatement cstmt) throws Exception {
        try {
            int i = 1;
            for (Parameters parameters : restRequest.getParametersList()) {
                switch (parameters.getType()) {
                    case "CHAR":
                        cstmt.setString(i, parameters.getValue());
                        break;
                    case "INT":
                        cstmt.setInt(i, Integer.parseInt(parameters.getValue()));
                        break;
                    case "DATE":
                        cstmt.setDate(i, Date.valueOf(parameters.getValue()));
                        break;
                    default:
                        cstmt.setString(i, parameters.getValue());
                        break;
                }
                i++;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *  Method to prepareQuery passed to DB
     * @param rs
     * return String JOSN response
     * */
    private String prepareResponse(ResultSet rs) throws Exception {
        RestResponse restResponse = new RestResponse();
        Gson gson = new Gson();
        try {
            HashMap<String, Object> map = new HashMap<>();
            List<HashMap<String, Object>> resultList = new ArrayList<>();
            ResultSetMetaData rsmd = rs.getMetaData();
            int coulumnCount = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= coulumnCount; i++) {
                    map.put(rsmd.getColumnName(i), rs.getString(i));
                }
                resultList.add(map);
            }
            restResponse.setResult(resultList);
            return gson.toJson(restResponse);
        } catch (Exception e) {
            restResponse.setErrorMessage("Error in ResultSet");
            restResponse.setResult(Collections.EMPTY_LIST);
        }
        return gson.toJson(restResponse);
    }


}
