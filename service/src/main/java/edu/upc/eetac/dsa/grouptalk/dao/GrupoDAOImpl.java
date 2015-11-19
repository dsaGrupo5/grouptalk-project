package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Grupo;
import edu.upc.eetac.dsa.grouptalk.entity.GrupoCollection;
import edu.upc.eetac.dsa.grouptalk.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GrupoDAOImpl implements GrupoDAO{
    @Override
    public Grupo crear_grupo(String nombre)throws SQLException,GrupoAlreadyExistException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            Grupo grupo = obtener_ID_grupo_por_NOMBRE(nombre);
            if (grupo != null)
                throw new GrupoAlreadyExistException();
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();
            connection.setAutoCommit(false);
            stmt.close();
            stmt = connection.prepareStatement(GrupoDAOQuery.CREAR_GRUPO);
            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return obtener_NOMBRE_por_ID(id);
    }
    @Override
    public boolean ingresar_grupo(String nombre_grupo,String nombre_usuario)throws SQLException,GrupoNoExisteException,UserNoExisteException{
        Connection connection = null;
        PreparedStatement stmt = null;
        UserDAOImpl comprobarUser = new UserDAOImpl();
        try {
            Grupo grupo = obtener_ID_grupo_por_NOMBRE(nombre_grupo);
            comprobarUser.obtener_UserByLoginid(nombre_usuario);
            if (grupo == null)throw new GrupoNoExisteException();
            if (comprobarUser == null) throw new UserNoExisteException();
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.INGRESAR_GRUPO);
            stmt.setString(1, obtener_ID_grupo_por_NOMBRE(nombre_grupo).getId());
            stmt.setString(2,comprobarUser.obtener_UserByLoginid(nombre_usuario).getId());
            stmt.executeUpdate();
            return true ;
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
    public boolean abandonar_grupo(String nombregrupo,String nombreusuario)throws SQLException,GrupoNoExisteException, UserNoExisteException,RelacionNoExisteException{
        Connection connection = null;
        PreparedStatement stmt = null;
        UserDAOImpl comprobarUser = new UserDAOImpl();
        User user = null;
        try {
            Grupo grupo =obtener_ID_grupo_por_NOMBRE(nombregrupo);
            user = comprobarUser.obtener_UserByLoginid(nombreusuario);
            if (grupo == null)throw new GrupoNoExisteException();
            if (user == null) throw new UserNoExisteException();
            grupo = comprobarUsuarioengrupo(grupo.getId(), user.getId());
            if (grupo == null)throw new RelacionNoExisteException();
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.ABANDONAR_GRUPO);
            stmt.setString(1,grupo.getId());
            stmt.setString(2,user.getId());
            stmt.executeUpdate();
            return true ;
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
    public GrupoCollection obtener_coleccion()throws SQLException{
        Grupo grup = null;
        GrupoCollection grupocollection = new GrupoCollection();
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.OBTENER_GRUPOS);
            ResultSet rs = stmt.executeQuery();
             while (rs.next()) {
               grup = new Grupo();
                grup.setId(rs.getString("id"));
                grup.setNombre(rs.getString("nombre"));
                 grupocollection.getGrupos().add(grup);
            }
        }
        catch (SQLException e)
        {
        throw e;
        }
        finally
        {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return grupocollection;
    }
    @Override
    public Grupo obtener_NOMBRE_por_ID(String id)throws SQLException{
        Grupo grupo = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.OBTENER_NOMBRE_POR_ID);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                grupo = new Grupo();
                grupo.setId(rs.getString("id"));
                grupo.setNombre(rs.getString("nombre"));
            }
        }
        catch (SQLException e)
        {
            throw e;
        }
        finally
        {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return grupo;
    }
    @Override
    public Grupo obtener_ID_grupo_por_NOMBRE(String nombre)throws SQLException{
        Grupo grupo = null;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.OBTENER_ID_GRUPO_POR_NOMBRE);
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                grupo = new Grupo();
                grupo.setId(rs.getString("id"));
                grupo.setNombre(rs.getString("nombre"));
            }
        }
        catch (SQLException e)
        {
            throw e;
        }
        finally
        {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return grupo;
    }
    @Override
    public Grupo comprobarUsuarioengrupo(String grupoid, String userid) throws SQLException, RelacionNoExisteException {

        Connection connection = null;
        PreparedStatement stmt = null;
        Grupo grupo = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.COMPROBAR_USER_ASIGNADO_A_GRUPO);
            stmt.setString(1, grupoid);
            stmt.setString(2, userid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                grupo = new Grupo();
                grupo.setId(rs.getString("grupoid"));
            }
        }
        catch (SQLException e)
        {
            throw e;
        }
        finally
        {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return grupo;
    }

    @Override
    public boolean eliminar_grupo (String nombregrupo) throws GrupoNoExisteException, SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            Grupo grupo = obtener_ID_grupo_por_NOMBRE(nombregrupo);

                if (grupo == null)throw new GrupoNoExisteException();

            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.ELIMINAR_GRUPO);
            stmt.setString(1, nombregrupo);
            stmt.executeUpdate();
            return true ;
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
}
