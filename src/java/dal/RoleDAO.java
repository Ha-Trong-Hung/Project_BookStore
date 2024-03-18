/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Account;
import entity.Role;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hatro
 */
public class RoleDAO extends DBContext {

    public ArrayList<Role> findAll() {
        ArrayList<Role> listFound = new ArrayList<>();
        connection = getConnection();
        String sql = "SELECT [id]\n"
                + "      ,[name]\n"
                + "  FROM [dbo].[Role]";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
               
                Role role = new Role(id, name);
                listFound.add(role);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;
    }

    public static void main(String[] args) {
        RoleDAO dao = new RoleDAO();
        System.out.println(dao.findAll());
    }
}
