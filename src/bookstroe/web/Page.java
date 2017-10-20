package bookstroe.web;

import java.util.List;

public class Page<T> {
	// ��ʾ��ǰҳ��
	private int pageNo;
	// ��ҳ����Ҫ��ʾ��List
	private List<T> list;
	// ÿҳ��ʾ������  
	private int pageSize = 3;
	// �ܼ�¼��:������������ҳ������
	private long totalItemNumber;

	// ��������������ʼ��****pageNo
	public Page(int pageNo) {
		this.pageNo = pageNo;
	}

	// ���ص�ǰҳ���ҳ�룬У��ҳ��
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

	// ���õ�ǰ��Ҫ��ʾ��****list����
	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	// ��ȡ��ҳ����
	public int getTotalPageNumber() {
		int number = (int) (totalItemNumber / pageSize);
		if (totalItemNumber % pageSize != 0) {
			number++;
		}
		return number;

	}

	// �����ܼ�¼�� ****totalItemNumber
	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	// ����ҳ���
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
