package mx.gob.tecdmx.seguridad.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.SegCatEstadoUsuario;

@Repository 
public interface SegCatEstadoUsuarioRepository extends CrudRepository<SegCatEstadoUsuario, Integer> {
  Optional<SegCatEstadoUsuario> findByDescripcion(String codigo);
	
}