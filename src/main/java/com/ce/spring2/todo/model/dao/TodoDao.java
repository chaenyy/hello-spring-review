package com.ce.spring2.todo.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ce.spring2.todo.model.dto.Todo;

@Mapper
public interface TodoDao {
	
	@Select("select * from (select * from todo where completed_at is null order by no) "
			+ "union all "
			+ "select * from (select * from todo where completed_at is not null order by completed_at desc)")
	List<Todo> selectAllTodo();

	@Insert("insert into todo values (seq_todo_no.nextval, #{todo}, default, null)")
	int insertTodo(Todo todo);

	int updateTodo(Map<String, Object> param);	

}
