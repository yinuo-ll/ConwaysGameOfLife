import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Separator
import javafx.scene.control.ToolBar
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javax.swing.GroupLayout

class ToolbarView(private val model: Model) : IView, ToolBar() {
    private val blockButton = Button("Block")
    private val beehiveButton = Button("Beehive")
    private val blinkerButton = Button("Blinker")
    private val toadButton = Button("Toad")
    private val gliderButton = Button("Glider")
    private val clearButton = Button("Clear")
    private val manualSwitch = Button("Manual Mode")
    private val separator1 = Separator(Orientation.VERTICAL)
    private val separator2 = Separator(Orientation.VERTICAL)
    private val separator3 = Separator(Orientation.VERTICAL)
    private val separator4 = Separator(Orientation.VERTICAL)
    private val separator5 = Separator(Orientation.VERTICAL)
    private val separator6 = Separator(Orientation.VERTICAL)

    init {
        // add buttons to toolbar
        blockButton.graphic = ImageView(Image("block.png",20.00, 20.00, true, true))
        beehiveButton.graphic = ImageView(Image("beehive.png",27.00, 27.00, true, true))
        blinkerButton.graphic = ImageView(Image("blinker.png",21.00, 21.00, true, true))
        toadButton.graphic = ImageView(Image("toad.png", 37.00, 37.00, true, true))
        gliderButton.graphic = ImageView(Image("glider.png", 21.00, 21.00, true, true))
        clearButton.graphic = ImageView(Image("clear.png", 21.00, 21.00, true, true))
        manualSwitch.graphic = ImageView(Image("manual.png", 20.00, 20.00, true, true))

        this.items.addAll(blockButton, beehiveButton, separator1, separator2, blinkerButton, toadButton, gliderButton, separator3, separator4, clearButton, separator5, separator6, manualSwitch)

        blockButton.setOnAction { model.updateButtonSelection(blockButton.text) }
        beehiveButton.setOnAction { model.updateButtonSelection(beehiveButton.text) }
        blinkerButton.setOnAction { model.updateButtonSelection(blinkerButton.text) }
        toadButton.setOnAction { model.updateButtonSelection(toadButton.text) }
        gliderButton.setOnAction { model.updateButtonSelection(gliderButton.text) }
        clearButton.setOnAction {
            model.updateButtonSelection("")
            model.clearGrid()
        }
        manualSwitch.setOnAction { model.manualModeSwitch() }

        blockButton.focusTraversableProperty().set(false)
        beehiveButton.focusTraversableProperty().set(false)
        blinkerButton.focusTraversableProperty().set(false)
        toadButton.focusTraversableProperty().set(false)
        gliderButton.focusTraversableProperty().set(false)
        clearButton.focusTraversableProperty().set(false)
        manualSwitch.focusTraversableProperty().set(false)
    }

    override fun update(board: Array<BooleanArray>) {}

}