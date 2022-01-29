package com.locoupon.common.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpEntity;

import com.locoupon.common.model.Shipment;

import okhttp3.Response;

public interface ShipmentAPIService {

	//String getTravelCharge() throws IOException;

	HttpEntity<String> getParams();

	Shipment getShipmentCharge(String weight,String pickupcode,String deliverycode) throws IOException;

	String getTravelCharge(String weight, String pickupcode, String deliverycode) throws IOException;

	//Response getTravelCharge(Shipment sh);

	
}
