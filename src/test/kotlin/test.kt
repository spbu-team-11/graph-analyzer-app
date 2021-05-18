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
import org.junit.jupiter.api.Test
import org.testfx.framework.junit5.ApplicationTest
import view.main.MainView


@ExperimentalStdlibApi
class Test: ApplicationTest(){


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

    @Test
    fun openExampleAndFindCommunityTest(){
        val button = lookup("Detect communities").query<Button>()
        val menuExamples = lookup("Examples").query<MenuBarButton>()


        clickOn(menuExamples)
        val example = lookup("Zachary karate club").query<Label>()
        clickOn(example)
        clickOn(button)
        sleep(1000)
    }


}