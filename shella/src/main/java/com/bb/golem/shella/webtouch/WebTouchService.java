package com.bb.golem.shella.webtouch;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WebTouchService {

	@Value("${shella.webtouch.proxy.active:false}")
	private boolean withProxy;

	@Value("${shella.webtouch.proxy.host}")
	private String proxyHost;

	@Value("${shella.webtouch.proxy.port}")
	private int proxyPort;

	public WebTouchService() {
		System.out.println("WebTouchService#new instance");
	}

	public String getSimpleStatusFromUrl(String host, String path){
		String result = "418 I’m a teapot";
		try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
			final HttpGet request = new HttpGet(path);
			final HttpHost target = new HttpHost("https", host, 443);
			if (withProxy) {
				final HttpHost proxy = new HttpHost("http", proxyHost, proxyPort);
				final RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
				request.setConfig(config);
			}
			//System.out.println("Executing request " + request.getMethod() + " " + request.getUri());
			try (final CloseableHttpResponse response = httpclient.execute(target, request)) {
				result = response.getCode() + " " + response.getReasonPhrase();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
