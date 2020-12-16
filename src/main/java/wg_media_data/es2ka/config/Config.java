package wg_media_data.es2ka.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Config {
	private  static PropertiesConfiguration  CONFIG ;
	

	
	static {
		try {
			CONFIG= new PropertiesConfiguration("push.properties");
		} catch (ConfigurationException e) {
		}
	}
	/**
	 * 获取上次更新时间
	 * @return
	 */
	public static Long getPushTime() {
		return CONFIG.getLong("pushTime", System.currentTimeMillis());
	}
	
	/**
	 * 文件保存
	 * @return
	 */
	public static void save () {
		try {
			CONFIG.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 线程休眠时间
	 * @return
	 */
	public static long getSleepTime() {
		return CONFIG.getLong("threadSleepTime",30)*60*1000;
	}
	
	/**
	 * 推送排序字段
	 * @return
	 */
	public static String getPushSortField() {
		return CONFIG.getString("pushSortField");
	}

	/**
	 * 保存更新信息
	 * @param updatedStr
	 */
	public static void setPushTime(String updatedStr) {
		CONFIG.setProperty("pushTime", updatedStr);
		save();
	}

	public static String getEsIndexs() {
		String esIndex = CONFIG.getString("esIndexs");
		return esIndex;
	}

	public static String getEsServerIp() {
		return CONFIG.getString("esServerIp");
	}

	public static String getEsTransProt() {
		return CONFIG.getString("esTransProt");
	}

	public static String getEsClusterName() {
		return CONFIG.getString("esClusterName");
	}
	
	public static void main(String[] args) {
		System.out.println(getEsClusterName());
	}
	

}
