package com.company;
public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try{
            Pic pic=new Pic();
            Thread t1=new Thread(pic);
            t1.start();

            Ui ui=new Ui();
            Thread t2=new Thread(ui);
            t2.start();

        }
        catch(Exception e){
            Pic pic=new Pic();
            Thread t1=new Thread(pic);
            t1.start();

            Ui ui=new Ui();
            Thread t2=new Thread(ui);
            t2.start();

        }

    }

}
