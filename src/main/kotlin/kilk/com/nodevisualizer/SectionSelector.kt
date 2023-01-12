package kilk.com.nodevisualizer

import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.layout.Region

object SectionSelector : HBox(5.0){
    val leftSection = Button("Left")
    val centerSection = Button("Co-op")
    val rightSection = Button("Right")
    val buttonHeight = NodeVisualizer.screen.visualBounds.height / 4
    val buttonWidth = NodeVisualizer.screen.visualBounds.width / 4
    val spacerLeft = Region()
    val spacerRight = Region()

    init {
        println("SectionSelector says hi!")

        spacerLeft.setPrefSize(9999.9, 0.0)
        spacerRight.setPrefSize(9999.9, 0.0)

        leftSection.setMinSize(buttonWidth, buttonHeight)
        centerSection.setMinSize(buttonWidth, buttonHeight)
        rightSection.setMinSize(buttonWidth, buttonHeight)

        SectionSelector.children.addAll(spacerLeft, leftSection, centerSection, rightSection, spacerRight)
    }
}