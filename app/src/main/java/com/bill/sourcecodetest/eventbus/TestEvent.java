package com.bill.sourcecodetest.eventbus;

/**
 * author : Bill
 * date : 2021/7/21
 * description :
 */
public class TestEvent extends TestEventParent implements TestEventInterface {

    public int id = 0;

    public TestEvent() {
        id = 1;
    }

}
