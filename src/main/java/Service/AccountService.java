package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    
    public Account addAccount(Account account){
        Account newAccount = null;
    if(null != account && isUsernameValid(account.getUsername()) && 
    !isUserExist(account.getUsername()) && isPasswordValid(account.getPassword())){

       newAccount = accountDAO.addAccount(account);
    }
    return newAccount;    
    }
    
    public Account login(Account account){
        Account loggedAccount = null;
        if (isUsernameValid(account.getUsername()) && isPasswordValid(account.getPassword())) {
            loggedAccount = accountDAO.login(account);
        }
        return loggedAccount;
    }

    public boolean isUsernameValid(String username){
        return !username.isEmpty() && !username.isBlank();
    }
    public boolean isUserExist(String username){
        return  null != accountDAO.getAccount(username);
    }
    private boolean isPasswordValid(String password){
        return password.length() >= 4;
    }

}
