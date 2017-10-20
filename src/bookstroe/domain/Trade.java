package bookstroe.domain;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class Trade {
	private Integer tradeId;
	private Date tradeTime;
	private Integer userId;
	//һ�ν��׹������TradeItem��Ϣ
	private Set<TradeItem> items = new LinkedHashSet<TradeItem>();
	
	//����ʹ�ÿղι�����
	public Trade() {
		super();
	}

	//���Բ���һ�����׼�¼ʱʹ��
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
	
	//���鿴trade������Ϣ��ʱ�� ����ϣ��������Щ����ֵ
	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", tradeTime=" + tradeTime + ", userId=" + userId + "]";
	}
	
	


}
