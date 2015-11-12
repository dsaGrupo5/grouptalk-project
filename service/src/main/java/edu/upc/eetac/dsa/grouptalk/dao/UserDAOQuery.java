package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by carlos on 21/10/2015.
 */
public interface UserDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String crear_usuario = "insert into users (id, loginid, password, email, fullname) values (UNHEX(?), ?, UNHEX(MD5(?)), ?, ?)";
    public final static String ASSIGN_ROLE_REGISTERED = "insert into user_roles (userid, role) values (UNHEX(?), 'registrado')";
    public final static String ASSIGN_ROLE_ADMINISTRADOR = "insert into user_roles (userid, role) values (UNHEX(?), 'administrador')";
    public final static String obtener_UserById = "select hex(u.id) as id, u.loginid, u.email, u.fullname from users u where id=unhex(?)";
    public final static String obtener_UserByLoginid = "select hex(u.id) as id, u.loginid, u.email, u.fullname from users u where u.loginid=?";
    public final static String GET_PASSWORD =  "select hex(password) as password from users where id=unhex(?)";
}