package pg.app.agenda.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pg.app.agenda.model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

}
