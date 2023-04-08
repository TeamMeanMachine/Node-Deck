package org.team2471.frc.nodeDeck

import edu.wpi.first.networktables.NetworkTableEvent
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.networktables.Topic
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ListView
import javafx.scene.layout.StackPane
import java.util.EnumSet

object NTTab: StackPane() {
    var view: ListView<String>? = null
    val instance = NetworkTableInstance.getDefault()
    var usedTopics = arrayListOf<Topic>()
    var list: ObservableList<String>? = null
    var assignedRow = 0

    init {
        list = FXCollections.observableArrayList()
        view = ListView(list)
        NTTab.children.add(view)
    }


    fun checkIfNewTopics() {
        val unusedTopics = usedTopics
        instance.topics.forEach { topic ->
            val tAssignedRow = if (assignedRow > (list?.size ?: 0)) list!!.size else assignedRow
            assignedRow += 1
            if (!usedTopics.contains(topic)) {
                usedTopics.add(topic)
                list?.add(tAssignedRow,"name: ${topic.name}      value: ${topic.getGenericEntry().get().value}")
                instance.addListener(topic, EnumSet.of(NetworkTableEvent.Kind.kPublish, NetworkTableEvent.Kind.kValueAll)) { event: NetworkTableEvent ->
                    println("name: ${topic.name} value: ${topic.getGenericEntry().get().value}")
                    Platform.runLater {
                        list?.removeAt(tAssignedRow)
                        list?.add(tAssignedRow,"name: ${topic.name}      value: ${topic.getGenericEntry().get().value}")
                    }
                }
            } else {
                unusedTopics.drop(unusedTopics.indexOf(topic))
            }
        }
        unusedTopics.forEach {
            usedTopics.drop(usedTopics.indexOf(it))
        }
    }
}