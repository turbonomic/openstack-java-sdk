package com.woorea.openstack.connector;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.ContextResolver;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.jboss.resteasy.client.ClientExecutor;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
import org.jboss.resteasy.plugins.providers.InputStreamProvider;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.woorea.openstack.base.client.OpenStackClientConnector;
import com.woorea.openstack.base.client.OpenStackRequest;
import com.woorea.openstack.base.client.OpenStackResponse;
import com.woorea.openstack.base.client.OpenStackResponseException;

public class RESTEasyConnector implements OpenStackClientConnector {

	public static ObjectMapper DEFAULT_MAPPER;

	public static ObjectMapper WRAPPED_MAPPER;

    // The HTTP client connection timeout (in milliseconds) used in an OpenStack request
	private int httpConnectionTimeout = -1;

    // The HTTP client socket timeout (in milliseconds) used in an OpenStack request
	private int httpSocketTimeout = -1;

	static class OpenStackProviderFactory extends ResteasyProviderFactory {

		private JacksonJsonProvider jsonProvider;
		private InputStreamProvider streamProvider;

		public OpenStackProviderFactory() {
			super();

			addContextResolver(new ContextResolver<ObjectMapper>() {
				public ObjectMapper getContext(Class<?> type) {
					return type.getAnnotation(JsonRootName.class) == null ? DEFAULT_MAPPER : WRAPPED_MAPPER;
				}
			});

			jsonProvider = new JacksonJsonProvider();
			addMessageBodyReader(jsonProvider);
			addMessageBodyWriter(jsonProvider);

			streamProvider = new InputStreamProvider();
			addMessageBodyReader(streamProvider);
			addMessageBodyWriter(streamProvider);
		}

	}

	private static OpenStackProviderFactory providerFactory;

	static {
		DEFAULT_MAPPER = new ObjectMapper();

		DEFAULT_MAPPER.setSerializationInclusion(Inclusion.NON_NULL);
		DEFAULT_MAPPER.enable(SerializationConfig.Feature.INDENT_OUTPUT);
		DEFAULT_MAPPER.enable(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		DEFAULT_MAPPER.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		DEFAULT_MAPPER.enable(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		WRAPPED_MAPPER = new ObjectMapper();

		WRAPPED_MAPPER.setSerializationInclusion(Inclusion.NON_NULL);
		WRAPPED_MAPPER.enable(SerializationConfig.Feature.INDENT_OUTPUT);
		WRAPPED_MAPPER.enable(SerializationConfig.Feature.WRAP_ROOT_VALUE);
		WRAPPED_MAPPER.enable(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE);
		WRAPPED_MAPPER.enable(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		WRAPPED_MAPPER.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		WRAPPED_MAPPER.enable(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		providerFactory = new OpenStackProviderFactory();
	}

	public <T> OpenStackResponse request(OpenStackRequest<T> request) {

		// Generate a Client Request object with the timeout settings
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpParams params = httpClient.getParams();

		if (httpConnectionTimeout >= 0) {
			HttpConnectionParams.setConnectionTimeout(params, httpConnectionTimeout);
		}

		if (httpSocketTimeout >= 0) {
			HttpConnectionParams.setSoTimeout(params, httpSocketTimeout);
		}

		ClientExecutor executor = new ApacheHttpClient4Executor(httpClient);

		ClientRequest client = new ClientRequest(UriBuilder.fromUri(
		        request.endpoint() + "/" + request.path()), executor, providerFactory);

		for(Map.Entry<String, List<Object> > entry : request.queryParams().entrySet()) {
			for (Object o : entry.getValue()) {
				client = client.queryParameter(entry.getKey(), String.valueOf(o));
			}
		}

		for (Entry<String, List<Object>> h : request.headers().entrySet()) {
			StringBuilder sb = new StringBuilder();
			for (Object v : h.getValue()) {
				sb.append(String.valueOf(v));
			}
			client.header(h.getKey(), sb);
		}

		if (request.entity() != null) {
			client.body(request.entity().getContentType(), request.entity().getEntity());
		}

		ClientResponse<T> response;

		try {
			response = client.httpMethod(request.method().name(), request.returnType());
		} catch (ConnectTimeoutException e) {
			throw new RuntimeException("Connection timeout exception", e);
		} catch (SocketTimeoutException e) {
			throw new RuntimeException("Socket timeout exception", e);
		} catch (Exception e) {
			throw new RuntimeException("Unexpected client exception", e);
		}

		if (response.getStatus() == HttpStatus.SC_OK
				|| response.getStatus() == HttpStatus.SC_CREATED
				|| response.getStatus() == HttpStatus.SC_NO_CONTENT
				|| response.getStatus() == HttpStatus.SC_ACCEPTED) {
			return new RESTEasyResponse(client, response);
		}

		response.releaseConnection();

		throw new OpenStackResponseException(response.getResponseStatus()
				.getReasonPhrase(), response.getStatus());
	}

	@Override
	public void setHttpConnectionTimeout(int httpConnectionTimeout) {
		this.httpConnectionTimeout = httpConnectionTimeout;
	}

	@Override
	public int getHttpConnectionTimeout() {
		return httpConnectionTimeout;
	}

	@Override
	public void setHttpSocketTimeout(int httpSocketTimeout) {
		this.httpSocketTimeout = httpSocketTimeout;
	}

	@Override
	public int getHttpSocketTimeout() {
		return httpSocketTimeout;
	}
}
