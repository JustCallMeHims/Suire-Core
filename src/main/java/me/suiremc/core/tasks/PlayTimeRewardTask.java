package me.suiremc.core.tasks;

import com.google.common.collect.Maps;
import me.suiremc.core.Core;
import me.suiremc.core.objects.CPlayer;
import me.suiremc.core.managers.CPlayerCache;
import me.suiremc.core.objects.Task;
import me.suiremc.core.util.ChatUtil;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayTimeRewardTask extends Task {

    // K,V = Play time ~ Reward
    private Map<Integer, Double> rewards = Maps.newHashMap();
    // K,V = Player ~ Play Time of Reward
    private Map<UUID, Integer> rewarded = Maps.newHashMap();

    public PlayTimeRewardTask(Core plugin) {
        super("PLAY_TIME_REWARD_TASK", plugin);
        super.setRequiresLogout(true);
        //TODO: Set rewards by config and commands.
        addReward(30, 15);
        addReward(60, 50);
        addReward(120, 100);
    }

    @Override
    public void run() {
        if(rewards.size() == 0) {
            getPlugin().getLogger().warning("PLAY_TIME_REWARD_TASK has been cancelled and removed " +
                    "from the task manager, please add rewards and restart.");
            getPlugin().getTaskManager().cancelTask(this, true);
            return;
        }

        getPlugin().getServer().getOnlinePlayers().forEach(this::reward);
    }

    @Override
    public void logout(Player player) {
        if(rewarded.containsKey(player.getUniqueId()))
            rewarded.remove(player.getUniqueId());
    }

    private void reward(Player player){
        CPlayer cPlayer = CPlayerCache.getInstance().get(player);

        long playTimeDivided = (System.currentTimeMillis()-cPlayer.getJoinTime());
        int playTime = Math.round(TimeUnit.MILLISECONDS.toMinutes(playTimeDivided));

        Double reward = getReward(player, playTime);

        if(reward == null)
            return;

        cPlayer.setTokens(cPlayer.getTokens()+reward);
        //cPlayer.update();

        String output = Core.getDisplay("tasks.play-time-reward");

        ChatUtil.sendMsg(player,
                Core.getDisplay("prefix") +
                output.replace("{tokens}", ""+reward)
        .replace("{minutes}", ""+playTime) +
                        Core.getDisplay("suffix"));
    }

    public void addReward(int playTime, double reward){
        rewards.put(playTime, reward);
    }

    public Double getReward(Player player, int playTime){
        Double reward = null;
        int rewarded = this.rewarded.getOrDefault(player.getUniqueId(), 0);

        for(int time: rewards.keySet()){
            if(playTime<time || rewarded>=time)
                continue;

            reward = rewards.get(time);
            this.rewarded.put(player.getUniqueId(), time);
        }

        return reward;
    }

}
