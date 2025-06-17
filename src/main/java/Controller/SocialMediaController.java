package Controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
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
//As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. 
//The body will contain a representation of a JSON Account, but will not contain an account_id.
    
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
// As a user, I should be able to verify my login on the endpoint POST localhost:8080/login.
//  The request body will contain a JSON representation of an Account, not containing an account_id. 
// In the future, this action may generate a Session token to allow the user to securely use the site.
//  We will not worry about this for now.       
    Account account = om.readValue(context.body(), Account.class);
    AccountService accountService = new AccountService();
    Account newAccount = accountService.login(account);
    if(null != newAccount){
    context.json(newAccount);
    } else{
        context.status(401);
    }

    }

     private void getAccountMessages(Context context){
// As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/accounts/{account_id}/messages.

     }

     private void postMessage(Context context){
// As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages.
//  The request body will contain a JSON representation of a message,
//  which should be persisted to the database, but will not contain a message_id.
    }
     private void getAllMessages(Context context){
// - The response body should contain a JSON representation of a list containing all messages retrieved from the database.
//  It is expected for the list to simply be empty if there are no messages.
//   The response status should always be 200, which is the default.

}
    private void getMessage(Context context){
    // As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{message_id}.
    
     }

    private void deleteMessage(Context context){
// As a User, I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}.

     }
    private void changeMessage(Context context){

// As a user, I should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}.
//  The request body should contain a new message_text values to replace the message identified by message_id.
//   The request body can not be guaranteed to contain any other information.

     }

}