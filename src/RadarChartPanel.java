import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadarChartPanel extends JPanel {
    private Map<String, Double> scores = new HashMap<>();

    public void setScores(Map<String, Double> scores) {
        this.scores = scores;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scores == null || scores.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int radius = Math.min(cx, cy) - 30;
        int n = scores.size();

        List<String> keys = new ArrayList<>(scores.keySet());

        g2.setColor(Color.LIGHT_GRAY);
        for (int i = 1; i <= 5; i++) {
            int r = radius * i / 5;
            Polygon p = new Polygon();
            for (int j = 0; j < n; j++) {
                double angle = j * 2 * Math.PI / n - Math.PI / 2;
                p.addPoint((int)(cx + r * Math.cos(angle)), (int)(cy + r * Math.sin(angle)));
            }
            g2.drawPolygon(p);
        }

        for (int i = 0; i < n; i++) {
            double angle = i * 2 * Math.PI / n - Math.PI / 2;
            g2.setColor(Color.GRAY);
            g2.drawLine(cx, cy, (int)(cx + radius * Math.cos(angle)), (int)(cy + radius * Math.sin(angle)));

            g2.setColor(Color.BLACK);
            int tx = (int)(cx + (radius + 15) * Math.cos(angle));
            int ty = (int)(cy + (radius + 15) * Math.sin(angle));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(keys.get(i), tx - fm.stringWidth(keys.get(i))/2, ty);
        }

        Polygon scorePoly = new Polygon();
        for (int i = 0; i < n; i++) {
            double angle = i * 2 * Math.PI / n - Math.PI / 2;
            double score = scores.get(keys.get(i));
            int r = (int)(radius * (score / 5.0));
            scorePoly.addPoint((int)(cx + r * Math.cos(angle)), (int)(cy + r * Math.sin(angle)));
        }

        g2.setColor(new Color(0, 102, 204, 100));
        g2.fillPolygon(scorePoly);
        g2.setColor(new Color(0, 102, 204));
        g2.setStroke(new BasicStroke(2));
        g2.drawPolygon(scorePoly);
    }
}