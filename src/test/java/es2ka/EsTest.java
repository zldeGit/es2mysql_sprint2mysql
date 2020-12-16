package es2ka;

import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountBuilder;
import org.junit.Test;

import wg_media_data.es2ka.config.Config;
import wg_media_data.es2ka.launch.ProductScrollGetData;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

public class EsTest {

	
	@Test
	public void testEs() {
		ProductScrollGetData p = new ProductScrollGetData();

		Client client = p.getClient();
		SearchRequestBuilder req = client.prepareSearch("sprint_mixmedia_product")
				.setQuery(new BoolQueryBuilder().must(matchAllQuery()))
				.setSize(35).setScroll(TimeValue.timeValueMinutes(4));

		SearchResponse searchResponse = req.get();
		System.out.println(searchResponse.getHits());

	}
}
