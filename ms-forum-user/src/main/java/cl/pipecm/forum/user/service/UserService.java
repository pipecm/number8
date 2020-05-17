package cl.pipecm.forum.user.service;

import java.util.List;
import cl.pipecm.forum.user.entity.Role;
import cl.pipecm.forum.user.entity.User;

public interface UserService {
	public List<User> findByRole(Role role);
	public User findByEmail(String email);
	public List<User> findByName(String name);
	public List<User> findOnlineUsers();
	public long add(User user);
	public void update(User user);
	public void delete(long id);
}
