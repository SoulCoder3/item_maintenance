package javawork_dao;

import javawork_model.Company;
import javawork_utils.C3P0Util;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Operation class about company table in database
 */
public class CompanyDao {
    DataSource dataSource = C3P0Util.getDataSource();

    /**
     * get all company names
     * @return company name list
     * @exception SQLException
     */
    public List<Company> queryCompanyName() throws SQLException {
        QueryRunner qry = new QueryRunner(dataSource);
        String sql = "SELECT * FROM company";
        List<Company> nameList = qry.query(sql, new BeanListHandler<Company>(Company.class));
        return nameList;
    }

    /**
     * Insert company data
     * @param companyList a company list to insert
     * @exception SQLException
     */
    public void insertData(List<Company> companyList) throws SQLException{
        Connection conn = C3P0Util.getConnection();
        String sql = "INSERT INTO company(company_name, address, phone_number)" +
                " VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "company_name = VALUES(company_name), address=VALUES(address), phone_number=VALUES(phone_number)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        for(int i = 0; i < companyList.size(); i++) {
            Company company = companyList.get(i);
            preparedStatement.setString(1, company.getCompany_name());
            preparedStatement.setString(2, company.getAddress());
            preparedStatement.setString(3, company.getPhone_number());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * delete company data by company name
     * @param nameList a string list of company name
     * @exception SQLException
     */
    public void deleteData(List<String> nameList) throws SQLException{
        Connection conn = C3P0Util.getConnection();
        String sql = "DELETE FROM company WHERE company_name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        for(int i = 0; i < nameList.size(); i++) {
            preparedStatement.setString(1, nameList.get(i));
            preparedStatement.executeUpdate();
        }
    }

}
