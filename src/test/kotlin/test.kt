import com.sun.javafx.scene.control.ContextMenuContent
import com.sun.javafx.scene.control.MenuBarButton
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.testfx.framework.junit5.ApplicationTest
import view.main.MainView


@ExperimentalStdlibApi
class Test : ApplicationTest() {


    override fun start(stage: Stage) {
        stage.scene = Scene(MainView().root)
        with(stage) {
            initStyle(StageStyle.UNDECORATED)
            width = 800.0
            height = 600.0
            isMaximized = true
        }
        stage.show()
    }

    @AfterEach
    fun kek(){

    }

    @Test
    fun openExampleAndFindCommunityTest() {
        val button = lookup("Detect communities").query<Button>()
        val menuExamples = lookup("Examples").query<MenuBarButton>()

        clickOn(menuExamples)
        val example = lookup("Zachary karate club").query<Label>()
        clickOn(example)
        clickOn(button)
        sleep(1000)
    }

    @Test
    fun openAllExamples() {
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
        }
    }
}