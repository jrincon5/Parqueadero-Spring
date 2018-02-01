package co.com.ceiba.parqueadero.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component("requestTimeInterceptor")
public class RequestTimeInterceptor extends HandlerInterceptorAdapter{
	private static final Log LOG = LogFactory.getLog(helloComponent.class);

	//PRIMERO QUE SE EJECUTA ANTES DE LLAMAR EL METODO DEL CONTROLADOR
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("startTime", System.currentTimeMillis()); //Tiempo actual en ms
		return true;
	}
	
	//ULTIMO QUE SE EJECUTA AL LLAMAR EL RETURN DEL METODO DEL CONTROLADOR
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long startTime=(long) request.getAttribute("startTime"); //Captura tiempo inicial ejecutado en el metodo de arriba
		LOG.info("--REQUEST URL: '"+request.getRequestURL().toString() + "' -- TOTAL TIME: '"+(System.currentTimeMillis()-startTime)+"' ms");
	}
	

}
