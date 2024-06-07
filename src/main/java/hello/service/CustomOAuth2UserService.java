package hello.service;

import hello.dto.oauth2.GoogleResponse;
import hello.dto.oauth2.KakaoResponse;
import hello.dto.oauth2.NaverResponse;
import hello.dto.oauth2.OAuth2Response;
import hello.dto.user.CustomOAuth2User;
import hello.security.exception.AdditionalInfoRequiredException;
import hello.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("kakao")){
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        // 처음 로그인한 회원 -> 회원가입 폼으로 리다이렉트
        if (userRepository.findByUsername(username) == null) {
            throw new AdditionalInfoRequiredException(oAuth2Response);
        }

        String role = oAuth2User.getAuthorities().iterator().next().getAuthority();
        return new CustomOAuth2User(oAuth2Response, role);
    }
}