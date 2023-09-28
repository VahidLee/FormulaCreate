import java.util.Scanner;

public class MyApp {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入1(生成题目)或2(校对答案): ");
        int choice = scanner.nextInt();

        if (1 == choice) {
            creatFormalu();
        }
        if (2 == choice) {
            checkFormula();
        }


//        FormulaBuilder.startCheck("Exercises.txt","Answers.txt");
    }


    public static void creatFormalu(){
        System.out.print("请输入生成的题目数：");
        int num=scanner.nextInt();
        System.out.print("请输入生成的最大的数值：");
        int max=scanner.nextInt();

        UtilClass.deleteFile("Exercises.txt");//先删除之前创建的式子
        UtilClass.deleteFile("Answers.txt");//先删除答案
        FormulaBuilder.startCreate(num,max);

        System.out.print("题目生成完毕");
    }

    public static void checkFormula(){
        System.out.print("请输入Exercises.txt的文件路径：");
        String exePath=scanner.next();
        System.out.print("请输入Answers.txt文件路径：");
        String workPath=scanner.next();

        UtilClass.deleteFile("Correct.txt");//先删除之前残留的文件
        UtilClass.deleteFile("Grade.txt");//删除残留的文件

        FormulaBuilder.startCheck(exePath,workPath);
        System.out.print("答案校对完毕");
    }



}
