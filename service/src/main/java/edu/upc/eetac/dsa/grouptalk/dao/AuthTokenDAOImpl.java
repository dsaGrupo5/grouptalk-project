package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.auth.UserInfo;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.Grupo;
import edu.upc.eetac.dsa.grouptalk.entity.Role;
import edu.upc.eetac.dsa.grouptalk.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthTokenDAOImpl implements AuthTokenDAO {
    @Override
    public UserInfo getUserByAuthToken(String token) throws SQLException {
        UserInfo userInfo = null;
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(AuthTokenDAOQuery.GET_USER_BY_TOKEN);
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userInfo = new UserInfo();
                userInfo.setName(rs.getString("id"));
                stmt.close();

                stmt = connection.prepareStatement(AuthTokenDAOQuery.GET_ROLES_OF_USER);
                stmt.setString(1, userInfo.getName());
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String role = rs.getString("role");
                    userInfo.getRoles().add(Role.valueOf(role));
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return userInfo;
    }
    @Override
    public AuthToken createAuthToken(String userid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String token = null;
        String role = null;
        AuthToken authToken = new AuthToken();
        try {

            connection = Database.getConnection();
            stmt = connection.prepareStatement(AuthTokenDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) token = rs.getString(1);
            else throw new SQLException();
            stmt.close();

            connection = Database.getConnection();
            stmt = connection.prepareStatement(AuthTokenDAOQuery.CREATE_TOKEN);
            stmt.setString(1, userid);
            stmt.setString(2, token);
            stmt.executeUpdate();
            stmt.close();

            connection = Database.getConnection();
            stmt = connection.prepareStatement(AuthTokenDAOQuery.GET_ROLES_OF_USER);
            stmt.setString(1, userid);
            rs = stmt.executeQuery();
            while (rs.next())
            {
                authToken.setRole(rs.getString("role"));
            }
            stmt.close();



            authToken.setToken(token);
            authToken.setUserid(userid);


        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return authToken;
    }
    @Override
    public void deleteToken(String nombreUser) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        AuthToken authToken = null;
        UserDAOImpl compuser = new UserDAOImpl();
        User user = compuser.obtener_UserByLoginid(nombreUser);
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(AuthTokenDAOQuery.DELETE_TOKEN);
            stmt.setString(1, user.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

}
