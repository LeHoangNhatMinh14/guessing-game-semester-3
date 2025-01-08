package assignment.individualtrack.business.impl.game;

import assignment.individualtrack.business.intefaces.GetPlayerGameHistoryUseCase;
import assignment.individualtrack.domain.Game.GetPlayerGameHistoryRequest;
import assignment.individualtrack.domain.Game.GetPlayerGameHistoryResponse;
import assignment.individualtrack.persistence.GameRepo;
import assignment.individualtrack.persistence.entity.GameEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetPlayerGameHistoryUseCaseImpl implements GetPlayerGameHistoryUseCase {
    private final GameRepo gameRepo;

    @Override
    public GetPlayerGameHistoryResponse getPlayerGameHistory(GetPlayerGameHistoryRequest request) {
        List<GameEntity> lastGames = gameRepo.findTop20ByPlayerIdOrderByIdDesc(request.getPlayerId());

        List<GetPlayerGameHistoryResponse.GameResponse> gameResponses = lastGames.stream()
                .map(game -> GetPlayerGameHistoryResponse.GameResponse.builder()
                        .gameId(game.getId())
                        .score(game.getScore())
                        .time(game.getTime())
                        .status(game.getStatus())
                        .correctGuesses(game.getCorrectGuesses())
                        .wrongGuesses(game.getWrongGuesses())
                        .build())
                .collect(Collectors.toList());

        return GetPlayerGameHistoryResponse.builder()
                .games(gameResponses)
                .build();
    }
}
