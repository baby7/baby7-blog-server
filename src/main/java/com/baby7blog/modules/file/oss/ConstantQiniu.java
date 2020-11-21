package com.baby7blog.modules.file.oss;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ConstantQiniu {

	private String accessKey = "QHqjfdZsbHZaAvogeFGM8cTrurShs7UmN5LIu-iZ";

	private String secretKey = "yDdNmZWUEzs2zsk46O6O5-WbZ0x1lF2r3neJ7xbO";

	private String bucket = "baby7-media";

	private String path = "https://media.baby7blog.com";
}