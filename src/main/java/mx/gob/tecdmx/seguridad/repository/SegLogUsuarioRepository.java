package mx.gob.tecdmx.seguridad.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.SegLogUsuario;

@Repository
public interface SegLogUsuarioRepository extends CrudRepository<SegLogUsuario, Integer> {
  
	
}