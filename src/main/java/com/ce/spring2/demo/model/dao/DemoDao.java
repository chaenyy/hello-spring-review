package com.ce.spring2.demo.model.dao;

import java.util.List;

import com.ce.spring2.demo.model.dto.Dev;

public interface DemoDao {

	int insertDev(Dev dev);

	List<Dev> selectDev();

	Dev selectOneDev(int no);

	int updateDev(Dev dev);

	int deleteDev(int no);

}
