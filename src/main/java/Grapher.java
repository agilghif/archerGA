//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class Grapher {
//    public BufferedImage img;
//    public Graphics2D g;
//    private String location;
//    private int w, h;
//
//    private static Color BG = new Color(32, 32, 34);
//    private static Color AC = new Color(100, 225, 50);
//    private static Color VIS = new Color(124, 121, 120);
//    private static Color AcVIS = new Color(207, 227, 31);
//    private static Color BLT = new Color(255, 231, 171);
//
//    private static Stroke line = new BasicStroke(3);
//    private static Stroke dash = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, new float[] {2f, 0f, 2f}, 2f);
//
//    private static ArrayList<Bullet> bullets = new ArrayList<>();
//    private static ArrayList<Bullet> removed = new ArrayList<>();
//
//    public Grapher(int w, int h) {
//        this.w = w;
//        this.h = h;
//
//        img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//        g = img.createGraphics();
//    }
//
//    public void drawArcher(Archer a) {
//        g.setColor(BG);
//        g.fillRect(0, 0, w, h);
//        int center = transformPointX(a.pos);
//
//        // draw bullet
////        for (Bullet bullet : bullets) {
////            bullet.update();
////            if (bullet.isOut()) removed.add(bullet);
////        }
////        for (Bullet bullet : removed)
////            bullets.removeAll(removed);
////        removed.clear();
////
////        for (Bullet bullet : bullets) {
////            g.setColor(BLT);
////            g.fillOval(
////                    transformPointX(bullet.x)-3,
////                    transformPointY(bullet.y)-3,
////                    100,
////                    100);
////            g.drawLine(0, 0, 500, 500);
////
////            System.out.println("x: " + (transformPointX(bullet.x)) + ", y: " + transformPointY(bullet.y));
////        }
////
////        // Check whether new bullet is added
////        if (a.shot) bullets.add(new Bullet(a));
//
//        // draw vision
//        g.setStroke(dash);
//        if (a.visCol == 10f) g.setColor(AcVIS);
//        else g.setColor(VIS);
//        g.drawLine(center, h-10,
//                (int) (600 * Math.sin(a.visT)) + center,
//                h - 10 - (int) (600 * Math.cos(a.visT)));
//
//        // draw body
//        g.setColor(AC);
//        g.fillRect(center-15, h-30, 30, 30);
//
//        // draw bullet thrower
//        g.setStroke(line);
//        g.drawLine(center, h-15,
//                (int) (60 * Math.sin(a.archT)) + center,
//                h - 10 - (int) (60 * Math.cos(a.archT)));
//
//        // draw goal
//        g.setColor(AcVIS);
//        g.drawLine(transformPointX(Archer.maxDist),
//                transformPointY(Archer.lowerLim),
//                transformPointX(Archer.maxDist),
//                transformPointY(Archer.upperLim));
//    }
//
//    public void saveImg(int i) {
//        File f = new File("C:\\Users\\user\\OneDrive\\Dokumen\\Kuliah UI\\ML AI Projects\\archerGA\\result\\im" + i + ".png");
//
//        try {
//            ImageIO.write(img, "png", f);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public int transformPointX(float f) {
//        return (int) ((-10+w/2)*f/Archer.maxDist + w/2);
//    }
//
//    public int transformPointY(float f) {
//        return (int) ((20-h)/Archer.maxDist*f + h - 10);
//    }
//}
//
//class Bullet {
//    float x,y,vx,vy;
//
//    public Bullet(Archer a) {
//        this.x = a.pos;
//        this.y = 0;
//        this.vx = a.vel + (float) (a.shootVel * Math.sin(a.archT));
//        this.vy = (float) (a.shootVel * Math.cos(a.archT));
//    }
//
//    public void update() {
//        this.x += vx * Archer.dt;
//
//        if (vx != 0)
//            this.y = vy/vx * x - Archer.hg * x * x / (vx*vx);
//        else {
//            this.vy -= Archer.hg * Archer.dt;
//            this.y += vy * Archer.dt;
//        }
//
//    }
//
//    public boolean isOut() {
//        if (x > Archer.maxDist || x < -Archer.maxDist || y > Archer.maxDist || y < Archer.maxDist)
//            return true;
//        return false;
//    }
//}