package javawork_service;

import javawork_dao.CompanyDao;
import javawork_model.Company;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Deal with company data
 */
public class CompanyService {
    public static Map<Integer, Company> companyMap = getCompanyMap();

    /**
     * get all company names
     * @return company name list
     * @exception SQLException
     */
    public String[] getCompanyName() {
        CompanyDao companyDao = new CompanyDao();
        String[] nameArray = null;
        try {
            List<Company> list = companyDao.queryCompanyName();
            nameArray = new String[list.size()];
            for(int i = 0; i < list.size(); i++) {
                nameArray[i] = list.get(i).getCompany_id() + "-" + list.get(i).getCompany_name();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return nameArray;
    }

    /**
     * get all company data
     * @param head Table's header
     * @return all company data
     * @exception SQLException
     */
    public static Object[][] getCompanyData(String[] head) {
        CompanyDao companyDao = new CompanyDao();
        List<Company> companyList = null;
        Object data[][] = null;
        try {
            companyList = companyDao.queryCompanyName();
            data = new Object[companyList.size()][head.length];
        } catch(SQLException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < companyList.size(); i++) {
            data[i][0] = companyList.get(i).getCompany_name();
            data[i][1] = companyList.get(i).getAddress();
            data[i][2] = companyList.get(i).getPhone_number();
        }
        return data;
    }

    /**
     * get a Map which key is company id, value is company object
     * @return a Map which key is company id, value is company object
     * @exception SQLException
     */
    public static Map<Integer, Company> getCompanyMap() {
        CompanyDao companyDao = new CompanyDao();
        Map<Integer, Company> companyMap = new HashMap<Integer, Company>();
        try{
            List<Company> list = companyDao.queryCompanyName();
            for(int i = 0; i < list.size(); i++) {
                companyMap.put(list.get(i).getCompany_id(),list.get(i));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return companyMap;
    }

    /**
     * Insert company data
     * @param companyList a company list to insert
     * @return true if inser successfully, else return false
     * @exception SQLException
     */
    public static boolean insertCompanyData(List<Company> companyList) {
        CompanyDao companyDao = new CompanyDao();
        boolean isUpdate = false;
        try {
            companyDao.insertData(companyList);
            isUpdate = true;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return isUpdate;
    }

    /**
     * delete company data by company name
     * @param nameList a string list of company name
     * @return true if delete successfully, else return false
     * @exception SQLException
     */
    public static boolean deleteCompanyData(List<String> nameList) {
        CompanyDao companyDao = new CompanyDao();
        Boolean isDelete = false;
        try {
            companyDao.deleteData(nameList);
            isDelete = true;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            return isDelete;
        }
    }
}
