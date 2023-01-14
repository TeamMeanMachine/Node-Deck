package kilk.com.nodevisualizer

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.layout.GridPane

object SectionSelector : GridPane(){
    val leftSection = Button("Left")
    val centerSection = Button("Co-op")
    val rightSection = Button("Right")
    val buttonHeight = 200.0
    val buttonWidth = 400.0
    var selectedButton: Button = centerSection

    init {
        println("SectionSelector says hi!")

        leftSection.setPrefSize(buttonWidth, buttonHeight)
        leftSection.setOnMousePressed {
            changeSelectedButton(leftSection)
        }
        centerSection.setPrefSize(buttonWidth, buttonHeight)
        centerSection.setOnMousePressed {
            changeSelectedButton(centerSection)
        }
        rightSection.setPrefSize(buttonWidth, buttonHeight)
        rightSection.setOnMousePressed {
            changeSelectedButton(rightSection)
        }

        SectionSelector.addRow(0, leftSection, centerSection, rightSection)
        SectionSelector.alignment = Pos.TOP_CENTER
    }
    private fun changeSelectedButton(thisButton: Button) {
        selectedButton.style = ""
        thisButton.style = "-fx-background-color: #ff0000"
        selectedButton = thisButton
    }
}