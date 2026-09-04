CREATE TABLE IF NOT EXISTS create_player_statistics (
                                                        player_uuid TEXT PRIMARY KEY,
                                                        total_actions INTEGER NOT NULL DEFAULT 0,
                                                        blocks_placed INTEGER NOT NULL DEFAULT 0,
                                                        blocks_broken INTEGER NOT NULL DEFAULT 0,
                                                        created_at TEXT NOT NULL,
                                                        updated_at TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS create_category_statistics (
                                                          player_uuid TEXT NOT NULL,
                                                          category TEXT NOT NULL,
                                                          blocks_placed INTEGER NOT NULL DEFAULT 0,
                                                          blocks_broken INTEGER NOT NULL DEFAULT 0,
                                                          updated_at TEXT NOT NULL,

                                                          PRIMARY KEY (
                                                          player_uuid,
                                                          category
)
    );