package site.metacoding.MyBlog.web.dto.request.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDto {
	private String email;
	private String password;
}
