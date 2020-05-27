package sample;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.control.ScrollBar;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent.*;
import java.io.File;
import java.util.Scanner;

public class Main extends Application {
	
	final static String[] colonne = {"1980-1999","2000-2019","2020-2039","2040-2059","2060-2079","2080-2099"};
 
    @Override public void start(Stage stage) {
        String fileName = "DATI.csv";
        File file = new File(fileName);
        int colonna = 1;
        
        stage.setTitle("Grafico ondate di calore");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<>(xAxis,yAxis);
        bc.setTitle("ONDATE DI CALORE");
        xAxis.setLabel("Anno");
        yAxis.setLabel("Valore");

        XYChart.Series series1 = creazioneLinee(file,colonna);
        series1.setName("Numero ondate");
        colonna++;

        XYChart.Series series2 = creazioneLinee(file,colonna);
        series2.setName("Lunghezza massima");
        colonna++;

        XYChart.Series series3 = creazioneLinee(file,colonna);
        series3.setName("Numero giorni ondate di calore");

        Scene scene  = new Scene(bc,1000,700);
        bc.getData().addAll(series1, series2, series3);
        stage.setScene(scene);
        stage.show();
    }
    
    public XYChart.Series creazioneLinee(File file,int colonna) {
    	
    	XYChart.Series series = new XYChart.Series();
    	double sum = 0;
        double numeroContatore = 0;
        double media = 0;
        int c = 0;
        int lung = colonne.length;
        try{
            Scanner inputStream = new Scanner(file);
            inputStream.next(); // ignora la prima linea
            for(int i = 0;i < lung;i++) { 
    	        while(c<20){
    		            String data = inputStream.next(); // prende un'intera linea
    		            String[] values = data.split(";"); // vettore valori
    		            double n = Double.parseDouble(values[colonna]);
    		            sum += n;
    		            numeroContatore++;
    		            media = sum / numeroContatore;
    		            series.getData().add(new XYChart.Data(colonne[i], media));
    		            c++;
    		        }
    	        sum = 0;
                numeroContatore = 0;
                c = 0;
    	    }
            inputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return series;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
