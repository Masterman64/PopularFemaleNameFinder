/**
 *
 * @author Jacob Jones
 * Date - 12/14/2019
 * This program will search for a name given by the user in a list
 * The program will also sort the list with quicksort
 */
package jjonesnamefinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class JJonesNameFinder extends Application {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        // Obtains the file that contains the names and readies it for reading
        Scanner namesReader = new Scanner(new File("jjonesnamefinder\\src\\assets\\names.txt"));
        
        // The list of names
        ArrayList<String> names = new ArrayList<>();
        
        // Imports the names form the file into the list
        while(namesReader.hasNextLine()){
            names.add(namesReader.nextLine());
        }
        
        // Sorts the names
        quickSort(names, 0, names.size() - 1);
        
        // Sets up the layout
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        
        // Sets up the scene
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Name Finder");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Shows a dialog box that tells the user about the program
        JOptionPane.showMessageDialog(null, "This program is designed to search for names within the 65 most popular female names");
        
        // Sets up the header
        Label header = new Label("Name Search");
        header.setFont(new Font(30));
        header.setPadding(new Insets(30));
        
        // Sets up the row that asks for the name
        Label askName = new Label("Enter a first name here:");
        TextField nameField = new TextField();
        nameField.setMaxSize(100, 10);
        
        // Puts the label and field into a row
        HBox nameFinders = new HBox(askName, nameField);
        nameFinders.setAlignment(Pos.CENTER);
        nameFinders.setSpacing(10);
        
        // Sets up the label that will show the result of the search
        Label foundText = new Label("");
        
        // Sets up the confirm button
        Button confirmBtn = new Button();
        confirmBtn.setText("Confirm");
        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                // The variables
                String chosenName = nameField.getText(); // The name the user is searching for
                int position = 0; // The position of the name
                int result = -1; // The result of the search
                
                // Goes throguh each name in the list and compares it to the chosen name
                for(String name : names){
                    
                    // If the name has not been found yet, then increment the position
                    // and compare names
                    if(result != 0){
                        result = name.compareTo(chosenName.toUpperCase());
                        position++;
                    }
                    
                }
                
                // Prints the results of the search
                if(result == 0){
                    foundText.setText(chosenName + " has been found in the list at position " + position + ".");
                } else {
                    foundText.setText(chosenName + " was not found in the list.");
                }
            }
        });
        
        // Combines all of the fields and rows into a column
        VBox infoCol = new VBox(header, nameFinders, confirmBtn, foundText);
        infoCol.setAlignment(Pos.CENTER);
        infoCol.setSpacing(10);
        
        // Adds the column to the layout
        layout.add(infoCol, 0, 0);
    }
    
    /**
    * This method will sort the given list by splitting it into two,
    * and then sorting those lists through recursion
    * 
    * @param list - The list to be sorted
    * @param low - The lowest position for the sort
    * @param high - The highest position for the sort
    */
    private static void quickSort(ArrayList<String> list, int low, int high){
        
        // The middle position of the given list
        int mid;
        
        // Checks if the list still needs to be sorted
        if(low < high){
            
            // Splits the list into two and sets mid the median position
            mid = partition(list, low, high);
            
            // Sorts the lower half of the list
            quickSort(list, low, mid - 1);
            
            // Sorts the higher half of the list
            quickSort(list, mid + 1, high);
        }
    }
    
    /**
    * This method will sort the given list
    * 
    * @param list - The list to be sorted
    * @param low - The lowest position for the sort
    * @param high - The highest position for the sort
    */
    private static int partition(ArrayList<String> list, int low, int high){
        
        // The Variables
        String pivotValue; // The value of the median value
        int endOfLeftList; // The end of the lower side of the list
        int mid = (high + low) / 2; // The median position of the list
        
        // Swaps the lower and middle positions
        swap(list, low, mid);
        
        // Obtains the lowest value in the list
        pivotValue = list.get(low);
        
        // Sets the end of the left list to the lowest position
        endOfLeftList = low;
        
        // Goes through the list and sorts it
        for(int i = low + 1; i <= high; i++){
            if(list.get(i).compareTo(pivotValue) < 0){
                endOfLeftList++;
                swap(list, endOfLeftList, i);
            }
        }
        
        // Swaps the end of the left list and the lowest position
        swap(list, low, endOfLeftList);
        
        return endOfLeftList;
    }
    
    /**
     * This method will swap the lower and higher positions within a given list
     * 
     * @param list - The list to be sorted
     * @param first - The first position to be swapped
     * @param second - The second position to be swapped
     */
    public static void swap(ArrayList<String> list, int first, int second){
        String element = list.get(first); // Obtains the value of the first position in the list
        list.set(first, list.get(second)); // Replaces the first position in the list with the value of the second one
        list.set(second, element); // Replaces the second position in the list with the old value of the first one
    }
}
