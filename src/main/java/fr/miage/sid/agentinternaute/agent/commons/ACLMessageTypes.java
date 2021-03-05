package fr.miage.sid.agentinternaute.agent.commons;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
public enum ACLMessageTypes {
	REQUEST_SEARCH_TITLE ("search-title"),
	REQUEST_SEARCH_FILTER("search-filter"),
	REQUEST_PURCHASE ("purchase");
	
	private final String value;
	
	ACLMessageTypes(String value) {
        this.value = value;
    }

	public String getValue() {
		return value;
	}
}