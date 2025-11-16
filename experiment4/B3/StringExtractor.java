import java.io.*;

public class StringExtractor {

    // 状态定义
    private enum State {
        OUTSIDE,        // 字符串外部
        INSIDE,         // 字符串内部
        ESCAPE          // 转义模式
    }

    // 当前状态
    private State currentState = State.OUTSIDE;

    // 输入与输出缓冲区
    private StringBuilder currentString = new StringBuilder();
    private BufferedReader reader;

    // 构造器：接收要提取字符串的源文件的路径
    public StringExtractor(String filePath) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
    }

    // 状态机执行一步
    private void runStep(int ch) {

        char c = (char) ch;

        switch (currentState) {

            case OUTSIDE:
                if (c == '"') { // 若为引号 "，则进入字符串内部
                    currentString.setLength(0);
                    currentState = State.INSIDE;
                }
                break;

            case INSIDE:
                if (c == '\\') { // 遇到转义符，进入转义状态
                    currentState = State.ESCAPE;
                } else if (c == '"') {
                    // 遇到双引号，字符串结束
                    System.out.println(currentString.toString()); // 将字符串内容打印到终端（示例程序，因此就不做输出到文件了）
                    currentString.setLength(0);
                    currentState = State.OUTSIDE;
                } else {
                    // 普通字符，加入字符串内容
                    currentString.append(c);
                }
                break;

            case ESCAPE:
                // 无论是什么，都直接加入字符串，然后回到字符串内部
                currentString.append(c);
                currentState = State.INSIDE;
                break;
        }
    }

    // 主运行方法
    public void start() throws IOException {
        int ch;
        while ((ch = reader.read()) != -1) {  // 若为 EOF，则结束运行
            runStep(ch);
        }
    }
}