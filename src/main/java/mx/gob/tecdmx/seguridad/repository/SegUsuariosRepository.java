package mx.gob.tecdmx.seguridad.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.gob.tecdmx.seguridad.entity.SegUsuarios;

@Repository
public interface SegUsuariosRepository extends CrudRepository<SegUsuarios, Integer> {
	Optional<SegUsuarios> findByEmail(String email);
    Optional<SegUsuarios> findBysToken(String resetToken);
	Optional<SegUsuarios> findByUsuario(String user);
	//List<SegUsuarios> findAllBysUsuario(String email);
	Page<SegUsuarios> findAll(Pageable pageable);
	
	Optional<SegUsuarios> findByEmailOrUsuario(String email, String user);

}
