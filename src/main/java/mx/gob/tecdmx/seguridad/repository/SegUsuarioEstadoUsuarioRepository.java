package mx.gob.tecdmx.seguridad.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.SegUsuarioEstadoUsuario;
import mx.gob.tecdmx.seguridad.entity.SegUsuarios;

@Repository
public interface SegUsuarioEstadoUsuarioRepository extends CrudRepository<SegUsuarioEstadoUsuario, Integer> {
	Optional<SegUsuarioEstadoUsuario> findByIdUsuario(SegUsuarios idUser);
	
}