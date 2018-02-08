package cn.jarlen.richcommon2.data;

/**
 * Created by jarlen on 2018/2/8.
 */

public class TestItem {

    private String itemName;

    private Class<?> testClass;

    public TestItem(String itemName, Class<?> testClass) {
        this.itemName = itemName;
        this.testClass = testClass;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Class<?> getTestClass() {
        return testClass;
    }

    public void setTestClass(Class<?> testClass) {
        this.testClass = testClass;
    }
}
