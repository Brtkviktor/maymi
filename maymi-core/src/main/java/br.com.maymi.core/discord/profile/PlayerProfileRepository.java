package br.com.maymi.core.discord.profile;

import br.com.maymi.core.persistence.database.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class PlayerProfileRepository {

    public Optional<PlayerProfileDto> findByNickname(
            String nickname
    ) {

        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException(
                    "Nickname não pode ser vazio."
            );
        }

        String sql = """
                WITH ranked_players AS (
                    SELECT
                        p.uuid,
                        p.nickname,
                        p.first_join_at,
                        p.last_seen_at,
                        p.login_count,

                        ps.maymi_xp,
                        ps.level,
                        ps.deaths,
                        ps.mob_kills,
                        ps.blocks_placed,
                        ps.blocks_broken,
                        ps.play_time_seconds,

                        RANK() OVER (
                            ORDER BY
                                ps.maymi_xp DESC,
                                ps.level DESC,
                                p.nickname COLLATE NOCASE ASC
                        ) AS ranking_position

                    FROM players p

                    INNER JOIN player_statistics ps
                        ON ps.player_uuid = p.uuid
                )

                SELECT
                    uuid,
                    nickname,
                    first_join_at,
                    last_seen_at,
                    login_count,
                    maymi_xp,
                    level,
                    ranking_position,
                    deaths,
                    mob_kills,
                    blocks_placed,
                    blocks_broken,
                    play_time_seconds

                FROM ranked_players

                WHERE nickname = ? COLLATE NOCASE

                LIMIT 1
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    nickname.trim()
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(
                        mapProfile(resultSet)
                );
            }

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível consultar o perfil de "
                            + nickname,
                    exception
            );
        }
    }

    // =====================================================
    // CONSULTA POR UUID
    // =====================================================

    public Optional<PlayerProfileDto> findByUuid(
            UUID playerUuid
    ) {

        Objects.requireNonNull(
                playerUuid,
                "UUID do jogador não pode ser nulo."
        );

        String sql = """
                WITH ranked_players AS (
                    SELECT
                        p.uuid,
                        p.nickname,
                        p.first_join_at,
                        p.last_seen_at,
                        p.login_count,

                        ps.maymi_xp,
                        ps.level,
                        ps.deaths,
                        ps.mob_kills,
                        ps.blocks_placed,
                        ps.blocks_broken,
                        ps.play_time_seconds,

                        RANK() OVER (
                            ORDER BY
                                ps.maymi_xp DESC,
                                ps.level DESC,
                                p.nickname COLLATE NOCASE ASC
                        ) AS ranking_position

                    FROM players p

                    INNER JOIN player_statistics ps
                        ON ps.player_uuid = p.uuid
                )

                SELECT
                    uuid,
                    nickname,
                    first_join_at,
                    last_seen_at,
                    login_count,
                    maymi_xp,
                    level,
                    ranking_position,
                    deaths,
                    mob_kills,
                    blocks_placed,
                    blocks_broken,
                    play_time_seconds

                FROM ranked_players

                WHERE uuid = ?

                LIMIT 1
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    playerUuid.toString()
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(
                        mapProfile(resultSet)
                );
            }

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível consultar o perfil do jogador "
                            + playerUuid,
                    exception
            );
        }
    }

    public List<PlayerRankingDto> findTopRanking(
            int limit
    ) {

        if (limit <= 0) {
            throw new IllegalArgumentException(
                    "O limite do ranking deve ser maior que zero."
            );
        }

        String sql = """
                SELECT
                    p.uuid,
                    p.nickname,
                    ps.maymi_xp,
                    ps.level

                FROM players p

                INNER JOIN player_statistics ps
                    ON ps.player_uuid = p.uuid

                ORDER BY
                    ps.maymi_xp DESC,
                    ps.level DESC,
                    p.nickname COLLATE NOCASE ASC

                LIMIT ?
                """;

        List<PlayerRankingDto> ranking =
                new ArrayList<>();

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    limit
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                int position = 1;

                while (resultSet.next()) {

                    ranking.add(
                            new PlayerRankingDto(
                                    position,
                                    UUID.fromString(
                                            resultSet.getString(
                                                    "uuid"
                                            )
                                    ),
                                    resultSet.getString(
                                            "nickname"
                                    ),
                                    resultSet.getLong(
                                            "maymi_xp"
                                    ),
                                    resultSet.getInt(
                                            "level"
                                    )
                            )
                    );

                    position++;
                }
            }

            return List.copyOf(
                    ranking
            );

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível consultar o ranking.",
                    exception
            );
        }
    }

    public GlobalStatisticsDto findGlobalStatistics() {

        String sql = """
                SELECT
                    COUNT(*) AS total_players,

                    COALESCE(
                        SUM(ps.maymi_xp),
                        0
                    ) AS total_xp,

                    COALESCE(
                        SUM(ps.deaths),
                        0
                    ) AS total_deaths,

                    COALESCE(
                        SUM(ps.mob_kills),
                        0
                    ) AS total_mob_kills,

                    COALESCE(
                        SUM(ps.blocks_placed),
                        0
                    ) AS total_blocks_placed,

                    COALESCE(
                        SUM(ps.blocks_broken),
                        0
                    ) AS total_blocks_broken,

                    COALESCE(
                        SUM(ps.play_time_seconds),
                        0
                    ) AS total_play_time_seconds

                FROM players p

                INNER JOIN player_statistics ps
                    ON ps.player_uuid = p.uuid
                """;

        try (
                var connection =
                        DatabaseManager.getConnection();

                var statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (!resultSet.next()) {

                return new GlobalStatisticsDto(
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                );
            }

            return new GlobalStatisticsDto(
                    resultSet.getLong(
                            "total_players"
                    ),
                    resultSet.getLong(
                            "total_xp"
                    ),
                    resultSet.getLong(
                            "total_deaths"
                    ),
                    resultSet.getLong(
                            "total_mob_kills"
                    ),
                    resultSet.getLong(
                            "total_blocks_placed"
                    ),
                    resultSet.getLong(
                            "total_blocks_broken"
                    ),
                    resultSet.getLong(
                            "total_play_time_seconds"
                    )
            );

        } catch (SQLException exception) {

            throw new IllegalStateException(
                    "Não foi possível consultar as estatísticas globais.",
                    exception
            );
        }
    }

    private PlayerProfileDto mapProfile(
            ResultSet resultSet
    ) throws SQLException {

        return new PlayerProfileDto(
                UUID.fromString(
                        resultSet.getString(
                                "uuid"
                        )
                ),
                resultSet.getString(
                        "nickname"
                ),
                Instant.parse(
                        resultSet.getString(
                                "first_join_at"
                        )
                ),
                Instant.parse(
                        resultSet.getString(
                                "last_seen_at"
                        )
                ),
                resultSet.getInt(
                        "login_count"
                ),
                resultSet.getLong(
                        "maymi_xp"
                ),
                resultSet.getInt(
                        "level"
                ),
                resultSet.getInt(
                        "ranking_position"
                ),
                resultSet.getLong(
                        "deaths"
                ),
                resultSet.getLong(
                        "mob_kills"
                ),
                resultSet.getLong(
                        "blocks_placed"
                ),
                resultSet.getLong(
                        "blocks_broken"
                ),
                resultSet.getLong(
                        "play_time_seconds"
                )
        );
    }
}