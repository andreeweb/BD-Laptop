package it.uniroma2.dicii.bd.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class responsible for managing scene change methods.
 * Implemented as a Singleton.
 *
 * @author Andrea Cerra
 */

public class SceneManager {

    /**
     * Reference to rootLayout
     */
    private BorderPane rootLayout;
    private RootViewController rootController;
    private Stage stage;

    /**
     *
     * The inner-class LazyCointainer is loaded only at the first invocation of getInstance.
     * This activity results "thread-safe".
     * @author gulyx
     *
     */
    private static class LazyCointainer{
        public final static SceneManager sigletonInstance = new SceneManager();
    }

    protected SceneManager() {

    }

    public static final SceneManager getSingletonInstance() {
        return LazyCointainer.sigletonInstance;
    }

    /**
     * Is called by the main application
     *
     * @param rootLayout reference to root layout
     */
    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

    /**
     * Is called by the main application
     *
     * @param stage reference to main stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Is called by the main application
     *
     * @param rootController reference to root layout
     */
    public void setRootController(RootViewController rootController) {
        this.rootController = rootController;
    }

    /**
     * Called when MainApp start stage
     *
     */
    public void init(){

        this.showLoginView();
    }

    /**
     * Shows the login view inside the root layout.
     *
     */
    public void showLoginView() {

        try {

            // Load login view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("LoginView.fxml"));
            BorderPane view = loader.load();

            // Set view into the center of root layout.
            rootLayout.setCenter(view);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Shows the main view inside the root layout.
     *
     */
    public void showAdminHomeView() {

        try {

            // Load main view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminHomeView.fxml"));
            BorderPane view = loader.load();

            // Set view into the center of root layout.
            rootLayout.setCenter(view);

            AdminHomeViewController controller = loader.getController();
            controller.onCreateView(view);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Shows the upload view
     *
     */
    public void showAdminImportView() {

        try {

            // Load main view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminImportView.fxml"));
            BorderPane view = loader.load();

            // Set view into the center of root layout.
            rootLayout.setCenter(view);

            AdminImportViewController controller = loader.getController();
            controller.onCreateView(view);
            controller.setStage(stage);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Shows the main view inside the root layout.
     *
     */
    /*public void showUserHomeView() {

        try {

            // Load main view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AdminHomeView.fxml"));
            BorderPane view = loader.load();

            // Set view into the center of root layout.
            rootLayout.setCenter(view);

            AdminHomeViewController controller = loader.getController();
            controller.onCreateView(view);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }*/

}
