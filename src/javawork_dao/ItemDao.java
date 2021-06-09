package javawork_dao;

import javawork_model.Item;
import javawork_utils.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;

/**
 * Operation class about item table in database
 */
public class ItemDao {
    DataSource dataSource = C3P0Util.getDataSource();

    /**
     * get all item data from database and return them as a list
     * @return a list of Item object
     * @exception SQLException
     */
    public List<Item> queryData() throws SQLException {
        QueryRunner qry = new QueryRunner(dataSource);
        String sql = "SELECT * FROM item ORDER BY update_date DESC";
        List<Item> itemList = qry.query(sql, new BeanListHandler<Item>(Item.class));
        return itemList;
    }

    /**
     * Insert item data
     * @param itemList a list of Item Object to insert
     * @exception SQLException
     */
    public void insertData(List<Item> itemList) throws SQLException {
        Connection conn = C3P0Util.getConnection();
        String sql = "INSERT INTO item(upc_code, description, category_id, unit_price, unit_size, " +
                "monthly_sales, company_id, update_date) VALUES (?, ?, ?, ?, ?, ?, ?, CURDATE()) " +
                "ON DUPLICATE KEY UPDATE " +
                "upc_code=VALUES(upc_code), description=VALUES(description), category_id=VALUES(category_id), " +
                "unit_price=VALUES(unit_price), unit_size=VALUES(unit_size), monthly_sales=VALUES(monthly_sales), " +
                "company_id=VALUES(company_id)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        for(int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            preparedStatement.setString(1, item.getUpc_code());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setInt(3, item.getCategory_id());
            preparedStatement.setFloat(4, item.getUnit_price());
            preparedStatement.setFloat(5, item.getUnit_size());
            preparedStatement.setInt(6, item.getMonthly_sales());
            preparedStatement.setInt(7, item.getCompany_id());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Delete item data by upc
     * @param upcList a list of upc code string to delete
     * @exception SQLException
     */
    public void deleteData(List<String> upcList) throws SQLException {
        Connection conn = C3P0Util.getConnection();
        String sql = "DELETE FROM item WHERE upc_code = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        for(int i = 0; i < upcList.size(); i++) {
            preparedStatement.setString(1, upcList.get(i));
            preparedStatement.executeUpdate();
        }
    }

    /**
     * search data by Map
     * @param params a Map which key is condition, value is search information
     * @return return search data in a list
     * @exception SQLException
     */
    public List<Item> searchData(Map<String, String> params) throws SQLException {
        QueryRunner qry = new QueryRunner(dataSource);
        String sql = "SELECT * FROM item WHERE 1=1 ";
        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        if(params.containsKey("description")) {
            builder.append(" AND description LIKE " + "'%" + params.get("description") + "%' " );
        }
        if(params.containsKey("upcStart") && params.containsKey("upcEnd")) {
            builder.append(" AND upc_code between " + params.get("upcStart") + " AND " + params.get("upcEnd"));
        } else if(params.containsKey("upcStart")) {
            builder.append(" AND upc_code >= " + params.get("upcStart"));
        } else if(params.containsKey("upcEnd")) {
            builder.append(" AND upc_code <= " + params.get("upcEnd"));
        }
        if(params.containsKey("category_id")) {
            builder.append(" AND category_id = " + params.get("category_id"));
        }
        if(params.containsKey("company_id")) {
            builder.append(" AND company_id = " + params.get("company_id"));
        }
        List<Item> itemList = qry.query(builder.toString(), new BeanListHandler<Item>(Item.class));
        return itemList;
    }

    /**
     * get item data by upc code
     * @param upc item's upc code
     * @return Item object list
     * @exception SQLException
     */
    public List<Item> searchDataByUpc(String upc) throws SQLException{
        QueryRunner qry = new QueryRunner(dataSource);
        String sql = "SELECT * FROM item WHERE upc_code = " + upc;
        List<Item> itemList = qry.query(sql, new BeanListHandler<Item>(Item.class));
        return itemList;
    }
}
