package net.msdh.kernel.security;

import net.minidev.json.JSONArray;
import net.msdh.kernel.errors.CoreException;

import javax.swing.text.TableView;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: TkachenkoAA
 * Date: 19.05.17
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
public class Session {
  Vector<User> users;

  public Session() {
    users = new Vector<User>(20);
  }

  public User GetUser(String login){
    User tUser = null;
      for(User user : users){
        if(user.getLogin().equals(login)){
          tUser = user;
          break;
        }
      }
    return tUser;
  }

  public void AddUser(User usr) throws CoreException {
    if(!users.contains(usr)){
      users.add(usr);
    }
    else{
      throw new CoreException(101,"Sessin.AddUser","User alredy exist");
    }
  }

  public JSONArray GetUserList(){
    JSONArray userList = new JSONArray();
    for(User user:users){
      userList.add(user.toJson());
    }
    return userList;
  }
}
