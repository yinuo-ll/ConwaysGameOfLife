import javafx.application.Platform
import java.util.*


class Model {
    // represent toolbar button selected
    var manualMode = false
    var drawEnabled = false
    var buttonSelected = ""
    var gridClear = false
    var updateGrid = false
    var position = Pair(0,0)

    // represent my board
    private val sizeOuter = 50
    private val sizeInner = 75

    private val views = ArrayList<IView>()
    private val board = Array(sizeOuter) { BooleanArray(sizeInner) }
    private val currBoard = Array(sizeOuter) { BooleanArray(sizeInner) }

    var timer = Timer()
    var task = object : TimerTask() {
        override fun run() {
            Platform.runLater {
                animateGrid()
            }
        }
    }
    var frameCount = 0

    // view management
    fun addView(view: IView) {
        views.add(view)
    }

    fun notifyView() {
        for (view in views) {
            view.update(board)
        }
    }

    fun nextFrame(){
        if(manualMode) {
            animateGrid()
        }
    }

    fun manualModeSwitch(){
        if(manualMode){ //switch off
            manualMode = false
            task = object : TimerTask() {
                override fun run() {
                    Platform.runLater {
                        animateGrid()
                    }
                }
            }
            timer.scheduleAtFixedRate(task, 0, 1000)
        }
        else { //switch on
            manualMode = true
            task.cancel()
        }
        notifyView()
    }

    fun updateButtonSelection(name: String){
        drawEnabled = true
        updateGrid = false
        buttonSelected = name
        notifyView()
    }

    fun clearGrid(){
        drawEnabled = false
        updateGrid = true
        gridClear = true
        for (row in 0..49) {
            for (column in 0..74) {
                board[row][column] = false
            }
        }
        notifyView()
    }

    fun selectCell(row: Int, column: Int){
       if(drawEnabled){
           drawEnabled = false
           gridClear = false
           updateGrid = true
           position = Pair(row + 1, column + 1)

           // Note: may not have been the best way to handle drawing along the border,
           //       but I wanted to support drawing up until the edge, then removing cells once
           //       they exit the visible grid
           if (buttonSelected == "Block") {
               board[row][column] = true
               try{board[row][column + 1] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 1][column] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 1][column + 1] = true} catch (e: ArrayIndexOutOfBoundsException){}
           } else if (buttonSelected == "Beehive") {
               try{board[row][column + 1] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row][column + 2] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 1][column] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 1][column + 3] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 2][column + 1] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 2][column + 2] = true} catch (e: ArrayIndexOutOfBoundsException){}
           } else if (buttonSelected == "Blinker") {
               try{board[row][column + 1] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 1][column + 1] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 2][column + 1] = true}  catch (e: ArrayIndexOutOfBoundsException){}
           } else if (buttonSelected == "Toad") {
               try{board[row][column + 1] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row][column + 2] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row][column + 3] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 1][column] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 1][column + 1] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 1][column + 2] = true} catch (e: ArrayIndexOutOfBoundsException){}
           } else if (buttonSelected == "Glider") {
               try{board[row][column + 2] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 1][column] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 1][column + 2] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 2][column + 1] = true} catch (e: ArrayIndexOutOfBoundsException){}
               try{board[row + 2][column + 2] = true} catch (e: ArrayIndexOutOfBoundsException){}
           }
           notifyView()
       }
    }

    fun countNeighbours(row: Int, col: Int): Int{
        var count = 0
        try{if(currBoard[row - 1][col - 1])count++} catch (e: ArrayIndexOutOfBoundsException){}
        try{if(currBoard[row - 1][col])count++} catch (e: ArrayIndexOutOfBoundsException){}
        try{if(currBoard[row - 1][col + 1])count++} catch (e: ArrayIndexOutOfBoundsException){}
        try{if(currBoard[row][col - 1])count++} catch (e: ArrayIndexOutOfBoundsException){}
        try{if(currBoard[row][col + 1])count++} catch (e: ArrayIndexOutOfBoundsException){}
        try{if(currBoard[row + 1][col - 1])count++} catch (e: ArrayIndexOutOfBoundsException){}
        try{if(currBoard[row + 1][col])count++} catch (e: ArrayIndexOutOfBoundsException){}
        try{if(currBoard[row + 1][col + 1])count++} catch (e: ArrayIndexOutOfBoundsException){}

        return count
    }

    fun animateGrid(){
        //update currBoard
        for (row in 0..49) {
            for (column in 0..74) {
                currBoard[row][column] = board[row][column]
            }
        }
        //update board
        for (row in 0..49) {
            for (column in 0..74) {
                val neighbours = countNeighbours(row, column)
                if(neighbours < 2){
                    board[row][column] = false
                }
                else if(neighbours == 3){
                    board[row][column] = true
                }
                else if(neighbours > 3){
                    board[row][column] = false
                }
            }
        }
        frameCount++
        notifyView()
    }
}