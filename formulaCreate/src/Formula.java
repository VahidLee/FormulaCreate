import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ForkJoinPool;

public class Formula {
    private static Random random = new Random();
    private StringBuilder sb = new StringBuilder(64);

    private int max;//最大数值
    private int numOfDigit = 0;
    private int numOfOperator = 0;//运算符个数
    private Element[] Digit;
    private char[] Operator;
    private String result;

    public Formula() {
    }

    public Formula(int max) {
        this.max = max;
        numOfDigit = random.nextInt(3) + 2;
        numOfOperator = numOfDigit - 1;

        makeElement();
        makeOperator();
        getFormula();

        calResult();
    }

    public void calResult() {
        String str = sb.toString();
        str = str.substring(0, str.length() - 2);//删除掉子字符串 " ="
        this.result = UtilClass.getResult(UtilClass.trunToSuffixString(str));

        //结果修饰后，再输出
        result=UtilClass.standard(result);
    }

    public void getFormula() {
        int OperatorIndex = 0;

        for (int i = 0; i < numOfDigit; i++) {
            if (OperatorIndex < numOfOperator && Operator[OperatorIndex] == '(') {//左括号
                sb.append("( ");
                OperatorIndex++;
            }
            if (Digit[i].isDivision()) {//操作数
                sb.append(Digit[i].getUp() + "/" + Digit[i].getDowm());
            } else {
                sb.append(Digit[i].getUp());
            }
            if (OperatorIndex < numOfOperator && Operator[OperatorIndex] == ')') {//右括号
                sb.append(" )");
                OperatorIndex++;
            }
            if (OperatorIndex < numOfOperator) {
                sb.append(" " + Operator[OperatorIndex] + " ");
                OperatorIndex++;
            }
        }
        sb.append(" =");
    }
//        //加上末尾的数值
//        if(true==Digit[Digit.length-1].isDivision()){
//            sb.append(Digit[Digit.length-1].getUp()+"/"+Digit[Digit.length-1].getDowm());
//        }else{
//            sb.append(Digit[Digit.length-1].getUp());
//        }
//        sb.append(" =");
//    }

    public void makeElement() {
        Digit = new Element[numOfDigit];
        for (int i = 0; i < numOfDigit; i++) {
            Digit[i] = new Element(random.nextDouble() < 0.3, max);// 3/10是分数
        }
    }

    public void makeOperator() {
        if (numOfOperator >= 2 && random.nextBoolean()) {//加对括号
            numOfOperator += 2;

            Operator = new char[numOfOperator];
            int leftIndex = random.nextInt(Operator.length - 2);
            Operator[leftIndex] = '(';
            int rightIndex = random.nextInt(Operator.length - leftIndex - 2) + leftIndex + 2;
            Operator[rightIndex] = ')';
        } else {//不加括号
            Operator = new char[numOfOperator];
        }
        //加减乘除
        for (int i = 0; i < numOfOperator; i++) {
            if (Operator[i] == '(' || Operator[i] == ')') {
                continue;
            }

            //确定加减乘除
            double whichChar = random.nextDouble();
            if (whichChar < 0.2) {
                Operator[i] = '+';
            }
            if (0.2 <= whichChar && whichChar < 0.4) {
                Operator[i] = '-';
            }
            if (0.4 <= whichChar && whichChar < 0.7) {
                Operator[i] = '×';
            }
            if (0.7 <= whichChar) {
                Operator[i] = '÷';
            }

        }

    }


    public String getResult() {return result;}

    public StringBuilder getSb() {
        return sb;
    }
}
