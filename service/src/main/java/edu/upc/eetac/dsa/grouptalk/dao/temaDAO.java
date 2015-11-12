package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Tema;
import edu.upc.eetac.dsa.grouptalk.entity.TemaCollection;

import java.sql.SQLException;


public interface temaDAO {
    public Tema crear_tema(String userid,String grupoid,String nombre,String comentario) throws SQLException, UserAlreadyExistsException,GrupoNoExisteException,UserNoExisteException;
    public boolean eliminar_tema(String id) throws SQLException,TemaIDNoExisteException;
    public Tema modificar_comentario(String id,String contenido) throws SQLException,TemaIDNoExisteException;
    public TemaCollection obtener_coleccion_temas_por_nombreGrupo(String nombreGrupo)throws SQLException,GrupoNoExisteException;
    public Tema obtener_tema_por_id(String id)throws SQLException;
    public Tema obtener_id_por_nombreTema(String nombreTema)throws SQLException;
}
