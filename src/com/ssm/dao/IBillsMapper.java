package com.ssm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ssm.entity.Bcount;
import com.ssm.entity.Bhistory;
import com.ssm.entity.Bills;
import com.ssm.entity.HisStock;

@Component("billsModel")
public interface IBillsMapper {

	public List<Bills> SelectBillAll();
	public List<Bcount> SelectBillNum();
	
	public List<Bhistory> SelectHistory();//在用户列表查看历史交易记录，主要计算盈亏率
	
	//在股票交易数据中查看历史交易记录
	public List<HisStock> SelectHisStock();
}
