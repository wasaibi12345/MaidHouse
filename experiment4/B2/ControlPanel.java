import java.io.IOException;
import java.time.*;
import java.util.Scanner;

public class ControlPanel {

    // 密码
    private String password = "0721";

    // 状态定义
    private enum State {
        STANDBY,        // 待机
        READ_INPUT,     // 读取输入
        VERIFY,         // 验证密码
        LOCKED,         // 锁定
        FUNCTION_SELECT // 功能选择
    }

    // 定义并初始化属性
    private Scanner scanner = new Scanner(System.in);
    private StringBuilder inputBuffer = new StringBuilder();
    private State currentState = State.STANDBY;
    private int errorCount = 0;


    // 状态机转移逻辑：根据当前状态，进行一次状态转移
    private void runStep() {

        switch (currentState) {

            case STANDBY:
                inputBuffer.setLength(0);  // 清空缓冲区
                System.out.println("按任意键唤醒 ...");
                try {
                    System.in.read();                // 等待用户按任意键唤醒
                } catch (IOException e) {}
                currentState = State.READ_INPUT;     // 唤醒后转移到读取输入状态
                break;

            case READ_INPUT:
                String in = scanner.nextLine();
                inputBuffer.append(in.charAt(0));  // 读取用户输入的第一个字符

                // 若输入了 4 次，则转换到验证状态
                if (inputBuffer.length() == 4) {
                    currentState = State.VERIFY;
                }
                break;

            case VERIFY:
                boolean current = inputBuffer.toString().equals(password);
                inputBuffer.setLength(0); // 验证后清空缓区

                if (current) { // 若正确，则清空错误计数并切换到功能选择
                    errorCount = 0;
                    currentState = State.FUNCTION_SELECT;
                } else {
                    // 否则增加错误计数
                    errorCount++;
                    // 若错误次数 >= 3，则锁定
                    if(errorCount >= 3){
                        System.out.println("连续三次错误，系统锁定 120 秒。");
                        currentState = State.LOCKED;
                    } else {
                        // 否则切换到 输入状态
                        currentState = State.READ_INPUT;
                    }
                }
                break;

            case LOCKED:
                try {
                        Thread.sleep(120000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // 清空缓冲与错误计数，并回到 READ_INPUT
                inputBuffer.setLength(0);
                errorCount = 0;
                currentState = State.READ_INPUT;
                break;

            case FUNCTION_SELECT:
                System.out.println("功能菜单");
                break;
            
        }
    }

    // 主运行方法
    public void start() {

        while (true) {
            if(currentState == FUNCTION_SELECT){
                runStep();
                break;
            }
            runStep();
        }
    }
}