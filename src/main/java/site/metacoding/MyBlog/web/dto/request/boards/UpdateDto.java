package site.metacoding.MyBlog.web.dto.request.boards;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.MyBlog.domain.boards.Boards;

@Getter
@Setter
public class UpdateDto {
	private String title;
	private String content;
	//update만 영속화 사용
}
