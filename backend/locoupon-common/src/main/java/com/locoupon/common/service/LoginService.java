package com.locoupon.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.locoupon.common.entity.Login;
import com.locoupon.common.enums.LoginType;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
import com.locoupon.common.enums.RequestTypes.Request;

@Service
public interface LoginService {

	ErrorCodes add(Login login, Request reqType);

	Login update(Long id, Login login);

	ErrorCodes delete(Login login, Request reqType);

	Object verifylogin(Login login, ErrorCodes reqError);

	Login validateUpdate(Login login, ErrorCodes reqError);

	ErrorCodes update(Login dblogin, Request reqType);

	List<Login> getLoginType(LoginType.type stype);

	ErrorCodes getCheckNumberExist(String username, ErrorCodes reqError);

	Login getById(Long id);

	Login getlogin(String username);

}
