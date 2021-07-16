package com.bill.sourcecodetest.factory;

/**
 * author : Bill
 * date : 2021/7/15
 * description :
 */
public interface MyAdapter {

    String adapt(int num);

    abstract class MyFactory {
        public abstract MyAdapter create();
    }

}
