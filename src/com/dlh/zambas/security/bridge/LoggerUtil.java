package com.dlh.zambas.security.bridge;

import com.actional.catalog.Catalog;
import com.actional.catalog.Message;
import com.actional.log.ILogger;
import com.actional.log.LoggerFactory;

/**
 * Logger class to log info or error messages
 * 
 * @author pesingh
 *
 */
public class LoggerUtil {
	
	private static Message INFO_MSG = null;
	private static Message ERROR_MSG = null;
	private static Catalog CATALOG = new PrepareFaultCatalog(
			"Security_Bridge", "EXTRACT-FAULT-PLUGIN",
			LoggerUtil.class);
	private static ILogger logger = LoggerFactory.get();
	
	
	public static void logErrorMessages(String error, Exception e) {
		ERROR_MSG = new Message(CATALOG, error + " : ", Integer.parseInt("3"));
		logger.log(ERROR_MSG, e);
	}
	
	public static void logErrorMessages(String error) {
		ERROR_MSG = new Message(CATALOG, error + " : ", Integer.parseInt("3"));
		logger.log(ERROR_MSG);
	}

	
	public static void logInfoMessages(String message) {
		INFO_MSG = new Message(CATALOG, message, Integer.parseInt("1"));
		logger.log(INFO_MSG);
	}
	
}
