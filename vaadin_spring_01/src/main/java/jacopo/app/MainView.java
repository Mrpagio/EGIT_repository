package jacopo.app;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import jacopo.app.GreetService;
import jacopo.core.model.GestoreModel;
import jacopo.core.persistence.ConnessioneDb;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField textField;

	/**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service The message service. Automatically injected Spring managed bean.
     */
    public MainView(@Autowired GreetService service) {

        // USe TextField for standard text input
        this.textField = new TextField("I'm your father");

        // Button click listeners can be defined as lambda expressions
        /* funzionante
         * Button button = new Button("Say hello, povero stronzo",
        
                e -> Notification.show(service.greet(textField.getValue())));
		*/
        Button button = new Button("Say hello, povero stronzo", e -> textfieldEvent(service));
        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(textField, button);
        
        /*Inizializzo il GestoreModel
         * Inizializzo la connessione a null
         * Tentativo di connessione
         * Scrivo su tentativo l'esito della connessione
         * Incapsulo la String in Html
         * Aggiungo il paragarfo html a MainView
         */
        GestoreModel ges = new GestoreModel();
        String user = "root";
        String psw = "Jacopo92";
        ConnessioneDb db = null;
        String tentativo = "";
        try
        {
        	 db = new ConnessioneDb(ges, user, psw);
        	 tentativo = db.getStatement().toString();
        }
        catch(Exception e)
        {
        	tentativo = e.getMessage();
        }
        Html provaConn = new Html("<p>Risultato: " + tentativo + "</p>");
        this.add(provaConn);
        
    }
    
    /*funzione che viene richiamata non appena viene premuto il bottone 
     * legge il valore del textfield
     * lo visualizza a schermo
     * resetta il valore del textfield
     * l'oggetto GreetService, passato alla funziona, controlla i dati immessi
     */
    
    
    private void textfieldEvent(GreetService gs)
    {
    	Notification.show(gs.greet(textField.getValue()));
    	this.textField.setValue("");
    }

}
