package com.toby.security.demo.dto;

public class FileInfo {
	public FileInfo(String filePath) {
		this.filePath = filePath;
	}
	
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
