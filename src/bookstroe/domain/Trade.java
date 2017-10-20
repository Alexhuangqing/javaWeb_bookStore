package bookstroe.domain;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class Trade {
	private Integer tradeId;
	private Date tradeTime;
	private Integer userId;
	//一次交易关联多个TradeItem信息
	private Set<TradeItem> items = new LinkedHashSet<TradeItem>();
	
	//反射使用空参构造器
	public Trade() {
		super();
	}

	//测试插入一条交易记录时使用
	public Trade(Date tradeTime, Integer userId) {
		super();
		this.tradeTime = tradeTime;
		this.userId = userId;
	}
	
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Set<TradeItem> getItems() {
		return items;
	}
	public void setItems(Set<TradeItem> items) {
		this.items = items;
	}
	
	//当查看trade对象信息的时候 ，我希望看到这些属性值
	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", tradeTime=" + tradeTime + ", userId=" + userId + "]";
	}
	
	


}
