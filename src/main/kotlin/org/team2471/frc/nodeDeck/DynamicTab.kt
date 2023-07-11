package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import org.team2471.frc.nodeDeck.NTClient.demoBoundary
import org.team2471.frc.nodeDeck.NTClient.demoBoundaryLimit
import org.team2471.frc.nodeDeck.NTClient.demoReachLimit
import org.team2471.frc.nodeDeck.NTClient.tagLookingAt

object DynamicTab: VBox(10.0) {
    val test= TextField("Test")



    const val fontSize = 50

    init {
        test.style = "-fx-font-size: $fontSize px"
        DynamicTab.alignment = Pos.TOP_CENTER
        DynamicTab.children.addAll(test)
    }
}