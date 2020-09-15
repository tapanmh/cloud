/**
 * 
 */
package com.brownfield.pss.book.component;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bhavesh_rajdev
 *
 */
@FeignClient(name="fares-service")
public interface FareServiceProxy {

	/**
	 * @param flightNumber
	 * @param flightDate
	 * @return Fare 
	 */
	@RequestMapping(value = "fares/get", method=RequestMethod.GET)
	public Fare getFare(@RequestParam(value = "flightNumber") String flightNumber,
			@RequestParam(value = "flightDate") String flightDate);

}
