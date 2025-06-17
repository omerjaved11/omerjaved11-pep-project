package Service;

import java.util.ArrayList;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;


public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();

    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public ArrayList<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    public Message getMessage(int message_id){
        return messageDAO.getMessage(message_id);
    }

    public Message postMessage(Message msg) {
        Message addedMsg = null;
        if(null != msg && isValidMessage(msg.getMessage_text()) && isUserExist(msg.getPosted_by())){
            addedMsg = messageDAO.newMessage(msg);
        }
        return addedMsg;
    }

    private boolean isUserExist(int posted_by) {
     return null != accountDAO.getAccountById(posted_by);
    }

    private boolean isValidMessage(String message_text) {

      return !message_text.isBlank() && 
      !message_text.isEmpty() &&
      message_text.length()<=255 ; 
        }

    public Message deleteMessage(Message msg) {
        Message deletedMessage = messageDAO.getMessage(msg.getMessage_id());
        if(null != deletedMessage){
            messageDAO.deleteMessage(msg.getMessage_id());
        }
        return deletedMessage;
    }

    public Message changeMessage(Message reqMessage, int message_id) {
        Message respMessage = null;
        if(isValidMessage(reqMessage.getMessage_text()) && isMessageExist(message_id)){
            messageDAO.updateMessage(reqMessage,message_id);
            respMessage = messageDAO.getMessage(message_id);
        }
        
        return respMessage;
    }

    private boolean isMessageExist(int message_id) {
        return null != messageDAO.getMessage(message_id);
    }

    public ArrayList<Message> getUserMessages(Message reqMessage) {
        return messageDAO.getUserMessage(reqMessage.getPosted_by());
    }
    
}
