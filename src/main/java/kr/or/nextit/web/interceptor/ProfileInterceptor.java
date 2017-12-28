package kr.or.nextit.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ProfileInterceptor implements HandlerInterceptor {

	//컨트롤러 실행 전
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		System.out.println("ProfileInterceptor.preHandle() 실행 : " + handler.getClass().getName());
		
		request.setAttribute("interceptor.startTime", System.currentTimeMillis());
		
		return true;
	}
	
	//컨트롤러 실행 후 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {
		
		
	}
	

	//뷰까지 생성 완료
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 컨트롤러 수행 시간 체크, 로깅 처리
		
		Logger logger = LoggerFactory.getLogger(handler.getClass());
	  //Logger, LoggerFactory = slf4j 사용
		
		Long startTime = (Long)request.getAttribute("interceptor.startTime");
		long duration = System.currentTimeMillis() - startTime;
		
		logger.info(request.getRequestURI()+ " : 수행시간 = " + duration);
		
		if(ex != null) {
			logger.error("afterCompletion : " + ex.getMessage(), ex);
		}
	}
}












