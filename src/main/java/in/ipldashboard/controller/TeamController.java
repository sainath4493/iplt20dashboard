package in.ipldashboard.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.ipldashboard.model.Match;
import in.ipldashboard.model.Team;
import in.ipldashboard.repository.MatchRepository;
import in.ipldashboard.repository.TeamRepository;

@RestController
@CrossOrigin
public class TeamController {

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	MatchRepository matchRepository;

	@GetMapping("/team")
	public Iterable<Team> getAllTeam() {
		return teamRepository.findAll();
	}

	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable String teamName) {
		Team team = this.teamRepository.findByTeamName(teamName);
		team.setMatches(matchRepository.findLatestMatchesbyTeam(teamName, 4));
		team.setYearsPlayedByTeam(matchRepository.getAllYearsofTeam(teamName));
		return team;
	}

	@GetMapping("/team/{teamName}/matches")
	public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam String year) {
		int yearInt = Integer.parseInt(year);
		LocalDate startDate = LocalDate.of(yearInt, 1, 1);
		LocalDate endDate = LocalDate.of(yearInt + 1, 1, 1);
		return matchRepository.getMatchesByTeamBetweenDates(teamName, startDate, endDate);
	}
}
