# A*-Algorithm
A sample implementation of the A* search algorithm to find the shortest way in a maze-like area. This project is part of a course at the DHBW Stuttgart. 

#### Compile Java files

First of all it is necessary to compile all Java files (create directory `bin` if it is not existing):

`javac -d bin src/de/dhbw/*java src/de/dhbw/astar/*java src/de/dhbw/datareader/*java src/de/dhbw/exceptions/*java src/de/dhbw/model/*java`

#### Start the Program

To start the program it is necessary to pass the path to the description matrix as well as the coordinates of the start node and the terminal nodes as arguments to the program. For example the command

`java -cp bin de/dhbw/AStarAlgorithmApplication resources/S_011_Daten.csv 10 5 4 9`

searches the shortes path from (10, 5) to (4, 9) in the territory map of the file resources/S_011_Daten.csv.