package fr.miage.sid.agentinternaute.agent;

import java.util.logging.Level;
import java.util.logging.Logger;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

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

	public void createNewAgentInternaute(String name) {
		try {
			Object[] arguments = { name };
			AgentController agent = this.agentContainer.createNewAgent(name,
					"fr.miage.sid.agentinternaute.agent.AgentInternaute", arguments);
			agent.start();
		} catch (ControllerException e) {
			LOGGER.log(Level.WARNING, "Couldn't create agent " + name + ", probably already exists.");
		}
	}

	public void destroyNewAgentInternaute(String name) {
		try {
			agentContainer.getAgent(name).kill();
		} catch (ControllerException e) {
			LOGGER.log(Level.WARNING, "Couldn't destroy agent " + name + ", probably doesn't exists.");
		}
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
}
