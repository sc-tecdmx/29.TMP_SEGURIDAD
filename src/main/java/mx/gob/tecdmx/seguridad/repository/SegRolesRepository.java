package mx.gob.tecdmx.seguridad.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.SegRoles;

@Repository
public interface SegRolesRepository extends CrudRepository<SegRoles, Integer> {
	Optional<SegRoles> findByEtiquetaRol(String etiquetaRol);
}
