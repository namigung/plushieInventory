
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.HashMap;
/*
 *
 * @author naomigong
 */
//This clas opreates the view inventory button
@WebServlet("/viewinventory")
public class viewinventory extends HttpServlet{
    
    //file instance
    final File inventory = new File ("/Users/naomigong/NetBeansProjects/plushieInventory/src/java/inventory.txt");
    HashMap<String, String> plushies = new HashMap<String,String>();
    
    //counts the total line numbers in the file 
    public int countLineNum(File inventory) throws IOException{
        int lines = 0;
  
        BufferedReader reader = new BufferedReader(new FileReader(inventory));
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }
    
    //returns the number of total plushies
    public int totalPlushies(File inventory) throws IOException{
        
        int totalNumPlushies = 0;
        
        Scanner countScnr = new Scanner(inventory);
        
        
        while(countScnr.hasNextLine()){
           String lineElement = countScnr.nextLine();
           Boolean nameOrNum = Character.isDigit(lineElement.charAt(0));
            if(!nameOrNum){
            }
            else{
                totalNumPlushies += Integer.parseInt(lineElement);
            }     
        
        }
        countScnr.close();
        return totalNumPlushies;
    }
   
    
    //loads it into the hashmap (used for properties and adding and removing)
    public void loadInventory(File inventory) throws FileNotFoundException{
        Scanner scnr = new Scanner(inventory);
        while(scnr.hasNextLine()){
            String plushieName = scnr.nextLine();
            String plushieCount = scnr.nextLine();
            plushies.put(plushieName, plushieCount);  
        }
        scnr.close();
 
    }
    
    //This method takes in the name of the plushie exactly it is from the file
    //and returns it in its jpeg form
    public static String getImage(String plushieName){
        String imageName = plushieName.replaceAll("\\s","");
        int indexOfColon = imageName.indexOf(":");
        imageName = imageName.substring(0, indexOfColon);
        imageName = imageName + ".jpeg";
        return imageName; 
    }
    
    //processes the plushie name and count ot retrieve (this is for hover effect)
    int fileIterator = 1;
    //the name of the plushie is at first empty
    String plushieNameAndInv = "";
    public String getPlushieNameAndInv() throws IOException{
       //Everytime this method its called it resets the name of the plushie and its inventory
       plushieNameAndInv = "";
       BufferedReader reader = new BufferedReader(new FileReader(inventory));
       for (int i = 1; i < countLineNum(inventory); i++){
           if (i == fileIterator){
               plushieNameAndInv += reader.readLine() + ": ";
               plushieNameAndInv += reader.readLine();   
           }
           else{
               reader.readLine();
           }
      }
       fileIterator += 2;
       reader.close();
       //String plushieName = reader.readLine()
       return plushieNameAndInv;
    }
    
    //frontend Code
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        //GOAL: Display pictures of plushies in rows 
      
       // get response writer
        PrintWriter writer = res.getWriter();
        writer.println("<!DOCTYPE html>");
       //links to bootstrap for css
        writer.println("<link rel = 'stylesheet' href = 'css/bootstrap.css'></link>");
        res.setContentType("text/html");
       //loads inventory into hashmap
        loadInventory(inventory);
        int totalNumPlushies = totalPlushies(inventory);
        
        
       
        //css styling
        writer.println("<style>");
        writer.println(".column{ width: 33.33%; padding: 5px;}"); 
        writer.println(".row::after {display: flex;}");
        writer.println("h2{ color: blue;}");
        writer.println("body, html{ margin-left: 2%; margin-right: 2%, margin-botton: 2%; margin-top: 1%}");
        writer.println("</style>");
        
        
         // build HTML for title and properties
        writer.println("<html><h2>All Plushies</h2></html>");
        //returns plushie properties
        writer.println("<html><p>"+ "The total number of plushies is " + totalNumPlushies+ "</p></html>");
        //returns the total types of plushies
        int totalTypePlushies = countLineNum(inventory)/2;
        writer.println("<html><p>"+ "The total number of types of  plushies is " + totalTypePlushies+ "</p><hr></html>");
        //creates button
        String htmlRespone = "<html><a href ='index.html'><button>go back</button></a></html>";
        writer.println(htmlRespone);
        
 
        //returns pictures of plushies
        writer.println("<html>"
                + "<div class = 'row'>"
                +   "<div class = 'column'>"
                +        "<div class = 'image-overlay'> </div>"
                +           "<div class = 'image-title'>" + getPlushieNameAndInv() + "</div>"
                +           "<img src = " + "\'" + getImage(plushieNameAndInv) + "\'" +  "style='width: 100%'>"
                +   "</div> "
                +  "<div class = 'column'>"
                +      "<div class = 'image=overlay'></div>"
                +            "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +           "<img src =" + "\'" + getImage(plushieNameAndInv) + "\'" +  " style = 'width: 100%'>"
                +   "</div>"
                +  "<div class = 'column'>"
                +        "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +       "<img src =" + "\'"+ getImage(plushieNameAndInv) + "\'" + " style = 'width: 100%'>"
                +  "</div>"
                + "</div>"
                + "<div class = 'row'>"
                +   "<div class = 'column'>"
                +        "<div class = 'image-overlay'> </div>"
                +           "<div class = 'image-title'>" + getPlushieNameAndInv() + "</div>"
                +           "<img src = " + "\'" + getImage(plushieNameAndInv) + "\'" +  "style='width: 100%'>"
                +   "</div> "
                +  "<div class = 'column'>"
                +      "<div class = 'image=overlay'></div>"
                +            "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +           "<img src =" + "\'" + getImage(plushieNameAndInv) + "\'" +  " style = 'width: 100%'>"
                +   "</div>"
                +  "<div class = 'column'>"
                +        "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +       "<img src =" + "\'"+ getImage(plushieNameAndInv) + "\'" + " style = 'width: 100%'>"
                +  "</div>"
                + "</div>"
                + "<div class = 'row'>"
                +   "<div class = 'column'>"
                +        "<div class = 'image-overlay'> </div>"
                +           "<div class = 'image-title'>" + getPlushieNameAndInv() + "</div>"
                +           "<img src = " + "\'" + getImage(plushieNameAndInv) + "\'" +  "style='width: 100%'>"
                +   "</div> "
                +  "<div class = 'column'>"
                +      "<div class = 'image=overlay'></div>"
                +            "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +           "<img src =" + "\'" + getImage(plushieNameAndInv) + "\'" +  " style = 'width: 100%'>"
                +   "</div>"
                +  "<div class = 'column'>"
                +        "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +       "<img src =" + "\'"+ getImage(plushieNameAndInv) + "\'" + " style = 'width: 100%'>"
                +  "</div>"
                + "</div>"
                + "<div class = 'row'>"
                +   "<div class = 'column'>"
                +        "<div class = 'image-overlay'> </div>"
                +           "<div class = 'image-title'>" + getPlushieNameAndInv() + "</div>"
                +           "<img src = " + "\'" + getImage(plushieNameAndInv) + "\'" +  "style='width: 100%'>"
                +   "</div> "
                +  "<div class = 'column'>"
                +      "<div class = 'image=overlay'></div>"
                +            "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +           "<img src =" + "\'" + getImage(plushieNameAndInv) + "\'" +  " style = 'width: 100%'>"
                +   "</div>"
                +  "<div class = 'column'>"
                +        "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +       "<img src =" + "\'"+ getImage(plushieNameAndInv) + "\'" + " style = 'width: 100%'>"
                +  "</div>"
                + "</div>"
                +"<div class = 'row'>"
                +   "<div class = 'column'>"
                +        "<div class = 'image-overlay'> </div>"
                +           "<div class = 'image-title'>" + getPlushieNameAndInv() + "</div>"
                +           "<img src = " + "\'" + getImage(plushieNameAndInv) + "\'" +  "style='width: 100%'>"
                +   "</div> "
                +  "<div class = 'column'>"
                +      "<div class = 'image=overlay'></div>"
                +            "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +           "<img src =" + "\'" + getImage(plushieNameAndInv) + "\'" +  " style = 'width: 100%'>"
                +   "</div>"
                +  "<div class = 'column'>"
                +        "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +       "<img src =" + "\'"+ getImage(plushieNameAndInv) + "\'" + " style = 'width: 100%'>"
                +  "</div>"
                + "</div>"
                + "<div class = 'row'>"
                +   "<div class = 'column'>"
                +        "<div class = 'image-overlay'> </div>"
                +           "<div class = 'image-title'>" + getPlushieNameAndInv() + "</div>"
                +           "<img src = " + "\'" + getImage(plushieNameAndInv) + "\'" +  "style='width: 100%'>"
                +   "</div> "
                +  "<div class = 'column'>"
                +      "<div class = 'image=overlay'></div>"
                +            "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +           "<img src =" + "\'" + getImage(plushieNameAndInv) + "\'" +  " style = 'width: 100%'>"
                +   "</div>"
                +  "<div class = 'column'>"
                +        "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +       "<img src =" + "\'"+ getImage(plushieNameAndInv) + "\'" + " style = 'width: 100%'>"
                +  "</div>"
                + "</div>"
                + "<div class = 'row'>"
                +   "<div class = 'column'>"
                +        "<div class = 'image-overlay'> </div>"
                +           "<div class = 'image-title'>" + getPlushieNameAndInv() + "</div>"
                +           "<img src = " + "\'" + getImage(plushieNameAndInv) + "\'" +  "style='width: 100%'>"
                +   "</div> "
                +  "<div class = 'column'>"
                +      "<div class = 'image=overlay'></div>"
                +            "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +           "<img src =" + "\'" + getImage(plushieNameAndInv) + "\'" +  " style = 'width: 100%'>"
                +   "</div>"
                +  "<div class = 'column'>"
                +        "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +       "<img src =" + "\'"+ getImage(plushieNameAndInv) + "\'" + " style = 'width: 100%'>"
                +  "</div>"
                + "</div>"
                + "<div class = 'row'>"
                +   "<div class = 'column'>"
                +        "<div class = 'image-overlay'> </div>"
                +           "<div class = 'image-title'>" + getPlushieNameAndInv() + "</div>"
                +           "<img src = " + "\'" + getImage(plushieNameAndInv) + "\'" +  "style='width: 100%'>"
                +   "</div> "
                +  "<div class = 'column'>"
                +      "<div class = 'image=overlay'></div>"
                +            "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +           "<img src =" + "\'" + getImage(plushieNameAndInv) + "\'" +  " style = 'width: 100%'>"
                +   "</div>"
                +  "<div class = 'column'>"
                +        "<div class = 'image-title'>" + getPlushieNameAndInv()+ "</div>"
                +       "<img src =" + "\'"+ getImage(plushieNameAndInv) + "\'" + " style = 'width: 100%'>"
                +  "</div>"
                + "</div>"
                + "</html>");
        
        /*
        //print out plushies into a list
        for (String i : plushies.keySet()) {
        writer.println("<html><p>" + i + " " +  plushies.get(i)+ "<p></html>");
        }
        */
      
        
        //resets the iterator for when you run application again
        fileIterator = 1; 
   
    }
      
}  