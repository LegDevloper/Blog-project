package site.metacoding.MyBlog.web.dto.request.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
	private String username;
	private String password;
}
