package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Grupo;
import edu.upc.eetac.dsa.grouptalk.entity.GrupoCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 21/10/2015.
 */
public interface GrupoDAO {

    public Grupo crear_grupo(String nombre)throws SQLException,GrupoAlreadyExistException;
    public boolean ingresar_grupo(String grupoid,String userid)throws SQLException,GrupoNoExisteException,UserNoExisteException;
    public boolean abandonar_grupo(String grupoid,String userid)throws SQLException,GrupoNoExisteException,UserNoExisteException,RelacionNoExisteException;
    public GrupoCollection obtener_coleccion(long timestamp, boolean before)throws SQLException;
    public Grupo obtener_NOMBRE_por_ID(String id)throws SQLException;
    public Grupo obtener_ID_grupo_por_NOMBRE(String nombre)throws SQLException;
    public Grupo comprobarUsuarioengrupo(String grupoid, String userid)throws RelacionNoExisteException, SQLException;
    public boolean eliminar_grupo (String nombregrupo) throws GrupoNoExisteException, SQLException;
    public Grupo editar_grupo (Grupo grupo) throws GrupoNoExisteException, SQLException;
}
