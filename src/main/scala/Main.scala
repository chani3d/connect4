import hevs.graphics.FunGraphics
import java.awt.Color
import java.awt.event.{KeyAdapter, KeyEvent}

object Main extends App {

  // Checks if the position is taken or not
  def checkPosition(board: Array[Array[Int]], column: Int): Boolean = {
    return board(0)(column) == 0
  }
  
  // Selects the next position available
  def nextPosition(board: Array[Array[Int]], column: Int): Int = {
    var n: Int = 0
    for (i <- board.indices) {
      if (board(i)(column) == 0) {
        n = i
      }
    }
    return n
  }
  // Checks if the player has won (4 different checkings)
  def checkWin(board: Array[Array[Int]], token: Int, r: Int, c: Int): Boolean = {
    var check: Boolean = false

    // Diagonal check (up > down)
    for(i <- 0 until r - 3; j <- 0 until c - 3){
      if(board(i)(j) == token && board(i + 1)(j + 1) == token  &&
        board(i + 2)(j + 2) == token && board(i + 3)(j + 3) == token) check = true
    }

    // Diagonal check (down > up)
    for(i <- 3 until r; j <- 0 until c - 3){
      if(board(i)(j) == token && board(i - 1)(j + 1) == token && 
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
      board(i + 2)(j) == token && board(i + 3)(j) == token) check = true
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
  var ok: Boolean = false // To avoid keyboard problems with the keyManager of FunGraphics
  val w: FunGraphics = new FunGraphics(700, 700, "Connect 4, 2024 by UK & SJCG - ISC")
  var board: Array[Array[Int]] = Array.empty
  var boardStatus: Boolean = false // Determines whether the board is empty or not
  var started: Boolean = false
  var interface: GUI = new GUI

  // Sound variables
  val mainMusic: Audio = new Audio("/res/wav/music/Shostakovich _waltz2_8bit.wav")
  val winEffect: Audio = new Audio("/res/wav/effects/win.wav")
  val tokenEffect: Audio = new Audio("/res/wav/effects/token.wav")
  val tokenEffect2: Audio = new Audio("/res/wav/effects/token2.wav")

  mainMusic.play()
  interface.textTitle(w,player)
  interface.textPressStart(w, 150, 400)

  // Keyboard control
  w.setKeyManager(new KeyAdapter(){
    override def keyPressed(e: KeyEvent): Unit = {
      ok = true
      if(e.getKeyChar == '1' && boardStatus) column = 0
      else if (e.getKeyChar == '2' && boardStatus) column = 1
      else if (e.getKeyChar == '3' && boardStatus) column = 2
      else if (e.getKeyChar == '4' && boardStatus) column = 3
      else if (e.getKeyChar == '5' && boardStatus) column = 4
      else if (e.getKeyChar == '6' && boardStatus) column = 5
      else if (e.getKeyChar == '7' && boardStatus) column = 6
      else if (e.getKeyChar == 's' && !started) {
        started = true
        gameOver = false
        board = interface.createBoard(r, c, w)
        boardStatus = true
        interface.textPlayer(w, player)
        ok = false
      }
      else if(e.getKeyChar == 'r' && started){
        turn = 0
        gameOver = false
        player = "Player 1"
        board = interface.createBoard(r, c, w)
        boardStatus = true
        interface.textPlayer(w, player)
        ok = false
      }
      else ok = false

      // Start of the game
      if (ok && !gameOver){
        if(turn % 2 == 0){
          ok = false

          // Player 1 turn
          if(checkPosition(board, column)){
            row = nextPosition(board, column)
            tokenEffect.play()
            interface.dropToken(board, row, column, 1, (column * 100) + 5, (row * 100) + 105, Color.yellow, w)
            player = "Player 2"
            interface.textPlayer(w, player)
            interface.textTurn(w, turn)
            interface.textPressRestart(w)

            if(checkWin(board, 1, r, c)){
              winEffect.play()
              player = "Player 1"
              interface.textWin(w, s"$player wins !")
              gameOver = true
            }
          }
        }
        else if(turn == 41){
          gameOver = true
          interface.textTie(w)
        }
        else {
          // Player 2 turn
          ok = false

          if(checkPosition(board, column)){
            row = nextPosition(board, column)
            tokenEffect2.play()
            interface.dropToken(board, row, column, 2, (column * 100) + 5, (row * 100) + 105, Color.red, w)
            player = "Player 1"
            interface.textPlayer(w, player)
            interface.textTurn(w, turn)

            if(checkWin(board, 2, r, c)){
              winEffect.play()
              player = "Player 2"
              interface.textWin(w, s"$player wins !")
              gameOver = true
            }
          }
        }
        if(!gameOver){
          turn += 1
        }
      }
    }
  })
}
