package wg_media_data.es2ka.domain;

import java.util.Map;

import wg_media_data.es2ka.domain.base.BaseProduct;

public class Live extends BaseProduct {

	@Override
	public void transSource(Map<String, Object> from) {
		
	}

	@Override
	public String getType() {
		return "APP";
	}

}
