import helicopter.HelicopterAgent;
import hospital.HospitalAgent;
import patient.PatientAgent;
import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import sajas.core.Runtime;
import sajas.sim.repast3.Repast3Launcher;
import sajas.wrapper.ContainerController;
import uchicago.src.sim.analysis.OpenSequenceGraph;
import uchicago.src.sim.analysis.Sequence;
import uchicago.src.sim.engine.Schedule;
import uchicago.src.sim.engine.SimInit;
import jade.wrapper.StaleProxyException;
import uchicago.src.sim.gui.DisplaySurface;
import uchicago.src.sim.gui.Network2DDisplay;
import uchicago.src.sim.network.DefaultDrawableNode;
import utils.Logger;
import utils.ScenarioBuilder;
import utils.ScenarioReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map;

public class MedicalEmergencyHelicoptersLauncher extends Repast3Launcher {

    private int N = 50;

    private int N_CONTRACTS = 100;

    public static final boolean SEPARATE_CONTAINERS = true;
    private ContainerController mainContainer;
    private ContainerController hospitalContainer;
    private ContainerController helicopterContainer;
    private ContainerController patientContainer;
    private ResultsCollector resultsCollector;

    private int mapWidth;
    private int mapLength;
    private int numPatients;
    private int numHelicopters;
    private int numHospitals;
    private int minHospitalCapacity;
    private int maxHospitalCapacity;
    private int minHospitalOccupancy;
    private int maxHospitalOccupancy;
    private int minPatientSeverity;
    private int maxPatientSeverity;
    private int minHelicopterRange;
    private int maxHelicopterRange;
    private int minHelicopterSpeed;
    private int maxHelicopterSpeed;
    private int minPatientDelay;
    private int maxPatientDelay;

//    private final String jsonPath;

//    public MedicalEmergencyHelicoptersLauncher(String jsonPath) {
//        this.jsonPath = jsonPath;
//    }
    public MedicalEmergencyHelicoptersLauncher() {}

    public int getN() {
        return N;
    }

    public void setN(int N) {
        this.N = N;
    }

    public int getN_CONTRACTS() {
        return N_CONTRACTS;
    }

    public void setN_CONTRACTS(int N_CONTRACTS) {
        this.N_CONTRACTS = N_CONTRACTS;
    }

    @Override
    public String[] getInitParam() {
        return new String[]{
            "mapWidth",
            "mapLength",
            "numPatients",
            "numHelicopters",
            "numHospitals",
            "minHospitalCapacity",
            "maxHospitalCapacity",
            "minHospitalOccupancy",
            "maxHospitalOccupancy",
            "minPatientSeverity",
            "maxPatientSeverity",
            "minHelicopterRange",
            "maxHelicopterRange",
            "minHelicopterSpeed",
            "maxHelicopterSpeed",
            "minPatientDelay",
            "maxPatientDelay"
        };
    }

    @Override
    public String getName() { return "MedicalEmergencyHelicopters -- SAJaS Repast3 Test"; }

    @Override
    public void setup() {

        super.setup();

        Random random = new Random(this.getRngSeed());
        setMapWidth(100);
        setMapLength(100);

        //random
        /*setNumPatients(random.nextInt(50));
        setNumHelicopters(random.nextInt(15));
        setMinHospitalCapacity(random.nextInt(numPatients / 2));
        setMaxHospitalCapacity(random.nextInt(numPatients - minHospitalCapacity + 1) + minHospitalCapacity);
        setMinHospitalOccupancy(random.nextInt(50));
        setMaxHospitalOccupancy(random.nextInt(100 - minHospitalOccupancy + 1) + minHospitalOccupancy);
        setMinPatientSeverity(random.nextInt(50));
        setMaxPatientSeverity(random.nextInt(100 - minPatientSeverity + 1) + minPatientSeverity);
        setMinHelicopterRange(random.nextInt(100));
        setMaxHelicopterRange(random.nextInt(100 - minHelicopterRange + 1) + minHelicopterRange);
        setMinHelicopterSpeed(random.nextInt(15));
        setMaxHelicopterSpeed(random.nextInt(50 - minHelicopterSpeed + 1) + minHelicopterSpeed);
        setMinPatientDelay(0);
        setMaxPatientDelay(random.nextInt(20 - minPatientDelay + 1) + minPatientDelay);*/

        //for testing
        setNumPatients(50);
        setNumHelicopters(15);
        setNumHospitals(5);
        setMinHospitalCapacity(20);
        setMaxHospitalCapacity(100);
        setMinHospitalOccupancy(0);
        setMaxHospitalOccupancy(100);
        setMinPatientSeverity(1);;
        setMaxPatientSeverity(100);
        setMinHelicopterRange(30);
        setMaxHelicopterRange(100);
        setMinHelicopterSpeed(15);
        setMaxHelicopterSpeed(50);
        setMinPatientDelay(0);
        setMaxPatientDelay(20);
    }

    @Override
    public void begin() {

        super.begin();

        // display surfaces, spaces, displays, plots, ...
        // ...
        displayNetworkModel();
        displayCharts();
    }

    private DisplaySurface dsurf;
    private List<DefaultDrawableNode> nodes;
//    private int WIDTH = 0, HEIGHT = 0;

    private void buildNetworkModel() {
        nodes = new ArrayList<>();
        for (HospitalAgent hospital: ScenarioBuilder.getHospitalAgents()) {
//        for (HospitalAgent hospital: ScenarioReader.getHospitalAgents()) {
            nodes.add(hospital.getNode());
//            if (hospital.getLocation().getX() * 10 >= WIDTH) {
//                WIDTH = (int) (hospital.getLocation().getX() * 11);
//            }
//            if (hospital.getLocation().getY() * 10 >= HEIGHT) {
//                HEIGHT = (int) (hospital.getLocation().getY() * 11);
//            }
        }
        for (HelicopterAgent helicopter: ScenarioBuilder.getHelicopterAgents()) {
//        for (HelicopterAgent helicopter: ScenarioReader.getHelicopterAgents()) {
            nodes.add(helicopter.getNode());
//            if (helicopter.getLocation().getX() * 10 >= WIDTH) {
//                WIDTH = (int) (helicopter.getLocation().getX() * 11);
//            }
//            if (helicopter.getLocation().getY() * 10 >= HEIGHT) {
//                HEIGHT = (int) (helicopter.getLocation().getY() * 11);
//            }
        }
        for (PatientAgent patient: ScenarioBuilder.getPatientAgents()) {
//        for (PatientAgent patient: ScenarioReader.getPatientAgents()) {
            nodes.add(patient.getNode());
//            if (patient.getPosition().getX() * 10 >= WIDTH) {
//                WIDTH = (int) (patient.getPosition().getX() * 11);
//            }
//            if (patient.getPosition().getY() * 10 >= HEIGHT) {
//                HEIGHT = (int) (patient.getPosition().getY() * 11);
//            }
        }
//        WIDTH += 50;
//        HEIGHT += 50;
    }

    private void displayNetworkModel() {

        if (dsurf != null) {
            dsurf.dispose();
        }

        dsurf = new DisplaySurface(this, "Service Consumer/Provider Display");
        registerDisplaySurface("Service Consumer/Provider Display", dsurf);
        Network2DDisplay display = new Network2DDisplay(nodes, mapWidth * 10 + 100, mapLength * 10 + 100);
        dsurf.addDisplayableProbeable(display, "Network Display");
        dsurf.addZoomable(display);
        addSimEventListener(dsurf);
        dsurf.display();

    }

    OpenSequenceGraph totalTreatedGraph;
    OpenSequenceGraph treatmentTimeGraph;
    OpenSequenceGraph treatmentQualityGraph;

    private void buildCharts() {
        // Patients vs Treated Patients
        totalTreatedGraph = new OpenSequenceGraph("Patients vs Treated Patients", this);

        totalTreatedGraph.setXRange(0, 2);
        totalTreatedGraph.setYRange(0, 20);
        totalTreatedGraph.setAxisTitles("time", "Quantity");

        totalTreatedGraph.addSequence("Total Patients", new Sequence() {
            public double getSValue() {
                return resultsCollector.getTimeForPatient().size();
            }
        });

        totalTreatedGraph.addSequence("Treated Patients", new Sequence() {
            public double getSValue() {
                int treatedPatients = 0;
                for (Map.Entry<AID, ArrayList<Long>> entry : resultsCollector.getTimeForPatient().entrySet()) {
                    if (entry.getValue().size() == 3) {
                        treatedPatients++;
                    }
                }
                return treatedPatients;
            }
        });

        // Average Treatment Time
        treatmentTimeGraph = new OpenSequenceGraph("Average Treatment Time", this);

        treatmentTimeGraph.setXRange(0, 2);
        treatmentTimeGraph.setYRange(0, 20);
        treatmentTimeGraph.setAxisTitles("time", "");

        treatmentTimeGraph.addSequence("Treatment Time", new Sequence() {
            public double getSValue() {
                int sum = 0;
                int treatedPatients = 0;
                for (Map.Entry<AID, ArrayList<Long>> entry : resultsCollector.getTimeForPatient().entrySet()) {
                    if (entry.getValue().size() == 3) {
                        sum += (entry.getValue().get(2) - entry.getValue().get(0)) / 1000;
                        treatedPatients++;
                    }
                }
                if (resultsCollector.getTimeForPatient().size() == 0) {
                    return 0;
                }

                return (double) sum / (double) treatedPatients;
            }
        });

        // Average Treatment Quality
        treatmentQualityGraph = new OpenSequenceGraph("Average Treatment Quality", this);

        treatmentQualityGraph.setXRange(0, 2);
        treatmentQualityGraph.setYRange(0, 20);
        treatmentQualityGraph.setAxisTitles("time", "");

        treatmentQualityGraph.addSequence("Treatment Quality", new Sequence() {
            public double getSValue() {
                int sum = 0;
                int treatedPatients = 0;

                for (Map.Entry<AID, Integer> entry : resultsCollector.getTreatmentQualityForPatient().entrySet()) {
                    sum += entry.getValue();
                    treatedPatients++;
                }

                if (resultsCollector.getTreatmentQualityForPatient().size() == 0) {
                    return 0;
                }
                return (double) sum / (double) treatedPatients;
            }
        });

    }

    private void displayCharts() {
        totalTreatedGraph.display();
        getSchedule().scheduleActionAtInterval(1, totalTreatedGraph, "step", Schedule.LAST);

        treatmentTimeGraph.display();
        getSchedule().scheduleActionAtInterval(1, treatmentTimeGraph, "step", Schedule.LAST);

        treatmentQualityGraph.display();
        getSchedule().scheduleActionAtInterval(1, treatmentQualityGraph, "step", Schedule.LAST);
    }

    @Override
    protected void launchJADE() {

        Runtime rt = Runtime.instance();
        Profile p1 = new ProfileImpl();
        p1.setParameter(Profile.GUI, "true");
        mainContainer = rt.createMainContainer(p1);

        if (SEPARATE_CONTAINERS) {
            Profile hospitalProfile = new ProfileImpl();
            hospitalProfile.setParameter(Profile.CONTAINER_NAME, "Hospitals");
            hospitalContainer = rt.createAgentContainer(hospitalProfile);
            Profile helicopterProfile = new ProfileImpl();
            helicopterProfile.setParameter(Profile.CONTAINER_NAME, "Helicopters");
            helicopterContainer = rt.createAgentContainer(helicopterProfile);
            Profile patientProfile = new ProfileImpl();
            patientProfile.setParameter(Profile.CONTAINER_NAME, "Patients");
            patientContainer = rt.createAgentContainer(patientProfile);
        } else {
            hospitalContainer = mainContainer;
            helicopterContainer = mainContainer;
            patientContainer = mainContainer;
        }

        launchAgents();

    }

    private void launchAgents() {

        int N_PATIENTS = N;
        int N_HELICOPTERS = N * 2 / 3;
        int N_HOSPITALS = N / 5;

        try {
            // create results collector
            resultsCollector = new ResultsCollector();
            mainContainer.acceptNewAgent("ResultsCollector", resultsCollector).start();
            AID resultsCollectorAID = resultsCollector.getAID();
            ScenarioBuilder.buildScenario(
                    hospitalContainer, numHospitals,
                    helicopterContainer, numHelicopters,
                    patientContainer, numPatients,
                    resultsCollectorAID,
                    mapWidth, mapLength,
                    minHospitalCapacity, maxHospitalCapacity,
                    minHospitalOccupancy, maxHospitalOccupancy,
                    minPatientSeverity, maxPatientSeverity,
                    minHelicopterRange, maxHelicopterRange,
                    minHelicopterSpeed, maxHelicopterSpeed,
                    minPatientDelay, maxPatientDelay
            );
//            ScenarioReader.readScenario(
//                    hospitalContainer, helicopterContainer, patientContainer, this.jsonPath, resultsCollectorAID
//            );
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

        buildNetworkModel();
        buildCharts();
    }



    /**
     * Launching Repast3
     *
     * @param args
     */
    public static void main(String[] args) {

//        if (args.length != 2) {
//            System.out.println("Usage: java MedicalEmergencyHelicopters <json-file> [ <test> ]\n"
//                    + "       json-file:  name of file (from test_files directory) containing helicopters, hospitals and patients\n"
//                    + "       test:       FALSE (default) if logger should document execution, TRUE if testing only");
//            System.exit(1);
//        }
        if (args.length != 1) {
            System.out.println("Usage: java MedicalEmergencyHelicopters <json-file> [ <test> ]\n"
                    + "       test:       FALSE (default) if logger should document execution, TRUE if testing only");
            System.exit(1);
        }

//        String jsonPath = "test_files/" + args[0] + ".json";
//        Logger.init(Boolean.parseBoolean(args[1]));
        Logger.init(Boolean.parseBoolean(args[0]));

        SimInit init = new SimInit();
        init.setNumRuns(1);   // works only in batch mode
//        init.loadModel(new MedicalEmergencyHelicoptersLauncher(jsonPath), null, false);
        init.loadModel(new MedicalEmergencyHelicoptersLauncher(), null, false);

    }

    public void setMapWidth(int mapWidth) { this.mapWidth = mapWidth; }
    public int getMapWidth() { return this.mapWidth; }
    public void setMapLength(int mapLength) { this.mapLength = mapLength; }
    public int getMapLength() { return this.mapLength; }
    public void setNumPatients(int numPatients) { this.numPatients = numPatients; }
    public int getNumPatients() { return this.numPatients; }
    public void setNumHelicopters(int numHelicopters) { this.numHelicopters = numHelicopters; }
    public int getNumHelicopters() { return this.numHelicopters; }
    public void setNumHospitals(int numHospitals) { this.numHospitals = numHospitals; }
    public int getNumHospitals() { return this.numHospitals; }
    public void setMinHospitalCapacity(int minHospitalCapacity) { this.minHospitalCapacity = minHospitalCapacity; }
    public int getMinHospitalCapacity() { return this.minHospitalCapacity; }
    public void setMaxHospitalCapacity(int maxHospitalCapacity) { this.maxHospitalCapacity = maxHospitalCapacity; }
    public int getMaxHospitalCapacity() { return this.maxHospitalCapacity; }
    public void setMinHospitalOccupancy(int minHospitalOccupancy) { this.minHospitalOccupancy = minHospitalOccupancy; }
    public int getMinHospitalOccupancy() { return this.minHospitalOccupancy; }
    public void setMaxHospitalOccupancy(int maxHospitalOccupancy) { this.maxHospitalOccupancy = maxHospitalOccupancy; }
    public int getMaxHospitalOccupancy() { return this.maxHospitalOccupancy; }
    public void setMinPatientSeverity(int minPatientSeverity) { this.minPatientSeverity = minPatientSeverity; }
    public int getMinPatientSeverity() { return this.minPatientSeverity; }
    public void setMaxPatientSeverity(int maxPatientSeverity) { this.maxPatientSeverity = maxPatientSeverity; }
    public int getMaxPatientSeverity() { return this.maxPatientSeverity; }
    public void setMinHelicopterRange(int minHelicopterRange) { this.minHelicopterRange = minHelicopterRange; }
    public int getMinHelicopterRange() { return this.minHelicopterRange; }
    public void setMaxHelicopterRange(int maxHelicopterRange) { this.maxHelicopterRange = maxHelicopterRange; }
    public int getMaxHelicopterRange() { return this.maxHelicopterRange; }
    public void setMinHelicopterSpeed(int minHelicopterSpeed) { this.minHelicopterSpeed = minHelicopterSpeed; }
    public int getMinHelicopterSpeed() { return this.minHelicopterSpeed; }
    public void setMaxHelicopterSpeed(int maxHelicopterSpeed) { this.maxHelicopterSpeed = maxHelicopterSpeed; }
    public int getMaxHelicopterSpeed() { return this.maxHelicopterSpeed; }
    public void setMinPatientDelay(int minPatientDelay) { this.minPatientDelay = minPatientDelay; }
    public int getMinPatientDelay() { return this.minPatientDelay; }
    public void setMaxPatientDelay(int maxPatientDelay) { this.maxPatientDelay = maxPatientDelay; }
    public int getMaxPatientDelay() { return this.maxPatientDelay; }

  }
