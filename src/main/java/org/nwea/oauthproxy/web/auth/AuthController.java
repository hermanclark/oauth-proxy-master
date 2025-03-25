package org.nwea.oauthproxy.web.auth;

import org.nwea.oauthproxy.domain.auth.Auth;
import org.nwea.oauthproxy.web.exceptions.NoResultsException;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@RepositoryRestController
@RequestMapping("/auth")
public class AuthController {
	
	/**
	 * "GET" Method - returns the Mapping based on "targetId"
	 * @param request 
	 * @return @ResponseBody Mapping
	 */
	@SuppressWarnings("unused")
	@RequestMapping(method = RequestMethod.GET, value ="/**", produces = {"application/json"})
	public @ResponseBody Auth getAuthType() {
		Auth authCreds = null;
		
		if (authCreds == null)
			throw new NoResultsException();

		return authCreds;
	}

}
