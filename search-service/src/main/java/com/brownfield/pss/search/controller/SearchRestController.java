package com.brownfield.pss.search.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.brownfield.pss.search.component.SearchComponent;
import com.brownfield.pss.search.entity.Flight;

/**
 * @author bhavesh_rajdev
 *
 */
@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchRestController {

	private static final Logger logger = LoggerFactory.getLogger(SearchComponent.class);

	/**
	 * 
	 */
	@Value("${originairports.shutdown}")
	private String originairports;

	private SearchComponent searchComponent;

	@Autowired
	public SearchRestController(SearchComponent searchComponent) {
		this.searchComponent = searchComponent;
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public List<Flight> search(@RequestBody SearchQuery query) {
		logger.info("Input : " + query);
		if (Arrays.asList(originairports.split(",")).contains(query.getOrigin())) {
			logger.info("The origin airport is in shutdown state");
			return new ArrayList<Flight>();
		}
		System.out.println("Input : " + query);
		logger.info("Input "+query);
		return searchComponent.search(query);
	}
	
	@RequestMapping("/hub")
	public String getHub(HttpServletRequest req){
		logger.info("Searching for Hub, received from search-apigateway ");
		return "SFO"; 
	}

	
}
