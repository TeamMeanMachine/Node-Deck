package org.team2471.frc.nodeDeck

import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object TabDeck: TabPane() {
    private val gridSelectionTab = Tab("Grid Selection")
    private val settingsTab = Tab("General Settings")
    private val testTab = Tab("Test")
    private val longTab = Tab("Node Deck")
    private val demoTab = Tab("Demo")
    private val dynamicTab = Tab("Dynamic Path Test")
    val fullscreenTab = Tab("Fullscreen Application")
    val autoTab = Tab("Robot Auto")

    private val allTabs = listOf<Tab>(gridSelectionTab, settingsTab, autoTab, testTab, longTab, fullscreenTab, demoTab, dynamicTab)

    const val fontSize = "15"

    lateinit var selectedTab: Tab

    init {
        println("TabDeck says hi!")

        tabMinHeight = 60.0
        tabMinWidth = 200.0

        for (tab in allTabs) {
            tab.style = "-fx-font-size: $fontSize px"
            tab.isClosable = false
            tab.setOnSelectionChanged {
                selectedTab = tab
            }
        }

        fullscreenTab.style = "${fullscreenTab.style}; -fx-background-color: #00c04b"
        fullscreenTab.setOnSelectionChanged {
            TabDeck.selectionModel.select(selectedTab)
            tabs.remove(fullscreenTab)
            NodeDeck.stage.isFullScreen = true
        }

        longTab.content = LongFormat
        gridSelectionTab.content = CompactFormat
        autoTab.content = AutoConfig
        settingsTab.content = SettingsTab
        testTab.content = TestTab
        demoTab.content = DemoTab
        dynamicTab.content = DynamicTab

        tabs.add(longTab)
//        tabs.add(gridSelectionTab)
        tabs.add(demoTab)
        tabs.add(autoTab)
//        tabs.add(testTab)
        tabs.add(settingsTab)
        tabs.add(dynamicTab)
        tabs.add(fullscreenTab)

    }
}