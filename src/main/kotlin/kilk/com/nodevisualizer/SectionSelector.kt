package kilk.com.nodevisualizer

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.GridPane

object SectionSelector : GridPane(){
    val leftSection = Button("Left")
    val centerSection = Button("Co-op")
    val rightSection = Button("Right")
    val buttonHeight = 225.0
    val buttonWidth = 400.0

    init {
        println("SectionSelector says hi!")

        leftSection.setPrefSize(buttonWidth, buttonHeight)
        centerSection.setPrefSize(buttonWidth, buttonHeight)
        rightSection.setPrefSize(buttonWidth, buttonHeight)

        SectionSelector.addRow(1, leftSection, centerSection, rightSection)
        SectionSelector.alignment = Pos.TOP_CENTER
    }
}