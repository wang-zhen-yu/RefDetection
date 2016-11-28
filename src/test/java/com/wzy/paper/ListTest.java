package com.wzy.paper;

import com.wzy.paper.search.util.ReferenceUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author wzy
 * @Date 2016/11/26 10:37
 */
public class ListTest {
    public void delete(){


        List<String> list1 = new ArrayList<String>();
        list1.add("");
        list1.add("2");
        list1.add("");
        list1.add(" ");



        for(String i:list1){
            if(i.equals("")){
                list1.remove(i);
            }

        }

        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        this.iteratorDelete(list.iterator(), 2);
        this.outputList(list);
    }

    private void iteratorDelete(Iterator<Integer> it, int deleteObject){
        while(it.hasNext()){
            int i = it.next();
            if(i==deleteObject){
                it.remove();
            }
        }
    }

    private void outputList(List<Integer> list){
        for (Integer i : list) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {

        ListTest t = new ListTest();
        t.delete();

    }
}
