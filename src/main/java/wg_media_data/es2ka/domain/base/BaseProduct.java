package wg_media_data.es2ka.domain.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import wg_media_data.es2ka.domain.*;
import wg_media_data.es2ka.persistence.DataBasePresistenceService;
import wg_media_data.es2ka.persistence.PersistenceService;

public abstract class BaseProduct {
	public static final String TYPE="__type";
	public static Logger logger = Logger.getLogger("mysql");
	
	
	protected Map<String,Object> data;
	
	public abstract void transSource(Map<String, Object> from);

	public abstract String getType();
	
	public PersistenceService persistenceService() {
//		return mysqlPresistenceService.instance;
//		return new ConsolePresistenceService();
		return new DataBasePresistenceService();
	}

	public static  BaseProduct create(String type) {
		switch (type) {
		case "APP":
			return new App();
		case "AUDIO":
			return new Audio();
		case "COLUMN":
			return new Column();
		case "COMPO":
			return new Compo();
		case "CONTENT":
			return new Content();
		case "GALLERY":
			return new Gallary();
		case "GRAPH":
			return new Graph();
		case "HOME":
			return new Home();
		case "HREF":
			return new Href();
		case "HTML5":
			return new Html5();
		case "LINK":
			return NULLTYPE;
		case "LIVE":
			return NULLTYPE;
		case "PAPER":
			return new Paper();
		case "PHOTO":
			return NULLTYPE;
		case "PLANNING":
			return new Planning();
		case "TOPIC":
			return new Topic();
		case "TEXT":
			return new Text();
		case "VIDEO":
			return new Video();
		case "WEBSITE":
			return new Website();
		case "WECHAT":
			return new Wechat();
		case "WEIBO":
			return new Weibo();
		case "RABBITMQ":
			return new Rabbitmq();
		default:
			return NULLTYPE;
		}
	}
	private static BaseProduct NULLTYPE = new BaseProduct(){

		@Override
		public void transSource(Map<String, Object> from) {
		}

		@Override
		public String getType() {
			return "NULL";
		}
		
	};

	/**
	 * 数据进入mysql
	 * 
	 * @param from 数据对象
	 * @return
	 */
	public void persistence(Map<String, Object> from) {
		if(this == NULLTYPE) {
			return;
		}
		processData(from);
		//不同数据源处理数据
//		String url = (String) from.get("url");
//		String uuid = MD5Util.createMd5(url);
//		from.put("uuid", uuid);
		//
	    if(data==null) {
	    	return;
	    }
	    
		transSource(data);
		data.put(TYPE, getType());
		try {
			//添加数据到mysql
			boolean result= persistenceService().save(data);
			logger.info("data type is: "+getName()+" insert db result  is :"+result);
//			logger.info("data type is: "+getName()+" insert mysql data :" + data+" insert result  is :"+result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("insert mysql error data :" + data);
		}
	}
	@SuppressWarnings("unused")
	private void processData(Map<String, Object> from) {
		//title,content,columnName,type,pubtime,url,author,website
		JSONObject obj = new JSONObject(from);
		data = new HashMap<>();
		data.put("title", obj.getString("title"));
		data.put("content", obj.getString("content"));
		data.put("columnName", obj.getString("columnNames"));
		data.put("type", obj.getString("type"));
		JSONArray channels = obj.getJSONArray("channel");
		if(channels!=null && channels.size()>0) {
			JSONObject channel = channels.getJSONObject(0);
			data.put("website", channel.getString("name"));
			data.put("pubtime", channel.getDate("issueDate"));
			JSONArray columns = channel.getJSONArray("column");
			if(columns!=null &&columns.size()>0) {
				data.put("url", columns.getJSONObject(0).getString("issueUrl"));
			}
		}
		JSONArray creators = obj.getJSONArray("creators");
		if(creators!=null && creators.size()>0) {
			String author = creators.getJSONObject(creators.size()-1).getString("name");
			data.put("author", author);
		}
		if(!data.containsKey("url")||data.get("url")==null) {
			data=null;
		}
	}
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
