package fr.miage.sid.agentinternaute.agent;

import java.util.logging.Level;
import java.util.logging.Logger;

import fr.miage.sid.agentinternaute.agent.commons.AgentTypes;
import fr.miage.sid.agentinternaute.agent.mock.distributeur.AgentDistributeur;
import fr.miage.sid.agentinternaute.agent.mock.ereputation.AgentEReputation;
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
		this.initAgentDistributeurMock(1);
		this.initAgentDistributeurMock(2);
		this.initAgentEReputationMock(1);
	}

	/**
	 * @param name
	 */
	public void createNewAgentInternaute(String name) {
		try {
			Object[] arguments = { name };
			AgentController agent = this.agentContainer.createNewAgent(name, AgentInternaute.class.getName(), arguments);
			agent.start();
		} catch (ControllerException e) {
			LOGGER.log(Level.WARNING, "Couldn't create agent " + name + ", probably already exists.");
		}
	}

	/**
	 * @param name
	 */
	public void destroyNewAgentInternaute(String name) {
		try {
			agentContainer.getAgent(name).kill();
		} catch (ControllerException e) {
			LOGGER.log(Level.WARNING, "Couldn't destroy agent " + name + ", probably doesn't exists.");
		}
	}

	
	/**
	 * @param ourAgentInternaute
	 */
	public void initAgentDistributeurMock(int distribeurNumber) {
		String name = AgentTypes.AGENT_DISTRIBUTEUR + "_Mock_" + distribeurNumber;
		try {
			Object[] arguments = { name };
			AgentController agent = this.agentContainer.createNewAgent(name, AgentDistributeur.class.getName(), arguments);
			agent.start();
		} catch (ControllerException e) {
			LOGGER.log(Level.WARNING, "Couldn't create agent " + name + ", probably already exists.");
		}
	}
	
	/**
	 * @param ourAgentInternaute
	 */
	public void initAgentEReputationMock(int eReputationNumber) {
		String name = AgentTypes.AGENT_E_REPUTATION + "_Mock_" + eReputationNumber;
		try {
			Object[] arguments = { name };
			AgentController agent = this.agentContainer.createNewAgent(name, AgentEReputation.class.getName(), arguments);
			agent.start();
		} catch (ControllerException e) {
			LOGGER.log(Level.WARNING, "Couldn't create agent " + name + ", probably already exists.");
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
