package mx.gob.tecdmx.seguridad.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.IDRolesModulos;
import mx.gob.tecdmx.seguridad.entity.SegRoles;
import mx.gob.tecdmx.seguridad.entity.SegRolesModulos;

@Repository
public interface SegRolesModulosRepository extends CrudRepository<SegRolesModulos, IDRolesModulos> {
	List<SegRolesModulos> findBynIdRol(Integer nIdRol);
	List<SegRolesModulos> findBySegRoles(SegRoles rol);
}
