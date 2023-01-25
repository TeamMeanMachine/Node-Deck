package org.team2471.frc.nodeDeck

import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object TabDeck: TabPane() {
    val infoTab = Tab("Node Info")
    val autoTab = Tab("Auto Options")

    init {
        println("TabDeck says hi!")

        infoTab.content = InformationPanel
        autoTab.content = AutoConfig

        infoTab.isClosable = false
        autoTab.isClosable = false

        TabDeck.tabs.add(infoTab)
        TabDeck.tabs.add(autoTab)

        TabDeck.setMinSize(165.0, 400.0)
    }
}