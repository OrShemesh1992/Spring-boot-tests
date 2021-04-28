package Whatslly.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *		      Tests for all servers
 	
 		https://api-us1.whatslly.com/test/ping.json
		https://api-br1.whatslly.com/test/ping.json
		https://api-eu1.whatslly.com/test/ping.json
		https://api-st1.whatslly.com/test/ping.json
		
 * @author Or Shemesh
 *
 */

@SpringBootTest
class TestsWhatsllyApplicationTests {
	
	//variables
	private static Ping p=new Ping();
	private static String host;
	
	@BeforeAll
	static void setUp() throws Exception {
		//Request user to enter host Example: https://api-us1.whatslly.com/test/ping.json
		Scanner sc= new Scanner(System.in);
		System.out.print("Enter a html test: ");
		host= sc.nextLine(); 
		
		// load from host json to object "ping"
		p.loadFromHtml(host);


		// Get the file
		File f = new File("Output.json");

		// Check if the specified file
		// Exists or not
		if (f.exists()) {
			System.out.println("Exists");
		}
		else {
			p.WriteToJson("Output.json");
		}

	}

	@Test
	void Status() {
		//check if status true
		assertEquals(p.getSuccess(),true);
	}	
	@Test
	void host() {
		// split string host by /
		//Example: [https:],[api-us1.whatslly.com],[test],[ping.json]
		String[] slice1 = host.split("/"); //returns an array with the 2 parts
		String[] slice2 = p.getHost().split("/");
		String host1="";
		String host2="";
		
		//Takes only the part of the host
		//Example: [api-us1.whatslly.com]
		for (int i = 0; i < slice1.length; i++) {
			if(slice1[i].contains("api")) {
				host1=slice1[i]; 
			}
		}
		for (int i = 0; i < slice2.length; i++) {
			if(slice2[i].contains("api")) {
				host2=slice2[i]; 
			}
		}
		//check if host is correct
		assertEquals(host1,host2);
	}	

	@Test
	void version() {
		Ping old_version;
		try {
			//get old version from older file json 
			old_version = p.loadFromJson("Output.json");

			//split version by "." [5],[5],[53]
			String[] old_V= old_version.getVersion().split("\\.");
			String[] new_V= p.getVersion().split("\\.");

			//Checks if the new version is larger than the old version
			for (int i = 0; i < new_V.length; i++) {
				assertTrue(Integer.parseInt(old_V[i])<=Integer.parseInt(new_V[i]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//if all tests pass load new json file 
		p.WriteToJson("Output.json");
	}
}
