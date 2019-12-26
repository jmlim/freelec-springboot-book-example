package io.jmlim.freelec.springboot.config.auth.dto;

import io.jmlim.freelec.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * User 클래스를 세션에 직접 저장하지 않는 이유
 * --> User 클래스는 현재 Entity 임.
 * --> 엔티티 클래스는 언제 Entity와의 관계가 형성될지 모름.
 * --> 만약 직렬화 대상에 자식들까지 포함될 경우 성능 이슈, 부수 효과가 발생할 확률이 높음.
 * --> 그래서 아래와 같이 직렬화 기능을 가진 세션 Dto 를 하나 추가로 만드는 것이 이후 운영 및 유지보수 때 많은 도움이 됨.
 */
@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
