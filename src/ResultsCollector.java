import jade.core.AID;
import jade.lang.acl.UnreadableException;
import sajas.core.Agent;
import sajas.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utils.PatientFinished;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultsCollector extends Agent {

	private static final long serialVersionUID = 1L;
	
	private int nResults;
	
	private long startTime = System.currentTimeMillis();

	//arrayList é um par onde guardamos: 1- date em que o paciente escolhe o helicopter"; 2- o tempo em que o paciente chega ao hospital. (helicopter finishes traveling behaviour)
	private Map<AID, ArrayList<Long>> timeForPatient = new HashMap<>();
	private Map<AID, Integer> treatmentQualityForPatient = new HashMap<>();
	
	public ResultsCollector(int nResults) {
		this.nResults = nResults;
	}
	
	@Override
	public void setup() {
		
		//todo - stuff

		// results listener
		addBehaviour(new ResultsListener());
	}
	
	protected void printResults() {
		long took = System.currentTimeMillis() - startTime;
		System.out.println("Took: \t" + took);

		//todo - actually print results
	}

	
	private class ResultsListener extends CyclicBehaviour {

		private static final long serialVersionUID = 1L;

		private MessageTemplate template = MessageTemplate.MatchPerformative((ACLMessage.INFORM));

		@Override
		public void action() {

			// ouvir todas as mensagens INFORM enviadas pelo paciente para o results collector -> para cada uma guardar no map o aid do paciente + o tempo autal
			// ouvir todas as mensagens INFORM enviadas pelo helicoptero para o results collector
			// 		-> para cada uma guardar no map, na entrada com o aid do paciente (que vem na mensagem) o tempo atual (no segundo elemento da lista)
			//		-> para cada uma guardar no outro map, uma entrada com o aid do paciente e a qualidade do serviço do hospital (que vem na mensagem)


			ACLMessage msg = myAgent.receive(template);
			Object content = null;

			try {
				content = msg.getContentObject();
			} catch (UnreadableException e) {
				e.printStackTrace();
			}

			//mensagem do paciente
			if (content instanceof String){
				AID patient = msg.getSender();

				ArrayList<Long> times = new ArrayList<>();
				times.add(System.currentTimeMillis());
				timeForPatient.put(patient, times);
			}
			//mensagem do helicopter
			else if (content instanceof PatientFinished){
				PatientFinished patientFinished = (PatientFinished)content;
				AID patient = patientFinished.getPatient();
				Integer hospitalSuitability = patientFinished.getHospitalSuitability();

				timeForPatient.get(patient).add(System.currentTimeMillis());
				treatmentQualityForPatient.put(patient, hospitalSuitability);
			}

		}
		
	}

}
