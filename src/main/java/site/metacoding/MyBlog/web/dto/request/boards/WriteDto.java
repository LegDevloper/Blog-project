package site.metacoding.MyBlog.web.dto.request.boards;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.MyBlog.domain.boards.Boards;

@Getter
@Setter
public class WriteDto {
	private String title;
	private String content;
	
	public Boards toEntity(Integer usersId) {
		Boards boards = new Boards(this.title,this.content,usersId);
		return boards;
	}
}
