package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class Main extends Application {
	 static ArrayList<City> citie = new ArrayList<>();
	static List<Edge> edges = new ArrayList<>(); 
	static double org_xMin = 34.1707489947603;
    static double org_xMax = 34.575060834817954;
    static double org_yMin = 31.614521165206845;
    static double org_yMax = 31.208163033163977;
    static int Wxmin=0;
    static int Wymin=0;
    static int Wxmax=589;
    static int Wymax=695;
    Stage primaryStage;
    GridPane pane2;
    Dijkstra myGraph=new Dijkstra();
    ComboBox<String> source,target;
    Circle point;
    Pane mapPane;
	@Override
	public void start(Stage primaryStage) {
		Label title = new Label("Welcome To Dijkstra Project:");
		title.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 25));
		Image image1 = new Image("C:\\Users\\user\\Desktop\\Afnan\\DijkstraProj\\src\\application\\Gaza_port.jpg");
		ImageView img = new ImageView(image1);
		img.setFitWidth(400);
		img.setFitHeight(400);
		Button btn1 = new Button("Get The Data From File");
		btn1.setOnAction(e->{ 
			readMapDataFromFile(primaryStage);
			});
		btn1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
		Button btn2 = new Button("Next");
		btn2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));
		
			    	
		VBox vBox = new VBox(40);
		vBox.getChildren().addAll(title, img, btn1, btn2);
		btn2.setOnAction(e -> {
			showStage();
			initializeMap();

		});
		vBox.setStyle("-fx-background:#9DD9F3");
		vBox.setAlignment(Pos.CENTER);
		Scene scene1 = new Scene(vBox, 800, 800);
		primaryStage.setScene(scene1);
		primaryStage.show();

	}
	public void showStage() {
			Stage stage2 = new Stage();
			pane2 = new GridPane();
			pane2.setPadding(new Insets(20,10,20,10));
		    pane2.setAlignment(Pos.CENTER); 
			pane2.setHgap(10);
			pane2.setVgap(10); 
			
			GridPane pane20 = new GridPane();
			pane20.setPadding(new Insets(20,10,20,10));
			pane20.setHgap(17);
			pane20.setVgap(17); 
			pane2.add(pane20,1,0);
			
			//Label Greeting User
			Label mapp = new Label("Gaza Map");
			mapp.setFont(Font.font(20));  
			pane20.add(mapp,0,0);
			
			BackgroundFill bgf1 = new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY,Insets.EMPTY);
			Background bg1= new Background(bgf1 );
			
			
			Label sourcel = new Label("Source:");
			sourcel.setFont(Font.font(16));  
			pane20.add(sourcel,0,2);

			source = new ComboBox<String>();
			source.setPrefHeight(30);
			source.setPrefWidth(150);
		
		    pane20.add(source, 1, 2);
		    
			//This label will be updated by process of file importing
			Label targetl = new Label("Target:");
			targetl.setFont(Font.font(16));  
			pane20.add(targetl,0,3);
			
			target = new ComboBox<String>();
			target.setPrefHeight(30);
			target.setPrefWidth(150);
			target.setBackground(bg1);
		    pane20.add(target, 1, 3);

			//This label will be updated by process of file importing
			Label pathl = new Label("Shortest Path:");
			pathl.setFont(Font.font(16));  
			pane20.add(pathl,0,8);
			
			TextArea path = new TextArea();
			path.setPrefHeight(100);
			path.setPrefWidth(150);
			path.setEditable(false);
			pane20.add(path, 1, 8);
			
			//This label will be updated by process of file importing
			Label distl = new Label("Distance:");
			distl.setFont(Font.font(16));  
			pane20.add(distl,0,9);
			
			TextField dist = new TextField();
			dist.setPrefHeight(30);
			dist.setPrefWidth(150);
			dist.setEditable(false);
			pane20.add(dist, 1, 9);
			
			//User button to reset map
			Button reset = new Button("Reset");
			reset.setFont(Font.font(14));
			reset.setPrefSize(100, 30);
    		GridPane.setHalignment(reset, HPos.CENTER);
			reset.setStyle("-fx-background-radius: 10, 5;-fx-background-color:lightgrey;");
			pane20.add(reset,1,4); 
			reset.setOnAction(e -> {
				
				source.setValue(null);
			    target.setValue(null);

			    // Remove all lines from the mapPane
			    mapPane.getChildren().removeIf(node -> node instanceof Line);
                point.setFill(Color.RED);
			    // Clear the contents of the TextArea and TextField
			    path.clear();
			    dist.clear();
			});
			Button run0 = new Button("Run");
			run0.setFont(Font.font(14));
			run0.setPrefSize(100, 30);
		
		   	
			 run0.setOnAction(event -> {
				 String sourceCityName = source.getValue();
				    String targetCityName = target.getValue();

				    if (sourceCityName != null && targetCityName != null) {
				        City sourceCity2 = findCityByName(sourceCityName);
				        City targetCity2 = findCityByName(targetCityName);

				        if (sourceCity2 != null && targetCity2 != null) {
				            // Draw the path between source and target cities
				            drawPath(sourceCity2, targetCity2);}
		            // Assuming source and target ComboBoxes are named source and target
		            String sourceCity = source.getValue();
		            String targetCity = target.getValue();
		           
		            System.out.println("Selected source city: " + source.getValue());
		            System.out.println("Selected target city: " + target.getValue());
		            // Perform Dijkstra's algorithm
		            if (sourceCity == null || targetCity == null) {
		                System.out.println("ERROR: Source or target city is null!");
		            } else {
		                System.out.println("Calling findShortestPath with source: " + sourceCity + ", target: " + targetCity);
		                List<String> shortestPath = myGraph.findShortestPath(sourceCity, targetCity);
		                double shortestDistance = myGraph.getShortestDistance(sourceCity, targetCity);

		                System.out.println("Shortest Path: " + shortestPath);
		                System.out.println("Shortest Distance: " + shortestDistance);

		                // Display results in TextArea and TextField (assuming path and dist are TextArea and TextField)
		               path.setText(String.join("to -> ", shortestPath));
		                dist.setText(String.format("% 3f km", Math.abs(shortestDistance)));
		            }}
		        });

			run0.setStyle("-fx-background-radius: 10, 5;-fx-background-color:lightgrey;");
			pane20.add(run0,0,4); 
			Image map = new Image("C:\\Users\\user\\Desktop\\Afnan\\DijkstraProj\\src\\application\\Gaza.png");
			ImageView v = new ImageView(map);
			pane2.add(v,0,0);
			//ImageView v = new ImageView(map);
			v.setFitWidth(588);  
			v.setFitHeight(695);

			pane2.setMinSize(588, 695); 

			
	         GridPane root = new GridPane();  
	         root.setAlignment(Pos.CENTER); 
	         root.setPrefHeight(800);
	         root.setPrefWidth(440);
	         pane2.add(root, 0, 0);

			//Scene setting
	    	Scene scene = new Scene(pane2); 
			stage2.setScene(scene);  
	    	stage2.show();  	
	}
	
	       
		
		
	   
	 public void readMapDataFromFile(Stage primaryStage) {
		    FileChooser fileChooser = new FileChooser();
		    fileChooser.setTitle("Select Map Data File");
		    File file = fileChooser.showOpenDialog(primaryStage);

		    if (file != null) {
		        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
		            // Read the number of cities and adjacent cities
		            String[] cityInfo = reader.readLine().trim().split(",");
		            int numberOfCities = Integer.parseInt(cityInfo[0]);
		            int numberOfAdjacentCities = Integer.parseInt(cityInfo[1]);

		            // Add cities to the graph
		            for (int i = 0; i < numberOfCities; i++) {
		                String[] cityData = reader.readLine().trim().split(",");
		                String cityName = cityData[0];
		                double latitude = Double.parseDouble(cityData[1]);
		                double longitude = Double.parseDouble(cityData[2]);
		                double x = (((longitude - org_xMin) / (org_xMax - org_xMin)) * Wxmax) + Wxmin;
		                double y = (((latitude - org_yMin) / (org_yMax - org_yMin)) * Wymax) + Wymin;
		                citie.add(new City(cityName, x, y, latitude, longitude));
		                myGraph.addCity(cityName, latitude, longitude);
		                System.out.println(citie.toString());
		               
		            }

		            // Add edges to the graph
		            for (int i = 0; i < numberOfAdjacentCities; i++) {
		                String[] edgeData = reader.readLine().trim().split(",");
		                String sourceCity = edgeData[0];
		                String destinationCity = edgeData[1];
		                City src = findCityByName(sourceCity);
		                City des = findCityByName(destinationCity);
		                double dis = haversine(src.latitude, src.longitud, des.latitude, des.longitud);
		                Edge ed = new Edge(src, des, dis);
		                edges.add(ed); // Replace 0.0 with the actual distance if available
		                myGraph.addEdge(sourceCity, destinationCity, dis);
		                System.out.println("hi" + dis);
		            }
		        } catch (IOException | NumberFormatException e) {
		            e.printStackTrace();
		        }
		    }
		}

	    public static final double EARTH_RADIUS = 6371; // Earth's radius in kilometers

	    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
	        double dLat = Math.toRadians(lat2 - lat1);
	        double dLon = Math.toRadians(lon2 - lon1);

	        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
	                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	                   Math.sin(dLon / 2) * Math.sin(dLon / 2);

	        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	        return EARTH_RADIUS * c;
	    }
	    
	    public City findCityByName(String cityName) {
	        for (City city : citie) {
	            if (city.getName().equals(cityName)) {
	                return city;
	            }
	        }
	        return null; 
	    }

	
	    public void initializeMap() {
	        // Create a new Pane for the map
	        mapPane = new Pane();
	        mapPane.setMinSize(589, 695);

	        // Add the map image to the mapPane
	        Image map = new Image("C:\\Users\\user\\Desktop\\Afnan\\DijkstraProj\\src\\application\\Gaza.png");
	        ImageView mapView = new ImageView(map);
	        mapView.setFitWidth(588);
	        mapView.setFitHeight(694);
	        mapPane.getChildren().add(mapView);

	        // Add circles and labels to the mapPane
	        for (City city : citie) {
	            // the circle that represents the city on the map
	            point = new Circle(5);

	            // a label that holds the city name
	            Label cityName = new Label(city.getName());
	            final double MAX_FONT_SIZE = 7.0;
	            cityName.setFont(new Font(MAX_FONT_SIZE));
	            
	          
	        
	            // set circle coordinates
	            point.setCenterX(city.getX());
	            point.setCenterY(city.getY());

	            // set label beside the circle
	            cityName.setLayoutX(city.getX() -10);
	            cityName.setLayoutY(city.getY());

	            point.setFill(Color.RED);
	      

	            // setting city circle to the circle above
	            city.setCircle(point);
	            source.getItems().add(city.getName());
				target.getItems().add(city.getName()); 
				
		
	            // add the circle and the label to the mapPane
	            mapPane.getChildren().addAll(city.getCircle(), cityName);
	            
	            point.setOnMouseClicked(e->{
	            	if (source.getValue()==null) {
	            		source.setValue(city.getName());
	            		point.setFill(Color.BLUE);}
	            		else if
	            			(target.getValue()==null){
	            			target.setValue(city.getName());
	            			point.setFill(Color.BLACK);
	            			}
	            		});
	            
	    }  

	        // Create a new GridPane and add the mapPane to it
	        GridPane root = new GridPane();
	        root.setAlignment(Pos.CENTER);
	        root.setPrefHeight(800);
	        root.setPrefWidth(440);
	        root.add(mapPane, 0, 0);

	        // Add the GridPane to pane2
	        pane2.getChildren().add(root);
	    

			}
		
	    public Line drawPath(City sourceCity, City targetCity) {
	        Line line = new Line();
	        line.setStartX(sourceCity.getX());
	        line.setStartY(sourceCity.getY());
	        line.setEndX(targetCity.getX());
	        line.setEndY(targetCity.getY());
	        line.setStroke(Color.GREEN);
	      
	        // Add the line to the mapPane
	        mapPane.getChildren().add(line);
	        return line;
	    }


	public static void main(String[] args) {
		launch(args);
	}
}
