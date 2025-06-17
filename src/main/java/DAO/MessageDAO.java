package DAO;

import java.util.ArrayList;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
public class MessageDAO {
    
    public Message newMessage(Message msg){
        Connection con = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message (posted_by,message_text, time_posted_epoch) values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, msg.getPosted_by());
            ps.setString(2,msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                msg.setMessage_id(rs.getInt(1));
                return msg;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

       public ArrayList<Message> getAllMessages(){
        Connection con = ConnectionUtil.getConnection();
        ArrayList<Message> msgList = null;
        try {
            String sql = "select * from message;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs =  ps.executeQuery();
            msgList = new ArrayList<>();
            while(rs.next()){
                Message newMsg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                 rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                msgList.add(newMsg);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return msgList;
    }

       public Message getMessage(int message_id){
        Connection con = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,message_id);
            ResultSet rs =  ps.executeQuery();
            while(rs.next()){
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                 rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

       public boolean deleteMessage(int message_id){
        Connection con = ConnectionUtil.getConnection();
        boolean isRowDeleted = false;
        try {
            String sql = "delete from message where message_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,message_id);
            isRowDeleted = ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return isRowDeleted;
    }

       public boolean updateMessage(Message msg, int message_id){
        Connection con = ConnectionUtil.getConnection();
        boolean isRowDeleted = false;
        try {
            String sql = "update message set message_text = ? where message_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, msg.getMessage_text());
            ps.setInt(2,message_id);
            isRowDeleted = ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return isRowDeleted;
    }
        public ArrayList<Message> getUserMessage(int posted_by){
            Connection con = ConnectionUtil.getConnection();
            ArrayList<Message> msgList = null;

            try {
                String sql = "select * from message where posted_by = ?;";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1,posted_by);
                ResultSet rs =  ps.executeQuery();
                            msgList = new ArrayList<>();

                while(rs.next()){
                Message newMsg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                    rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                    msgList.add(newMsg);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return msgList;
        }
}
