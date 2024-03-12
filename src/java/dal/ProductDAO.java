/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import constant.CommonConst;
import entity.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author hatro
 */
public class ProductDAO extends DBContext {

    public ArrayList<Product> findAll() {
        ArrayList<Product> listFound = new ArrayList<>();
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "select *\n"
                + "from Product";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String image = resultSet.getString("image");
                int quantity = resultSet.getInt("quantity");
                float price = resultSet.getFloat("price");
                String description = resultSet.getString("description");
                int categoryId = resultSet.getInt("categoryId");
                Product product = new Product(id, name, image, quantity, price, description, categoryId);
                listFound.add(product);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;
    }

    public Product findById(int id) {
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "      ,[image]\n"
                + "      ,[quantity]\n"
                + "      ,[price]\n"
                + "      ,[description]\n"
                + "      ,[categoryId]\n"
                + "  FROM [dbo].[Product]\n"
                + "  where id = ?";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int idSearch = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String image = resultSet.getString(3);
                int quantity = resultSet.getInt(4);
                float price = resultSet.getFloat(5);
                String description = resultSet.getString(6);
                int categoryId = resultSet.getInt(7);
                Product product = new Product(idSearch, name, image, quantity, price, description, categoryId);
                return product;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int findTotalRecord() {
        ArrayList<Product> findTotalRecord = new ArrayList<>();
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "select *\n"
                + "from Product p\n";

        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);

            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String image = resultSet.getString(3);
                int quantity = resultSet.getInt(4);
                float price = resultSet.getFloat(5);
                String description = resultSet.getString(6);
                int categoryId = resultSet.getInt(7);
                Product product = new Product(id, name, image, quantity, price, description, categoryId);
                findTotalRecord.add(product);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return findTotalRecord.size();
    }

    public ArrayList<Product> findByPage(int page) {
        ArrayList<Product> listFoundByPage = new ArrayList<>();
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "select *\n"
                + "from Product p\n"
                + "order by id\n"
                + "offset ? rows\n"
                + "fetch next ? rows only";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setInt(1, (page - 1) * CommonConst.RECORD_PER_PAGE);
            statement.setInt(2, CommonConst.RECORD_PER_PAGE);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int idSearch = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String image = resultSet.getString(3);
                int quantity = resultSet.getInt(4);
                float price = resultSet.getFloat(5);
                String description = resultSet.getString(6);
                int categoryId = resultSet.getInt(7);
                Product product = new Product(idSearch, name, image, quantity, price, description, categoryId);
                listFoundByPage.add(product);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFoundByPage;
    }

    public ArrayList<Product> findByCategory(String categoryIdSearch, int page) {
        ArrayList<Product> listFoundByCategory = new ArrayList<>();
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "select *\n"
                + "from Product p\n"
                + "where p.categoryId = ?\n"
                + "order by id\n"
                + "offset ? rows\n"
                + "fetch next ? rows only";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setString(1, categoryIdSearch);
            statement.setInt(2, (page - 1) * CommonConst.RECORD_PER_PAGE);
            statement.setInt(3, CommonConst.RECORD_PER_PAGE);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int idSearch = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String image = resultSet.getString(3);
                int quantity = resultSet.getInt(4);
                float price = resultSet.getFloat(5);
                String description = resultSet.getString(6);
                int categoryId = resultSet.getInt(7);
                Product product = new Product(idSearch, name, image, quantity, price, description, categoryId);
                listFoundByCategory.add(product);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFoundByCategory;
    }

    public int findTotalRecordByCategory(String categoryIdSearch) {
        ArrayList<Product> countCategory = new ArrayList<>();
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "select *\n"
                + "from Product p\n"
                + "where p.categoryId = ?";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setString(1, categoryIdSearch);

            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String image = resultSet.getString(3);
                int quantity = resultSet.getInt(4);
                float price = resultSet.getFloat(5);
                String description = resultSet.getString(6);
                int categoryId = resultSet.getInt(7);
                Product product = new Product(id, name, image, quantity, price, description, categoryId);
                countCategory.add(product);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return countCategory.size();
    }

    public ArrayList<Product> findByName(String keyword, int page) {
        ArrayList<Product> listFoundByName = new ArrayList<>();
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "select *\n"
                + "from Product p\n"
                + "where p.name like ? \n"
                + "order by id\n"
                + "offset ? rows\n"
                + "fetch next ? rows only";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + keyword + "%");
            statement.setInt(2, (page - 1) * CommonConst.RECORD_PER_PAGE);
            statement.setInt(3, CommonConst.RECORD_PER_PAGE);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int idSearch = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String image = resultSet.getString(3);
                int quantity = resultSet.getInt(4);
                float price = resultSet.getFloat(5);
                String description = resultSet.getString(6);
                int categoryId = resultSet.getInt(7);
                Product product = new Product(idSearch, name, image, quantity, price, description, categoryId);
                listFoundByName.add(product);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFoundByName;
    }

    public int findTotalRecordByName(String keyword) {
        ArrayList<Product> listFoundByName = new ArrayList<>();
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "select *\n"
                + "from Product\n"
                + "where Product.name like ?";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + keyword + "%");
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String image = resultSet.getString(3);
                int quantity = resultSet.getInt(4);
                float price = resultSet.getFloat(5);
                String description = resultSet.getString(6);
                int categoryId = resultSet.getInt(7);
                Product product = new Product(id, name, image, quantity, price, description, categoryId);
                listFoundByName.add(product);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFoundByName.size();
    }

    public Product addProductInDB(Product product) {

        connection = getConnection();
        String sql = "INSERT INTO [dbo].[Product]\n"
                + "           ([name]\n"
                + "           ,[image]\n"
                + "           ,[quantity]\n"
                + "           ,[price]\n"
                + "           ,[description]\n"
                + "           ,[categoryId])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getImage());
            statement.setInt(3, product.getQuantity());
            statement.setFloat(4, product.getPrice());
            statement.setString(5, product.getDescription());
            statement.setInt(6, product.getCategoryId());
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return product;

    }

    public void deleteProductById(int id) {
        connection = getConnection();
        String sql = "DELETE FROM [dbo].[Product]\n"
                + "      WHERE id = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editProduct(Product product) {
        connection = getConnection();
        String sql = "UPDATE [dbo].[Product]\n"
                + "   SET [name] = ?\n"
                + "      ,[image] = ?\n"
                + "      ,[quantity] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[categoryId] = ?\n"
                + " WHERE id = ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getImage());
            statement.setInt(3, product.getQuantity());
            statement.setFloat(4, product.getPrice());
            statement.setString(5, product.getDescription());
            statement.setInt(6, product.getCategoryId());
            statement.setInt(7, product.getId());
            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
