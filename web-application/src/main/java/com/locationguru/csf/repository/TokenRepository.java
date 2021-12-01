package com.locationguru.csf.repository;

import java.util.*;

import com.locationguru.csf.model.Token;
import com.locationguru.csf.model.support.TokenStatus;
import org.springframework.stereotype.Repository;

@Repository
public class TokenRepository
		extends BaseRepository<Token>
{
	public Token findByIdentity(final String identity)
	{
		return this.first(this.db.createQuery("SELECT t FROM Token t WHERE t.identity = :identity AND t.isActive = TRUE", Token.class)
								 .setParameter("identity", identity)
								 .getResultList());
	}

	public Token findActiveTokenByIdentity(final UUID uid, final String identity)
	{
		return this.first(this.db.createQuery("SELECT t FROM Token t WHERE t.uid = :uid AND t.identity = :identity AND t.status = :status AND t.isActive = TRUE", Token.class)
								 .setParameter("uid", uid)
								 .setParameter("identity", identity)
								 .setParameter("status", TokenStatus.ACTIVE)
								 .getResultList());
	}

	public Integer updateExpirationStatus()
	{
		final String queryString = "UPDATE Token t SET t.expirationTimestamp = t.expectedExpirationTimestamp, t.expirationDate = t.expectedExpirationTimestamp, " +
								   "t.status = :status, t.isActive = FALSE WHERE t.status = :currentStatus AND t.expectedExpirationTimestamp <= :expirationTimestamp";

		return this.db.createQuery(queryString)
					  .setParameter("status", TokenStatus.EXPIRED)
					  .setParameter("currentStatus", TokenStatus.ACTIVE)
					  .setParameter("expirationTimestamp", new Date(System.currentTimeMillis()))
					  .executeUpdate();
	}

	public List<Date> findExpirationDates(final Date expirationDate)
	{
		final String queryString = "SELECT DISTINCT t.expirationDate FROM Token t WHERE t.expirationDate <= :expirationDate ORDER BY t.expirationDate";

		return this.db.createQuery(queryString, Date.class)
					  .setParameter("expirationDate", expirationDate)
					  .getResultList();
	}

	public Integer deleteTokensWithExpirationDate(final Date expirationDate)
	{
		final String queryString = "DELETE FROM Token t WHERE t.expirationDate = :expirationDate AND t.status = :status";

		return this.db.createQuery(queryString)
					  .setParameter("status", TokenStatus.EXPIRED)
					  .setParameter("expirationDate", expirationDate)
					  .executeUpdate();
	}
}
