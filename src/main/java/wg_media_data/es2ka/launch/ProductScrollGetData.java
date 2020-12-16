package wg_media_data.es2ka.launch;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import org.springframework.web.client.RestTemplate;
import wg_media_data.es2ka.config.Config;
import wg_media_data.es2ka.constant.Constant;
import wg_media_data.es2ka.domain.base.BaseProduct;

public class ProductScrollGetData {

	public static Logger logger = Logger.getLogger(ProductScrollGetData.class);
	/**
	 * 连接es客户端
	 */
	private Client client;

	/**
	 * es滚动id
	 */
	private String scollorId;

	private String esIndexs = "";

	public String getType() {
		return type;
	}

	public String getEsIndexs() {
		return esIndexs;
	}

	public void setEsIndexs(String esIndexs) {
		this.esIndexs = esIndexs;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String type = "";

//	private ESTomysql esTomysql = new ESTomysql();

	private SearchHits hits = Constant.EMPTY_SEARCHHITS;

	@SuppressWarnings("resource")
	public Client getClient() {
		if (client == null) {
			try {
				String esServer = Config.getEsServerIp();
				String esProt = Config.getEsTransProt();
				String esClusterName = Config.getEsClusterName();
				Map<String, String> map = new HashMap<String, String>();
				map.put("cluster.name", esClusterName);
				client = new TransportClient(ImmutableSettings.settingsBuilder().put(map)).addTransportAddress(
						new InetSocketTransportAddress(InetAddress.getByName(esServer), Integer.parseInt(esProt)));
			} catch (NumberFormatException | UnknownHostException e) {
				logger.error("init es client error:" + e.getMessage());
				throw new RuntimeException("init es client error:" + e.getMessage());
			}
		}
		return client;
	}


	/**
	 * 获取scollorId
	 * 
	 * @return
	 */
	private void scollorId() {
		while (true) {
			if (scollorId == null || "".equals(scollorId)) {
				try {
					Long from = Config.getPushTime();
					SearchRequestBuilder builder = getClient().prepareSearch().setIndices(esIndexs)
							.setQuery(new BoolQueryBuilder().must(matchAllQuery())
									.must(new RangeQueryBuilder(Config.getPushSortField()).gt(from)))
							.setSize(35).setScroll(TimeValue.timeValueMinutes(4))
							.addSort(Config.getPushSortField(), SortOrder.ASC);
					logger.info("query DSL:" + builder.toString());
					if (type != null && type != "")
						builder.setTypes(type);

					SearchResponse response = builder.execute().actionGet();
					hits = response.getHits();
					if (!validateHits()) {
						threadSleep("暂无数据");
						continue;
					}
					this.scollorId = response.getScrollId();
					pushDataTomysql();
					saveLastPushTime();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("get es scolorId error:" + e.getMessage());
					saveLastPushTime();
					this.scollorId = "";
					threadSleep(e.getMessage());
				}
			} else {
				break;
			}
		}
	}

	/**
	 * 线程休眠一段时间
	 */
	private void threadSleep(String msg) {
		if (StringUtils.isEmpty(msg)) {
			msg = "暂无数据";
		}
		try {
			long sleepTime = Config.getSleepTime();
			logger.info(msg + "、线程休息: " + sleepTime + " 毫秒");
			Thread.sleep(sleepTime);

		} catch (InterruptedException e1) {
			e1.printStackTrace();
			logger.error("thread sleep exception:" + e1.getMessage());
		}
	}

	/**
	 * 向mysql推送数据
	 */
//	private void pushDataTomysql() {
//		for (SearchHit hit : hits) {
//			String dataType = hit.getType();
//			Map<String, Object> source = hit.getSource();
//			ProductType type = ProductType.valueOf(dataType);
//			type.transSource(source);
//			esTomysql.insertTomysql(type.transSource(source));
//		}
//	}

	private void pushDataTomysql() {
		for (SearchHit hit : hits) {
			String dataType = hit.getType();
			Map<String, Object> source = hit.getSource();
			BaseProduct.create(dataType).persistence(source);
		}
	}

	/**
	 * 根据scollorId获取数据并推送到mysql
	 * 
	 * @throws InterruptedException
	 */
	public void socllorGetAndPushData() throws InterruptedException {
		while (true) {
			if (scollorId == null || "".equals(scollorId)) {
				scollorId();
			}
			try {
				SearchResponse searchResponse = getClient().prepareSearchScroll(scollorId)
						.setScroll(TimeValue.timeValueMinutes(2)).execute().actionGet();
				hits = searchResponse.getHits();
				if (!validateHits()) {
					threadSleep("暂无数据");
					continue;
				}

				pushDataTomysql();
				saveLastPushTime();
			} catch (ElasticsearchException e) {
				e.printStackTrace();
				logger.error("get es socllorGetDate error:" + e.getMessage());
				scollorId=null;
				threadSleep(e.getMessage());
			}
		}
	}

	/**
	 * 验证es查询数据是否为空
	 * 
	 * @return
	 */
	private boolean validateHits() {
		if (hits == null || hits.getHits().length <= 0) {
			this.scollorId = "";
			this.hits = Constant.EMPTY_SEARCHHITS;
			return false;
		}
		return true;
	}

	/**
	 * 保留上次推送信息
	 * 
	 */
	public void saveLastPushTime() {
		if (hits == null)
			return;
		SearchHit[] searchHits = hits.getHits();
		if (searchHits.length <= 0)
			return;
		SearchHit searchHit = searchHits[searchHits.length - 1];
		Map<String, Object> source = searchHit.getSource();
		Object updatedObj = source.get(Config.getPushSortField());
		if (updatedObj == null)
			return;
		String updtedtr = String.valueOf(updatedObj);
		Config.setPushTime(updtedtr);
	}

	
	public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException {
		File directory = new File("");//设定bai为当前文件夹
			System.out.println(directory.getCanonicalPath());//获取标准的du路径
			System.out.println(directory.getAbsolutePath());//获取绝对路径
		InitDb.init();
		ProductScrollGetData productScrollGetData = new ProductScrollGetData();
		productScrollGetData.esIndexs = Config.getEsIndexs();
		productScrollGetData.socllorGetAndPushData();
	}
	
	
	
	

}
