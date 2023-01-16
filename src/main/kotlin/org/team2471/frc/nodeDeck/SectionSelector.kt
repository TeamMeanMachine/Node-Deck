package org.team2471.frc.nodeDeck

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
    val buttonWidth = 600.0
    var selectedSectionButton: Button = rightSection
    var selectedSection: Int = 0

    init {
        println("SectionSelector says hi!")

        leftSection.setPrefSize(buttonWidth, buttonHeight)
        leftSection.style = "-fx-font-weight: bold; -fx-font-size: 60px"
        leftSection.setOnMousePressed {
            if (NodeDeck.isRedAlliance) {
                selectedSection = 18
            } else {
                selectedSection = 0
            }
            changeSelectedSectionButton(leftSection)
            InformationPanel.updateInfoPanel()

        }
        centerSection.setPrefSize(buttonWidth, buttonHeight)
        centerSection.style = "-fx-font-weight: bold; -fx-font-size: 60px"
        centerSection.setOnMousePressed {
            changeSelectedSectionButton(centerSection)
            selectedSection = 9
            InformationPanel.updateInfoPanel()

        }
        rightSection.setPrefSize(buttonWidth, buttonHeight)
        rightSection.style = "-fx-font-weight: bold; -fx-font-size: 60px"
        rightSection.setOnMousePressed {
            if (NodeDeck.isRedAlliance) {
                selectedSection = 0
            } else {
                selectedSection = 18
            }
            changeSelectedSectionButton(rightSection)
            InformationPanel.updateInfoPanel()

        }

        SectionSelector.addRow(0, leftSection, centerSection, rightSection)
        SectionSelector.alignment = Pos.TOP_CENTER

        changeSelectedSectionButton(rightSection)
    }
    fun changeSelectedSectionButton(thisButton: Button) {
        selectedSectionButton.graphic = ImageView()
        selectedSectionButton.style = "-fx-font-weight: bold; -fx-font-size: 60px"
        if (selectedSectionButton == leftSection) {
            selectedSectionButton.text = "Left"
        } else if (selectedSectionButton == centerSection) {
            selectedSectionButton.text = "Co-op"
        } else { selectedSectionButton.text = "Right" }

        thisButton.text = ""
        thisButton.graphic = ImageView(Image("mean-logo.png"))
        thisButton.style = "-fx-background-color: #ff0000; -fx-text-fill: WHITE; -fx-font-weight: bold; -fx-font-size: 70px"

        selectedSectionButton = thisButton
    }
}