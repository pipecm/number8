package cl.pipecm.forum.user.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import cl.pipecm.forum.user.dao.UserDAO;
import cl.pipecm.forum.user.entity.Role;
import cl.pipecm.forum.user.entity.User;
import cl.pipecm.forum.user.exception.UserException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;
	
	@Override
	public List<User> findByRole(Role role) {
		return userDao.findByRole(role);
	}

	@Override
	public User findByEmail(String email) throws UserException {
		return userDao.findByEmailIgnoreCase(email)
					  .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND));
	}

	@Override
	public List<User> findByName(String name) {
		return userDao.findByNameContainingIgnoreCase(name);
	}

	@Override
	@Transactional
	public List<User> findOnlineUsers() {
		return userDao.findOnlineUsers();
	}

	@Override
	@Transactional
	public long add(User user) throws UserException {
		if(userDao.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
			throw new UserException(HttpStatus.CONFLICT);
		}
		user.setCreationDate(now());
		user.setActive(true);
		return userDao.save(user).getId();
	}

	@Override
	@Transactional
	public void update(User user) {
		userDao.save(user);
	}

	@Override
	@Transactional
	public void delete(long id) throws UserException {
		User user = userDao.findById(id)
						   .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND));
		user.setActive(false);
		userDao.save(user);	
	}
	
	private Date now() {
		return new Date();
	}
}
