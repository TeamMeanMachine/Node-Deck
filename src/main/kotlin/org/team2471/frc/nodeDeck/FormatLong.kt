package org.team2471.frc.nodeDeck

import javafx.scene.control.Button
import javafx.scene.layout.GridPane

object FormatLong: GridPane() {
    val one = Button()
    val two = Button()
    val three = Button()
    val four = Button()
    val five = Button()
    val six = Button()
    val seven = Button()
    val eight = Button()
    val nine = Button()
    val ten = Button()
    val eleven = Button()
    val twelve = Button()
    val thirteen = Button()
    val fourteen = Button()
    val fifteen = Button()
    val sixteen = Button()
    val seventeen = Button()
    val eighteen = Button()
    val nineteen = Button()
    val twenty = Button()
    val twentyOne = Button()
    val twentyTwo = Button()
    val twentyThree = Button()
    val twentyFour = Button()
    val twentyFive = Button()
    val twentySix = Button()
    val twentySeven = Button()
    val buttonSideLength: Double = 400.0
    var selectedNodeButton: Button = one
    var selectedNode: Int = 1
    val allButtons = listOf<Button>(one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fifteen, sixteen, seventeen, eighteen, nineteen, twenty, twentyOne, twentyTwo, twentyThree, twentyFour, twentyFive, twentySix, twentySeven)

    init {
        var assignedButtonNode = 0
        for (button in allButtons) {
            assignedButtonNode += 1
            val thisButtonNode = assignedButtonNode
            button.style = "-fx-background-color: red"
            button.setPrefSize(20.0, 20.0)
            button.setOnAction {
                println(thisButtonNode)
            }
            println(thisButtonNode)
            if (assignedButtonNode <= 3) {
                FormatLong.add(button, assignedButtonNode, 0 )
            } else if (assignedButtonNode in 4..6) {
                FormatLong.add(button, assignedButtonNode - 3, 1)
            } else if (assignedButtonNode in 7..9) {
                FormatLong.add(button, assignedButtonNode - 6, 2)
            } else if (assignedButtonNode in 10..12) {
                FormatLong.add(button, assignedButtonNode - 9, 0)
            } else if (assignedButtonNode in 13..15) {
                FormatLong.add(button, assignedButtonNode - 12, 1)
            } else if (assignedButtonNode in 16..18) {
                FormatLong.add(button, assignedButtonNode - 15, 2)
            } else if (assignedButtonNode in 19..21) {
                FormatLong.add(button, assignedButtonNode - 18, 3)
            } else if (assignedButtonNode in 22..24) {
                FormatLong.add(button, assignedButtonNode - 21, 0)
            }
        }
    }
}