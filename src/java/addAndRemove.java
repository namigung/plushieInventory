
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author naomigong
 */
@WebServlet("/addAndRemove")
public class addAndRemove extends viewinventory {
    
    //This method updates the file after each add or removed plushie(s)
     public void updateFile(HashMap plushies, HttpServletResponse res) throws IOException{
        BufferedWriter bf = null;
        PrintWriter writer = res.getWriter();
        try{
            bf = new BufferedWriter(new FileWriter(inventory));
            //declares iterator
            Iterator hmIterator = plushies.entrySet().iterator();
            int start = 0;
            //1 gets iterator
            //.hasNEext() iteraters through hash map
            while (hmIterator.hasNext()) {
                Map.Entry<String,String> plushieElement = (Map.Entry<String,String>)hmIterator.next();
                //only prints to next line if its not the first of the file
                if (start != 0)
                    bf.newLine();
                
                bf.write(plushieElement.getKey());
                bf.newLine();
                bf.write(plushieElement.getValue());
                start++;
            }
            
            //clears data and pushes the data from the iterator to the file 
            bf.flush();
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                //closers the writer
                bf.close();
            }
            catch(Exception e){
                
            }
        }
    }
     
    //write method that retrieves the alt. 

    //This method executes the form 
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        
        loadInventory(inventory);
        PrintWriter writer = res.getWriter();
        res.setContentType("text/html");
        //get Values
        String plushieName = req.getParameter("PlushieName");
        String plushieCount = req.getParameter("PlushieCount");
        String action = req.getParameter("addOrRemove");
       
        
        //searchArray for plushie --> if not found, have user return
        if (plushies.containsKey(plushieName)){
            //finds the current number of specific plushie
            int currentCount = Integer.parseInt(plushies.get(plushieName));
            String stringCurrentCount;
            //removes plushie from inventory
            if (action.equals("removed")){
                //int and string conversion
                currentCount -= Integer.parseInt(plushieCount); 
                stringCurrentCount = Integer.toString(currentCount);
                //updates the hashmap
                plushies.replace(plushieName, stringCurrentCount);
            }
            //adds plushie to inventory
            else if (action.equals("added")){
                currentCount += Integer.parseInt(plushieCount); 
                stringCurrentCount = Integer.toString(currentCount);
                //updates the hashmap
                plushies.replace(plushieName, stringCurrentCount);
                
            }
            //loads into file 
            updateFile(plushies, res);
            String completedMessage = "<html><p>" + plushieCount + " " +  plushieName + "was successfully " + action + "</p></html>";
            String goBackRes = "<html><a href ='index.html'><button>Home Page</button></a></html>";
            writer.println(completedMessage);
            writer.println(goBackRes);
        }
        //Prints this and goes back if plushie isnt in inventory
        else{
            String errorMessage = "<html><p>We could not find this plushie. Please check inventory to see if the plushie exists or check to see if you typed it in correctly </p></html>";
            String goBackRes = "<html>"
                    + "<a href ='index.html'><button>go back</button></a>"
                    + "</html>";
            writer.println(errorMessage);
            writer.println(goBackRes);
            
        }
        
    }
   
}
