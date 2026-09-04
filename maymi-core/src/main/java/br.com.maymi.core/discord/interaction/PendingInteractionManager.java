package br.com.maymi.core.discord.interaction;

import net.dv8tion.jda.api.interactions.InteractionHook;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PendingInteractionManager {

    private static final Map<String, InteractionHook> pendingInteractions =
            new ConcurrentHashMap<>();

    private PendingInteractionManager() {
    }

    public static void register(
            String requestId,
            InteractionHook hook
    ) {

        if (requestId == null || hook == null) {
            return;
        }

        pendingInteractions.put(
                requestId,
                hook
        );

    }

    public static InteractionHook remove(
            String requestId
    ) {

        if (requestId == null) {
            return null;
        }

        return pendingInteractions.remove(
                requestId
        );

    }

    public static boolean contains(
            String requestId
    ) {

        return requestId != null
                && pendingInteractions.containsKey(requestId);

    }

    public static int size() {
        return pendingInteractions.size();
    }

}