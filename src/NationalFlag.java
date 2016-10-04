import javax.swing.*;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

/**
 * Created by Owen on 2016/10/4.
 */
public class NationalFlag extends JFrame {
    private int width = 288, height = width /3*2;
    private double maxR = 0.15,minR = 0.05;
    private double maxX = 0.50,maxY = 0.50;
    private double[] minX = {0.75,0.85,0.85,0.75};
    private double[] minY = {0.35,0.45,0.60,0.70};

    public NationalFlag(){
        setTitle("国旗-时元文");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    @Override
    public void paint(java.awt.Graphics graphics){
        super.paint(graphics);
        java.awt.Graphics2D g = (java.awt.Graphics2D)graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        //旗面
        g.setColor(java.awt.Color.red);
        g.fillRect(50,50,width,height);

        g.setColor(java.awt.Color.yellow);
        //画大星星
        double ox= height*maxX,oy = height*maxY;
        g.fill(createPentacle(ox,oy,height*maxR,-Math.PI/2));

        //画小星星
        for(int idx=0;idx<4;idx++){
            double sx = minX[idx]*height,sy = minY[idx]*height;
            double theta = Math.atan2(oy-sy,ox-sx);
            g.fill(createPentacle(sx,sy,height*minR,theta));
        }
    }

    /**
     * 创建一个五角星形状，该五角星的中心坐标为（sx,sy）,中心到顶点的距离为radius，其中某个顶点与中心的连线的
      偏移角度为theta(弧度）
     *
     * @return pentacle 一个五角星
     */


    public static java.awt.Shape createPentacle(double sx,double sy,double radius,double theta){
        final double arc = Math.PI/5;
        final double rad = Math.sin(Math.PI/10)/Math.sin(3*Math.PI/10);
        GeneralPath path = new GeneralPath();
        path.moveTo(1,0);
        for(int idx = 0;idx<5;idx++){
            path.lineTo(rad*Math.cos((1+2*idx)*arc),rad*Math.sin((1+2*idx)*arc));
            path.lineTo(Math.cos(2*(idx+1)*arc),Math.sin(2*(idx+1)*arc));
        }
        path.closePath();
        AffineTransform atf = AffineTransform.getScaleInstance(radius,radius);
        atf.translate(sx/radius,sy/radius);
        atf.rotate(theta);
        return atf.createTransformedShape(path);
    }

    public static void main(String[] args) {
        NationalFlag flag = new NationalFlag();
        flag.setBounds(100,100,400,400);
        flag.setVisible(true);
    }
}
