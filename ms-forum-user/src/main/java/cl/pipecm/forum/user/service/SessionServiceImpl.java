package cl.pipecm.forum.user.service;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cl.pipecm.forum.user.dao.SessionDAO;
import cl.pipecm.forum.user.entity.Session;
import cl.pipecm.forum.user.entity.User;
import cl.pipecm.forum.user.exception.UserException;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

	@Autowired
	private SessionDAO sessionDao;
	
	@Override
	public Session findById(UUID id) throws UserException {
		return sessionDao.findById(id)
						 .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND));
	}

	@Override
	public Session findLatestSession(User user) throws UserException {
		return sessionDao.findLatestSessionByUser(user.getId())
						 .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND));
	}

	@Override
	public boolean isOnline(User user) {
		Optional<Session> latestSession = sessionDao.findLatestSessionByUser(user.getId());
		if(latestSession.isPresent()) {
			return latestSession.get().isActive();
		}
		return false;
	}

	@Override
	public UUID add(Session session) {
		session.setActive(true);
		return sessionDao.save(session).getId();
	}

	@Override
	public void update(Session session) {
		sessionDao.save(session);
	}

	@Override
	public void delete(Session session) {
		session.setActive(false);
		sessionDao.save(session);
	}
}
