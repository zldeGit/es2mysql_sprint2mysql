package wg_media_data.es2ka.persistence;

import java.util.Map;

public class ConsolePresistenceService implements PersistenceService {

	@Override
	public boolean save(Map<String, Object> from) {
		System.out.println(from);
		return true;
	}

}
