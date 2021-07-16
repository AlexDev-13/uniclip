package com.company.uniclip.backend.controller;

import com.company.uniclip.backend.enums.SessionKey;
import com.company.uniclip.backend.exception.AccessDeniedException;
import com.company.uniclip.backend.service.OauthTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.stream.Stream;

@Controller
public class GoogleAuthRedirectController {

    private final OauthTokenService oauthTokenService;

    public GoogleAuthRedirectController(OauthTokenService oauthTokenService) {
        this.oauthTokenService = oauthTokenService;
    }

    @RequestMapping("/oauth2/callback/google")
    public String callbackUrl(
            HttpServletRequest request,
            HttpSession httpSession) {
        String code = request.getParameter("code");
        String accessDenied = request.getParameter("access_denied") == null
                ? "" : request.getParameter("access_denied");
        if (!accessDenied.isBlank()) throw new AccessDeniedException("Authorization from google failed");
        String error = request.getParameter("error") == null
                ? "" : request.getParameter("error");
        if (!error.isBlank()) throw new AccessDeniedException("Authorization from google failed");
        String[] scopes = request.getParameter("scope").split(" ");
        if (code.isBlank()) throw new AccessDeniedException("Authorization from google failed");
        String permission =
                Stream.of(scopes)
                        .filter(s -> s.contains("drive"))
                        .findFirst()
                        .orElseThrow(() -> new AccessDeniedException("You must have to allow drive data to be accessed."));
        httpSession
                .setAttribute(SessionKey.GOOGLE_OAUTH_TOKEN.toString(),
                        oauthTokenService.fetchToken(code, permission)
                );
        return "redirect:/";
    }
}
