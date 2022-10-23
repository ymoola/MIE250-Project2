import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Pro2_moolayus {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {
        int[][] cords = new int[20][2];
        int houseCount =0;
        int radius = 0; //declaring variables and initializing them

        int choice = 10;
        while (choice != 0){
            showMenu();  // while the user does not input 0, it will show the menu and start the while loop
            try {
                System.out.print("Enter choice: ");
                choice = Integer.parseInt(reader.readLine()); //reading user input
                if(choice < 0 || choice > 5)
                    throw new Exception();
            }
            catch (Exception e){
                System.out.println("\nERROR: Invalid menu choice!\n"); //try-catch statement to catch invalid user entries
                choice = 10;
            }

            if(choice == 1){
                houseCount = getHouseCoords(cords,houseCount); //if user input  = 1, getHouseCoords is called
            }
            else if(choice == 2){
                displayHouses(cords,houseCount);  //if user input  = 2, DisplayHouses is called 
            }
            else if(choice == 3){
                radius = setFireStationRadius(); //if user input  = 3, setFireStationRaius is called 
            }
            else if(choice == 4){
                assessFireStationLocations(radius, cords, houseCount);  //if user input  = 4, assessFireStationLocations is called 
            }
            else if(choice == 5){
                houseCount = 0; //sets house count to 0
                System.out.println("\nHouse location database cleared!\n"); //print statement to tell user that database is cleared
            }

        }

        System.out.println("\nAu revoir!\n"); //when the user inputs 0 in the menu, it will exit while loop and print this

    }

    public static void showMenu(){  // This is the menu function displaying all options for the user to input
        System.out.println("   JAVA FIRE STATION LOCATION PROGRAM\n" +
                "0 - Quit\n" +
                "1 - Enter location of houses\n" +
                "2 - Display house locations\n" +
                "3 - Set fire station radius\n" +
                "4 - Assess fire station locations\n" +
                "5 - Clear house locations\n");
    }

    public static int getHouseCoords(int[][] cords, int nHouses){
        System.out.println("\nEnter coordinates (0, 0)  to quit at any time.\n");
        int x=1, y=1; // initialize variables
        while ((x != 0 || y != 0) && nHouses < 21){ //While loop with the condition that x and y are not 0 and that number houses is less than 21

            x = getInteger("x-coordinate of house " + (nHouses+1) + ": ", 0,20); //to get x and y coordinates, getInteger is called and the bounds are set between 0 and 20
            y = getInteger("y-coordinate of house " + (nHouses+1) + ": ", 0,20);
            if(x != 0 || y != 0) {
                cords[nHouses][0] = x; //indexing array to access x and y coordinates
                cords[nHouses][1] = y;
                nHouses++;  //after each x and y pair of coordinates in entered, 1 more house is added to the count
                System.out.println();
            }

        }

        System.out.println();

        return  nHouses; //function returns the number of houses
    }

    public static void displayHouses(int [][] houseCoords, int nHouses){
        if(nHouses == 0){
            System.out.println("\nERROR: No houses entered!\n"); //is statement to print this statement when there are no houses in the database
            return;
        }
        System.out.println("\nHouse   X   Y"); 
        System.out.println("-------------"); //formatting display title
        for(int i=0;i<nHouses;i++){ //for loop starting with i = 0, where i can't exceed number of houses and i increases by 1 after each loop
            System.out.println(String.format("%2d%7d%4d",(i+1),houseCoords[i][0],houseCoords[i][1])); //formatting x and y values 
        }
        System.out.println();
    }

    public static int getInteger(String prompt, int LB, int UB){ //getInteger function
        boolean correct = false; //initializing variables
        int inp = 0;

        String lbMsg = String.valueOf(UB);
        String ubMsg = String.valueOf(LB);

        if(UB == Double.MAX_VALUE)
            ubMsg = "infinity";
        if(LB == Double.MAX_VALUE*-1)
            lbMsg = "-infinity";
     // if UB and LB are positive and negative max's of a double, it will print the above messages
        while (correct == false){
            try {
                System.out.print(prompt);
                inp = Integer.parseInt(reader.readLine()); //reads user input after prompt is printed
                if(inp >= LB && inp <= UB)
                    correct = true;
                else
                    throw new IllegalArgumentException();
            }
            catch (IllegalArgumentException e){

                System.out.println("ERROR: Input must be an integer in [" + LB +", " + UB + "]!\n");
            }
            catch (Exception e){
                System.out.println("ERROR: Input must be an integer in [" + LB +", " + UB + "]!\n"); //try catch statement to check if input is within bounds and if input is an integer
            }
        }
        return inp; //function returns input
    }

    public static void assessFireStationLocations(int r, int [][] houseCoords, int nHouses){
        if(nHouses == 0){ //checks if nHouses is 0, and prints this if true
            System.out.println("\nERROR: No houses entered!\n");
            return;
        }
        else
            System.out.println("\nEnter coordinates (0, 0)  to quit at any time.\n");
        int locationsTried = 0; //initializing variables

        int x=1, y=1;
        int bestX = 0 , bestY = 0;
        double bestDistance = Double.MAX_VALUE; //max value of a double
        while ((x != 0 && y != 0) && nHouses < 21){ //While loop with the condition that x and y are not 0 and that number houses is less than 20

            x = getInteger("x-coordinate of fire station: ", 0,20); //gets x and y coordinate of fire station
            y = getInteger("y-coordinate of fire station: ", 0,20);
            if(x != 0 && y != 0) {
                locationsTried++; //adds 1 to LocationsTried if statement is true
                double currentDistance;
                double totalDistance = 0;
                for(int i=0;i<nHouses;i++) //for loop starting with i = 0, where i can't exceed number of houses and i increases by 1 after each loop
                {
                    currentDistance =  Math.sqrt( Math.pow(houseCoords[i][0] - x,2) + Math.pow(houseCoords[i][1] - y,2)); //calculates distance between house and fire station using euclidean distance formula
                    if(currentDistance > r) //checks if the distance calculated is more than radius
                        totalDistance += currentDistance; //adds to total distance
                }
                System.out.println(String.format("Score of fire station at (%d, %d): %.2f", x, y, totalDistance));
                if(totalDistance < bestDistance){
                    bestDistance = totalDistance; //checks in total distance is less than best distance and if it is, it's value will go to best distance
                    bestX = x;
                    bestY = y;//getting best x and y coordinates

                }

                System.out.println();

            }
        }

        if(locationsTried > 0){
            System.out.println();
            System.out.println(locationsTried + " locations tried.");
            System.out.println(String.format("Best location: (%d, %d) with score %.2f.", bestX, bestY, bestDistance));
            System.out.println(); //if locations tried is more than zero, the following will be printed 
        }


    }

    static int setFireStationRadius(){
        int radius = 0; //initialize radius
        System.out.println();
        radius = getInteger("Enter fire station radius: ",0,30); //sets radius bounds and calls getInteger function
        System.out.println();
        return radius; //returns radius


    }

}
