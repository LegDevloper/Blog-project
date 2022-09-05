package site.metacoding.MyBlog.domain.boards;

import java.util.List;

import site.metacoding.MyBlog.web.dto.response.boards.DetailDto;
import site.metacoding.MyBlog.web.dto.response.boards.MainDto;


public interface BoardsDao {
	public void insert(Boards boards); // DTO 생각해보기
	public DetailDto findById(Integer id);
	public List<MainDto> findAll(int startNum);
	public void update(Boards boards); // DTO 생각해보기
	public void delete(Integer id);
}
