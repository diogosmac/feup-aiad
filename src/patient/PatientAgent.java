package patient;

import sajas.core.Agent;
import jade.core.AID;
import sajas.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import uchicago.src.sim.network.DefaultDrawableNode;
import utils.AgentType;
import utils.Location;
import utils.Logger;
import injury.Injury;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PatientAgent extends Agent {
    
    private Injury injury;
    private Location position;
    private int waitPeriod = 0;
    private ArrayList<AID> responders = new ArrayList<>();
    private int numberOfResponders;
    private AID resultsCollector = null;
    private DefaultDrawableNode node;

    public PatientAgent() {}

    public PatientAgent(Object[] args) {
        this.setArguments(args);

        String[] argsString = Arrays.copyOf(args, args.length, String[].class);
        String x = argsString[0], y = argsString[1];
        this.position = new Location(Integer.parseInt(x), Integer.parseInt(y));
    }

    public Injury getInjury() { return injury; }

    public ArrayList<AID> getResponders(){
        return responders;
    }

    public int getNumberOfResponders() {
        return numberOfResponders;
    }

    public Location getPosition() {
        return position;
    }

    public AID getResultsCollector(){ return resultsCollector; }

    public void setResultsCollector(AID resultsCollector){ this.resultsCollector = resultsCollector; }

    public DefaultDrawableNode getNode() {
        return node;
    }

    public void setNode(DefaultDrawableNode node) {
        this.node = node;
    }

    public void setup() {

        Object[] objArgs = getArguments();
        String[] args = Arrays.copyOf(objArgs, objArgs.length, String[].class);

        String x = args[0], y = args[1], injuryType = args[2], injurySeverity = args[3];
        this.injury = new Injury(injuryType, Integer.parseInt(injurySeverity));
        this.position = new Location(Integer.parseInt(x), Integer.parseInt(y));

        if (args.length > 4) {
            this.waitPeriod = Integer.parseInt(args[4]);
        }

        if (this.dfSearch()) {
            numberOfResponders = responders.size();

            String logMessage = getAID().getLocalName() + ": " +
                    "trying to delegate action [ pick-me-up ]" +
                    " to one of " + numberOfResponders + " helicopters";
            Logger.writeLog(logMessage, Logger.PATIENT);

            if (this.waitPeriod == 0) {
                addBehaviour(new PatientNetInitiator(this, numberOfResponders, new ACLMessage(ACLMessage.CFP)));
            }
            else {
                logMessage = getLocalName() + ": " +
                        "waiting [ " + waitPeriod + " ] seconds before sending out request";
                Logger.writeLog(logMessage, Logger.PATIENT);
                addBehaviour(new PatientWaitBehaviour(this, this.waitPeriod * 1000));
            }
        }
        else {
            String logMessage = getLocalName() + ": " +
                    "no responder specified";
            Logger.writeLog(logMessage, Logger.PATIENT);
        }

    }

    protected void takeDown() {
        String logMessage = getLocalName() + ": shutting down";
        Logger.writeLog(logMessage, Logger.PATIENT);
    }

    private boolean dfSearch() {
        responders = new ArrayList<>();
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(AgentType.HELICOPTER);
        template.addServices(serviceDescription);
        try {
            DFAgentDescription[] result = DFService.search(this, template);
            for (DFAgentDescription dfAgentDescription : result) {
                String logMessage = getLocalName() + ": " +
                        "found [ " + dfAgentDescription.getName().getLocalName() + " ]";
                Logger.writeLog(logMessage, Logger.PATIENT);
                // Add to list and/to initiate ContractNet to each one of them
                responders.add(dfAgentDescription.getName());
            }
        } catch(FIPAException fe) {
            fe.printStackTrace();
        }

        return responders != null && responders.size() > 0;
    }
}
