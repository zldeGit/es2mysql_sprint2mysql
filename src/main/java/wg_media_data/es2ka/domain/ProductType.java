package wg_media_data.es2ka.domain;

import java.util.Map;

/**
 * 采编发稿件类型
 * 
 * @author 18170 COMMPO 混合类型 VIDEO 视频类型 AUDIO 音频类型
 */
public enum ProductType {

	APP, AUDIO, COMPO, CONTENT, GALLERY, GRAPH, HOME, HREF, HTML5, LINK, LIVE, PAPER, PHOTO, PLANNING, TEXT, TOPIC,
	VIDEO, WEBSITE, WECHAT, WEIBO, RABBITMQ;
	
	public Map<String, Object> transSource(Map<String, Object> from) {
		return from;
	}

}
