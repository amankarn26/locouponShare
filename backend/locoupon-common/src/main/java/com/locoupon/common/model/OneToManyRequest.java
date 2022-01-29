package com.locoupon.common.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

import com.locoupon.common.enums.RequestState;
import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestState.state;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;

public class OneToManyRequest {

	private Long container;

	private List<Long> ids;

	private int m_totalCount;

	private RequestState.state status = RequestState.state.SUCCESS;
	private RequestTypes.ErrorCodes error = RequestTypes.ErrorCodes.NONE;

	@XmlAttribute(name = "error")
	public RequestTypes.ErrorCodes getError() {
		return error;
	}

	public void setError(RequestTypes.ErrorCodes error) {
		this.error = error;
	}

	@XmlAttribute(name = "status")
	public RequestState.state getStatus() {
		return status;
	}

	public void setStatus(RequestState.state status) {
		this.status = status;
	}

	@XmlAttribute(name = "container")
	public Long getContainer() {
		return container;
	}

	public void setContainer(Long container) {
		this.container = container;
	}

	@XmlAttribute(name = "ids")
	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public int getM_totalCount() {
		return m_totalCount;
	}

	public void setM_totalCount(int m_totalCount) {
		this.m_totalCount = m_totalCount;
	}

}
