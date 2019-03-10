package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

public class LeaderboardController {

    @FXML
    AnchorPane Anchor;

    @FXML
    BarChart<Number, String> bc;

    @FXML
    NumberAxis xAxis;
    @FXML
    CategoryAxis yAxis;

    public void initialize() {
        initChart();
    }

    private void initChart() {

        //get list with points from db in desc order, for now a made up list
        //List<Entries> list = retrievePoints();
        //An entry should be a username and the amount of points of that user

//        int[] list = {50, 40, 30, 10};
//        String user = "User";
//
//        xAxis.setLabel("Points");
//        xAxis.setTickLabelRotation(90);
//        yAxis.setLabel("Name");
//
//        XYChart.Series entries = new XYChart.Series();
//
//        for (int i = 0 ; i < list.length; i++) {
//            entries.getData().add(new XYChart.Data(list[i], user + i));
//        }
//
//        bc.getData().add(entries);
    }



//    //Temporary main method
//    Stage primaryStage;
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        this.primaryStage = primaryStage;
//        primaryStage.setTitle("GoGreen");
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("fxml/leaderboard.fxml"));
//
//        AnchorPane pane = loader.load();
//        primaryStage.setScene(new Scene(pane));
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
}
