#Chess

##Description

Implemented the functionality of a game of chess in Java as my CIS120 (Programming Languages and Techniques) 
final project.

The goal of the project was to apply concepts like 2D Arrays, subtypes of the Collections
interface (like LinkedLists), and inheritance. 

2D Arrays were a natural way to represent the 8x8 board of a game of chess. The board is populated with Objects
representing the different pieces, and if a cell is empty, then the 2D array contains null at that entry.

I used a LinkedList to implement the "undo" button functionality. For each move, a game object is added to this list,
where each game object contains information such as the pieces in the board, whether the king is checked or not,
and whose player turn is it.

The concept of inheritance is easy to apply in this kind of game: there is a Piece class that has some properties all
chess pieces have, and then there are subtypes of this class (like the King class) that extend it and implement methods
that allow the piece to behave in the particular way it should. For instance, the move() method is 
implemented differently depending on what class of piece you are (Bishops move diagonaly, Rooks horizontally/vertically,
Knights can jump over pieces...).

##Classes overview

RunChess.java. - The class that runs the game.

ChessGameBoard. - This is the visual element of the game. It listens to mouse-clicks, translates the pixels to
coordinates in the 2d array, identifies the selected piece, and calls the move method of the piece using the
translated coordinates. It also toggles the value of "whitePlays" in the state if a move was successful.

ChessGame. - The class that stores most of the state of the game. Some of its instance fields include a checkmate boolean,
an int[][] board, and an undoList (of type LinkedList).

Piece. - As described above, this is the father class of classes like Pawn, Knight, Rook... it contains the more
abstract properties of a chess piece.

Pawn, Knight, Bishop, Rook, Queen, King. - Each of these are children of Piece. They contain the more specific
properties of each piece, like how they can move and capture.

##Design evaluation / Things to improve

There are some instance fields that I left as public. If I could go back, I would change them to private and make
appropriate getter and setter methods.

I would also strive to make more reusable code. Towards the end of the project I realized that there were 
some aspects of certain move() methods that were similar enough that a helper function,
probably implemented in the Piece class, that could be used across the different methods would have been a good 
design choice.

Finally, I would make my code more modular and easier to read by breaking down some large methods into one method
that uses helper functions.

##Installation

To run the project, clone the repository and run the Game class in src/main/java/org.cis120

##External Resources
I used images from freepnglogos for the chess pieces: https://www.freepnglogos.com/pics/chess


