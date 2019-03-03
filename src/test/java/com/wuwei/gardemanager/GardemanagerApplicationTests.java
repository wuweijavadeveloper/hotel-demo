package com.wuwei.gardemanager;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GardemanagerApplicationTests {
	@Autowired
	private SolrClient solrClient;

	@Test
	public void contextLoads() throws IOException, SolrServerException {

		SolrInputDocument document = new SolrInputDocument();
		// 注意：id的域不能少
		document.addField("id", "4290061993");
		document.addField("name", "15609282900");

		solrClient.add(document);
		solrClient.commit();

	}

	@Test
	public void querySolr() throws IOException, SolrServerException {

		SolrQuery solrQuery=new SolrQuery();
		solrQuery.set("q","name:全是灰110");
		//solrQuery.setStart(2);
		//solrQuery.setRows(3);
		solrQuery.setHighlight(true);
		QueryResponse queryResponse=solrClient.query(solrQuery);
		SolrDocumentList results = queryResponse.getResults();
		// 查询结果总数
		long cnt = results.getNumFound();
		System.out.println("查询结果总数:" + cnt);
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("name"));
		}





	}

}

