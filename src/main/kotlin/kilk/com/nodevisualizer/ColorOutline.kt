package kilk.com.nodevisualizer

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.GridPane

object ColorOutline : GridPane() {
    init {
        println("ColorOutline says hi!")

        ColorOutline.padding = Insets(0.0)
        ColorOutline.alignment = Pos.CENTER
        ColorOutline.add(SectionSelector, 0, 0)
        ColorOutline.add(NodeSelector, 0, 1)
    }
    fun checkAlliance(red: Boolean = NodeVisualizer.isRedAlliance) {
        if (red) {
            ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #ff0000; -fx-border-width: 10 10 10 10"
        } else {
            ColorOutline.style = "-fx-background-color: #a8a8a8; -fx-border-color: #0000ff; -fx-border-width: 10 10 10 10"
        }

    }
}