package app.justlykbot.datatypes;

import java.io.Serializable;

public class Server implements Serializable{
	
	private String name;
	private String function;
	private String ipAddress;
	private String domain;
	private String port;
	private String service;
	private String xcapRoot;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getXcapRoot() {
		return xcapRoot;
	}
	public void setXcapRoot(String xcapRoot) {
		this.xcapRoot = xcapRoot;
	}
	
	@Override
	public String toString()
	{
		return name+": "+function+" server"+
				"\nIP:"+ipAddress;
	}
	
	

}
