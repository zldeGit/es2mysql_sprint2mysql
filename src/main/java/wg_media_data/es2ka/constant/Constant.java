package wg_media_data.es2ka.constant;

import java.io.IOException;
import java.util.Iterator;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class Constant {
	/**
	 * 空searchhits对象
	 */
	public  static SearchHits EMPTY_SEARCHHITS = new SearchHits() {

		@Override
		public void readFrom(StreamInput in) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void writeTo(StreamOutput out) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Iterator<SearchHit> iterator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long totalHits() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getTotalHits() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float maxScore() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float getMaxScore() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public SearchHit[] hits() {
			// TODO Auto-generated method stub
			return new SearchHit[0];
		}

		@Override
		public SearchHit getAt(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public SearchHit[] getHits() {
			return new SearchHit[0];
		}
		
	};
	

}
