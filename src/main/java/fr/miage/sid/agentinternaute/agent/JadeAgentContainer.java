package fr.miage.sid.agentinternaute.agent;

import java.util.logging.Level;
import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.controller.HistoryController;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public final class JadeAgentContainer {
	
	private static final Logger LOGGER = Logger.getLogger(JadeAgentContainer.class.getName());

	private static JadeAgentContainer INSTANCE;

	private AgentContainer agentContainer;

	private JadeAgentContainer() {
		jade.core.Runtime rt = jade.core.Runtime.instance();
		rt.setCloseVM(true);
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.PLATFORM_ID, "P1");
		profile.setParameter(Profile.CONTAINER_NAME, "Container internautes");
		profile.setParameter(Profile.MAIN_HOST, "localhost");
		profile.setParameter(Profile.MAIN_PORT, "1099");
		this.agentContainer = rt.createAgentContainer(profile);
	}

	public static JadeAgentContainer getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new JadeAgentContainer();
		}
		return INSTANCE;
	}

	public static void setINSTANCE(JadeAgentContainer instance) {
		INSTANCE = instance;
	}

	public AgentContainer getAgentContainer() {
		return this.agentContainer;
	}

	public void setAgentContainer(AgentContainer agentContainer) {
		this.agentContainer = agentContainer;
	}

	public void createNewAgentInternaute(String name) {
		try {
			if (this.agentContainer.getAgent(name, false) == null) {
				Object[] arguments = { name };
				AgentController agent = this.agentContainer.createNewAgent(name,
						"fr.miage.sid.agentinternaute.agent.AgentInternaute", arguments);
				agent.start();
			} else {
				LOGGER.log(Level.WARNING, "Agent " + name + " already exists");
			}
		} catch (ControllerException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Couldn't create agent " + name);
		}
	}
	
	public void destroyNewAgentInternaute(String name) {
	
	}
}
