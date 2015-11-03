package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by carlos on 21/10/2015.
 */
public interface RespuestaDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREAR_RESPUESTA = "insert into respuesta (id, temaid, userid, respuesta) values (UNHEX(?), UNHEX(?), UNHEX(?), ?)";
    public final static String EDITAR_RESPUESTA = "update respuesta set respuesta=? where id=unhex(?)";
    public final static String ELIMINAR_RESPUESTA = "delete from respuesta where id=unhex(?)";
    public final static String OBTENER_RESPUESTA_APARTIR_ID = "select hex(r.id) as id, hex(r.temaid) as temaid, hex(r.userid) as userid, r.respuesta from respuesta r where id=unhex(?)";
    public final static String OBTENER_COLECCION_RESPUESTAS_APARTIR_USERID_PAGINADA_A_5 =       "select hex(id) as id, hex(temaid) as temaid,hex(userid) as userid, respuesta, last_modified, creation_timestamp from respuesta where creation_timestamp < ? and userid=unhex(?) order by creation_timestamp desc limit 5";
    public final static String OBTENER_COLECCION_RESPUESTAS_APARTIR_USERID_PAGINADA_A_5_after = "select hex(id) as id, hex(temaid) as temaid,hex(userid) as userid, respuesta, last_modified, creation_timestamp from respuesta where creation_timestamp > ? and userid=unhex(?) order by creation_timestamp desc limit 5";

}