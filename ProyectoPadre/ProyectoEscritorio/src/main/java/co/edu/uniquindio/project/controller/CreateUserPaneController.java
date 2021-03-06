/**
 * Sample Skeleton for 'CreateUserPane.fxml' Controller Class
 */

package co.edu.uniquindio.project.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.uniquindio.project.exceptions.RepeatedUserException;
import co.edu.uniquindio.project.model.DelegateTest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * The class CreateUserPaneController
 *
 * @author Cristian G. Sanchez Pineda
 * @author Luisa F. Cotte Sanchez
 */
public class CreateUserPaneController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="cedulaField"
	private TextField cedulaField; // Value injected by FXMLLoader

	@FXML // fx:id="emailField"
	private TextField emailField; // Value injected by FXMLLoader

	@FXML // fx:id="passwordField"
	private PasswordField passwordField; // Value injected by FXMLLoader

	@FXML // fx:id="rePasswordField"
	private PasswordField rePasswordField; // Value injected by FXMLLoader

	/** The init controller. */
	private InitController initController;

	/**
	 * Handle create user button.
	 *
	 * @param event the event
	 */
	@FXML
	void handleCreateUserButton(ActionEvent event) {
		DelegateTest delegate = DelegateTest.delegateTest;
		if (isInputValid()) {
			String code = cedulaField.getText();
			String email = emailField.getText();
			String password = passwordField.getText();
			try {
				delegate.logginUser(code, email, password);
				InitController.showAlert("Bienvenido: " + email + " ha unihogar", "BIENVENIDO", "",
						AlertType.INFORMATION);
				cedulaField.setText("");
				emailField.setText("");
				passwordField.setText("");
				rePasswordField.setText("");
				cedulaField.setPromptText("Ingresa tu cedula");
				emailField.setPromptText("Ingresa tu correo electrónico");
				passwordField.setPromptText("Ingresa tu contraseña");
				rePasswordField.setPromptText("Repite tu contraseña");
			} catch (RepeatedUserException e) {
				InitController.showAlert(e.getMessage(), "ERROR", "", AlertType.ERROR);
			}
			// Se tiene que llevar a la interfaz de inicio de la aplicación
			// Haciendo un splash
		}

	}

	/**
	 * Initialize.
	 */
	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert cedulaField != null : "fx:id=\"cedulaField\" was not injected: check your FXML file 'CreateUserPane.fxml'.";
		assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'CreateUserPane.fxml'.";
		assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'CreateUserPane.fxml'.";
		assert rePasswordField != null : "fx:id=\"rePasswordField\" was not injected: check your FXML file 'CreateUserPane.fxml'.";
	}

	/**
	 * Checks if is input valid.
	 *
	 * @return true, if is input valid
	 */
	public boolean isInputValid() {
		String errorMessage = "";
		boolean isValid = false;
		if (cedulaField.getText().isEmpty())
			errorMessage += "Debes ingresar la cedula\n";
		else
			try {
				Integer.parseInt(cedulaField.getText());
			} catch (Exception e) {
				errorMessage += "La cédula debe contener solo caracteres numéricos\n";
			}
		if (emailField.getText().isEmpty())// Validar correo electrónico
			errorMessage += "Debes ingresar el correo electrónico\n";
		else if (!validateEmail())
			errorMessage += "No es un correo electrónico valido\n";
		if (passwordField.getText().isEmpty())
			errorMessage += "Debes ingresar la contraseña\n";
		if (rePasswordField.getText().isEmpty())
			errorMessage += "Debes repetir la contraseña\n";
		else if (!validateSamePassword())
			errorMessage += "Las contraseñas deben ser iguales!";
		if (errorMessage.length() == 0)
			isValid = true;
		else
			InitController.showAlert(errorMessage, "ADVERTENCIA!", "", AlertType.WARNING);
		return isValid;
	}

	/**
	 * Validate same password.
	 *
	 * @return true, if successful
	 */
	public boolean validateSamePassword() {
//		String pas1 = passwordField.getText();
//		pas1 = pas1.replaceAll("\\s+", "");
//		String pas2 = rePasswordField.getText();
//		pas2 = pas2.replaceAll("\\s+", "");
		return passwordField.getText().equals(rePasswordField.getText());
	}

	/**
	 * Based on: https://stackoverflow.com/questions/8204680/java-regex-email
	 */
	public final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Validate email.
	 *
	 * @return true, if successful
	 */
	public boolean validateEmail() {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailField.getText());
		return matcher.find();
	}

	/**
	 * Gets the inits the controller.
	 *
	 * @return the inits the controller
	 */
	public InitController getInitController() {
		return initController;
	}

	/**
	 * Sets the inits the controller.
	 *
	 * @param initController the new inits the controller
	 */
	public void setInitController(InitController initController) {
		this.initController = initController;
	}

	/**
	 * Handle back button.
	 *
	 * @param event the event
	 */
	@FXML
	void handleBackButton(ActionEvent event) {
		initController.loadLogginPane();
	}
}
