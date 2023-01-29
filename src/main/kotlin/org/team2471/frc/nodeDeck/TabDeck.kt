package org.team2471.frc.nodeDeck

import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object TabDeck: TabPane() {
    val gridSelectionTab = Tab("Grid Selection")
    val infoTab = Tab("Node Info")
    val autoTab = Tab("Auto Options")
    val alternateATab = Tab("Alternate A")

    init {
        println("TabDeck says hi!")

        tabMinHeight = 50.0
        tabMinWidth = 100.0

        gridSelectionTab.content = ColorOutline
        infoTab.content = InformationPanel
        autoTab.content = AutoConfig
        alternateATab.content = AlternateA

        gridSelectionTab.isClosable = false
        infoTab.isClosable = false
        autoTab.isClosable = false
        alternateATab.isClosable = false

        tabs.add(gridSelectionTab)
        tabs.add(infoTab)
        tabs.add(autoTab)
        tabs.add(alternateATab)
    }
}