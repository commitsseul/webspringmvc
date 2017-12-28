package kr.or.nextit.web.handler;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import kr.or.nextit.web.servlet.Controller;

public class UrlHandlerMapping { // 프로퍼티에서 유알아이와 클라스를 읽어와서 컨트롤러 객체를 만들어 맵에 담는다.

	public static Map<String, Controller> handlerMap = new HashMap<>();

	private UrlHandlerMapping() {}
	
	public static void init(String configFilePath) throws Exception {

		Properties prop = new Properties();
		prop.load(new FileReader(configFilePath));

		Iterator keyItr = prop.keySet().iterator();

		while (keyItr.hasNext()) {
			String uri = (String) keyItr.next();
			String handlerClassName = prop.getProperty(uri);

			Class handlerClass = Class.forName(handlerClassName);

			Controller controller = (Controller) handlerClass.newInstance();

			handlerMap.put(uri, controller);
		}
	}

	public static Controller getHandler(String uri) {
		return handlerMap.get(uri);
	}
}




