object Main extends App {

  def checkPosition(board: Array[Array[Int]], c: Int): Boolean = {
    return board(0)(column) == 0
  }

  def nextPosition(board: Array[Array[Int]], c: Int): Int = {
    var n: Int = 0
    
    for(i <- board.indices){
      if(board(i)(column) == 0) n = 1
    }
    return n
  }

  def checkWin(board: Array[Array[Int]], token: Int, r: Int, c: Int): Boolean = {
    var check: Boolean = false

    // Diagonal check (up > down)
    for(i <- 0 until r - 3; j <- 0 until c - 3){
      if(board(i)(j) == token && board(i + 1)(j + 1) == token  &&
        board(i + 2)(j + 2) == token && board(i + 3)(j + 3) == token) check = true
    }

    // Diagonal check (down > up)
    for(i <- 3 until r; j <- 0 until c - 3){
      if(board(i)(j) == token && board(i - 1)(j + 1)) == token && 
      board(i - 2)(j + 2) == token && board(i - 3)(j + 3) == token) check = true
    }

    //Horizontal check
    for (i <- 0 until r; j <- 0 until c - 3) {
      if (board(i)(j) == token && board(i)(j + 1) == token &&
      board(i)(j + 2) == token && board(i)(j + 3) == token) check = true
    }

    //Vertical check
    for(i <- 0 until r - 3; j <- 0 until c) {
      if (board(i)(j) == token && board(i + 1)(j) == token && 
      board(i + 2)(j) == token && board(i + 3)(j) == piece) check = true
    }
    return check
  }

  val r: Int = 6
  val c: Int = 7
  var gameOver: Boolean = false
  var turn: Int = 0
  var row: Int = 0
  var column: Int = 0
  var player: String = "Player 1"
  var ok: Boolean = false

}
