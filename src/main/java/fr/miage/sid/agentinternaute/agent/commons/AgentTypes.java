package fr.miage.sid.agentinternaute.agent.commons;

/**
 * @author Louis MASSICARD (user name : louis)
 * @version 
 * @since %G% - %U% (%I%)
 *
 */
public enum AgentTypes {
	AGENT_INTERNAUTE ("internaute"),
	AGENT_DISTRIBUTEUR ("distributeur"),
	AGENT_E_REPUTATION ("e-reputation");
	
	private final String value;
	
	AgentTypes(String value) {
        this.value = value;
    }

	public String getValue() {
		return value;
	}
}