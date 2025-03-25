package org.nwea.oauthproxy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;

public class NoContextPathRedirectStrategy implements RedirectStrategy
{
    private static final Logger logger = LoggerFactory.getLogger(NoContextPathRedirectStrategy.class);

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException
    {
        String redirectUrl = calculateRedirectUrl(request.getContextPath(), url);
        redirectUrl = response.encodeRedirectURL(redirectUrl);

        if (logger.isDebugEnabled())
        {
            logger.debug("Redirecting to '{}'", redirectUrl);
        }

        response.sendRedirect(redirectUrl);
    }

    private String calculateRedirectUrl(String contextPath, String url)
    {
        if (!UrlUtils.isAbsoluteUrl(url))
        {
            return url;
        }
        else
        {
            int contextPathIndex = url.indexOf(contextPath);
            int contextPathLength = contextPath.length();

            // check to see if there is a context path in this url
            if (contextPathIndex >= 0)
            {
                // strip out the context path
                url = url.substring(0, contextPathIndex) + url.substring(contextPathIndex + contextPathLength);
            }

            // check to see if there is a leading /
            if (url.length() > 1 && url.charAt(0) == '/')
            {
                // remove the leading slash
                url = url.substring(1);
            }

            return url;
        }
    }
}