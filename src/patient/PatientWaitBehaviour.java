package patient;

import sajas.core.Agent;
import sajas.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class PatientWaitBehaviour extends WakerBehaviour {
    PatientAgent patientAgent;

    public PatientWaitBehaviour(Agent a, long timeout) {
        super(a, timeout);
        patientAgent = (PatientAgent) a;
    }

    @Override
    protected void onWake() {
        patientAgent.addBehaviour(
                new PatientNetInitiator(
                        this.patientAgent, this.patientAgent.getNumberOfResponders(), new ACLMessage(ACLMessage.CFP)));
    }
}
