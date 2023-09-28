import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FormulaBuilder {
    private static Random random=new Random();
    private static StringBuilder sb=new StringBuilder(64);


    public static void startCreate(int num,int max){
        Formula[] formulas=new Formula[num];

        for (int i = 0; i < num; i++) {//num条式子
            Formula sigleFormula=new Formula(max);
            //输出和写入文件
//            System.out.println(sigleFormula.getSb());
            UtilClass.wirteTxt("Exercises.txt",sigleFormula.getSb().toString()+"\n");
            UtilClass.wirteTxt("Answers.txt",i+1+"."+sigleFormula.getResult()+"\n");
        }

    }

    public static void startCheck(String exePath,String workPath) {
        //先计算出正确结果，并写入Correct.txt文件
        String exeString = UtilClass.readTxt(exePath);
        String[] exeSpilt = exeString.split(" =");
        exeSpilt[0]=exeSpilt[0].replace("null","");//txt文件开头有个null

        String record;
        int index=1;
        for (String item : exeSpilt) {
            record = UtilClass.standard(UtilClass.getResult(UtilClass.trunToSuffixString(item)));
            UtilClass.wirteTxt("Correct.txt", (index++)+"."+record + "\n");
        }

        //进行比对，Correct.txt 和 Answers.txt比对
        String ansString=UtilClass.readTxt(workPath);
        String[] ansSpilt = ansString.split("[.]");
        String corString=UtilClass.readTxt("Correct.txt");
        String[] corSpilt = corString.split("[.]");

        List<Integer> rightList=new ArrayList<>();
        List<Integer> wrongList=new ArrayList<>();
        for (int i = 1; i < corSpilt.length; i++) {//除去开头的 null1
            if(ansSpilt[i].equals(corSpilt[i])){
                rightList.add(i);
            }else{
                wrongList.add(i);
            }
        }

        //输出Grade.txt文件
        String gradeContent="Correct:"+rightList.size()+" (";
        while(!rightList.isEmpty()){
            gradeContent+=rightList.remove(0)+",";
        }
        if(gradeContent.charAt(gradeContent.length()-1)==','){//除去末尾多余的逗号
            gradeContent=gradeContent.substring(0,gradeContent.length()-1);
        }
        gradeContent+=")\nWrong: "+wrongList.size()+" (";
        while(!wrongList.isEmpty()){
            gradeContent+=wrongList.remove(0)+",";
        }
        if(gradeContent.charAt(gradeContent.length()-1)==','){//除去末尾多余的逗号
            gradeContent=gradeContent.substring(0,gradeContent.length()-1);
        }
        gradeContent+=")";
        UtilClass.wirteTxt("Grade.txt",gradeContent);
    }
}
