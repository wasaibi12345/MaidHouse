import java.util.Calendar;

// ==========================
// 良品马类 Thoroughbred
// ==========================
public class Thoroughbred {
    // 属性
    private String mother;
    private String father;
    private int birthyear;

    // 方法
    public int getCurrentAge() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int currentYear = calendar.get(java.util.Calendar.YEAR);
        return currentYear - birthyear;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }
}

