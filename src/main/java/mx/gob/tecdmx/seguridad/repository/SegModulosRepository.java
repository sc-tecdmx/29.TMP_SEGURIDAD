package mx.gob.tecdmx.seguridad.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.SegModulos;

@Repository
public interface SegModulosRepository extends CrudRepository<SegModulos, Integer> {
  Optional <SegModulos> findByDescModulo(String desc);
	
}