package kilk.com.nodevisualizer

import javafx.application.Application
import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage

class NodeVisualizer : Application() {
    companion object {
        lateinit var stage: Stage
        var isRedAlliance: Boolean = true

        @JvmStatic
        fun main(args: Array<String>) {
            println("Launching NodeVisualizer...")
            launch(NodeVisualizer::class.java, *args)
        }
    }

    override fun start(stage: Stage) {

        val screen = Screen.getPrimary()

        stage.title = "NodeVisualizer"
        NodeVisualizer.stage = stage

        val bounds = Rectangle2D(screen.visualBounds.minX, screen.visualBounds.minY, screen.visualBounds.width, screen.visualBounds.height)
        stage.scene = Scene(ColorOutline, bounds.width, bounds.height)
        stage.sizeToScene()
        stage.show()
        stage.isFullScreen = true

        ColorOutline.checkAlliance()
    }
}