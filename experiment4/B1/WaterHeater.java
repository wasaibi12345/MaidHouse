import java.time.*;

// 热水器类
public class WaterHeater {

    // 状态定义
    private enum State {
        SLEEP,     // 休眠（断电）
        STANDBY,   // 待机（通电但不加热）
        HEAT,      // 烧水（加热）
        REPAIR,    // 维修中
        END        // 报废
    }

    private State currentState;

    // 事件与条件定义
    private boolean isMorning;        // 当前时间是否处于 7AM ~ 11PM
    private int waterTemp;            // 当前水温
    private boolean hasWater;         // 是否有水
    private boolean failure;          // 烧坏产生概率
    private boolean repairable;       // 是否可维修

    // 基于当前时间更新 isMorning 变量的值
    private void updateTime() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        isMorning = (hour >= 7 && hour < 23);
    }

    // 随机生成是否故障，故障概率 0.1
    private void randFailure() {
        failure = Math.random() < 0.1;
    }

    // 随机生成是否可维修，不可维修概率 0.5
    private void randRepairable() {
        repairable = Math.random() < 0.5;
    }

    // 给水加热，一轮加热 30 度
    private void heatWater() {
        waterTemp += 30;
        if (waterTemp > 100) waterTemp = 100; // 限制最高温度 100 度
    }

    // 状态机转移逻辑：根据当前状态，进行一次状态转移
    private void runStep(){

        updateTime(); // 读取并更新事件相关的参数

        switch (currentState) {

            case SLEEP:
                if (isMorning) {  // 在 SLEEP 状态时，若时间为 7AM ~ 11PM，则转为 STANDBY 状态
                    currentState = State.STANDBY;
                }
                break;

            case STANDBY:
                if (!isMorning) {  // 在 STANDBY 状态时，若时间为 11PM ~ 7AM，则转为 SLEEP 状态
                    currentState = State.SLEEP;
                } else if (waterTemp < 20 && hasWater) {  // 有水且水温小于 20，则转入加热状态
                    currentState = State.HEAT;
                }
                break;

            case HEAT:
                randFailure();  // 随机更新 failure 的值
                if (failure) {  // 在 HEAT 状态时，先进行故障检测，若发生故障，则进入 REPAIR 状态
                    currentState = State.REPAIR;
                } else if (waterTemp >= 100) {  // 无故障，且水温大于等于 100，则停止加热
                    currentState = State.STANDBY;
                } else {  // 否则进行加热
                    heatWater();
                }
                break;

            case REPAIR:
                randRepairable();  // 随机更新 repairable 的值
                if (repairable) {  // 在 REPAIR 状态时，检查是否可维修，可以则重回循环，不能则报废
                    currentState = State.STANDBY;
                } else {
                    currentState = State.END;
                }
                break;

            case END:
                // 报废状态，无法继续处理
                break;
        }

        // 结束后，在一定条件下将水温减少 20 度，模拟温度自然衰减
        if(waterTemp > 20 && hasWater){
            waterTemp -= 20;
        }
    }

    // ---------------- 对外暴露的方法 ----------------

    // 构造器，初始化各种环境参数
    public WaterHeater(int waterTemp, boolean hasWater) {
        this.currentState = State.SLEEP; // 初始状态为休眠模式
        this.waterTemp = waterTemp;
        this.hasWater = hasWater;
    }

    // 主运行方法
    public void start() {
        System.out.println("启动热水器...");
        int i = 0;
        while (true) {
            i++;
            System.out.println(
                    "周期 " + i +
                    " | 当前状态：" + currentState +
                    " | 水温=" + waterTemp +
                    " | 时间段=" + (isMorning ? "白天" : "夜间")
            );
            runStep();

            // 若模拟结束后为 END 状态，则结束模拟
            if(currentState == State.END){
                break;
            }

            // 设置运行间隔，每隔 1s 模拟一次状态转移
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}