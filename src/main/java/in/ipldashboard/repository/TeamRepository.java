package in.ipldashboard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.ipldashboard.model.Team;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long>{

	Team findByTeamName(String teamName);
}
