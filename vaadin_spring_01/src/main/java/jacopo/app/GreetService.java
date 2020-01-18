package jacopo.app;

import java.io.Serializable;

import org.springframework.stereotype.Service;

@Service
public class GreetService implements Serializable 
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user";
        } else {
            return "Hello " + name + " sei anche coglione";
        }
    }

}
