=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: edgoze
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays. They are a natural way to represent the 8x8 board of a game of chess. If there's something on
  that square, then there is an object in the array. Otherwise, the element of the array is null. It also makes it easy
  to implement the "move" methods of each piece, since moving simply involves changing the positions of things in the
  2D array. The reason why it's an array of objects (specifically of ChessGameObj) is because that's an accurate
  representation of what a chess game would look like in real life, and because that way I can easily call the methods
  of the different pieces in the board.
  No feedback was given on this, concept was fully approved.

  2. Collections. Feedback was given on this concept: initially, I was thinking about using a hashmap for the undo
  function. However, not only was this not the best data structure for the purpose, but hashmaps were not allowed.
  Thus, it was decided to use a LinkedList instead. This was a good pick for something like an undo button because
  I can add as many game objects as I want to the list, and then I just pop them from the end of the list as the user
  presses the undo button. It makes sense to have ChessGame objects as the content of the linked list because I need to
  store all the information of the game (whether a king was in check or not, who's turn it is...), not only the board.

  3. Inheritance. - No feedback was given on this, concept was approved. I have a parent class called Piece. It has the
  more abstract properties of a piece like its color, row and column, piece number... It also outlines an interface
  for children to implement methods like .move(), which are specific to each piece. It is worthwhile to do
  inheritance because pieces share some properties and methods, like .draw() or .treasonChecker() or .saveGame(), and
  yet the pieces can differ a lot in terms of how they move or capture or the special moves each can do (like rooks
  and castling).

  4. No feedback was given on this, concept was fully approved. I implemented the basics of chess like not allowing
  illegal moves to happen, correct movement of each piece, checkmate, castling, and forcing players to resolve check
  before moving.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

    RunChess.java. - This one simply runs the game, similar to RunTicTacToe or RunMushroomOfDoom. The one thing
    to note is that that's where I added the undo button.
    ChessGameBoard. - This is the visual element of the game. It listens to mouseclicks, translates the pixels to
    coordinates in the 2d array, identifies the selected piece, and calls the move method of the piece using the
    translated coordinates. It also toggles the value of "whitePlays" in the state if a move was successful.
    ChessGame. - The state of the game. It's instances:

        public Piece[][] board;
        public boolean whitePlays = true;
        public Piece kingInCheck = null;
        public Piece pieceChecking = null;
        public LinkedList<ChessGame> undoList = new LinkedList<>();
        public boolean checkMate = false;
        public boolean whiteKingHasMoved = false;
        public boolean blackKingHasMoved = false;
        public int[] xDrawingPositions = {6,56,106,156,206,256,306,356};
        public int[] yDrawingPositions = {10,60,110,160,210,260,310,360};

    represent important things to keep track of in the game. Their name is a clear indicator of what they track. For
    instance, kingInCheck is null until a king is checked, and pieceChecking takes the value of the piece doing the
    check.
    Piece. - As described above, this is the father class of classes like Pawn, Knight, Rook... it contains the more
    abstract properties of a chess piece.
    Pawn, Knight, Bishop, Rook, Queen, King. - Each of these are children of Piece. They contain the more specific
    properties of each piece, like how the can move and capture.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  Not really, it was just extremely time-consuming. I had to do the usual debugging here and there, but what made
  this project challenging was how many things you have to take into account in chess. There are many small rules,
  and you have to check for many things on every turn, so implementing that was challenging. I think my approach
  to the game was correct, I just faced the usual stumbling blocks in the form of small bugs that went undetected
  and took a while to fix. I think it really helped that I went to OH early on solely to ask conceptual questions.
  That allowed me to have a clear roadmap of what I had to do, and then it was just a matter of coding it down.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

    I would probably go back and change many instance fields to private. Doing this game was very time-consuming,
    so I ended up leaving them as public because style was not being graded and I needed to prioritize tasks.

    I would also do some methods for code that is very similar within each class. For instance, the code to check
    for correct movement in some classes is very similar, so I would go back and think about how to generalize it
    and hence create a helper method.

    I think there is a good separation of functionality. It is possible to play the game solely from the ChessGame
    class by invoking move methods on the pieces. All that ChessGameBoard.java does is provide visual cues as to what's
    happening in the state of the program.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  I used images from freepnglogos for the chess pieces.
  https://www.freepnglogos.com/pics/chess
