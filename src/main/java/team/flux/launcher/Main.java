package team.flux.launcher;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import team.flux.launcher.instance.Flux;
import team.flux.launcher.instance.Vanilla;
import team.flux.launcher.logging.MyLogger;
import team.flux.launcher.model.MojangSession;
import team.flux.launcher.utils.OSUtils;

public class Main {

    public static final String name = "Flux", version = "v0.1 Beta", build = "01_10_2026";
    private static final MyLogger log = new MyLogger(Main.class);

    private static Vanilla vanilla;
    private static Flux flux;
    private static MojangSession mojangSession;

    public static Vanilla getVanilla() { return vanilla; }
    public static Flux getFlux() { return flux; }
    public static MojangSession getMojangSession() { return mojangSession; }

    public static void main(String[] args) throws Exception {
        MyLogger.installGlobalExceptionHandler();
        log.info(String.format("%s Launcher (%s | %s) | jwwzr333", name, version, build));

        Option var2 = Option.builder("v").longOpt("version").argName("version").hasArg().desc("Minecraft version to be launched").build();
        Option var3 = Option.builder("n").longOpt("minecraftUsername").argName("username").hasArg().desc("Minecraft player username (required)").build();
        Option var4 = Option.builder("t").longOpt("minecraftToken").argName("token").hasArg().desc("Minecraft player token (required)").build();
        Option var5 = Option.builder("u").longOpt("minecraftUUID").argName("uuid").hasArg().desc("Minecraft player uuid (required)").build();
        Option var7 = Option.builder("c").longOpt("forceClassPath").desc("Forces the use of classpath instead of classloader").build();
        Option var8 = Option.builder("f").longOpt("gameFolder").argName("path").hasArg().desc("Uses the user given path instead of .minecraft").build();
        Option var9 = Option.builder("x").longOpt("startOnFirstThread").desc("Starts the game on first thread (macos)").build();

        Options options = new Options();
        options.addOption(var2).addOption(var3).addOption(var4).addOption(var5).addOption(var7).addOption(var8).addOption(var9);
        CommandLine cmd = (new DefaultParser()).parse(options, args);

        String gameFolder = cmd.hasOption(var8) ? cmd.getOptionValue(var8) : OSUtils.getWorkingDirectory("minecraft").getPath();

        if (cmd.getOptionValue(var3) != null && cmd.getOptionValue(var4) != null && cmd.getOptionValue(var5) != null) {

            mojangSession = new MojangSession(cmd.getOptionValue(var4), cmd.getOptionValue(var3), cmd.getOptionValue(var5));

            if (cmd.getOptionValue(var2) != null) {
                String version = cmd.getOptionValue(var2);

                if (version.startsWith("flux")) {

                    (flux = new Flux(version, cmd.hasOption(var9))).prepareLaunch(gameFolder);
                } else {

                    (vanilla = new Vanilla(version, cmd.hasOption(var7), cmd.hasOption(var9))).prepareLaunch(gameFolder);
                }
            }
        } else {

            printHelp(options);
        }
    }

    private static void printHelp(Options options) {
        System.out.println("Usage: java -Djava.library.path=<nativespath> -jar Launcher.jar [options]");
        System.out.println("\nAvailable options:");
        for (Option option : options.getOptions()) {
            System.out.println(String.format("-%s, -%-45s %s", option.getOpt(), (option.getLongOpt() + (option.hasArg() ? " <" + option.getArgName() + ">" : "")), option.getDescription()));
        }
        System.out.println("\nCheck website for more details: https://fluxlauncher.xyz\n");
    }

    public static String getVersionsURL() {
        return "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    }

    public static String getAssetsURL() {
        return "https://resources.download.minecraft.net";
    }

    public static String getLibrariesURL() {
        return "https://libraries.minecraft.net";
    }

    public static String getFabricVersionsURL() {
        return "https://meta.fabricmc.net/v2/versions";
    }

    public static String getForgeVersionsURL() {
        return "https://files.minecraftforge.net/net/minecraftforge/forge/maven-metadata.json";
    }

    public static String getForgeInstallerURL() {
        return "https://maven.minecraftforge.net/net/minecraftforge/forge/";
    }

    public static String getNeoForgeURL() {
        return "https://maven.neoforged.net";
    }

    public static String getNeoForgeInstallURL() {
        return String.format("%s/releases/net/neoforged/", getNeoForgeURL());
    }

    public static String getNeoForgeVersionsURL() {
        return String.format("%s/api/maven/versions/releases/net/neoforged/", getNeoForgeURL());
    }

    public static String getOptifineVersionsURL() {
        return String.format("%s/downloads/optifine.json", getFluxAPI());
    }

    public static String getFluxAPI() {
        return "https://fluxlauncher.xyz";
    }
}
