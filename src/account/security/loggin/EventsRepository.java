package account.security.loggin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface EventsRepository extends JpaRepository<EventEntity, Long> {
}
