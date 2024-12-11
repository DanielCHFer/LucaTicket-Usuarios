package com.ejemplos.spring.controller.error;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomDefaultError extends DefaultErrorAttributes {

	/**
	 * Personalizar gestión de errores, actualizando el método en control para
	 * lanzar las excepciones.
	 */
	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
		// quitamos la traza
		errorAttributes.remove("trace");

		return errorAttributes;

	}

}
