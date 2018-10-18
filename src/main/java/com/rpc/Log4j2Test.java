package com.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:wjy
 * @Date: 2018/8/16.
 */
@RequestMapping(value = "/log")
@RestController
public class Log4j2Test {

	public static final Logger logger = LoggerFactory.getLogger(Log4j2Test.class);


	@RequestMapping(value = "/test_log.json")
	public void testLog(){
		logger.trace("trace");
		logger.debug("debug");
		logger.warn("warn");
		logger.info("info"+"11");
		logger.error("error+111");
	}
}
