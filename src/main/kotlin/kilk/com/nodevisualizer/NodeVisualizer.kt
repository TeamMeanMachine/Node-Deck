package kilk.com.nodevisualizer

import javafx.application.Application
import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Screen
import javafx.stage.Stage

class NodeVisualizer : Application() {
    companion object {
        lateinit var stage: Stage
        val screen = Screen.getPrimary()

        @JvmStatic
        fun main(args: Array<String>) {
            println("Launching NodeVisualizer...")
            launch(NodeVisualizer::class.java, *args)
        }
    }

    override fun start(stage: Stage) {


        stage.title = "NodeVisualizer"
        NodeVisualizer.stage = stage


        val borderPane = BorderPane()
        borderPane.style = "-fx-background-color: #a8a8a8"
        borderPane.center = NodeSelector
        borderPane.top = SectionSelector

        val bounds = Rectangle2D(screen.visualBounds.minX, screen.visualBounds.minY, screen.visualBounds.width, screen.visualBounds.height)
        stage.scene = Scene(borderPane, bounds.width, bounds.height)
        stage.sizeToScene()
        stage.show()
    }
}