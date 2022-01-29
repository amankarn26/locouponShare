package com.locoupon.common.daoImpl;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.dao.ConfigDao;
import com.locoupon.common.entity.Config;

@Transactional
@Service
public class ConfigDaoImpl implements ConfigDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Config> getAll() {
		@SuppressWarnings("unused")
		boolean flag = false;
		String qlString = "from Config";
		TypedQuery<Config> query = this.entityManager.createQuery(qlString, Config.class);

		return query.getResultList();
	}

	@Override
	public void save(Config c) {
		this.entityManager.persist(c);
	}

	@Override
	public void delete(Config c) {
		this.entityManager.remove(c);
	}

	@Override
	public void update(Config c) {
		String query = "update Config set value = :value where name = :name";
		Query q = this.entityManager.createQuery(query);
		q.setParameter("value", c.value);
		q.setParameter("name", c.name);
		q.executeUpdate();
	}

	@Override
	public List<Config> getConfigList(String[] nameArray) {
		List<String> list = Arrays.asList(nameArray);
		String qlString = "from Config c where c.name in (:name)";
		TypedQuery<Config> query = this.entityManager.createQuery(qlString, Config.class);
		query.setParameter("name", list);
		List<Config> cList = query.getResultList();

		return cList;
	}

	@Override
	public void saveOrUpdate(Config c) {
		String query = "from Config c where c.name= :name";
		Query q = this.entityManager.createQuery(query);
		q.setParameter("name", c.getName());
		@SuppressWarnings("unchecked")
		List<Config> res = q.getResultList();
		if ((res == null) || (res.size() == 0)) {
			save(c);
		} else {
			update(c);
		}
	}

	@Override
	public Config getConfigVersion() {
		// TODO Auto-generated method stub
		return null;
	}
}
