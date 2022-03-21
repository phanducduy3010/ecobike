package views.screen;

import java.io.IOException;
import java.util.Hashtable;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class BaseScreenHandler extends FXMLScreenHandler {

	private Scene scene;
	private BaseScreenHandler prev;
	protected final Stage stage;
	protected Hashtable<String, String> messages;
	//private BaseController bController;

	private BaseScreenHandler(String screenPath) throws IOException {
		super(screenPath);
		this.stage = new Stage();
	}
	public BaseScreenHandler(Stage stage, String screenPath) throws IOException {
		super(screenPath);
		this.stage = stage;
		//System.out.println("xong Basescreen "+screenPath);
	}


	public void setPreviousScreen(BaseScreenHandler prev) {
		this.prev = prev;
	}

	public BaseScreenHandler getPreviousScreen() {
		return this.prev;
	}


	public void show() {
		if (this.scene == null) {
			this.scene = new Scene(this.content);
		}
		this.stage.setScene(this.scene);
		this.stage.show();
	}

	public void setScreenTitle(String string) {
		this.stage.setTitle(string);
	}

//	public void setBController(BaseController bController){
//		this.bController = bController;
//	}
//
//	public BaseController getBController(){
//		return this.bController;
//	}

	public void forward(Hashtable messages) {
		this.messages = messages;
	}

//	public void setHomeScreenHandler(HomeScreenHandler HomeScreenHandler) {
//		this.homeScreenHandler = HomeScreenHandler;
//	}


}
