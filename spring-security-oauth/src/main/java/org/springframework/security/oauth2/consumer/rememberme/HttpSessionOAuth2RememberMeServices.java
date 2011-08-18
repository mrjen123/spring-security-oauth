package org.springframework.security.oauth2.consumer.rememberme;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Default implementation of the OAuth2 rememberme services. Just stores everything in the session.
 * 
 * @author Ryan Heaton
 * @author Dave Syer
 */
public class HttpSessionOAuth2RememberMeServices implements OAuth2RememberMeServices {

	public static final String REMEMBERED_TOKENS_KEY = HttpSessionOAuth2RememberMeServices.class.getName()
			+ "#REMEMBERED_TOKENS";
	public static final String STATE_PREFIX = HttpSessionOAuth2RememberMeServices.class.getName() + "#STATE#";
	public static final String GLOBAL_STATE_KEY = HttpSessionOAuth2RememberMeServices.class.getName() + "GLOBAL";

    private boolean allowSessionCreation = true;

    /**
     * If set to true (the default), a session will be created (if required) to store the token if it is
     * determined that its contents are different from the default empty context value.
     * <p>
     * Note that setting this flag to false does not prevent this class from storing the token. If your
     * application (or another filter) creates a session, then the token will still be stored for an
     * authenticated user.
     *
     * @param allowSessionCreation
     */
    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }

    public Map<String, OAuth2AccessToken> loadRememberedTokens(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		Map<String, OAuth2AccessToken> rememberedTokens = null;
		if (session != null) {
			rememberedTokens = (Map<String, OAuth2AccessToken>) session.getAttribute(REMEMBERED_TOKENS_KEY);
		}
		return rememberedTokens;
	}

	public void rememberTokens(Map<String, OAuth2AccessToken> tokens, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(allowSessionCreation);
		if (session != null) {
			session.setAttribute(REMEMBERED_TOKENS_KEY, tokens);
		}
	}

	public Object loadPreservedState(String stateKey, HttpServletRequest request, HttpServletResponse response) {
		Object state = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			if (stateKey == null) {
				stateKey = GLOBAL_STATE_KEY;
			}
			state = session.getAttribute(STATE_PREFIX + stateKey);
		}
		return state;
	}

	public void preserveState(String id, Object state, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(allowSessionCreation);
		if (session != null) {
			if (id == null) {
				id = GLOBAL_STATE_KEY;
			}

			session.setAttribute(STATE_PREFIX + id, state);
		}
	}
}
