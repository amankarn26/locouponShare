package com.locoupon.common.dao;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.locoupon.common.entity.SystemConfig;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;



@Transactional
@Service
public interface SystemConfigDao {

	List<SystemConfig> getAll(Long firm);

	SystemConfig getSystemConfigById(Long id);

	ErrorCodes deleteSystemConfig(SystemConfig dbsupp);

	ErrorCodes addSystemConfig(SystemConfig sys);

	

}
