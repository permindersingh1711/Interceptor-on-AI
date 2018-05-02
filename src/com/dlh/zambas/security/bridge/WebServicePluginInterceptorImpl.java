package com.dlh.zambas.security.bridge;

import java.util.Map;
import java.util.TreeMap;

import com.actional.soapstation.plugin.inproc.DOMType;
import com.actional.soapstation.plugin.inproc.ICallInfo;
import com.actional.soapstation.plugin.inproc.IInit;
import com.actional.soapstation.plugin.inproc.IInterceptor;

/**
 * sample Interceptor to understand core concepts
 * 
 * @author pesingh
 *
 */
public class WebServicePluginInterceptorImpl implements IInterceptor, IInit {

	private ICallInfo callInfo = null;

	/**
	 * default constructor to mock
	 */
	public WebServicePluginInterceptorImpl() {

	}

	/**
	 * Method getting called for every request
	 */
	@Override
	public void invoke(IInvokeContext context) throws Exception {
		Map<String, Object> map = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);
		try {
			LoggerUtil.logInfoMessages("Sample AI Interceptor");
			callInfo = context.getCallInfo();
			map.putAll(callInfo.getTransportProperties());
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				LoggerUtil.logInfoMessages("key=" + entry.getKey());
				LoggerUtil.logInfoMessages("value=" + entry.getValue());
			}
			LoggerUtil.logInfoMessages("Request type : " + callInfo.getXmlDocument(DOMType.XML_DOCUMENT_READ_ONLY));
			if (!callInfo.getAccessPointName().contains("AccessPoint")) {
				LoggerUtil.logInfoMessages("This is an Soap Request");
			} else {
				LoggerUtil.logInfoMessages("This is an Rest Request");
			}
		} catch (Exception e) {
			LoggerUtil.logErrorMessages("Exception thrown:", e);
			throw new Exception("Exception thrown :", e);
		}
		/**
		 * to change anything in Request please use the below commented code
		 */
		/*callInfo.getTransportProperties().put(Constants.AUTHORIZATION, Constants.BASIC + this.authString);
		callInfo.setTransportProperties(callInfo.getTransportProperties(), true);*/
	}

	@Override
	public void destroy(IDestroyContext arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * This method will get called only once during plugin deployment and will
	 * check if plugin contains all required params .please put all required
	 * configuration files on AI
	 */
	@Override
	public void init(IInitContext paramIInitContext) throws Exception {

		String[] items = ((String) paramIInitContext.getSettings().getSetting("SETTINGS")).split(";");

		LoggerUtil.logInfoMessages("Total items : " + items.length);

		/**
		 * initial check to confirm whether plugin contains all required params.
		 * One time activity required at time of plugin deployment.
		 */
		if (items.length != 1) {

			throw new Exception("Wrong number of items in SETTINGS. (need 1: configurationFile): "
					+ (String) paramIInitContext.getSettings().getSetting("SETTINGS"));

		} else {
			// queueName
			String configPath = items[0].substring(items[0].indexOf('=') + 1);
			LoggerUtil.logInfoMessages("configuration file path is : " + configPath);
		}
	}

}
