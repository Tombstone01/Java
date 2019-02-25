package csc2a.ham;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

/** @author Kananelo Khotle
 *  @version 0.0.1
 */
public class TeamFileHandler {
    
    /** Reads information from text file 
     *  about a team.
     *  @param filename of the file to be read 
     *  @return Team object.
     */
    public Team readTeam(String filename) {

        // Create an instance of file.
        File file = new File(filename);

        // Check if file is opened.
        if (!checkFile(file)) return null;

        /** Reads the file and returns
         *  an instance of a team class.
         */
        Team team = readFile(file);

        return team;
    }

    /** @param File to be checked.
     *  @return true or false.
     */
    private boolean checkFile(File file) {
        return file.exists() ? true : false;
    }

    /** @param File to be read.
     *  @return the team object.
     */
    private Team readFile(File file) {
        
        Scanner textin = null;
        int counter = 0;

        String teamDetails = "";
        String leaderDetails = "";
        String teamMemberDetails = "";

        try {

            // Read file using scanner object.
            textin = new Scanner(file);
            
            // Read each line in the file.
            while (textin.hasNext()) {

                if (counter == 0) {
                    teamDetails = textin.nextLine();
                } else if (counter == 1) {
                    leaderDetails = textin.nextLine();
                } else {
                    teamMemberDetails += textin.nextLine() + "\t";
                }

                // Keeps track of line number with the file.
                counter+=1;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } finally {
            textin.close();
        }

        Team team = createTeam(teamDetails, leaderDetails, teamMemberDetails, counter);

        return team;
    }


    /** @param team_info, leader_info, member info and counter
     *  @return Team object.
     */
    public Team createTeam(String team_info, String leader_info, String member_info, int counter) {
        
        Team team = teamDetails(team_info, counter);
        Hero leader = createLeader(leader_info);
        
        team.setLeader(leader);

        team = createMembers(member_info, team);

        return team;
    }

    /** @param team and counter.
     *  @return Team
     */
    public Team teamDetails(String team, int counter) {
        
        String[] teamDetailsArr = team.split("\t");

        String teamID = teamDetailsArr[0];
        String teamName = teamDetailsArr[1];
        String teamSlogan = teamDetailsArr[2];

        return new Team(teamID, teamName, teamSlogan, counter-2);
    }
    /** @param leader
     *  @return Hero instance.
     */
    public Hero createLeader(String leader) {

        String[] leaderDetailArr = leader.split("\t");

        String ldrID = leaderDetailArr[0];
        String ldrName = leaderDetailArr[1];

        Hero hero = new Hero(ldrID, ldrName);
        
        String ldrStrength = leaderDetailArr[2].split("\\|")[0];
        ldrStrength = ldrStrength.substring(1);
        hero.setStrength(ldrStrength);
        
        String ldrWeakness = leaderDetailArr[2].split("\\|")[1];
        ldrWeakness = ldrWeakness.substring(0, ldrWeakness.length() - 1);
        hero.setWeakness(ldrWeakness);
        
        String ldrRenown = leaderDetailArr[3];
        hero.setRenown(ldrRenown);

        return hero;
    }

    /** @param members and teams
     *  @return Team
     */
    private Team createMembers(String members, Team team) {
        
        String[] members_arr = members.split("\t");

        if (!checkTeams(members_arr)) {
            
            System.out.println("Something wrong with team members.");
            System.exit(0);
        }

        team = addMembers(members_arr, team);

        return team;
    }

    /** @param members array
     *  @return true of false;
     */
    private boolean checkTeams(String[] members) {
        return members.length % 4 == 0 ? true : false;
    }

    /** @param members string and team
     *  @return team
     */
    private Team addMembers(String[] members, Team team) {

        for (int i = 0; i <  members.length / 4; i++) {
            
            String memID = members[0 + (4 * i)];
            String memName = members[1 + (4 * i)];
            String strength = members[2 + (4 * i)].split("\\|")[0];
            strength = strength.substring(1);

            String weakness = members[2 + (4 * i)].split("\\|")[1];
            weakness = weakness.substring(0, weakness.length() - 1);

            String reown = members[3 + (4 * i)];

            Hero member = new Hero(memID, memName);
            member.setStrength(strength);
            member.setWeakness(weakness);
            member.setRenown(reown);

            // Add other member information.
            team.addMember(member);
        }

        return team;
    }
}