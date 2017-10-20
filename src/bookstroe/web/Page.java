package bookstroe.web;

import java.util.List;

public class Page<T> {
	// 显示当前页码
	private int pageNo;
	// 本页面需要显示的List
	private List<T> list;
	// 每页显示的条数  
	private int pageSize = 3;
	// 总记录数:可以用来计算页的总数
	private long totalItemNumber;

	// 构造器，用来初始化****pageNo
	public Page(int pageNo) {
		this.pageNo = pageNo;
	}

	// 返回当前页面的页码，校验页码
	public int getPageNo() {
		if (pageNo < 1) {
			return 1;
		}
		if (pageNo > getTotalPageNumber()) {
			return getTotalPageNumber();
		}
		return pageNo;
	}

	public List<T> getList() {
		return list;
	}

	// 设置当前需要显示的****list对象
	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	// 获取总页码数
	public int getTotalPageNumber() {
		int number = (int) (totalItemNumber / pageSize);
		if (totalItemNumber % pageSize != 0) {
			number++;
		}
		return number;

	}

	// 设置总记录数 ****totalItemNumber
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	// 上下页相关
	public boolean isHasNext() {
		if (pageNo == getTotalPageNumber()) {
			return false;
		}
		return true;
	}

	public boolean isHasPrev() {
		if (pageNo == 1) {
			return false;
		}
		return true;
	}

	public int getPrePage() {
		return pageNo - 1;
	}

	public int getNextPage() {
		return pageNo + 1;
	}

}
