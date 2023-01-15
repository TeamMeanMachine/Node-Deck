package kilk.com.nodevisualizer

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane

object SectionSelector : GridPane(){
    val leftSection = Button("Left")
    val centerSection = Button("Co-op")
    val rightSection = Button("Right")
    val buttonHeight = 200.0
    val buttonWidth = 400.0
    var selectedButton: Button = rightSection
    var selectedSection: Int = 0

    init {
        println("SectionSelector says hi!")

        leftSection.setPrefSize(buttonWidth, buttonHeight)
        leftSection.style = "-fx-font-weight: bold; -fx-font-size: 60px"
        leftSection.setOnMousePressed {
            changeSelectedButtonColor(leftSection)
            selectedSection = 18
            InformationPanel.updateInfoPanel()

        }
        centerSection.setPrefSize(buttonWidth, buttonHeight)
        centerSection.style = "-fx-font-weight: bold; -fx-font-size: 60px"
        centerSection.setOnMousePressed {
            changeSelectedButtonColor(centerSection)
            selectedSection = 9
            InformationPanel.updateInfoPanel()

        }
        rightSection.setPrefSize(buttonWidth, buttonHeight)
        rightSection.style = "-fx-font-weight: bold; -fx-font-size: 60px"
        rightSection.setOnMousePressed {
            changeSelectedButtonColor(rightSection)
            selectedSection = 0
            InformationPanel.updateInfoPanel()

        }

        SectionSelector.addRow(0, leftSection, centerSection, rightSection)
        SectionSelector.alignment = Pos.TOP_CENTER

        changeSelectedButtonColor(rightSection)
    }
    private fun changeSelectedButtonColor(thisButton: Button) {
        selectedButton.graphic = ImageView()
        selectedButton.style = "-fx-font-weight: bold; -fx-font-size: 60px"
        if (selectedButton == leftSection) {
            selectedButton.text = "Left"
        } else if (selectedButton == centerSection) {
            selectedButton.text = "Co-op"
        } else { selectedButton.text = "Right" }

        thisButton.text = ""
        thisButton.graphic = ImageView(Image("mean-logo.png"))
        thisButton.style = "-fx-background-color: #ff0000; -fx-text-fill: WHITE; -fx-font-weight: bold; -fx-font-size: 70px"

        selectedButton = thisButton
    }
}