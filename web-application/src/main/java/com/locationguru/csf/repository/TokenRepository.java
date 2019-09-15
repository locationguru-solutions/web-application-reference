package com.locationguru.csf.repository;

import java.util.UUID;

import com.locationguru.csf.model.Token;
import com.locationguru.csf.model.support.TokenStatus;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository
		extends BaseRepository<Token>
{
	public Token findByIdentity(final String identity)
	{
		return first(db.createQuery("SELECT t FROM Token t WHERE t.identity = :identity AND t.isActive = TRUE", Token.class)
					   .setParameter("identity", identity)
					   .getResultList());
	}

	public Token findActiveTokenByIdentity(final UUID uid, final String identity)
	{
		return first(db.createQuery("SELECT t FROM Token t WHERE t.uid = :uid AND t.identity = :identity AND t.status = :status AND t.isActive = TRUE", Token.class)
					   .setParameter("uid", uid)
					   .setParameter("identity", identity)
					   .setParameter("status", TokenStatus.ACTIVE)
					   .getResultList());
	}
}
