import java.net.SocketTimeoutException;
import java.util.Random;

public class Element {
    private boolean isDivision=false;
    private static Random random=new Random();

    private int max;
    private int up;//分子
    private int dowm=-1;//分母,整数时为-1

    public Element() {
    }

    public Element(boolean isDivision,int max) {
        this.isDivision = isDivision;
        this.max=max;

        if(true==isDivision){//分数
            this.dowm=random.nextInt(max-2)+2;//[2,max)
            this.up=random.nextInt(this.dowm-1)+1;
        }else{//整数
            this.dowm=-1;
            this.up=random.nextInt(max-1)+1;
        }
    }


    //数值计算
    public Element add(Element anotherElement){
        if(this.isDivision&&anotherElement.isDivision){
            //俩分数计算,先通分
            try {
                int sameDown = UtilClass.LCM(this.getDowm(), anotherElement.getDowm());
                this.up=this.up*sameDown/this.dowm;
                this.dowm=sameDown;
                anotherElement.up=anotherElement.up*sameDown/anotherElement.dowm;
                anotherElement.dowm=sameDown;
            }catch (Exception e){
                System.out.println(this.getDowm()+"  "+anotherElement.getDowm());
            }

            //计算
            this.up+=anotherElement.up;
            return this;
        }else if(!this.isDivision&&!anotherElement.isDivision){//俩整数
            this.up+=anotherElement.up;
            return this;
        }else{//一整数和一分数
            if(this.isDivision){
                this.up += anotherElement.up*this.dowm;
                return this;
            }else{
                anotherElement.up +=this.up*anotherElement.dowm;
                return anotherElement;
            }
        }
    }

    public Element sub(Element anotherElement){
        if(this.isDivision&&anotherElement.isDivision){//俩分数
            //俩分数计算,先通分
            try{
            int sameDown=UtilClass.LCM(this.getDowm(),anotherElement.getDowm());
            this.up=this.up*sameDown/this.dowm;
            this.dowm=sameDown;
            anotherElement.up=anotherElement.up*sameDown/anotherElement.dowm;
            anotherElement.dowm=sameDown;
            }catch (Exception e){
                System.out.println(this.getDowm()+"  "+anotherElement.getDowm());
            }

            this.up-=anotherElement.up;
            return this;
        }else if(!this.isDivision&&!anotherElement.isDivision){//俩整数
            this.up-=anotherElement.up;
            return this;
        }else{
            if(this.isDivision){
                this.up=this.up-anotherElement.up*this.dowm;
                return this;
            }else{
                this.isDivision=true;
                this.up=this.up*anotherElement.dowm-anotherElement.up;
                this.dowm=anotherElement.dowm;
                return this;
            }
        }
    }

    public Element mul(Element anotherElement){
        if(this.isDivision&&anotherElement.isDivision){
            this.up*=anotherElement.up;
            this.dowm*=anotherElement.dowm;
            return this;
        }else if(!this.isDivision&&!anotherElement.isDivision){
            this.up*=anotherElement.up;
            return this;
        }else{
            if(this.isDivision){
                this.up*=anotherElement.up;
                return this;
            }else{
                anotherElement.up*=this.up;
                return anotherElement;
            }
        }
    }


    public Element div(Element anotherElement){
        if(this.isDivision&&anotherElement.isDivision){
            this.up*=anotherElement.dowm;
            this.dowm*=anotherElement.up;
            return this;
        }else if(!this.isDivision&&!anotherElement.isDivision){
            this.isDivision=true;
            this.dowm=anotherElement.up;
            return this;
        }else{
            if(this.isDivision){
                this.dowm*=anotherElement.up;
                return this;
            }else{
                anotherElement.dowm*=this.up;
                return anotherElement;
            }
        }
    }


    public static Element parseElement(String str){
        Element ele=new Element();
        //判断是否为分数
        if(str.matches("-?\\d+/-?\\d+")){
            ele.isDivision=true;
            String[] split=str.split("/");
            ele.up=Integer.parseInt(split[0]);
            ele.dowm=Integer.parseInt(split[1]);
        }else{
            ele.isDivision=false;
            ele.up=Integer.parseInt(str);
        }
        return ele;
    }

    @Override
    public String toString() {
        if(this.isDivision){
            return ""+this.up+"/"+this.dowm;
        }else{
            return ""+this.up;
        }
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDowm() {
        return dowm;
    }

    public void setDowm(int dowm) {
        this.dowm = dowm;
    }

    public boolean isDivision() {
        return isDivision;
    }

    public void setDivision(boolean division) {
        isDivision = division;
    }
}
