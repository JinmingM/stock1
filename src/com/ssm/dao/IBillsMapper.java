package com.ssm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ssm.entity.Bcount;
import com.ssm.entity.Bhistory;
import com.ssm.entity.Bill;
import com.ssm.entity.Bills;
import com.ssm.entity.HisStock;

/**
 * @author WILLIAM
 *
 */

@Component("billsModel")
public interface IBillsMapper {
	
	public List<Bill> SelectAllBill();
	public Bill SelectBillByBill(Bill bill);
	public List<Bill> SearchBillByUid(int uid);
	public int BuyStocks(Bill bill);
	public int UpdateStocks(Bill bill);
	public int SellStocks(Bill bill);
    
	
	public List<Bcount> SelectBillNum();
	public List<Bhistory> SelectHistory();//在用户列表查看历史交易记录，主要计算盈亏率
	public List<HisStock> SelectHisStock();//在股票交易数据中查看历史交易记录
	public List<Bills> SelectBillAll();
	
	
}
