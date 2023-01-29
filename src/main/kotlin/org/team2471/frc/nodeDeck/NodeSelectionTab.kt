package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.layout.GridPane

object NodeSelectionTab : GridPane() {
    init {
        NodeSelectionTab.alignment = Pos.CENTER

        NodeSelectionTab.add(GridSelector, 0, 0)
        NodeSelectionTab.add(NodeSelector, 0, 1)
        NodeSelectionTab.add(InformationPanel, 1, 1)
    }
}