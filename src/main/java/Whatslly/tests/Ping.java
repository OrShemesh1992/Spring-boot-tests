package Whatslly.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;

public class Ping {
	private String success;
	private String sfVersion;
	private String host;
	private String version;
	private String saEndpoint;
	private Boolean newPackage;
	
	public Ping(String html) {
		// connect to html example html = https://api-us1.whatslly.com/test/ping.json
        Connection conn = Jsoup.connect(html).ignoreContentType(true).method(Method.GET);
        Connection.Response response;
    	try {
			response = conn.execute();
			Document parsed = response.parse();
	        Element form = parsed.select("body").first();
	        //parse json string to onject ping by gson of google
	        Gson g = new Gson(); 
	        Ping temp = g.fromJson(form.text(), Ping.class) ;
			
	        this.success = temp.getSuccess();
	        this.sfVersion = temp.getSfVersion();
	        this.host = temp.getHost();
	        this.version = temp.getVersion();
	        this.saEndpoint = temp.getSaEndpoint();
	        this.newPackage = temp.getNewPackage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getSuccess() {
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
	
	public static void main(String[] args) {
		Ping a = new Ping("https://api-us1.whatslly.com/test/ping.json");
		System.out.println(a);
	}

}
