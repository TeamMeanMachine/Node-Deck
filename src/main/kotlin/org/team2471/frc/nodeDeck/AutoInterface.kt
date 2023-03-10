package org.team2471.frc.nodeDeck

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.text.Font

object AutoInterface: GridPane() {
    private val zero = NodeButton()
    private val one = NodeButton()
    private val two = NodeButton()
    private val three = NodeButton()
    private val four = NodeButton()
    private val five = NodeButton()
    private val six = NodeButton()
    private val seven = NodeButton()
    private val eight = NodeButton()

    private val twoT = Button("Toggle Cone/Cube")
    private val fiveT = Button("Toggle Cone/Cube")
    private val eightT = Button("Toggle Cone/Cube")

    private val allButtons = listOf<NodeButton>(zero, one, two, three, four, five, six, seven, eight)
    private val allConeButtons = listOf<NodeButton>(zero, one, six, seven)
    private val allCubeButtons = listOf<NodeButton>(three, four)
    private val allFloorButtons = listOf<NodeButton>(two, five, eight)
    private val allToggleButtons = listOf<Button>(twoT, fiveT, eightT)

    var first: Int? = null
    var second: Int? = null
    var third: Int? = null
    var fourth: Int? = null
    var fifth: Int? = null
    val amountOfPieces: Int
        get() = calculatePiecesAmount()


    val buttonHeight: Double = 200.0
    val buttonWidth: Double = 300.0
    val buttonBorderSize: String = "5 5 5 5"
    init {
        println("AutoInterface says hello!")
        alignment = Pos.CENTER

        //assigns all buttons their values
        var buttonNumber = -1
        for (button in allButtons) {
            buttonNumber += 1
            val thisButtonNode = buttonNumber
            button.assignedNode = thisButtonNode

            button.font = Font.font ("Verdana", 84.0)
            button.setPrefSize(buttonWidth, buttonHeight)
            button.setOnAction {
                button.isSelected = !button.isSelected
                if (button.isSelected) {
                    if (first == null) {
                        first = button.assignedNode
                    } else if (second == null) {
                        second = button.assignedNode
                    } else if (third == null) {
                        third = button.assignedNode
                    } else if (fourth == null) {
                        fourth = button.assignedNode
                    } else if (fifth == null) {
                        fifth = button.assignedNode
                    }
                    if (allFloorButtons.contains(button)) {
//                        button.setPrefSize(buttonWidth, buttonHeight/2)
//                        button.font = Font.font ("Verdana", 1.0)
                        showFloorTypeButtons(button)
                    }
                } else {
                    if (first == button.assignedNode) {
                        first = null
                    } else if (second == button.assignedNode) {
                        second = null
                    } else if (third == button.assignedNode) {
                        third = null
                    }else if (fourth == button.assignedNode) {
                        fourth = null
                    } else if (fifth == button.assignedNode) {
                        fifth = null
                    }
                    if (allFloorButtons.contains(button)) {
//                        button.setPrefSize(buttonWidth, buttonHeight/2)
//                        button.font = Font.font ("Verdana", 84.0)
                        hideFloorTypeButtons(button)
                    }
                }
                setVisual()
                NTClient.setTables()
            }
            for (button in allToggleButtons) {
                button.setPrefSize(buttonWidth, buttonHeight/2)
            }

            if (allConeButtons.contains(button)) {
                button.style = "-fx-background-color: #FFFF00; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
            } else if (allCubeButtons.contains(button)) {
                button.style = "-fx-background-color: #9900ff; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
            } else {
                button.style = "-fx-background-color: #595959; -fx-border-width: ${buttonBorderSize}; -fx-border-color: black"
                button.setPrefSize(buttonWidth, buttonHeight/2)
            }

            AutoInterface.add(button, thisButtonNode/3, thisButtonNode.mod(3))
        }
    }
    fun setVisual() {
        for (button in allButtons) {
            button.text = ""
            button.graphic = ImageView()
            if (button.assignedNode == first) {
                button.text = "1"
                setIcon(button)
            }
            if (button.assignedNode == second) {
                button.text = "2"
                setIcon(button)
            }
            if (button.assignedNode == third) {
                button.text = "3"
                setIcon(button)
            }
            if (button.assignedNode == fourth) {
                button.text = "4"
                setIcon(button)
            }
            if (button.assignedNode == fifth) {
                button.text = "5"
                setIcon(button)
            }
        }
    }
    fun setIcon(button: Button) {
        if (allConeButtons.contains(button)) {
            button.graphic = ImageView(Image("cone-icon.png", buttonWidth - 110.0, buttonHeight - 110.0, true, false))
            button.text = " ${button.text}"
        } else if (allCubeButtons.contains(button)) {
            button.graphic = ImageView(Image("cube-icon.png", buttonWidth - 110.0, buttonHeight - 110.0, true, false))
            button.text = " ${button.text}"
        }
    }

    fun clear() {
        first = null
        second = null
        third = null
        fourth = null
        fifth = null
        setVisual()
    }
    fun showFloorTypeButtons(button: Button) {
        if (button == two) {
            AutoInterface.add(twoT, 0, 3)
        } else if (button == five) {
            AutoInterface.add(fiveT, 1, 3)
        } else if (button == eight) {
            AutoInterface.add(eightT, 2, 3)
        }
    }
    fun hideFloorTypeButtons(button: Button) {
        if (button == two) {
            AutoInterface.children.removeAll(twoT)
        } else if (button == five) {
            AutoInterface.children.removeAll(fiveT)
        } else if (button == eight) {
            AutoInterface.children.removeAll(eightT)
        }
    }
    fun calculatePiecesAmount(): Int {
        if (first == null) {
            return 0
        } else if (second == null) {
            return 1
        } else if (third == null) {
            return 2
        } else if (fourth == null) {
            return 3
        } else if (fifth == null) {
            return 4
        } else return 5
    }
    fun realNodeNumber(x: Int?): Long? {
        var n = x
        if (n != null) {
            if (!NTClient.isRed) {
                n += 27
                if (AutoConfig.startingPoint == StartingPoint.INSIDE) {
                    n += 18
                }
            } else {
                if (AutoConfig.startingPoint == StartingPoint.OUTSIDE) {
                    n += 18
                }
            }
            if (AutoConfig.startingPoint == StartingPoint.MIDDLE) {
                n += 9
            }
            return n.toLong()
        } else {
            return null
        }
    }

}
class NodeButton: Button() {
    var assignedNode: Int? = null
    var isSelected: Boolean = false
}