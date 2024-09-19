import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> companies = List.of("A", "B", "C", "D", "E");
        List<String> programmers = List.of("1", "2", "3", "4", "5");

        // First set of preferences
        Map<String, List<String>> companyPreferences = new HashMap<>();
        companyPreferences.put("A", List.of("1", "2", "3", "4", "5"));
        companyPreferences.put("B", List.of("2", "3", "4", "5", "1"));
        companyPreferences.put("C", List.of("3", "4", "5", "1", "2"));
        companyPreferences.put("D", List.of("4", "5", "1", "2", "3"));
        companyPreferences.put("E", List.of("5", "1", "2", "3", "4"));

        Map<String, List<String>> programmerPreferences = new HashMap<>();
        programmerPreferences.put("1", List.of("A", "B", "C", "D", "E"));
        programmerPreferences.put("2", List.of("B", "C", "D", "E", "A"));
        programmerPreferences.put("3", List.of("C", "D", "E", "A", "B"));
        programmerPreferences.put("4", List.of("D", "E", "A", "B", "C"));
        programmerPreferences.put("5", List.of("E", "A", "B", "C", "D"));

        // Second set of preferences
        List<String> companies2 = List.of("A", "B", "C", "D", "E");
        List<String> programmers2 = List.of("1", "2", "3", "4", "5");

        Map<String, List<String>> companyPreferences2 = new HashMap<>();
        companyPreferences2.put("A", List.of("1", "2", "3", "4", "5"));
        companyPreferences2.put("B", List.of("1", "2", "3", "4", "5"));
        companyPreferences2.put("C", List.of("1", "2", "3", "4", "5"));
        companyPreferences2.put("D", List.of("1", "2", "3", "4", "5"));
        companyPreferences2.put("E", List.of("1", "2", "3", "4", "5"));

        Map<String, List<String>> programmerPreferences2 = new HashMap<>();
        programmerPreferences2.put("1", List.of("A", "B", "C", "D", "E"));
        programmerPreferences2.put("2", List.of("A", "B", "C", "D", "E"));
        programmerPreferences2.put("3", List.of("A", "B", "C", "D", "E"));
        programmerPreferences2.put("4", List.of("A", "B", "C", "D", "E"));
        programmerPreferences2.put("5", List.of("A", "B", "C", "D", "E"));

        // Third set of preferences
        List<String> companies3 = List.of("A", "B", "C", "D", "E");
        List<String> programmers3 = List.of("1", "2", "3", "4", "5");

        Map<String, List<String>> companyPreferences3 = new HashMap<>();
        companyPreferences3.put("A", List.of("2", "5", "1", "3", "4"));
        companyPreferences3.put("B", List.of("1", "2", "3", "4", "5"));
        companyPreferences3.put("C", List.of("5", "3", "2", "1", "4"));
        companyPreferences3.put("D", List.of("1", "3", "2", "4", "5"));
        companyPreferences3.put("E", List.of("2", "3", "5", "4", "1"));

        Map<String, List<String>> programmerPreferences3 = new HashMap<>();
        programmerPreferences3.put("1", List.of("E", "A", "D", "B", "C"));
        programmerPreferences3.put("2", List.of("D", "E", "B", "A", "C"));
        programmerPreferences3.put("3", List.of("D", "B", "C", "E", "A"));
        programmerPreferences3.put("4", List.of("C", "B", "D", "A", "E"));
        programmerPreferences3.put("5", List.of("A", "D", "B", "C", "E"));

        // Find pairings for the first set of preferences
        Map<String, String> pairings = findPairings(companies, programmers, companyPreferences, programmerPreferences);
        System.out.println("First set of pairings:");
        for (Map.Entry<String, String> pairing : pairings.entrySet()) {
            System.out.println(pairing.getKey() + " is paired with " + pairing.getValue());
        }
        int satisfactionScore1 = computeSatisfactionScore(pairings, companyPreferences, programmerPreferences);
        System.out.println("First set satisfaction score: " + satisfactionScore1);

        // Find pairings for the second set of preferences
        Map<String, String> pairings2 = findPairings(companies2, programmers2, companyPreferences2, programmerPreferences2);
        System.out.println("Second set of pairings:");
        for (Map.Entry<String, String> pairing : pairings2.entrySet()) {
            System.out.println(pairing.getKey() + " is paired with " + pairing.getValue());
        }
        int satisfactionScore2 = computeSatisfactionScore(pairings2, companyPreferences2, programmerPreferences2);
        System.out.println("Second set satisfaction score: " + satisfactionScore2);

        // Find pairings for the third set of preferences
        Map<String, String> pairings3 = findPairings(companies3, programmers3, companyPreferences3, programmerPreferences3);
        System.out.println("Third set of pairings:");
        for (Map.Entry<String, String> pairing : pairings3.entrySet()) {
            System.out.println(pairing.getKey() + " is paired with " + pairing.getValue());
        }
        int satisfactionScore3 = computeSatisfactionScore(pairings3, companyPreferences3, programmerPreferences3);
        System.out.println("Third set satisfaction score: " + satisfactionScore3);
    }

    public static Map<String, String> findPairings(List<String> companies, List<String> programmers,
                                                   Map<String, List<String>> companyPreferences,
                                                   Map<String, List<String>> programmerPreferences) {
        Map<String, String> companyPairings = new HashMap<>();
        Map<String, String> programmerPairings = new HashMap<>();
        Map<String, Integer> companyNextProposal = new HashMap<>();
        for (String company : companies) {
            companyNextProposal.put(company, 0);
        }

        Queue<String> freeCompanies = new LinkedList<>(companies);

        while (!freeCompanies.isEmpty()) {
            String company = freeCompanies.poll();
            List<String> preferences = companyPreferences.get(company);
            int proposalIndex = companyNextProposal.get(company);
            String programmer = preferences.get(proposalIndex);
            companyNextProposal.put(company, proposalIndex + 1);

            if (!programmerPairings.containsKey(programmer)) {
                companyPairings.put(company, programmer);
                programmerPairings.put(programmer, company);
            } else {
                String currentCompany = programmerPairings.get(programmer);
                List<String> programmerPref = programmerPreferences.get(programmer);
                if (programmerPref.indexOf(company) < programmerPref.indexOf(currentCompany)) {
                    companyPairings.put(company, programmer);
                    programmerPairings.put(programmer, company);
                    freeCompanies.add(currentCompany);
                } else {
                    freeCompanies.add(company);
                }
            }
        }

        return companyPairings;
    }

    public static int computeSatisfactionScore(Map<String, String> pairings,
                                               Map<String, List<String>> companyPreferences,
                                               Map<String, List<String>> programmerPreferences) {
        int score = 0;

        for (Map.Entry<String, String> pairing : pairings.entrySet()) {
            String company = pairing.getKey();
            String programmer = pairing.getValue();

            // Company preference score
            List<String> companyPrefList = companyPreferences.get(company);
            int companyScore = companyPrefList.indexOf(programmer) + 1;

            // Programmer preference score
            List<String> programmerPrefList = programmerPreferences.get(programmer);
            int programmerScore = programmerPrefList.indexOf(company) + 1;

            // Sum the scores
            score += companyScore + programmerScore;
        }

        return score;
    }
}