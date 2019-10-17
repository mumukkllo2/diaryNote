package com.company;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;


class Ui implements Runnable{
    /*
     * 创建窗体以及里面的组件
     * */
    private Frame frame;	//整个框架
    private Panel northpanel,southpanel;	//添加控制板"北面"和“南面”
    private MenuBar menubar;		//菜单栏
    private Menu menu1,menu2;		//创建两个菜单条目
    private MenuItem menuitem1,menuitem2,menuitem3,menuitem4;	//创建三个最小级别菜单条目
    private TextArea textarea;	//文本区域，写日记
    private TextField textfield1,textfield2,textfiled3;	//创建文本框1和2，用来写时间和天气
    private Button button1,button2,button3;	//创建按钮1和2分别用来保存和清空内容
    private Label label1,label2,label3,label4;	//创建“时间”“天气”“作者信息”“星期”标签
    private Dialog dialog;	//创建“对话框”
    private File file;		//创建文件对象
    private FileDialog fileDialog;	//创建“保存”对话框
    private FileDialog fileOpen;	//创建“打开”对话框
    private Image image;
    private Toolkit kit;
    private Font font;


    private void init(){	//初始化函数内容
        kit =Toolkit.getDefaultToolkit();
        font=new Font("Serif",Font.PLAIN,15);

        frame=new Frame("My Note");		//窗口和标题
        frame.setSize(600, 500);//设置窗口大小
        frame.setLocationRelativeTo(null);	//默认窗口出现位置居中，也可以手动进行设置
        frame.setResizable(false);	//不能更改大小
        frame.setVisible(true);		//设置可见性
        frame.setBackground(Color.lightGray);	//设置背景色，暂定为null
        frame.setFont(font);

        northpanel=new Panel();		//实例化“控制板”
        southpanel=new Panel();

        label1=new Label("Time：");	//实例化“时间”“天气”“星期”标签
        label4=new Label("Week：");
        label2=new Label("Weather：");

        menubar=new MenuBar();		//这里是菜单栏，菜单层级以此是MenuBar--Menu--MenuItem
        menu1=new Menu("File");		//添加菜单条目menu
        menu2=new Menu("About");
        menuitem1=new MenuItem("Save");
        menuitem2=new MenuItem("Exit");
        menuitem3=new MenuItem("Author");
        menuitem4=new MenuItem("Open");
        menubar.add(menu1);			//将菜单条目添加到菜单栏
        menu1.add(menuitem4);		//添加的是“打开”条目
        menu1.add(menuitem1);		//添加的是“保存”条目
        menu1.addSeparator();
        menu1.add(menuitem2);		//添加的是“退出”条目
        menu1.addSeparator();
        menu1.add(menu2);			//添加的是“关于”条目
        menu2.add(menuitem3);		//添加的是“作者简介”条目
        frame.setMenuBar(menubar);	//窗口使用的菜单栏是menubar

        textarea=new TextArea("此处写日记",10,40);	//实例化文本域，初始文字和行列数

        textfield1=new TextField(10);		//时间文本框，10列
        textfield2=new TextField(10);		//天气文本框，10列
        textfiled3=new TextField(10);		//星期文本框，10列

        button1=new Button("Save");		//保存按钮
        button2=new Button("Clear");		//清空文本按钮

        northpanel.add(label1);			//"北面"的控制面板添加“标签”和“文本框”
        northpanel.add(textfield1);
        northpanel.add(label4);
        northpanel.add(textfiled3);
        northpanel.add(label2);
        northpanel.add(textfield2);

        southpanel.add(button1);		//“南面”的控制面板添加“保存”和“清空”按钮
        southpanel.add(button2);

        frame.add(northpanel,BorderLayout.NORTH);	//frame将“控制面板”的位置添加到“北面”和“南面”的borderlayout布局中
        frame.add(southpanel,BorderLayout.SOUTH);
        frame.add(textarea,BorderLayout.CENTER);	//将“文本域”添加到中间的布局中

        dialog=new Dialog(frame, "Author",true);	//点击按钮的时候创建“作者简介”对话框
        dialog.setSize(195, 100);		//dialog默认是borderlayout布局
        dialog.setLocationRelativeTo(null);
        label3 = new Label("Made By cyc");
        button3 = new Button("Good");
        dialog.add(label3,BorderLayout.NORTH);
        dialog.add(button3,BorderLayout.SOUTH);
        dialog.setResizable(false);

        fileDialog=new FileDialog(frame,"Save",FileDialog.SAVE);	//文件保存路径对象的创建
        fileOpen = new FileDialog(frame,"Open",FileDialog.LOAD);


        listener();
    }
    /*
    *文件打开功能
    * */

    private void open() {
        fileOpen.setVisible(true);
        //System.out.println(dirPath +"++"+ fileName);
        String dirPath = fileOpen.getDirectory();//获取文件路径
        String fileName = fileOpen.getFile();//获取文件名称
//        System.out.println(dirPath +"++"+ fileName);

        //如果打开路径 或 目录为空 则返回空
        if(dirPath == null || fileName == null) {
            return ;
        }

        file = new File(dirPath,fileName);
        try
        {
            BufferedReader bufr = new BufferedReader(new FileReader(file));
            textfield1.setText(bufr.readLine()); // 时间
            textfiled3.setText(bufr.readLine()); // 星期
            textfield2.setText(bufr.readLine()); // 天气
            String line = null;
            String textA = "";
            while((line=bufr.readLine())!=null)
            {
                textA += line;
                textA += '\n';
            }
            textarea.setText(textA); // 文本域
            bufr.close();
        }
        catch (IOException ex)
        {
            throw new RuntimeException("文件读取失败！");
        }
    }

    /*
     *文件保存的功能函数
     * */
    private void save(){
        if(file==null){
            fileDialog.setVisible(true);
            String pass=fileDialog.getDirectory();
            String name=fileDialog.getFile();
            if(pass==null||name==null)
                return;
            file=new File(pass,name);
        }
        try{
            BufferedWriter bufw=new BufferedWriter(new FileWriter(file));
            String text=textarea.getText();	//获取文本域内容
            String time=textfield1.getText();	//获取写作时间
            String weather=textfield2.getText();	//获取天气情况
            String week=textfiled3.getText();	//获取星期
            bufw.append(time+"\n");
            bufw.append(week+"\n");
            bufw.append(weather+"\n");
            bufw.append(text);
            bufw.close();
        }
        catch(IOException error){
            throw new RuntimeException("IO error happend!");
        }
    }
    /*
     *清空所有内容操作
     * */
    private void clear(){
        textarea.setText("");
        textfield1.setText("");
        textfield2.setText("");
        textfiled3.setText("");
    }


    /*
     * 监听器函数设计
     * */
    private void listener(){
        /*
         * 第一个窗口监听器，处理窗口关闭时的动作
         * */
        frame.addWindowListener(new WindowAdapter () {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        /*
         * 第二个监听器，处理菜单级别的退出动作
         * */
        menuitem2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        /*
         * 第三个监听器，处理作者信息按钮
         * */
        menuitem3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(true);
            }
        });
        /*
         * 第四个监听器，处理“作者信息”对话窗口的关闭操作
         * */
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
            }
        });
        /*
         * 第五个监听器，处理“作者信息”对话窗口的“好”操作
         * */
        button3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        /*
         * 第六个监听器，菜单栏的“保存”按钮操作
         * */
        menuitem1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        /*
         * 第七个监听器，下面的“保存按钮”的动作
         * */
        button1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        /*
         * 第八个监听器，“清除按钮”的作用
         * */
        button2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                textarea.setText("");
            }
        });
        /*
         * 第九个监听器，“打开按钮”的作用
         * */
        menuitem4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        init();
    }


}
