package com.yc.index.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class TokenFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		//获取zuul 的请求上下文对象
		RequestContext requestContext = RequestContext.getCurrentContext();
		//获取Servlet请求对象
		HttpServletRequest req = requestContext.getRequest();
		//获取令牌
		String token = req.getParameter("token");
		if(token == null) {
			//设置是否发送zuul响应
			requestContext.setSendZuulResponse(false);
			//设置结果码401 ==> 4xx 客户端错误
			requestContext.setResponseStatusCode(401);
			//设置响应的信息
			requestContext.setResponseBody("{\"result\":\"accessToken is empty!\"}");
		}
		return null;
	}

	@Override
	//pre 前置 ，post 后置，route 路由中，error 错误
	public String filterType() {
		return "pre";
	}

	@Override
	//拦截的顺序0表示第一个
	public int filterOrder() {
		return 0;
	}

	
}
