package com.locationguru.csf.service;

import java.util.UUID;

import com.locationguru.csf.model.User;
import com.locationguru.csf.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService
{
	private static final Logger logger = LogManager.getLogger(UserService.class);

	private final UserRepository repository;

	@Autowired
	public UserService(final UserRepository repository)
	{
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public User findById(final UUID uid)
	{
		return repository.findByUid(uid);
	}
}
