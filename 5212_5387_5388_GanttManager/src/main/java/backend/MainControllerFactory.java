package backend;

public class MainControllerFactory {

	public IMainController createMainController() {
		return TaskManager.getInstance();
	}

}
