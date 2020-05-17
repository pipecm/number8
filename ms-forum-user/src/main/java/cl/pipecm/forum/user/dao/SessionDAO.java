package cl.pipecm.forum.user.dao;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import cl.pipecm.forum.user.entity.Session;

@Repository
public interface SessionDAO extends JpaRepository<Session, UUID> {
	static final String QUERY_LATEST_SESSION = "select * from forum_session where user_id = ?1 order by timestamp desc limit 1";
	
	public Optional<Session> findById(UUID id);
	
	@Query(value = QUERY_LATEST_SESSION, nativeQuery = true)
	public Optional<Session> findLatestSessionByUser(Long userId);
	
}
