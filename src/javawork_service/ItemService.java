package javawork_service;

import javawork_dao.ItemDao;
import javawork_model.Item;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Deal with item data
 */
public class ItemService {

    /**
     * get all item data from database and fill them into two-dimensional array
     * @param head Table's head String
     * @return Two-dimensional array of item data
     * @exception SQLException
     */
    public static Object[][] fillData(String[] head) {
        ItemDao itemDao = new ItemDao();
        List<Item> itemList = null;
        Object[][] data = null;
        try {
            itemList = itemDao.queryData();
            data = new Object[itemList.size()][head.length];
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < itemList.size(); i++) {
            data[i][0] = itemList.get(i).getUpc_code();
            data[i][1] = itemList.get(i).getDescription();
            data[i][2] = itemList.get(i).getCategory_id() + "-" +
                    CategoryService.getCategoryMap().get(itemList.get(i).getCategory_id()).getCategory_name();
            data[i][3] = itemList.get(i).getUnit_price();
            data[i][4] = itemList.get(i).getUnit_size();
            data[i][5] = itemList.get(i).getMonthly_sales();
            data[i][6] = itemList.get(i).getCompany_id() + "-" +
                    CompanyService.getCompanyMap().get(itemList.get(i).getCompany_id()).getCompany_name();
            data[i][7] = itemList.get(i).getUpdate_date();
        }
        return data;
    }

    /**
     * Insert item data
     * @param itemList a list of Item Object to insert
     * @return return true if insert successfully, else return false
     * @exception SQLException
     */
    public static boolean insertData(List<Item> itemList) {
        ItemDao itemDao = new ItemDao();
        Boolean isUpdate = false;
        try {
            itemDao.insertData(itemList);
            isUpdate = true;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            return isUpdate;
        }
    }

    /**
     * Delete item data by upc
     * @param upcList a list of upc string to delete
     * @return return true if delete successfully, else return false
     * @exception SQLException
     */
    public static boolean deleteData(List<String> upcList) {
        ItemDao itemDao = new ItemDao();
        Boolean isDelete = false;
        try {
            itemDao.deleteData(upcList);
            isDelete = true;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            return isDelete;
        }
    }

    /**
     * search data by information and return two dimensional array
     * @param description a string if item description contain
     * @param upcStart a string item upc code start
     * @param upcEnd a string item upc code end
     * @param category_id search by category id
     * @param company_id search by company id
     * @return return search data in two dimensional array
     * @exception SQLException
     */
    public static Object[][] searchData(String description, String upcStart, String upcEnd,
                                        String category_id, String company_id) {
        Map<String, String> params = new HashMap<String, String>();
        ItemDao itemDao = new ItemDao();
        List<Item> itemList = null;
        Object[][] data = null;

        if(!description.equals("")) {
            params.put("description", description);
        }
        if(!upcStart.equals("")) {
            params.put("upcStart", upcStart);
        }
        if(!upcEnd.equals("")) {
            params.put("upcEnd", upcEnd);
        }
        if(category_id != null) {
            params.put("category_id", category_id);
        }
        if(company_id != null) {
            params.put("company_id", company_id);
        }
        try {
           itemList = itemDao.searchData(params);
           data = new Object[itemList.size()][7];
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < itemList.size(); i++) {
            data[i][0] = itemList.get(i).getUpc_code();
            data[i][1] = itemList.get(i).getDescription();
            data[i][2] = itemList.get(i).getCategory_id() + "-" +
                    CategoryService.getCategoryMap().get(itemList.get(i).getCategory_id()).getCategory_name();
            data[i][3] = itemList.get(i).getUnit_price();
            data[i][4] = itemList.get(i).getUnit_size();
            data[i][5] = itemList.get(i).getMonthly_sales();
            data[i][6] = itemList.get(i).getCompany_id() + "-" +
                    CompanyService.getCompanyMap().get(itemList.get(i).getCompany_id()).getCompany_name();
        }
        return data;
    }

    /**
     * get item data by upc code
     * @param upc item's upc code
     * @return Item object
     * @exception SQLException
     */
    public static Item getDataByUpc(String upc) {
        ItemDao itemDao = new ItemDao();
        List<Item> itemList = null;
        Item item = null;
        try {
            itemList = itemDao.searchDataByUpc(upc);
            if(itemList.size() != 0) {
                item = itemList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
