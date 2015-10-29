package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;
import edu.upc.eetac.dsa.grouptalk.entity.RespuestaCollection;
import edu.upc.eetac.dsa.grouptalk.entity.Tema;
import edu.upc.eetac.dsa.grouptalk.entity.User;
import edu.upc.eetac.dsa.grouptalk.dao.UserDAOImpl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RespuestaDAOImpl implements RespuestaDAO{
    @Override
    public Respuesta obtener_respuesta(String respuestaid) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        Respuesta respuesta = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(RespuestaDAOQuery.OBTENER_RESPUESTA_APARTIR_ID);
            stmt.setString(1, respuestaid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                respuesta = new Respuesta();
                respuesta.setId(rs.getString("id"));
                respuesta.setTemaid(rs.getString("temaid"));
                respuesta.setUserid(rs.getString("userid"));
                respuesta.setRespuesta(rs.getString("respuesta"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            // Libera la conexión
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        // Devuelve el modelo
        return respuesta;
    }
    @Override
    public Respuesta crear_respuesta(String nombreTema, String nombreUser, String respuesta) throws SQLException, UserNoExisteException, TemaIDNoExisteException {
        boolean c;
        String id;
        Connection connection = null;
        PreparedStatement stmt = null;
        Tema tema = null;
        try {
            tema = new Tema();
            UserDAOImpl comprobaruser = new UserDAOImpl();
            User user = comprobaruser.obtener_UserByLoginid(nombreUser);
            if (user == null) throw new UserNoExisteException();

            temaDAOImpl comprobartema = new temaDAOImpl();
            tema = comprobartema.obtener_id_por_nombreTema(nombreTema);
            if (tema == null) throw new TemaIDNoExisteException();
            connection = Database.getConnection();
            stmt = connection.prepareStatement(RespuestaDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();
            connection.setAutoCommit(false);
            stmt.close();
            stmt = connection.prepareStatement(RespuestaDAOQuery.CREAR_RESPUESTA);
            stmt.setString(1, id);
            stmt.setString(2, tema.getId());
            stmt.setString(3, user.getId());
            stmt.setString(4, respuesta);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return obtener_respuesta(id);
    }
    @Override
    public boolean eliminar_respuesta(String id) throws SQLException, RespuestaNoExisteException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            Respuesta respuesta = obtener_respuesta(id);
            if (respuesta == null)
                throw new RespuestaNoExisteException();
            connection = Database.getConnection();
            stmt = connection.prepareStatement(RespuestaDAOQuery.ELIMINAR_RESPUESTA);
            stmt.setString(1, id);
            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
    @Override
    public Respuesta editar_respuesta(String id, String respuesta) throws SQLException, RespuestaNoExisteException {
        Connection connection = null;
        PreparedStatement stmt = null;
        Respuesta resp = null;

        try {
            resp = obtener_respuesta(id);
            if (resp == null)
                throw new RespuestaNoExisteException();
            connection = Database.getConnection();
            stmt = connection.prepareStatement(RespuestaDAOQuery.EDITAR_RESPUESTA);
            stmt.setString(1, respuesta);
            stmt.setString(2, id);

            int rows = stmt.executeUpdate();

            if (rows == 1) {
                resp = obtener_respuesta(id);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return resp;

    }
    @Override
    public RespuestaCollection obtener_respuestas_por_User(String nombreUser) throws SQLException,  UserNoExisteException {
        Respuesta resp = null;
        RespuestaCollection respcol = new RespuestaCollection();
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            UserDAOImpl comprobaruser = new UserDAOImpl();
            User user = comprobaruser.obtener_UserByLoginid(nombreUser);
            if (user == null) throw new UserNoExisteException();
            connection = Database.getConnection();
            stmt = connection.prepareStatement(RespuestaDAOQuery.OBTENER_COLECCION_RESPUESTAS_APARTIR_USERID);
            stmt.setString(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                resp = new Respuesta();
                resp.setId(rs.getString("id"));
                resp.setTemaid(rs.getString("temaid"));
                resp.setUserid(rs.getString("userid"));
                resp.setRespuesta(rs.getString("respuesta"));
                respcol.getRespuestas().add(resp);
            }
        }
        catch (SQLException e)
        {
            throw e;
        }
        finally
        {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return  respcol;
    }
}
