package com.NeoAPI.practice;

/*The below API provides details of all near earth objects:
http://www.neowsapp.com/rest/v1/neo/browse
Write a program that does the following:
Call the API to retrieve the list of all near earth objects (neo)
Display three neo (and their properties) that orbit earth and have the ‘close approach date’ nearest to current date (ignore those which have a ‘close approach date’ in the past). 
Display the smallest neo that orbits Jupiter and the largest neo that orbits Mercury (based on diameter)
Write automated unit test cases for the above code
Provide a readme file with the steps required to setup and run the above code


*/

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.junit.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;




public class ApiNeo {
	

		public List <HashMap<String,String>> NeoList;
		
		long closestDate = Long.MAX_VALUE;
		float smallestSize = Long.MAX_VALUE;
		float biggestSize = Long.MIN_VALUE;
		
		String[] closestAppNeo = new String[3];
		String neo;
		String largestNeoMercury;
		String smallestNeoJupiter;
		

		@Test
		public void getAllNeos() throws ParseException {

			RestAssured.baseURI = "http://www.neowsapp.com/rest/v1/neo";

		
			JsonPath jPath1 = given().when().get("/browse").jsonPath();
			
			int pagesNum=10;

			
			NeoList = new ArrayList <HashMap<String, String>>(); //All NEO list

			for (int i =0; i<=pagesNum; i++) {
				Response response=given().queryParam("page", i).when().get("/browse");

				String js=response.body().asString();

				JsonPath jPath2=new JsonPath(js);

				List <HashMap<String, String>> neoList=jPath2.get("near_earth_objects");

				for (int j = 0; j < neoList.size(); j++) {
					neo = jPath2.get("near_earth_objects[" + j + "].id");
					Float minDiameter = jPath2
							.get("near_earth_objects[" + j + "].estimated_diameter.kilometers.estimated_diameter_min");
					if (!((ArrayList) jPath2.get("near_earth_objects[" + j + "].close_approach_data.orbiting_body"))
							.isEmpty()) {
						for (int x = 0; x < ((ArrayList) jPath2
								.get("near_earth_objects[" + j + "].close_approach_data.orbiting_body")).size(); x++) {
							String orbitBody = jPath2
								.get("near_earth_objects[" + j + "].close_approach_data[" + x + "].orbiting_body");
							String closeAppDate = jPath2.get(
									"near_earth_objects[" + j + "].close_approach_data[" + x + "].close_approach_date");
							threeNeos(orbitBody, closeAppDate);
							smallestJupiter(orbitBody, minDiameter);
							biggestMercury(orbitBody, minDiameter);
						}
					}

			}

				NeoList.addAll(neoList);
				System.out.println("Current size of fullNeoList: " + NeoList.size() + ". Pages worked " + i + " of "
						+ pagesNum);

			}

            System.out.println("closestAppNeo ID's: " + closestAppNeo[0] + " " + closestAppNeo[1] + " "
					+ closestAppNeo[2]);
			System.out.println("smallestJupiter: " + smallestNeoJupiter);
			System.out.println("largestMercury: " + largestNeoMercury);

			
		}


		public void threeNeos(String orbit, String date) throws ParseException { //Display three NEO 
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			long today = new Date().getTime();
			long appDate = dateFormat.parse(date).getTime();
			if (orbit.equals("Earth") && appDate - today > 0 && appDate - today < closestDate) {
				closestDate = appDate - today;
				closestAppNeo[2] = closestAppNeo[1];
				closestAppNeo[1] = closestAppNeo[0];
				closestAppNeo[0] = neo;
			}
		}


		public void smallestJupiter(String orbit, Float diameter) {
			if (orbit.equals("Juptr") && diameter < smallestSize) {
				smallestSize = diameter;
				smallestNeoJupiter = neo;
			}
		}


		public void biggestMercury(String orbit, Float diameter) {
			if (orbit.equals("Merc") && diameter > biggestSize) {
				biggestSize = diameter;
				largestNeoMercury = neo;
			}
		}

		public void NeoID (String id) {
			given().when().get("/"+id+"").jsonPath().prettyPrint();
		}

}
