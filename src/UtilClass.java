import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UtilClass {
    public static String readTxt(String filePath) {
        String content = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                content+=line;
            }
            br.close();

        } catch (UnsupportedEncodingException e) {
            System.out.println("文件编码异常");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("文件输入异常");
            throw new RuntimeException(e);
        }

        return content;
    }

    public static void deleteFile(String fileName){
        //删除原有文件
            File file =new File(fileName);
            file.delete();
    }

    public static void wirteTxt(String fileName,String content){

        try {


            FileWriter writer = new FileWriter(fileName,true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(content,0,content.length());
            bufferedWriter.flush();

            bufferedWriter.close();;
            writer.close();
        }catch (Exception e){
            System.out.println("答案文件写入异常");
        }
    }

    //求最大公约数
    public static int GCD(int first,int second){
        //保留副本
        int one=first;
        int two=second;

        //确保first>=second
        if(first<second){
            int temp=first;
            first=second;
            second=temp;
        }

//        if(second==0){//0没有公约数
//            return 1;
//        }
        int remainder=first%second;//remainder 余数
        while(remainder!=0){//余数不为零
            first=second;
            second=remainder;
            remainder=first%second;
        }
        //跳出循环，second即为最大公约数
        return second;
    }

    //求最小公倍数
    public static int LCM(int first,int second){
        int gcd=UtilClass.GCD(first,second);

        //根据最大公约数和最小公倍数的关系，求出最小公倍数
        return first*second/gcd;
    }

    class Operation {
        private static int ADD = 1;
        private static int SUB = 1;
        private static int MUL = 2;
        private static int DIV = 2;
        public static int getPrio(String operation){
            int res=0;
            switch (operation){
                case "+"->res=ADD;
                case "-"->res=SUB;
                case "×"->res=MUL;
                case "÷"->res=DIV;
            }
            return res;
        }
    }
    //中缀表达式转后缀表达式
    public static String trunToSuffixString(String infixString){
        //先将 中缀表达式转成list，方便操作
        String[] split = infixString.split(" ");
        List<String> list=new ArrayList<String>();
        for(String ele:split){
            list.add(ele);
        }

        Stack<String> s1=new Stack<>();
        List<String> s2=new ArrayList<>();
        for(String item:list){
            if(isElement(item)){
                s2.add(item);
            }else if(item.equals("(")){
                s1.push(item);
            }else if(item.equals(")")){
                while(!s1.peek().equals("(")){ //直到碰到"("结束
                    s2.add(s1.pop());
                }
                s1.pop();//弹出s1栈里的括号
            }else{//乘除优先级
                //item优先级小于等于s1栈顶，将s1栈顶的元素弹出并加入s2，直到item优先级大于s1栈顶
                while(!s1.empty() && Operation.getPrio(item)<=Operation.getPrio(s1.peek())){
                    s2.add(s1.pop());
                }
                //最后将item压入s1
                s1.push(item);
            }
        }

        //将剩余的s1中的元素压入s2
        while(!s1.empty()){
            s2.add(s1.pop());
        }

        //将s2转为String
        String suffixString=new String();
        for(String item: s2){
            suffixString=suffixString+item+" ";
        }
        return suffixString;
    }



    //后缀表达式求值
    public static String getResult(String suffixString){
        String[] split =suffixString.split(" ");
        List<String> list=new ArrayList<String>();
        for(String ele:split){
            list.add(ele);
        }

        //
        Stack<String> stack =new Stack<>();
        for(String item:list){
            if(isElement(item)){
                //数字直接入栈
                stack.push(item);
            }else{
                //遇到运算符，pop出来两个数,解析后运算
                Element num2=Element.parseElement(stack.pop());
                Element num1=Element.parseElement(stack.pop());

                String res=null;
                if(item.equals("+")){
                    res=num1.add(num2).toString();
                }else if(item.equals("-")){
                    res=num1.sub(num2).toString();
                }else if(item.equals("×")){
                    res=num1.mul(num2).toString();
                }else if(item.equals("÷")){
                    res=num1.div(num2).toString();
                }else{
                    System.out.println("运算符输入错误");
                }
                stack.push(res);
            }
        }
        //最后的结果在栈里
        return stack.pop();
    }


    public static boolean isElement(String item){
        if(item.equals("(")||item.equals(")")
                ||item.equals("+")||item.equals("-")
                ||item.equals("×")||item.equals("÷")
                ||item.equals("=")) {
            return false;
        }else{
            return true;
        }
    }

    //将结果规范化
    public static String standard(String firstResult){
        Element element = Element.parseElement(firstResult);

        if(element.isDivision()){
            if(element.getDowm()==0||element.getUp()==0){//式子无意义 he 式子为零,统一用零表示
                return "0";
            }
            if(element.getUp()!=0 && GCD(element.getUp(),element.getDowm())!=1){//分式可约分(约分后可能变为整数
                int gcd=GCD(element.getUp(),element.getDowm());
                element.setUp(element.getUp()/gcd);
                element.setDowm(element.getDowm()/gcd);
            }

            if(element.getUp()*element.getDowm()<0 &&element.getUp()>0){//将符号统一到分子处
                element.setUp(element.getUp()*(-1));
                element.setDowm(element.getDowm()*(-1));
            }

            //假分数转陈带分数
            if (element.getUp() >= element.getDowm()) {
                int times = element.getUp() / element.getDowm();
                element.setUp(element.getUp() - times * element.getDowm());
                if (element.getUp() == 0) {
                    return ""+times;
                } else {
                    return times + "'" + element.toString();
                }
            }



            if(element.getDowm()==1){//分母为一，转成整数
                element.setDivision(false);
            }
        }

        return element.toString();
    }

}
