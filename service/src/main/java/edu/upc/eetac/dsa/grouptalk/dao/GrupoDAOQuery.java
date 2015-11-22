package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by carlos on 21/10/2015.
 */
public interface GrupoDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREAR_GRUPO = "insert into grupo (id, nombre) values (UNHEX(?), ?)";
    public final static String INGRESAR_GRUPO = "insert into relaciones_grupo (grupoid, userid) values (UNHEX(?), unhex(?))";
    public final static String ABANDONAR_GRUPO = "delete from relaciones_grupo where grupoid=unhex(?) and userid=unhex(?)";
    public final static String OBTENER_GRUPOS = "select hex(g.id)as id, g.nombre from grupo g";
    public final static String OBTENER_NOMBRE_POR_ID = "select  hex(g.id) as id, g.nombre from grupo g where id=unhex(?)";
    public final static String OBTENER_ID_GRUPO_POR_NOMBRE = "select  hex(g.id) as id, g.nombre from grupo g where g.nombre=?";
    public final static String COMPROBAR_USER_ASIGNADO_A_GRUPO = "select hex(g.grupoid) as grupoid from relaciones_grupo g where grupoid=unhex(?) and userid=unhex(?)";
    public final static String ELIMINAR_GRUPO = "delete from grupo where nombre=(?)";
    public final static String MODIFICAR_NOMBRE_GRUPO = "update grupo set nombre=? where id=unhex(?) ";
    public final static String OBTENER_COLECCION_GRUPOS_APARTIR_ID_PAGINADA_A_5 =       "select hex(id) as id,nombre, last_modified, creation_timestamp from grupo where creation_timestamp < ?  order by creation_timestamp desc limit 5";
    public final static String OBTENER_COLECCION_GRUPOS_APARTIR_ID_PAGINADA_A_5_after = "select hex(id) as id,nombre, last_modified, creation_timestamp from grupo where creation_timestamp > ?  order by creation_timestamp desc limit 5";

}
