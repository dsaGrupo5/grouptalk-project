package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;
import edu.upc.eetac.dsa.grouptalk.entity.RespuestaCollection;

import java.sql.SQLException;

/**
 * Created by carlos on 21/10/2015.
 */
public interface RespuestaDAO {
    public Respuesta crear_respuesta(String temaid, String userid, String respuesta) throws SQLException,UserNoExisteException, TemaIDNoExisteException;
    public boolean eliminar_respuesta(String id) throws SQLException,RespuestaNoExisteException;
    public Respuesta editar_respuesta(String id, String respuesta) throws SQLException,RespuestaNoExisteException;
    public RespuestaCollection obtener_respuestas_por_User(String userid) throws SQLException, UserNoExisteException;
    public Respuesta obtener_respuesta(String respuestaid) throws SQLException;


}
