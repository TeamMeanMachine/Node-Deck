package kilk.com.nodevisualizer

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.text.Font

object InformationPanel: GridPane() {
    val nodeLabel = Label()
    val toggleAllianceButton = Button("Toggle Team Color")
    init {
        println("InformationPanel says hi!")

        InformationPanel.addRow(0, nodeLabel)
        InformationPanel.addRow(1, toggleAllianceButton)
        nodeLabel.alignment = Pos.CENTER
        nodeLabel.font = Font(20.0)
        nodeLabel.setPrefSize(150.0, 25.0)
        nodeLabel.style = "-fx-background-color: #f0f0f0; -fx-font-weight: bold; -fx-font-size: 30px"

        toggleAllianceButton.setPrefSize(150.0, 50.0)
        toggleAllianceButton.setOnAction {
            if (NodeVisualizer.isRedAlliance) {
                NodeVisualizer.isRedAlliance = false
                ColorOutline.checkAlliance()
            } else {
                NodeVisualizer.isRedAlliance = true
                ColorOutline.checkAlliance()
            }
        }

        updateInfoPanel()
    }
    fun updateInfoPanel() {
        nodeLabel.text = "Node #: ${NodeSelector.selectedNode + SectionSelector.selectedSection}"

    }
}