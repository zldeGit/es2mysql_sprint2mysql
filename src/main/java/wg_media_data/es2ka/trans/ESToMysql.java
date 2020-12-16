package wg_media_data.es2ka.trans;

import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import wg_media_data.es2ka.util.MD5Util;

public class ESToMysql {
	Logger logger  = Logger.getLogger("mysql");
	/**
	 * 数据进入mysql
	 * @param from 数据对象
//	 * @param type 类型
	 * @return
	 */
	public void insertToMysql(Map<String,Object> from) {
		String url = (String)from.get("url");
		String uuid = MD5Util.createMd5(url);
		from.put("uuid", uuid);
		boolean result = false;
		try {
			String dataJsonStr = JSON.toJSONString(from);
//			result = FusionsightProducer.send("news_info",uuid , JSON.toJSONString(from));
			logger.info("insert mysql data :"+dataJsonStr);
			logger.info("insert mysql result :"+result);
			System.out.println(dataJsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("insert mysql error data :"+from);
		}
	}
}
