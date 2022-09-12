package site.metacoding.MyBlog.domain.users;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.MyBlog.web.dto.request.users.UpdateDto;

@Getter
@Setter

public class Users {
	private Integer id;
	private String username;
	private String password;
	private String email;
	private Timestamp createdAt;
	
	public void updateUsers(UpdateDto updateDto) {
		this.email=updateDto.getEmail();
		this.password=updateDto.getPassword();
	}
	
}
