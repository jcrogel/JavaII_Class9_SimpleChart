package sample;

import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class Controller implements Initializable {
    public final int ITEMS_AMT = 10;
    public BarChart chart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // This happens on first load
        chart.setTitle("Self generated Chart");

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName("Random Values");
        Random rnd = new Random();

        for (int i=0; i<this.ITEMS_AMT; i++){
            Double value = new Double(rnd.nextDouble());
            XYChart.Data<String, Double> item = new XYChart.Data<String, Double>(Integer.toString(i), value);
            series.getData().add(item);
        }

        chart.getData().add(series);

        Timer timer = new Timer();
        BGTask t = new BGTask();
        t.series = series;
        timer.scheduleAtFixedRate(t, 0, 500);
    }


    class BGTask extends TimerTask {
        XYChart.Series<String, Double> series;

        @Override
        public void run() {
            int item_num = this.series.getData().size();
            Random rnd = new Random();

            int replaceIndex = Math.abs(rnd.nextInt() % item_num);

            Double value = rnd.nextDouble();
            XYChart.Data<String, Double> item = new XYChart.Data<String, Double>(Integer.toString(replaceIndex), value);

            // Update the chart
            Platform.runLater(() -> {
                // Notice we are only changing the data
                series.getData().set(replaceIndex, item);
            });
        }
    }


}
