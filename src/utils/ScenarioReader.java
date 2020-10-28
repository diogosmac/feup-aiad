package utils;

import hospital.HospitalAgent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import patient.PatientAgent;
import helicopter.HelicopterAgent;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ScenarioReader {

    private static int hospitalID = 1;
    private static int helicopterID = 1;
    private static int patientID = 1;

    public static void readScenario(AgentContainer container, String filename) throws StaleProxyException {
        JSONParser parser = new JSONParser();
        JSONObject obj;
        try {
            obj = (JSONObject) parser.parse(new FileReader(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        readHospitals(container, obj);
        readHelicopters(container, obj);
        readPatients(container, obj);
    }

    private static void readHospitals(AgentContainer container, JSONObject obj) throws StaleProxyException {
        JSONArray hospitals = (JSONArray) obj.get("hospitals");
        for (Object o : hospitals) {
            JSONObject hospital = (JSONObject) o;
            String x, y;
            x = hospital.get("x").toString();
            y = hospital.get("y").toString();
            List<String> argList = new ArrayList<>();
            argList.add(x);
            argList.add(y);
            JSONArray specialties = (JSONArray) hospital.get("specialties");
            for (Object spec : specialties) {
                JSONObject specialty = (JSONObject) spec;
                String name, competence;
                name = specialty.get("name").toString();
                competence = specialty.get("competence").toString();
                argList.add(name);
                argList.add(competence);
            }
            String[] args = argList.toArray(String[]::new);
            AgentController hos = container.createNewAgent(
                    "hospital" + hospitalID++,
                    HospitalAgent.class.getName(),
                    args
            );
            hos.start();
        }
    }

    private static void readHelicopters(AgentContainer container, JSONObject obj) throws StaleProxyException {
        JSONArray helicopters = (JSONArray) obj.get("helicopters");
        for (Object o : helicopters) {
            JSONObject helicopter = (JSONObject) o;
            String x, y;
            JSONObject location = (JSONObject) helicopter.get("location");
            x = location.get("x").toString();
            y = location.get("y").toString();
            String[] args = {x, y};
            AgentController hel = container.createNewAgent(
                    "helicopter" + helicopterID++,
                    HelicopterAgent.class.getName(),
                    args
            );
            hel.start();
        }
    }

    private static void readPatients(AgentContainer container, JSONObject obj) throws StaleProxyException{
        JSONArray patients = (JSONArray) obj.get("patients");
        for (Object o : patients) {
            JSONObject patient = (JSONObject) o;
            String x, y, injuryType, injurySeverity;
            JSONObject location = (JSONObject) patient.get("location");
            x = location.get("x").toString();
            y = location.get("y").toString();
            JSONObject injury = (JSONObject) patient.get("injury");
            injuryType = injury.get("type").toString();
            injurySeverity = injury.get("severity").toString();
            String[] args = {x, y, injuryType, injurySeverity};
            AgentController pat = container.createNewAgent(
                    "patient" + patientID++,
                    PatientAgent.class.getName(),
                    args
            );
            pat.start();
        }
    }

}
