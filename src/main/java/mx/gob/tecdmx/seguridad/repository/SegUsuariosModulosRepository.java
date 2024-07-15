package mx.gob.tecdmx.seguridad.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.IDUsuariosModulos;
import mx.gob.tecdmx.seguridad.entity.SegUsuariosModulos;

@Repository
public interface SegUsuariosModulosRepository extends CrudRepository<SegUsuariosModulos, IDUsuariosModulos> {

}
