package cl.pipecm.forum.user.service;

import java.util.UUID;
import cl.pipecm.forum.user.entity.Session;
import cl.pipecm.forum.user.entity.User;

public interface SessionService {
	public Session findById(UUID id);
	public Session findLatestSession(User user);
	public boolean isOnline(User user);
	public UUID add(Session session);
	public void update(Session session);
	public void delete(Session session);
}
