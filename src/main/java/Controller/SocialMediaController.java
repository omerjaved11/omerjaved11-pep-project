package Controller;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    ObjectMapper om = new ObjectMapper();
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::register);
        app.post("/login",this::login);
        app.post("/messages", this::postMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessage);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::changeMessage);
        app.get("/accounts/{account_id}/messages",this::getAccountMessages);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void register(Context context) throws JsonMappingException, JsonProcessingException{    
        Account account = om.readValue(context.body(), Account.class);
        AccountService accountService = new AccountService();
        Account newAccount = accountService.addAccount(account);
        if(null != newAccount){
        context.json(newAccount);
        } else{
            context.status(400);
        }
    }
    private void login(Context context) throws JsonMappingException, JsonProcessingException{
        Account account = om.readValue(context.body(), Account.class);
        AccountService accountService = new AccountService();
        Account loginAccount = accountService.login(account);
        if(null != loginAccount){
        context.json(loginAccount);
        } else{
            context.status(401);
        }
    }

     private void getAccountMessages(Context context) throws JsonMappingException, JsonProcessingException{
        MessageService msgService = new MessageService();
        Message reqMessage = new Message();
        reqMessage.setPosted_by(Integer.parseInt(context.pathParam("account_id")));
        ArrayList<Message> respMessageList = msgService.getUserMessages(reqMessage);
        context.json(respMessageList);
     }

     private void postMessage(Context context) throws JsonMappingException, JsonProcessingException{
        MessageService msgService = new MessageService();
        Message reqMessage = om.readValue(context.body(), Message.class);
        Message respMessage = msgService.postMessage(reqMessage);
        if(null != respMessage){
        context.json(respMessage);
        } else {
            context.status(400);
        }
        
    }
     private void getAllMessages(Context context) throws JsonMappingException, JsonProcessingException{
        MessageService msgService = new MessageService();
        ArrayList<Message> messageList = msgService.getAllMessages();
        context.json(messageList);
}
    private void getMessage(Context context) throws JsonMappingException, JsonProcessingException{
        MessageService msgService = new MessageService();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message respMessage = msgService.getMessage(message_id);
        if(null != respMessage){
        context.json(respMessage);
        } else {
            context.json("");
        }
     }

    private void deleteMessage(Context context){
         MessageService msgService = new MessageService();
        Message reqMessage = new Message();
        reqMessage.setMessage_id(Integer.parseInt(context.pathParam("message_id")));
        Message respMessage = msgService.deleteMessage(reqMessage);
        if(null != respMessage){
        context.json(respMessage);
        } else {
            context.json("");
        }
     }
    private void changeMessage(Context context) throws JsonMappingException, JsonProcessingException{

        MessageService msgService = new MessageService();
        Message reqMessage = om.readValue(context.body(), Message.class);
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message respMessage = msgService.changeMessage(reqMessage,message_id);
        if(null != respMessage){
        context.json(respMessage);
        } else {
            context.status(400);
        }
     }
}