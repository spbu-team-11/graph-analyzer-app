import view.main.MainView

import com.sun.javafx.scene.control.MenuBarButton
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.testfx.framework.junit5.ApplicationTest

@ExperimentalStdlibApi
class Test : ApplicationTest() {

    private lateinit var view: MainView

    override fun start(stage: Stage) {
        view = MainView()
        stage.scene = Scene(view.root)
        with(stage) {
            if (style != StageStyle.UNDECORATED) initStyle(StageStyle.UNDECORATED)
            width = 800.0
            height = 600.0
            isMaximized = true
        }
        stage.show()
    }

    @Test
    fun openExampleAndFindCommunityTest() {

        val menuExamples = lookup("Examples").query<MenuBarButton>()
        clickOn(menuExamples)

        val example = lookup("Zachary karate club").query<Label>()
        clickOn(example)

        val findCommunity = lookup("Detect communities").query<Button>()
        clickOn(findCommunity)

        for (vertex in view.graphView.vertices()) {
            assertNotEquals(vertex.color, Color.BLACK)
        }
    }

    @Test
    fun openAllExamplesTest() {
        val menuExamples = lookup("Examples").query<MenuBarButton>()

        clickOn(menuExamples)
        val example = arrayOf(
            lookup("American College football").query<Label>(),
            lookup("Big synthetic graph").query(),
            lookup("Dolphin social network").query(),
            lookup("Nouns and adjectives network").query(),
            lookup("Zachary karate club").query()
        )
        var i = 0
        example.forEach {
            i++
            clickOn(it)
            MainView().root.isManaged
            sleep(1000)
            if (i < example.size) clickOn(menuExamples)
            assertFalse(view.graphView.vertices().isEmpty())
        }
    }

    @Test
    fun openExampleAndDetectCentralityTest() {
        val menuExamples = lookup("Examples").query<MenuBarButton>()
        clickOn(menuExamples)

        val example = lookup("Dolphin social network").query<Label>()
        clickOn(example)

        val findCommunity = lookup("Highlight vertices").query<Button>()
        clickOn(findCommunity)

        for (vertex in view.graphView.vertices()) {
            assertNotEquals(vertex.radius, 5.0)
        }
    }
}