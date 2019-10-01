package com.locationguru.csf.repository;

import com.locationguru.csf.model.Authentication;
import com.locationguru.csf.model.support.AuthenticationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepository
		extends BaseRepository<Authentication>
{
	private static final Logger logger = LogManager.getLogger(AuthenticationRepository.class);

	public Authentication findByIdentityAndType(final String identity, final AuthenticationType type)
	{
		final String queryString = "SELECT a FROM Authentication a WHERE a.identity = :identity AND a.type = :type AND a.isActive = TRUE";

		return first(db.createQuery(queryString, Authentication.class)
					   .setParameter("identity", identity.toLowerCase())
					   .setParameter("type", type)
					   .getResultList());
	}
}
