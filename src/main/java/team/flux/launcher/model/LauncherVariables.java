package team.flux.launcher.model;

public class LauncherVariables {

    private String mcVersion;
    private boolean modded;
    private boolean classPath;
    private String gamePath;
    private boolean startOnFirstThread;

    public LauncherVariables(String mcVersion, boolean modded, boolean useclasspath, String gamePath, boolean startonfirstthread) {
        this.mcVersion = mcVersion;
        this.modded = modded;
        this.classPath = useclasspath;
        this.gamePath = gamePath;
        this.startOnFirstThread = startonfirstthread;
    }

    public LauncherVariables(String gameversion, String gamePath, boolean startOnFirstThread) {
        this(gameversion, true, false, gamePath, startOnFirstThread);
    }

    public String getMcVersion() { return mcVersion; }
    public boolean isModded() { return modded; }
    public boolean isClassPath() { return classPath; }
    public String getGamePath() { return gamePath; }
    public boolean isStartOnFirstThread() { return startOnFirstThread; }
}
