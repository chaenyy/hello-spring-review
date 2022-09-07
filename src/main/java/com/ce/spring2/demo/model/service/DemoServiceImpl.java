package com.ce.spring2.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ce.spring2.demo.model.dao.DemoDao;
import com.ce.spring2.demo.model.dto.Dev;

@Service
public class DemoServiceImpl implements DemoService {
	@Autowired
	private DemoDao demoDao;
	
	@Override
	public int insertDev(Dev dev) {
		return demoDao.insertDev(dev);
	}
	
	@Override
	public List<Dev> selectDev() {
		return demoDao.selectDev();
	}
	
	@Override
	public Dev selectOneDev(int no) {
		return demoDao.selectOneDev(no);
	}
	
	@Override
	public int updateDev(Dev dev) {
		return demoDao.updateDev(dev);
	}
	
	@Override
	public int deleteDev(int no) {
		return demoDao.deleteDev(no);
	}
	
	@Override
	public int updatePartialDev(Dev dev) {
		return demoDao.updatePartialDev(dev);
	}
}
