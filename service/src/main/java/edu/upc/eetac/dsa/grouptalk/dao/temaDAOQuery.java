package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by carlos on 21/10/2015.
 */
public class temaDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREAR_TEMA = "insert into tema (id, userid, grupoid, nombre, comentario) values (UNHEX(?),UNHEX(?),UNHEX(?), ?, ?)";
    public final static String OBTENER_TEMA_POR_ID = "select hex(t.id) as id,hex(t.userid) as userid, hex(t.grupoid) as grupoid, t.nombre, t.comentario from tema t where id=unhex(?)";
    public final static String OBTENER_COLECCION_TEMAS_POR_GROUPID = "select hex(id) as id,hex(t.userid) as userid, hex(t.grupoid) as grupoid, t.nombre, t.comentario from tema where grupoid=unhex(?)";
    public final static String ELIMINAR_TEMA = "delete from tema where id=unhex(?)";
    public final static String MODIFICAR_COMENTARIO_TEMA = "update tema set comentario=? where id=unhex(?)";




}
