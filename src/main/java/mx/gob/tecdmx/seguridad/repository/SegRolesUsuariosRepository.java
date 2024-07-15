package mx.gob.tecdmx.seguridad.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.SegRoles;
import mx.gob.tecdmx.seguridad.entity.SegRolesUsuarios;
import mx.gob.tecdmx.seguridad.entity.SegUsuarios;

@Repository
public interface SegRolesUsuariosRepository extends CrudRepository<SegRolesUsuarios, Integer> {
	List<SegRolesUsuarios> findByIdUsuario(SegUsuarios usuario);
	Optional<SegRolesUsuarios> findByIdRolAndIdUsuario(SegRoles rol, SegUsuarios user);
}
