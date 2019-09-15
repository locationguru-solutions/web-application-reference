package com.locationguru.csf.repository;

import java.util.UUID;

import com.locationguru.csf.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository
		extends BaseRepository<User>
{
	private static final Logger logger = LogManager.getLogger(UserRepository.class);

	public User findByUid(final UUID uid)
	{
		return first(db.createQuery("SELECT u FROM User u WHERE u.uid = :uid AND u.isActive = TRUE", User.class)
					   .setParameter("uid", uid)
					   .getResultList());
	}
}
