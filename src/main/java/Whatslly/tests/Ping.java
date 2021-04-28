package Whatslly.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
/**
 * class Ping represents the file json from the server
 * @author orshemesh
 *
 */
public class Ping {
	//variables
	private Boolean success;
	private String sfVersion;
	private String host;
	private String version;
	private String saEndpoint;
	private Boolean newPackage;
	
	//constructor
	public Ping() {
		super();
	}
	
	//copy constructor
	public void init(Ping other) {
		this.success=other.getSuccess();
		this.sfVersion=other.getSfVersion();
		this.host=other.getHost();
		this.version=other.getVersion();
		this.saEndpoint=other.getSaEndpoint();
		this.newPackage=other.getNewPackage();
	}
	
	/**
	 * The function gets a file name ans then 
	 * reads from file and convert to object "Ping" 
	 * I used "Gson" to load object
	 * @param file_name
	 * @return object "Ping" 
	 * @throws FileNotFoundException
	 */
	public Ping loadFromJson(String file_name) throws FileNotFoundException {
		//read from file to JsonObject
		FileInputStream input = new FileInputStream(file_name);
		JsonReader read = new JsonReader(new InputStreamReader(input));
		JsonObject element = JsonParser.parseReader(read).getAsJsonObject(); 
		
		//convert JsonObject to object "Ping" 
		Gson g = new Gson(); 
	    Ping temp = g.fromJson(element, Ping.class);

	    return temp;
	}
	
	/**
	 * The function gets a file name and then write to file 
	 * I used "Gson" to write object
	 * @param file_name
	 */
	public void WriteToJson(String file_name) {
		try {
			
			Writer writer = new FileWriter(file_name);
		    Gson gson = new Gson();
		    gson.toJson(this , writer);
		    writer.flush(); //flush data to file   <---
		    writer.close(); //close write          <---
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  The function gets a host name Connecting and then from
	 *  html file get json string and initialize this object
	 * @param html
	 */
	public void loadFromHtml(String html) {
		try {
			// connect to html example html = https://api-us1.whatslly.com/test/ping.json
			//jsoup libary
	        Connection conn = Jsoup.connect(html).ignoreContentType(true).method(Method.GET);
	        Connection.Response response= conn.execute();
			
	        //get element from respone
	        Document parsed = response.parse();
		    Element form = parsed.select("body").first();
		    
		    //parse json string to object ping by gson of google
		    Gson g = new Gson(); 
		    Ping temp = g.fromJson(form.text(), Ping.class);
		    
		    //initialization
		    init(temp);
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean getSuccess() {
		return success;
	}
	public String getSfVersion() {
		return sfVersion;
	}
	public String getHost() {
		return host;
	}
	public String getVersion() {
		return version;
	}
	public String getSaEndpoint() {
		return saEndpoint;
	}
	public Boolean getNewPackage() {
		return newPackage;
	}
	
	@Override
	public String toString() {
		return "Ping [success=" + success + ", sfVersion=" + sfVersion + ", host=" + host + ", version=" + version
				+ ", saEndpoint=" + saEndpoint + ", newPackage=" + newPackage + "]";
	}

}
