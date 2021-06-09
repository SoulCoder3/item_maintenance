package javawork_dao;

import javawork_model.Category;
import javawork_utils.C3P0Util;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Operation class about category table in database
 */
public class CategoryDao {
    DataSource dataSource = C3P0Util.getDataSource();

    /**
     * get a list of all category data
     * @return a list of all category data
     * @exception SQLException
     */
    public List<Category> queryCategory() throws SQLException {
        QueryRunner qry = new QueryRunner(dataSource);
        String sql = "SELECT * FROM category";
        List<Category> categoryList = qry.query(sql, new BeanListHandler<Category>(Category.class));
        return categoryList;
    }
}
