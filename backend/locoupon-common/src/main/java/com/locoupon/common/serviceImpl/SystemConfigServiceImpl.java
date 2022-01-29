package com.locoupon.common.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locoupon.common.dao.SystemConfigDao;
import com.locoupon.common.entity.SystemConfig;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;
import com.locoupon.common.service.SystemConfigService;
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

	private static final Logger logger = LoggerFactory.getLogger(SystemConfigServiceImpl.class);

	@Autowired
	private SystemConfigDao sysconfDao;

	@Override
	public List<SystemConfig> getAllSystemConfig(Long firm) {
		logger.debug("getAllSystemConfig Start");
		return sysconfDao.getAll(firm);
	}

	@Override
	public SystemConfig getSystemConfigById(Long id) {
		logger.debug("getSystemConfigById Start");
		return sysconfDao.getSystemConfigById(id);
	}

	@Override
	public ErrorCodes deleteSystemConfig(SystemConfig dbsupp, Request reqType) {
		String logprefix = "[" + reqType + "] deleteSystemConfig ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		reqError = sysconfDao.deleteSystemConfig(dbsupp);
		logger.debug(logprefix + " End");
		return reqError;
	}

	@Override
	public ErrorCodes addSystemConfig(SystemConfig role, Request reqType) {
		String logprefix = "[" + reqType + "] addSystemConfig ";
		logger.debug(logprefix + " Begin");
		RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE;

		reqError = sysconfDao.addSystemConfig(role);
		logger.debug(logprefix + " End");
		return reqError;
	}

}
