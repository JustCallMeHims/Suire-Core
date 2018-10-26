package me.suiremc.core.managers;

import com.google.common.collect.Sets;
import me.suiremc.core.objects.Task;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskManager {


    private JavaPlugin plugin;
    private Set<Task> tasks = Sets.newHashSet();

    public TaskManager(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public Task getTask(String name){
        return tasks.stream().filter(t -> t.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public Set<Task> get(Predicate<Task> predicate){
        return tasks.stream().filter(predicate).collect(Collectors.toSet());
    }

    public Task get(String name){
        return tasks.stream().filter(t-> t.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void addTaskAndRun(Task task, long bootTime, long circleTime){
        tasks.add(task);
        task.runTaskTimer(plugin, bootTime*20L, circleTime*20L);
        //Bukkit.getScheduler().runTaskTimer(plugin, task, bootTime*20L, circleTime*20L);
    }

    public void cancelTask(String name, boolean remove){
        cancelTask(getTask(name), remove);
    }

    public void cancelTask(Task task, boolean remove){
        if(remove)
            tasks.remove(task);

        task.cancel();
    }



}
