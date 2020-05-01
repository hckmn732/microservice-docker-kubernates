package com.knbs.covidservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knbs.covidservice.wrapper.Country;
import com.knbs.covidservice.wrapper.Wrapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class MyRestController {
	
	@GetMapping("/")
	public ResponseEntity getHome(){
		return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
	}
	
	@GetMapping("/data")
	private List<Country> allCountries() {
		List<Country> result = new ArrayList<Country>();
		try {
			result	= this.getData().getCountries();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GetMapping("/data/{cntr}")
	private Country getCountry(@PathVariable("cntr") String cntr) {
		System.out.println(cntr);
		Country country = new Country();
		try {
			List<Country> result = this.getData().getCountries();
			for (Country c : result) {
				if(c.getCountry().equalsIgnoreCase(cntr)){
					country = c;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return country;
	}
	
	@GetMapping("/countries")
	private List<String> getCountryList() {
		List<String> countries = new ArrayList<String>();
		try {
			List<Country> result = this.getData().getCountries();
			for (Country c : result) {
				countries.add(c.getCountry());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return countries;
	}
  
	private Wrapper getData() throws Exception {
	String url = "https://api.covid19api.com/summary";
	URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	con.setRequestMethod("GET");
	con.setRequestProperty("User-Agent", "Mozilla/5.0");
	BufferedReader in = new BufferedReader(
			new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();

    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(response.toString(), Wrapper.class);    
  }

}