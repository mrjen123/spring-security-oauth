package org.springframework.security.oauth2.consumer.rememberme;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collections;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author Dave Syer
 */
public class TestHttpSessionOAuth2RememberMeServices {
	
	private HttpSessionOAuth2RememberMeServices services = new HttpSessionOAuth2RememberMeServices();
	
	private MockHttpServletRequest request = new MockHttpServletRequest();
	
	private MockHttpServletResponse response = new MockHttpServletResponse();
	
	@Test
	public void testSaveTokensCreatesSessionByDefault() throws Exception {
		assertNull(request.getSession(false));
		services.rememberTokens(Collections.<String,OAuth2AccessToken>emptyMap(), request, response);
		assertNotNull(request.getSession(false));
	}
}
