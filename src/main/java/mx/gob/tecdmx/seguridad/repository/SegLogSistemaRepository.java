package mx.gob.tecdmx.seguridad.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.SegLogSistema;

@Repository
public interface SegLogSistemaRepository extends CrudRepository<SegLogSistema, Integer> {
  
	
}