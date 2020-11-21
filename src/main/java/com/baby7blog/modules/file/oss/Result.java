package com.baby7blog.modules.file.oss;

import lombok.Data;

@Data
public class Result {
	private Integer code;
	private String url;

	public Result(String url){
		if(url != null){
			this.code = 200;
			this.url = url;
		}
		else {
			this.code = 500;
		}
	}
}
