package bookstroe.web;

public class CriteriaBook {
	private int minPrice = 0;
	private int maxPrice = Integer.MAX_VALUE;
	private int pageNo;
	
	public CriteriaBook(){
		
	}
	public CriteriaBook(int minPrice,int maxPrice, int pageNo){
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.pageNo = pageNo;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	@Override
	public String toString() {
		return "CriteriaBook [minPrice=" + minPrice + ", maxPrice=" + maxPrice + ", pageNo=" + pageNo + "]";
	}
	
	

}
