package org.team2471.frc.nodeDeck

import edu.wpi.first.networktables.NetworkTableEvent
import edu.wpi.first.networktables.NetworkTableInstance
import edu.wpi.first.networktables.Topic
import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.control.ListView
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.scene.text.Text
import java.util.EnumSet

object NTTab: StackPane() {
    val instance = NetworkTableInstance.getDefault()
    var usedTopics = arrayListOf<Topic>()
    var list = GridPane()
    var assignedRow = 0

    init {
        list.hgap = 10.0
        NTTab.children.add(list)
    }


    fun checkIfNewTopics() {
        val unusedTopics = usedTopics
        instance.topics.forEach { topic ->
//            println(topic.name)
            val tAssignedRow = if (assignedRow > list.rowCount) list.rowCount else assignedRow
            assignedRow += 1
            if (!usedTopics.contains(topic)) {
                usedTopics.add(topic)
                list.addRow(tAssignedRow, Text(topic.name), Text("${topic.getGenericEntry().get().value}"))
                instance.addListener(topic, EnumSet.of(NetworkTableEvent.Kind.kPublish, NetworkTableEvent.Kind.kValueAll)) { event: NetworkTableEvent ->
                    println("name: ${topic.name} value: ${topic.getGenericEntry().get().value}")
                    Platform.runLater {
                        list.children.removeIf { node: Node -> GridPane.getRowIndex(node) == tAssignedRow }
                        list.addRow(tAssignedRow, Text(topic.name), Text("${topic.getGenericEntry().get().value}"))
//                        list.addRow(tAssignedRow, Text(topic.name), Text("${topic.getGenericEntry().get().value}"))
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