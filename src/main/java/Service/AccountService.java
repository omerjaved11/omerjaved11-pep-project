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
    if(isUsernameValid(account) && isUserExist(account) && isPasswordValid(account)){
       newAccount = accountDAO.addAccount(account);
    }
    return newAccount;    
    }
    
    public Account login(Account account){
        Account loggedAccount = null;
        if (isUsernameValid(account) && isPasswordValid(account)) {
            loggedAccount = accountDAO.login(account);
        }
        return loggedAccount;
    }

    private boolean isUsernameValid(Account account){
        return null != account.getUsername() && !account.getUsername().isEmpty() && !account.getUsername().isBlank();
    }
    private boolean isUserExist(Account account){
        return  null == accountDAO.geAccount(account);
    }
    private boolean isPasswordValid(Account account){
        return  null != account.password && account.password.length() >= 4;
    }

}
