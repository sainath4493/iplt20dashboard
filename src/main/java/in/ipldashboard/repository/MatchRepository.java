package in.ipldashboard.repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.ipldashboard.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

	// Pageable : to access method page by page
	List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);


	@Query("select m from Match m where (m.team1= :teamName or m.team2= :teamName) and m.date between :dateStart and :dateEnd order by date desc")
	List<Match> getMatchesByTeamBetweenDates(@Param("teamName") String teamName,
			@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

	// List<Match> getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc
	// (String teamName1, LocalDate date1, LocalDate date2,
	// String teamName2,LocalDate date3, LocalDate date4);

	default List<Match> findLatestMatchesbyTeam(String teamName, int count) {
		// if page request is zero then count entity should be display
		return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
	}
	
	List<Match> getByTeam1OrTeam2(String teamName1, String teamName2);
	default List<Integer> getAllYearsofTeam(String teamName){
		List<Match> matches = getByTeam1OrTeam2(teamName,teamName);
		return matches.stream().map(match -> match.getDate().getYear()).distinct().sorted(Collections.reverseOrder()).collect(Collectors.toList());
	}
}
