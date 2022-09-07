package com.ce.spring2.demo.model.service;

import java.util.List;

import com.ce.spring2.demo.model.dto.Dev;

public interface DemoService {

	int insertDev(Dev dev);

	List<Dev> selectDev();

	Dev selectOneDev(int no);

	int updateDev(Dev dev);

	int deleteDev(int no);

	int updatePartialDev(Dev dev);

}
