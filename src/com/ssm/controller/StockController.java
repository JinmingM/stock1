package com.ssm.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.dao.IBillsMapper;
import com.ssm.dao.IStocksMapper;
import com.ssm.dao.IUsersMapper;
import com.ssm.entity.Bcount;
import com.ssm.entity.Bhistory;
import com.ssm.entity.Bills;
import com.ssm.entity.HisStock;
import com.ssm.entity.Stock;
import com.ssm.entity.User;

@Controller
@RequestMapping("/stockt")
public class StockController {
	IStocksMapper stocksMapper=null;
	
	public IStocksMapper getStockMapper() {
		return stocksMapper;
	}
	@Autowired
	public void setStockMapper(@Qualifier("stocksModel")IStocksMapper stockMapper) {
		this.stocksMapper = stockMapper;
	}
	
	
    IBillsMapper billsMapper=null;
	
	public IBillsMapper getBillMapper() {
		return billsMapper;
	}
	@Autowired
	public void setBillMapper(@Qualifier("billsModel")IBillsMapper billsMapper) {
		this.billsMapper = billsMapper;
	}
	//查找所有股票
	@RequestMapping("/searchStock")
	@ResponseBody
	public List<Map<String, Object>> FindStocksAll(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		List<Stock> listStock=this.stocksMapper.SelectStockAll();
		Map<String, Object> map=null;
		
		System.out.println("search all stock111");
		
		for (Stock stock : listStock) {
			map=new HashMap<String, Object>();
			map.put("sid", stock.getSid());
			map.put("sname",stock.getSname());
			
			res.add(map);
		}
		return res;
	}
	//查找全部股票订单
		@RequestMapping("/searchAllbill")
		@ResponseBody
		public List<Map<String, Object>> FindAllBills(HttpServletRequest request,
				HttpServletResponse response) throws UnsupportedEncodingException{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
			System.out.println("search stock history");
			List<Bills> listStock=this.billsMapper.SelectBillAll();
			Map<String, Object> map=null;
			
			//System.out.println("search all stock");
			
			for (Bills stock : listStock) {
				map=new HashMap<String, Object>();
				map.put("uid", stock.getUid());
				map.put("sid", stock.getSid());
				map.put("cost", stock.getCost());
				map.put("date", stock.getDate());
				res.add(map);
			}
			return res;
		}
	//查找股票历史交易记录
	@RequestMapping("/searchHisstock")
	@ResponseBody
	public List<Map<String, Object>> FindHisStocks(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
		System.out.println("search stock history");
		List<HisStock> listStock=this.billsMapper.SelectHisStock();
		Map<String, Object> map=null;
		
		//System.out.println("search all stock");
		
		for (HisStock stock : listStock) {
			map=new HashMap<String, Object>();
			map.put("uid", stock.getUid());
			map.put("sid", stock.getSid());
			map.put("state", stock.getState());
			map.put("num", stock.getNum());
			map.put("bdate", stock.getBdate());
			map.put("sdate", stock.getSdate());
			res.add(map);
		}
		return res;
	}
	//查找本周股票交易数
		@RequestMapping("/searchWeekstock")
		@ResponseBody
		public List<Map<String, Object>> FindWeekBills(HttpServletRequest request,
				HttpServletResponse response) throws UnsupportedEncodingException{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			List<Map<String, Object>> res=new ArrayList<Map<String,Object>>();
			System.out.println("search stock history");
			List<HisStock> listStock=this.billsMapper.SelectHisStock();
			List<Bills> listStock1=this.billsMapper.SelectBillAll();
			Map<String, Object> map=null;
			
			//System.out.println("search all stock");
			
			for (HisStock stock : listStock) {
				map=new HashMap<String, Object>();
				
			}
			return res;
		}
}
