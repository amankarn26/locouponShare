package com.locoupon.common.dao;

import java.util.List;

import com.locoupon.common.entity.Config;


public interface ConfigDao {
	
	List<Config> getAll();
	
	void save(Config c);

	void delete(Config c);

	void update(Config c);

	List<Config> getConfigList(String[] nameArray);

	void saveOrUpdate(Config c);

	Config getConfigVersion();
	
}
