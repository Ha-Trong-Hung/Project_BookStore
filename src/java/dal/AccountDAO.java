/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Account;
import entity.Category;
import entity.Product;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hatro
 */
public class AccountDAO extends DBContext {

    public ArrayList<Account> findAll() {
        ArrayList<Account> listFound = new ArrayList<>();
        connection = getConnection();
        String sql = "SELECT [id]\n"
                + "      ,[username]\n"
                + "      ,[password]\n"
                + "      ,[address]\n"
                + "      ,[roleID]\n"
                + "  FROM [dbo].[Account]";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                String address = resultSet.getString(4);
                int roleId = resultSet.getInt(5);
                Account account = new Account(id, username, password, address, roleId);
                listFound.add(account);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFound;
    }

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
        Account account = new Account("Tuong", "123");
        System.out.println(dao.insertAccount(account));
    }

    public Account findByUserNameAndPass(Account accout) {
        connection = getConnection();
        String sql = "SELECT [id]\n"
                + "      ,[username]\n"
                + "      ,[password]\n"
                + "      ,[address]\n"
                + "      ,[roleID]\n"
                + "  FROM [dbo].[Account]\n"
                + "  where username like ? and password like ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, accout.getUsername());
            statement.setString(2, accout.getPassword());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                String address = resultSet.getString(4);
                int roleId = resultSet.getInt(5);
                Account account = new Account(id, username, password, address, roleId);
                return account;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account insertAccount(Account account) {
        connection = getConnection();
        String sql = "INSERT INTO [dbo].[Account]\n"
                + "           ([username]\n"
                + "           ,[password]\n"
                + "           ,[address]\n"
                + "           ,[roleID])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,2)";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, account.getUsername());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getAddress());

            // thực thi câu lệnh
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return account;
    }

    public Account checkIsExit(Account account) {
        connection = getConnection();
        String sql = "SELECT [id]\n"
                + "      ,[username]\n"
                + "      ,[password]\n"
                + "      ,[address]\n"
                + "      ,[roleID]\n"
                + "  FROM [dbo].[Account]\n"
                + "  where username like ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, account.getUsername());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);
                String address = resultSet.getString(4);
                int roleId = resultSet.getInt(5);
                Account account1 = new Account(id, username, password, address, roleId);
                return account1;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
