package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane

object GridSelector : GridPane(){
    val leftSection = Button("Left")
    val centerSection = Button("Co-op")
    val rightSection = Button("Right")
    val buttonHeight = 200.0
    val buttonWidth = 600.0
    var selectedSectionButton: Button = rightSection
    var selectedSection: Int = 0

    init {
        println("GridSelector says hi!")

        leftSection.setPrefSize(buttonWidth, buttonHeight)
        leftSection.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-width: 2 2 2 2; -fx-border-color: black"
        leftSection.setOnAction {
            if (NTClient.isRed) {
                selectedSection = 18
            } else {
                selectedSection = 0
            }
            changeSelectedSectionButton(leftSection)
            InformationPanel.updateInfoPanel()

        }
        centerSection.setPrefSize(buttonWidth, buttonHeight)
        centerSection.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-width: 2 2 2 2; -fx-border-color: black"
        centerSection.setOnAction {
            changeSelectedSectionButton(centerSection)
            selectedSection = 9
            InformationPanel.updateInfoPanel()

        }
        rightSection.setPrefSize(buttonWidth, buttonHeight)
        rightSection.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-width: 2 2 2 2; -fx-border-color: black"
        rightSection.setOnAction {
            if (NTClient.isRed) {
                selectedSection = 0
            } else {
                selectedSection = 18
            }
            changeSelectedSectionButton(rightSection)
            InformationPanel.updateInfoPanel()

        }

        GridSelector.addRow(0, leftSection, centerSection, rightSection)
        GridSelector.alignment = Pos.TOP_CENTER

        changeSelectedSectionButton(rightSection)
    }
    fun changeSelectedSectionButton(thisButton: Button) {
        selectedSectionButton.graphic = ImageView()
        selectedSectionButton.style = "-fx-font-weight: bold; -fx-font-size: 60px; -fx-border-color: black"
        if (selectedSectionButton == leftSection) {
            selectedSectionButton.text = "Left"
        } else if (selectedSectionButton == centerSection) {
            selectedSectionButton.text = "Co-op"
        } else { selectedSectionButton.text = "Right" }

//        thisButton.text = ""
//        thisButton.graphic = ImageView(Image("mean-logo.png"))
        thisButton.style = "-fx-font-weight: bold; -fx-font-size: 70px; -fx-border-color: red; -fx-border-width: 20 20 20 20"

        selectedSectionButton = thisButton
    }
}