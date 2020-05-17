package cl.pipecm.forum.user.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cl.pipecm.forum.user.entity.Role;
import cl.pipecm.forum.user.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	static final String QUERY_ONLINE_USERS = "select * from forum_user where user_id in "
												+ "(select user_id from forum_session where is_active = 1)";
	
	public List<User> findByRole(Role role);
	
	public Optional<User> findByEmailIgnoreCase(String email);
	
	public List<User> findByNameContainingIgnoreCase(String name);
	
	@Query(value = QUERY_ONLINE_USERS, nativeQuery = true)
	public List<User> findOnlineUsers();
}
