package DAO;
import java.sql.SQLException;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {
    
    public Account addAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username,password) VALUES (?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                account.setAccount_id((int)rs.getLong(1));
                return account;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        

        return null;
    }
    public Account geAccount(Account account){
        Connection con = ConnectionUtil.getConnection();
        try {
            String sql = "Select * from account where username = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,account.getUsername());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
            
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return null;
    }

     public Account login(Account account){
        Connection con = ConnectionUtil.getConnection();
        try {
            String sql = "Select * from account where username = ? and password = ? ;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,account.getUsername());
            ps.setString(2, account.getPassword());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
            
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return null;
    }
}
