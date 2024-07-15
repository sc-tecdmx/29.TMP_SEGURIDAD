package mx.gob.tecdmx.seguridad.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.SegLogSesion;

@Repository
public interface SegLogSesionRepository extends CrudRepository<SegLogSesion, Integer> {
	
   Optional<SegLogSesion> findTopByOrderByIdDesc();
	
}