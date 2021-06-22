import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class GameV extends JFrame implements ActionListener {
    private JButton[][] GMap;
    private GameControl GameC;
    private Random random;
    private JButton clear,init,startNext,start,stop;
    private static GameV frame;
    private Thread thread;
    private int N;
    public static void main(String args[]){

        frame=new GameV("生命游戏");

    }
    GameV(String name){
        super(name);
        initGameV();
    }
    public void initGameV(){
        GameC=new GameControl();
        GameC.initMap();
        JPanel backPanel,centerPanel,bottomPanel;
        backPanel=new JPanel(new BorderLayout());
        centerPanel=new JPanel(new GridLayout(GameC.Width,GameC.Height));
        bottomPanel=new JPanel();
        this.setContentPane(backPanel);
        backPanel.add(centerPanel,"Center");
        backPanel.add(bottomPanel,"North");
        clear=new JButton("清空");
        init=new JButton("随机生成细胞");
        startNext=new JButton("下一步演化");
        start=new JButton("开始不间断演化");
        stop=new JButton("停止不间断演化");
        GMap=new JButton[GameC.Width][GameC.Height];
        bottomPanel.add(clear);
        bottomPanel.add(init);
        bottomPanel.add(start);
        bottomPanel.add(startNext);
        bottomPanel.add(stop);
        random=new Random();//颜色随机数
        for(int i=0;i<GameC.Width;i++)//初始化细胞颜色
            for(int j=0;j<GameC.Height;j++){
                GMap[i][j]=new JButton("");
                if(GameC.Map[i][j]==0)
                    GMap[i][j].setBackground(Color.WHITE);
                else
                    GMap[i][j].setBackground(Color.getHSBColor(Math.abs(random.nextFloat()%255),
                            Math.abs(random.nextFloat()%255),Math.abs(random.nextFloat()%255)));
                centerPanel.add(GMap[i][j]);
            }
        this.setSize(1000,800);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        clear.addActionListener(this);
        init.addActionListener(this);
        startNext.addActionListener(this);
        start.addActionListener(this);
        stop.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==clear){
            GameC.DeleteMap();
            frame.showMap();
        }else if(e.getSource()==init){
            GameC.reinitMap();
            frame.showMap();
        }else if(e.getSource()==startNext){
            frame.updateMap();
            frame.showMap();
        }else if(e.getSource()==start){
            N=1;
            thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    while(N==1){
                        frame.updateMap();
                        frame.showMap();
                        try{
                            TimeUnit.MILLISECONDS.sleep(800);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }else if(e.getSource()==stop){
            N=0;
            thread=null;
        }
    }
    public void updateMap(){
        GameC.SetMap();
    }
    public void showMap(){
        for(int i=0;i<GameC.Width;i++)//更新细胞颜色
            for(int j=0;j<GameC.Height;j++){
                if(GameC.Map[i][j]==0)
                    GMap[i][j].setBackground(Color.WHITE);
                else
                    GMap[i][j].setBackground(Color.getHSBColor(Math.abs(random.nextFloat()%255),
                            Math.abs(random.nextFloat()%255),Math.abs(random.nextFloat()%255)));
            }
    }
}
