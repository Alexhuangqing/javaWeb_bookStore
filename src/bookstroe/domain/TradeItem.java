package bookstroe.domain;

public class TradeItem {
	private Integer tradeItemId;
	private Integer bookId;
	private Book book;
	private Integer quantity;
	private Integer tradeId;
	
	public TradeItem() {
		super();
	}
	public TradeItem(Integer bookId, Integer quantity, Integer tradeId) {
		super();
		this.bookId = bookId;
		this.quantity = quantity;
		this.tradeId = tradeId;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Integer getTradeItemId() {
		return tradeItemId;
	}
	public void setTradeItemId(Integer tradeItemId) {
		this.tradeItemId = tradeItemId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getTradeId() {
		return tradeId;
	}
	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}
	
	//显示 我想要的信息 检验从数据表获得的数据
	@Override
	public String toString() {
		return "TradeItem [tradeItemId=" + tradeItemId + ", bookId=" + bookId + ", quantity=" + quantity + ", tradeId="
				+ tradeId + "]";
	}
	
	
	

	
	
	
	
	

}
