
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
import java.util.LinkedHashMap;
import java.util.Map;
/*
 *
 * @author naomigong
 */
//This clas opreates the view inventory button
@WebServlet("/viewinventory")
public class viewinventory extends HttpServlet{
    
    //file instance
    final File inventory = new File ("/Users/naomigong/NetBeansProjects/plushieInventory/src/java/inventory.txt");
    LinkedHashMap<String, String> plushies = new LinkedHashMap<String,String>();
    
    
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
    //this variable puts it in the correct section (only reading every other 2 starting at 1)
    int fileIterator = 1;
    //the name of the plushie is at first empty
    String plushieNameAndInv = "";
    public String getPlushieNameAndInv() throws IOException{
       //Everytime this method its called it resets the name of the plushie and its inventory
       plushieNameAndInv = "";
       BufferedReader reader = new BufferedReader(new FileReader(inventory));
       for (int i = 1; i < countLineNum(inventory); i++){
           if (i == fileIterator){
               //reads the name of plushies
               plushieNameAndInv += reader.readLine() + ": ";
               //reads the amount of that plushie in inventory
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
    
    //ADD FUNCTION THAT RETURNS TOP 3 PLUSHIES WITH MOST AMOUNT IN AN ARRAY
    public String[][] top3Plushies(){
        String[][] top3 = new String[3][2];
        //begin by setting the first and second max to really high numbers -- not possible number of plushies
        int firstMax = 100000;
        int secondMax = 10000;
        int tempMax = 0;
        String tempMaxPlush = "";
        for (int row = 0; row < top3.length; row++){
            for (int col =0; col < top3[0].length; col++){
                for (String i: plushies.keySet()){
                    int inv = Integer.parseInt(plushies.get(i));
                    //the inventory must be bigger than tempp max but less tthan the firstMax or Second to find all top 3
                    if (inv > tempMax && inv < firstMax && inv  < secondMax ){
                       tempMax = inv; 
                       tempMaxPlush = i;
                    }
                 } 
            }
            top3[row][0] = tempMaxPlush;
            top3[row][1] = Integer.toString(tempMax);
            if (row == 0){
                firstMax = tempMax;
            }
            if (row == 1){
                secondMax = tempMax;
            }
            //resets to find the next biggest
            tempMax = 0;
        }
        return top3;
    }
    
    
    //Method prints out a row
    public String printRow() throws IOException{
        return "<html>"
                + "<div class = 'row'>"
                +   "<div class = 'column'>"
                +       "<div class = container>"
                +        "<div class = 'image-overlay'>"
                +           "<div class = 'image-title'><center>" + getPlushieNameAndInv() + "</center></div>"
                +           "</div>"
                +           "<img src = " + "\'" + getImage(plushieNameAndInv) + "\'" + "class = 'image'" + "style='width: 100%'>"
                +      "</div>"
                +   "</div> "
                +  "<div class = 'column'>"
                +      "<div class = container>"
                +      "<div class = 'image-overlay'>"
                +            "<div class = 'image-title'><center>" + getPlushieNameAndInv()+ "</center></div>"
                +           "</div>"
                +           "<img src =" + "\'" + getImage(plushieNameAndInv) + "\'" +  "class = 'image'" +  "style = 'width: 100%'>"
                +      "</div>"
                +   "</div>"
                +  "<div class = 'column'>"
                +       "<div class = container>"
                +       "<div class = 'image-overlay'>"
                +        "<div class = 'image-title'><center>" + getPlushieNameAndInv()+ "</center></div>"
                +          "</div>"
                +       "<img src =" + "\'"+ getImage(plushieNameAndInv) + "\'" + "class = 'image'" + " style = 'width: 100'>"
                +   "</div>"
                +  "</div>"
                + "</div>"
                + "</html>";
    }
    
    //prints the last row
    public String lastRowString() throws IOException{
    String lastRow = "<div class = 'column'>"
                +   "<div class = container>"
                +     "<div class = 'image-overlay'>"
                +        "<div class = 'image-title'><center>" + getPlushieNameAndInv()+ "</center></div>"
                +         "</div>"
                +       "<img src =" + "\'"+ getImage(plushieNameAndInv) + "\'" + "class = 'image'" +" style = 'width: 100%'>"
                +  "</div>"
                +  "</div>";
    
    return lastRow;
    
    }
    
    //frontend Code
    int rows = 0;
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
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
        //styles the row of images
        writer.println(".column{ width: 33.33%; padding: 10px;}"); 
        writer.println(".row::after {display: flex;}");
        //hover effect
        writer.println(".container{position:relative;}");
        writer.println(".container:hover .image-overlay{opacity: 1;}");
        writer.println(".image{ display: block; width: 100%; height: auto; border: 3px solid black}");
        writer.println(".image-overlay{position: absolute; top:0; buttom:0;  left:0; right:0; height:100%; opacity: 0; width:100%; transition:.5s ease; background-color: rgb(254 205 211);}");
        writer.println(".image-title{ color: black; font-size: 20px; position: absolute; top: 50%; left:50%; transform: translate(-50%, -50%); -ms-transform: translate(-50%, -50%);}");
        //general styling
        writer.println("h2{ color: rgb(159 18 57);}");
        writer.println("body, html{ margin-left: 2%; margin-right: 2%, margin-botton: 2%; margin-top: 1%; background-color: rgb(254 252 232);}");
        writer.println("</style>");
        
        
         // build HTML for title and properties
        writer.println("<html><h2>All Plushies</h2></html>");
        //returns plushie properties
        writer.println("<html><p>"+ "The total number of plushies is " + totalNumPlushies+ "</p></html>");
        //prints the total types of plushies
        int totalTypePlushies = countLineNum(inventory)/2;
        writer.println("<html><p>"+ "The total number of types of  plushies is " + totalTypePlushies+ "</p></html>");
        //prints top 3 plushies
        
        writer.println("<html><p>The plushies with the most amount in the inventory are: </p></html>");
        String[][] top3plushies = top3Plushies();
        for (int i = 0; i < top3plushies.length; i++){
            writer.println("<html>" + (i + 1) + ". " + "</html>");
            for (int j = 0; j < top3plushies[0].length; j++){
                writer.println("<html>" + top3plushies[i][j] +"</html>" );
            }
            writer.println("<html><p></p></html>");
        }
        
        writer.println("<html><p><hr></p></html>");
        //creates button
        String htmlRespone = "<html><a href ='index.html'><button>go back</button></a></html>";
        writer.println(htmlRespone);
       
        
        //returns pictures of plushies
        int tempTotalTypePlushies = totalTypePlushies;
        int row = totalTypePlushies/3;
        for (int i = 1; i <=  row ; i++){
            //this tells you how many type of plushies will be in the last row
            tempTotalTypePlushies -= 3;
            writer.println(printRow());
            //for last row
            if (i == row && tempTotalTypePlushies != 0){
            String lastRow = lastRowString();
               writer.println(
             "<html>"
             + "<div class = 'row'>"
             + lastRow.repeat(tempTotalTypePlushies)
             + "</div></html>"); 
           
            }
            
        }
       
        //resets the iterator for when you run application again
        fileIterator = 1; 
    }
      
}  