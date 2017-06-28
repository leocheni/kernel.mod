package net.msdh.kernel.security;

import net.minidev.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 26.05.17
 * Time: 13:22
 * To change this template use File | Settings | File Templates.
 */
public class User {
  private String login;
  private String password;
  private String name;
  private boolean logged;


  public User() {
    logged=false;
  }

  public User(String login, String password, String name) {
    this.login = login;
    this.password = password;
    this.name = name;
    this.logged = false;
  }

  public boolean Auth(String password){
    if(!logged){
      logged = this.password.equals(password);
    }
    return logged;
  }

  public void Exit(){
    this.logged = false;
  }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public JSONObject toJson(){
      JSONObject user = new JSONObject();
      user.put("login", login);
      user.put("password",password);
      user.put("name",name);
      user.put("logged",logged);

      return user;
    }
}
