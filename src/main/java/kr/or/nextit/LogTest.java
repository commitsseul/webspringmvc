package kr.or.nextit;
//주석주석주석주석주석
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LogTest {
	
	private static Logger logger = Logger.getLogger(LogTest.class);
	
	public static void main(String[] args) {
		
		//Trace
		//Debug
		//Info
		//Warn
		//Error
		//Fatal
		
		
		BasicConfigurator.configure();
		
		logger.setLevel(Level.WARN);
	
		
		logger.debug("debug 메세지입니다.");
		logger.info("Info 메세지입니다");
		logger.warn("warn 메세지입니다");
		logger.error("error 메세지입니다");
		logger.fatal("faral 메세지입니다");
		
	}
	

}

















