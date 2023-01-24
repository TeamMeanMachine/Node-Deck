package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.text.Font

object InformationPanel: GridPane() {
    val nodeLabel = Label()
    val toggleAllianceButton = Button("Check Team Color")
    init {
        println("InformationPanel says hi!")

        InformationPanel.addRow(0, nodeLabel)
        InformationPanel.addRow(1, toggleAllianceButton)

        nodeLabel.alignment = Pos.CENTER
        nodeLabel.font = Font(20.0)
        nodeLabel.style = "-fx-background-color: #f0f0f0; -fx-font-weight: bold; -fx-font-size: 30px"
        nodeLabel.setMinSize(160.0, 25.0)

        toggleAllianceButton.setPrefSize(160.0, 50.0)
        toggleAllianceButton.setOnAction {
//            ColorOutline.checkAlliance()
//            Client.printNTTopicConnection()
//            updateButtons()
            Client.printNTTopicConnection()
        }

        updateInfoPanel()
    }
    fun updateInfoPanel() {
        nodeLabel.text = "Node #: ${NodeSelector.selectedNode + SectionSelector.selectedSection}"
    }
    fun updateButtons() {
//        NodeSelector.selectedNodeButton.fireEvent(MouseEvent.MOUSE_CLICKED)
        SectionSelector.selectedSectionButton.fire()
    }
}