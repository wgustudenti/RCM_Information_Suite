package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import model.RemarkCode;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class provides the instructions for the GUI with the corresponding name.
 */
public class ViewChartsController extends InformationSuiteController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private PieChart pieChart;

    @FXML
    private ScatterChart<String, Integer> scatterChart;

    private static String previousScene;

    public static String getPreviousScene() {
        return previousScene;
    }

    public static void setPreviousScene(String previousScene) {
        ViewChartsController.previousScene = previousScene;
    }

    /**
     * Returns the user to the previous screen
     * @param event
     * @throws IOException
     */
    @FXML
    void backButtonClick(ActionEvent event) throws IOException {
        changeScene(event, previousScene);
    }

    /**
     * Creates the data necessary to populate the pie chart with the top 10 most used remark codes.
     * @return ObservableList of PieChart.Data objects
     */
    public static ObservableList<PieChart.Data> createPieChartData(){
        ObservableList<PieChart.Data> listOfPieChartData = FXCollections.observableArrayList();
        ObservableList<RemarkCode> listOfRemarkCodes = RemarkCode.getObservableListOfAll();
        for(int i = 0; i < 10; i++){
            RemarkCode remarkCode = listOfRemarkCodes.get(i);
            if(remarkCode.getTotalTimesUsed() != 0) {
                PieChart.Data pieChartData = new PieChart.Data("Remark: " + remarkCode.getRemarkCode(), remarkCode.getTotalTimesUsed());
                listOfPieChartData.add(pieChartData);
            }
        }
        return listOfPieChartData;
    }

    /**
     * Creates the data necessary to populate the xy chart with the top 10 most used remark codes.
     * @return ObservableList of XYChart.Series objects
     */
    public static ObservableList<XYChart.Series<String, Integer>> createXYChartData(){
        ObservableList<XYChart.Series<String, Integer>> listOfXYChartData = FXCollections.observableArrayList();
        ObservableList<RemarkCode> listOfRemarkCodes = RemarkCode.getObservableListOfAll();
        for(int i = 0; i < 10; i++){
            RemarkCode remarkCode = listOfRemarkCodes.get(i);
            if(remarkCode.getTotalTimesUsed() != 0) {
                XYChart.Series<String, Integer> series = new XYChart.Series<>();
                series.setName("R: " + remarkCode.getRemarkCode());
                XYChart.Data XYChartData = new XYChart.Data<>("Remark: " + remarkCode.getRemarkCode(), remarkCode.getTotalTimesUsed());
                series.getData().add(XYChartData);
                listOfXYChartData.add(series);
            }
        }
        return listOfXYChartData;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pieChart.setData(createPieChartData());
        barChart.setData(createXYChartData());
        scatterChart.setData(createXYChartData());
    }
}
