import java.util.Random;
import java.util.Scanner;

public class GameControl {
    int[][] Map;
    int Height;
    int Width;
    public void initMap() {
        System.out.println("请输入行数：");
        Scanner scanner = new Scanner(System.in);
        Height = scanner.nextInt();
        System.out.println("请输入列数：");
        Width = scanner.nextInt();
        Map=new int[Width][Height];
        //Random random = new Random();
        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                Map[i][j] = 0;//Math.abs(random.nextInt() % 2);
            }
        }
    }
    public void DeleteMap(){
        for (int i = 0; i < Width; i++)
            for (int j = 0; j < Height; j++)
                Map[i][j]=0;
        }
    public void reinitMap(){
        Random random = new Random();
        for (int i = 0; i < Width; i++) {
            for (int j = 0; j < Height; j++) {
                Map[i][j] = Math.abs(random.nextInt() % 2);
            }
        }
    }
    public void SetMap(){
        GameLogic logic=new GameLogic(Width,Height);
        Map=logic.SetLifeState(Map);
    }

}

