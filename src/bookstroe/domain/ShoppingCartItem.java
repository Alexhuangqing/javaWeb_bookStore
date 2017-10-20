package bookstroe.domain;

public class ShoppingCartItem {
	private Book book;
	private int quantity;
	
	public ShoppingCartItem(Book book){
		this.book = book;
		quantity = 1;
	}
	public Book getBook(){
		return book;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getQuantityMoney(){
		return quantity*book.getPrice();
	}
	public void increment(){
		quantity++;
	}
	//一定要重写toString方法，后面遍历集合才能获得属性
	@Override
	public String toString() {
		return "ShoppingCartItem [book=" + book + ", quantity=" + quantity + "]";
	}
	
	

}
