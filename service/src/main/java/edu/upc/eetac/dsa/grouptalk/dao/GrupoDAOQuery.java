package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by carlos on 21/10/2015.
 */
public interface GrupoDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREAR_GRUPO = "insert into grupo (id, nombre) values (UNHEX(?), ?)";
    public final static String INGRESAR_GRUPO = "insert into relaciones_grupo (grupoid, userid) values (UNHEX(?), unhex(?))";
    public final static String ABANDONAR_GRUPO = "delete from relaciones_grupo where grupoid=unhex(?) and userid=unhex(?)";
    public final static String OBTENER_GRUPOS = "select * from grupo";
    public final static String OBTENER_NOMBRE_POR_ID = "select  hex(g.id) as id, g.nombre from grupo g where id=unhex(?)";
    public final static String OBTENER_ID_GRUPO_POR_NOMBRE = "select  hex(g.id) as id, g.nombre from grupo g where g.nombre=?";
}
