import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region


class StatusView(private val model: Model) : IView, HBox() {
    val status = Label("")
    val frameCount = Label("")

    init {
        status.padding = Insets(3.00, 0.00, 2.00, 5.00)
        frameCount.padding = Insets(3.00, 5.00, 2.00, 0.00)

        val region = Region()
        setHgrow(region, Priority.ALWAYS)

        this.children.addAll(status, region, frameCount)
        this.isFocusTraversable = false
    }

    override fun update(board: Array<BooleanArray>) {
        frameCount.text = "Frame " + model.frameCount
        if(model.gridClear){
            status.text = "Cleared"
        }
        else if(model.updateGrid){
            status.text = "Created " + model.buttonSelected + " at " + model.position.first + ", " + model.position.second
        }
    }
}