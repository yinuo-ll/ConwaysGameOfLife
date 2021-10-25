import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle


class GridView(private val model: Model) : IView, GridPane() {
    init {
        // populate the grid here
        this.isGridLinesVisible = true
        this.hgap = 1.0
        this.vgap = 1.0

        for (c in 0..74) {
            for (r in 0..49) {
                val rect = Rectangle(10.0, 10.0)
                rect.fill = Color.WHITE
                rect.setOnMouseClicked {
                    model.timer
                    model.selectCell(r, c)
                }
                this.add(rect, c, r)
            }
        }

        this.setOnKeyPressed { key: KeyEvent ->
            if (key.code.equals(KeyCode.SPACE)) {
                model.nextFrame()
            } else if (key.code.equals(KeyCode.M)) {
                model.manualModeSwitch()
            }
        }

        this.isFocusTraversable = true
    }

    override fun update(board: Array<BooleanArray>) {
        for (child in this.children) {
            if (child is Rectangle) {
                val r = getRowIndex(child)
                val c = getColumnIndex(child)
                if (board[r][c]) {
                    child.fill = Color.BLACK
                }
                else{
                    child.fill = Color.WHITE
                }
            }
        }
    }
}