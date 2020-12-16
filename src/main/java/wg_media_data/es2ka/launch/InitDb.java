package wg_media_data.es2ka.launch;

import java.lang.reflect.Method;
import java.util.List;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;

import wg_media_data.es2ka.model.common.SyncCaibianData;

public class InitDb {
	private static String jdbcUrl = "jdbc:mysql://#IP/#DB?characterEncoding=utf8";
	private static Prop prop = PropKit.use("push.properties");
	
	public static void init() {

		String ip = prop.get("jdbc.ip");
		String db = prop.get("jdbc.db");
		String username = prop.get("jdbc.username");
		String password = prop.get("jdbc.password");

		initDataSource(ip,db,username,password, "wg_media_data.es2ka.model.common._MappingKit");
	}

	/**
	 * jfinal初始化数据库
	 * @param ip
	 * @param dbname
	 * @param userName
	 * @param password
	 * @param mappingClassName
	 */
	public static void initDataSource(String ip,String dbname,String userName,String password,String mappingClassName){
		try{
			String str_kb = jdbcUrl.replace("#IP", ip).replace("#DB",dbname);			
			DruidPlugin dp_kb = new DruidPlugin(str_kb, userName, password);			
			Class<?> obj = Class.forName(mappingClassName);
			Method method = obj.getMethod("mapping", ActiveRecordPlugin.class);
			ActiveRecordPlugin arp_kb = new ActiveRecordPlugin(dbname, dp_kb);
			method.invoke(obj, new Object[] { arp_kb });
			arp_kb.setShowSql(false);			
			dp_kb.start();
			arp_kb.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
