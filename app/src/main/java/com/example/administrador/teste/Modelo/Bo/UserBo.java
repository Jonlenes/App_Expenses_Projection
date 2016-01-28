package com.example.administrador.teste.Modelo.Bo;

import com.example.administrador.teste.Modelo.Dao.UserDao;
import com.example.administrador.teste.Modelo.Vo.User;

/**
 * Created by Administrador on 20/01/2016.
 */
public class UserBo {

    private UserDao userDao;

    public UserBo() {
        userDao = new UserDao();
    }

    public void insert(User user) throws ModelException {
        if (userDao.containByLogin(user.getLogin())) {
            throw new ModelException("Nome de usuário indisponivel");
        }

        userDao.insert(user);
        login(user.getLogin(), user.getPassword(), user.getIsConnected());
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void login(String login, String password, Boolean isConnected) throws ModelException {
        if (!userDao.login(login, password))
            throw new ModelException("Usuário ou senha incorretos");

        User user = userDao.getUser(login);
        if (user != null && isConnected) {
            user.setIsConnected(Boolean.TRUE);
            userDao.update(user);
        }
        DbHelper.getInstance().setUserActive(user);
    }

    public void logout() {
        User user = DbHelper.getInstance().getUserActive();

        if (user != null && user.getIsConnected()) {
            user.setIsConnected(Boolean.FALSE);
            userDao.update(user);
        }

        DbHelper.getInstance().setUserActive(null);
    }

    public Boolean userIsConnected() {
        User user = userDao.getUserConnected();

        if (user != null) {
            DbHelper.getInstance().setUserActive(user);
            return true;
        }
        return false;
    }
}
