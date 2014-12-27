package com.linyongan.sortlistview;

import java.util.Comparator;

/**
 * PinyinComparator�ӿ�������ListView�е����ݸ���A-Z��������ǰ������if�ж���Ҫ�ǽ������Ժ��ֿ�ͷ�����ݷ��ں���
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<SortModel> {

	public int compare(SortModel o1, SortModel o2) {
		//������Ҫ��������ListView��������ݸ���ABCDEFG...������ 
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
