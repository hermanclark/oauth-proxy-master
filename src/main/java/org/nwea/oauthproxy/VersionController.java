package org.nwea.oauthproxy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")

public class VersionController {
	/**
	 * "GET" Method - returns the version of release 
	 * @param request 
	 * @return @ResponseBody String
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody String getVersion() {
		String version = "version 1.0";
		return version;
	}
}
