/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Category;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 *
 * @author hatro
 */
public class CategoryDAO extends DBContext {

    public ArrayList<Category> findAll() {
        ArrayList<Category> listFound = new ArrayList<>();
        // connect with DB
        connection = getConnection();
        // viết câu lệnh sql
        String sql = "select *\n"
                + "from Category";
        try {
            // tạo đối tượng preparestatement
            statement = connection.prepareStatement(sql);
            // thực thi câu lệnh
            resultSet = statement.executeQuery();
            // trả về kết quả
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Category product = new Category(id, name);
                listFound.add(product);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;
    }

}
