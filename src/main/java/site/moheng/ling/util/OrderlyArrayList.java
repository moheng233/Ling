package site.moheng.ling.util;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * 保证有序的排序列表
 * 使用二分法进行查找与插入
 */
public class OrderlyArrayList<E extends Comparator<E>> extends ArrayList<E> {
    /**
     * 向数组中有序插入一个数
     */
    @Override
    public boolean add(E arg0) {
        int find = size() / 2;
        while(find != 0) {
            
        } 
        return super.add(arg0);
    }
}
