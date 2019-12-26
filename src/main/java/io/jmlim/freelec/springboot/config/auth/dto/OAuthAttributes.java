package io.jmlim.freelec.springboot.config.auth.dto;

import io.jmlim.freelec.springboot.domain.user.Role;
import io.jmlim.freelec.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }


    //
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("naver")) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        log.info(" ---> google attributes : {}", attributes);

        return OAuthAttributes.builder()
                .name(Objects.toString(attributes.get("name")))
                .email(Objects.toString(attributes.get("email")))
                .picture(Objects.toString(attributes.get("picture")))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        log.info(" ---> naver response : {}, attributes: {}", response, attributes);
        return OAuthAttributes.builder()
                .name(Objects.toString(response.get("name")))
                .email(Objects.toString(response.get("email")))
                .picture(Objects.toString(response.get("profileImage")))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
