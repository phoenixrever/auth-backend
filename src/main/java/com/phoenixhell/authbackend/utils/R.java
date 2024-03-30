package com.phoenixhell.authbackend.utils;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;


/*
 * @author チヨウ　カツヒ
 * @email phoenixrever@gmail.com
 * @date 2024/03/30 22:02
 * @throws
 *
 * 看情况也可以用 ResponseEntity
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html
 *
 */

public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", HttpStatus.OK.value());
		put("message", "success");
	}

	public static R error() {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
	}

	public static R error(String message) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
	}

	public static  R error(Integer code, String message) {
		R r = new R();
		r.put("code", code);
		r.put("message", message);
		return r;
	}

	public static R ok(String message) {
		R r = new R();
		r.put("message", message);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}


//	fastjson 反序列化 暂时用不到 用到的时候换成jackson
/*	public <T> T getData(TypeReference<T> typeReference) {
		Object data = get("data");
		return objectMapper.convertValue(data, typeReference);
	}

	public <T> T getData(String key, TypeReference<T> typeReference) {
		Object data = get(key);
		return objectMapper.convertValue(data, typeReference);
	}*/


	public int getCode() {
		return (int) this.get("code");
	}
}