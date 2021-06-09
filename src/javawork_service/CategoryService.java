package javawork_service;

import javawork_dao.CategoryDao;
import javawork_model.Category;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Deal with category data
 */
public class CategoryService {
    public static Map<Integer, Category> categoryMap = getCategoryMap();

    /**
     * get a Map which key is category id, value is category object
     * @return a Map which key is category id, value is category object
     * @exception SQLException
     */
    public static Map<Integer, Category> getCategoryMap() {
        CategoryDao companyDao = new CategoryDao();
        Map<Integer, Category> categoryMap = new HashMap<Integer, Category>();
        try{
            List<Category> list = companyDao.queryCategory();
            for(int i = 0; i < list.size(); i++) {
                categoryMap.put(list.get(i).getId(), list.get(i));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return categoryMap;
    }
}
