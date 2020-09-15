package com.brownfield.pss.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.brownfield.pss.book.component.BookingComponent;
import com.brownfield.pss.book.entity.BookingRecord;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@CrossOrigin
@RequestMapping("/booking")
public class BookingController {
	private BookingComponent bookingComponent;

	private final AtomicLong counter = new AtomicLong();

	private static final String template = "Hello, %s!";

	@Autowired
	public BookingController(BookingComponent bookingComponent){
		this.bookingComponent = bookingComponent;
	}

	@RequestMapping(value="/create" , method = RequestMethod.POST)
	public long book(@RequestBody BookingRecord record){
		System.out.println("Booking Request" + record); 
		return bookingComponent.book(record);
	}
	
	@RequestMapping("/get/{id}")
	public Optional<BookingRecord> getBooking(@PathVariable long id){
		return bookingComponent.getBooking(id);
	}

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		System.out.println("Greeting REST Call........................");
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}


}
