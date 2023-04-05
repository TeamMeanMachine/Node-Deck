package org.team2471.frc.nodeDeck

import edu.wpi.first.networktables.NetworkTableInstance
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ListView
import javafx.scene.layout.StackPane

object NTTab: StackPane() {
    var view: ListView<String>? = null
    var list: ObservableList<String>? = null

    init {
        list = FXCollections.observableArrayList()
        view = ListView(list)
        NTTab.children.add(view)
    }

    fun update() {
        list?.clear()
        NetworkTableInstance.getDefault().topics.forEach {
            val t = NetworkTableInstance.getDefault().getEntry(it.name)
            println("name: ${t.name} value: ${t.value.value}")
            list?.add("name: ${t.name} value: ${t.value.value}")
        }
    }
}