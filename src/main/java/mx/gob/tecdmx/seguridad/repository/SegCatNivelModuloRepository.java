package mx.gob.tecdmx.seguridad.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.SegCatNivelModulo;

@Repository
public interface SegCatNivelModuloRepository extends CrudRepository<SegCatNivelModulo, Integer> {
	Optional<SegCatNivelModulo> findByDescNivel(String nivelModulo);
	
}