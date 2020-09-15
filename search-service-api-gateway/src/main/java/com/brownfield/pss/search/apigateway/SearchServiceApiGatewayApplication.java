package com.brownfield.pss.search.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.cloud.sleuth.sampler.AlwaysSampler
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@CrossOrigin
@EnableSwagger2
//@EnableCircuitBreaker
public class SearchServiceApiGatewayApplication {

	private static final Logger logger = LoggerFactory.getLogger(SearchServiceApiGatewayApplication.class);

	/*@Bean
	public AlwaysSampler defaultSampler() {
		System.out.println("Sample Getting Initalized.....+++++ in Search API Gateway");
		return new AlwaysSampler();
	}*/

	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApiGatewayApplication.class, args);
	}

}

@RestController
class SearchAPIGatewayController {
	private static final Logger logger = LoggerFactory.getLogger(SearchAPIGatewayController.class);

	@Autowired
	SearchAPIGatewayComponent component;

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/")
	String greet(HttpServletRequest req) {
		return "<H1>Search Gateway Powered By Zuul</H1>";
	}

	@RequestMapping("/hubongw")
	String getHub() {
		logger.info("Search Request in API gateway for getting Hub, forwarding to search-service ");
		return component.getHub();
	}
}

@Component
class SearchAPIGatewayComponent {

	@LoadBalanced
	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getDefaultHub")
	public String getHub() {
		String hub = restTemplate.getForObject("http://search-service/search/hub", String.class);
		return hub;
	}

	public String getDefaultHub() {
		return "Possibily SFO";
	}
}

@Configuration
class AppConfiguration {

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
