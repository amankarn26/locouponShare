package com.locoupon.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.SystemConfig;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;

@Service
public interface SystemConfigService {

	List<SystemConfig> getAllSystemConfig(Long firm);

	SystemConfig getSystemConfigById(Long id);

	ErrorCodes deleteSystemConfig(SystemConfig dbsupp, Request reqType);

	ErrorCodes addSystemConfig(SystemConfig role, Request reqType);

}
