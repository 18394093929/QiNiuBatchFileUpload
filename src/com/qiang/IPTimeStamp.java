package com.qiang;
//PTimeStamp.java

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IPTimeStamp {
	private String ip;
	private Date date;
	private SimpleDateFormat format;

	public IPTimeStamp() {
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTimeStamp() {
		format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(new Date());
	}

	public String addZero(String str, int len) {
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		while (sb.length() < len) {
			sb.insert(0, "0");
		}
		return sb.toString();
	}

	public String getIPTimeStampRandom() {
		StringBuffer sb = new StringBuffer();
		String[] ips = this.ip.split("\\.");

		for (int j = 0; j < ips.length; j++) {
			// System.out.println(ips[j]);
			sb.append(this.addZero(ips[j], 3));
		}
		sb.append(this.getTimeStamp());
		Random rod = new Random();
		for (int i = 0; i < 3; i++) {
			sb.append(rod.nextInt(10));
		}
		return sb.toString();
	}
}
