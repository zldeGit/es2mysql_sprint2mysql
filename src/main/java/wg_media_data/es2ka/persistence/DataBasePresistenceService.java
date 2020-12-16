package wg_media_data.es2ka.persistence;

import java.util.Date;
import java.util.Map;

import wg_media_data.es2ka.model.common.SyncCaibianData;

public class DataBasePresistenceService implements PersistenceService {

	@Override
	public boolean save(Map<String, Object> from) {
		//title,content,columnName,type,pubtime,url,author,website
		String content = String.valueOf(from.get("content"));
		String title = String.valueOf(from.get("title"));
		String url = String.valueOf(from.get("url"));
		String website = String.valueOf(from.get("website"));
		Date pubtime = (Date)from.get("pubtime");
		String author = String.valueOf(from.get("author"));
//		String pictures = String.valueOf(from.get("pictures"));
		String columnName = String.valueOf(from.get("columnName"));
		SyncCaibianData data = new SyncCaibianData();
		
		data.setTitle(title);
		data.setContent(content);
		data.setUrl(url);
		data.setWebsite(website);
		data.setPubtime(pubtime);
		data.setAuthor(author);
		data.setColumnName(columnName);
		data.setInsertTime(new Date());
		try {
			return data.save();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("info error:");
		}
	}

}
