package com.locoupon.common.dao;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Service
public class GoogleMapDao {

	private static final Logger logger = LoggerFactory.getLogger(GoogleMapDao.class);

	public String getDistance(String source, String destination) throws IOException {
		String API_KEY = "AIzaSyDIEWI-HzocknPuXgHyvgD3n7pbi1mOdlc";
		OkHttpClient client = new OkHttpClient();

		String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + source
				+ "&destinations=" + destination + "&key=" + API_KEY;
		Request request = new Request.Builder().url(url).build();

		Response response = client.newCall(request).execute();
		return response.body().string();

	}

}
