package org.team2471.frc.nodeDeck

import javafx.scene.control.Button
import javafx.scene.layout.GridPane

object FormatLong: GridPane() {
    private val one = Button()
    private val two = Button()
    private val three = Button()
    private val four = Button()
    private val five = Button()
    private val six = Button()
    private val seven = Button()
    private val eight = Button()
    private val nine = Button()
    private val ten = Button()
    private val eleven = Button()
    private val twelve = Button()
    private val thirteen = Button()
    private val fourteen = Button()
    private val fifteen = Button()
    private val sixteen = Button()
    private val seventeen = Button()
    private val eighteen = Button()
    private val nineteen = Button()
    private val twenty = Button()
    private val twentyOne = Button()
    private val twentyTwo = Button()
    private val twentyThree = Button()
    private val twentyFour = Button()
    private val twentyFive = Button()
    private val twentySix = Button()
    private val twentySeven = Button()
    val buttonSideLength: Double = 400.0
    var selectedNodeButton: Button = one
    var selectedNode: Int = 1
    val allButtons = listOf(one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve, thirteen, fourteen, fifteen, sixteen, seventeen, eighteen, nineteen, twenty, twentyOne, twentyTwo, twentyThree, twentyFour, twentyFive, twentySix, twentySeven)
    val allFloorButtons = listOf<Button>(seven, eight, nine, sixteen, seventeen, eighteen, twentyFive, twentySix, twentySeven)
    val allConeButtons = listOf<Button>(one, three, four, six, ten, twelve, thirteen, fifteen, nineteen, twentyOne, twentyTwo, twentyFour)
    val allCubeButtons = listOf<Button>(two, five, eleven, fourteen, twenty, twentyThree)

    init {
        var assignedButtonNode = 0
        for (button in allButtons) {
            assignedButtonNode += 1
            val thisButtonNode = assignedButtonNode
            button.setPrefSize(50.0, 50.0)
            button.setOnAction {
                println(thisButtonNode)
            }
            println(thisButtonNode)
            FormatLong.addColumn(assignedButtonNode, button)
        }
        for (button in allConeButtons) {
            button.style = "-fx-background-color: #FFFF00"
        }
        for (button in allCubeButtons) {
            button.style = "-fx-background-color: #9900ff"
        }
        for (button in allFloorButtons) {
            button.style = "-fx-background-color: #595959"
        }
    }
}