package team.flux.launcher.utils.modutils;

import team.flux.launcher.Launcher;
import team.flux.launcher.Main;
import team.flux.launcher.model.modloaders.InstallerProfile;
import team.flux.launcher.model.modloaders.NeoForgeProduct;
import team.flux.launcher.utils.modutils.commons.InstallerFiles;
import team.flux.launcher.utils.modutils.commons.LoaderVersions;
import team.flux.launcher.utils.modutils.commons.ModLoaderInstaller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NeoForgeUtils {

    public static void doNeoForgeSetup(String requested, File jsonFile) throws IOException, InterruptedException {
        LoaderVersions.Request request = LoaderVersions.parse(requested, "neoforge");
        List<NeoForgeProduct.Release> candidates = new ArrayList<>();

        String[] artifactList = new String[]{"forge", "neoforge"};
        for (String artifact : artifactList) {
            NeoForgeProduct catalog = InstallerFiles.readJson(new URL(Main.getNeoForgeVersionsURL() + artifact), NeoForgeProduct.class);
            candidates.addAll(selectVersions(request, artifact, catalog));
        }
        File installer = resolveInstaller(request, candidates, Main.getNeoForgeInstallURL());
        new ModLoaderInstaller(installer, Launcher.env.getGameFolder()).install(jsonFile);
    }

    static File resolveInstaller(LoaderVersions.Request request, List<NeoForgeProduct.Release> candidates, String repository) throws IOException {
        if (request.minecraft == null && candidates.size() > 1)
            throw new IOException("Ambiguous NeoForge version " + request.version + "; specify Minecraft too");
        candidates.sort((left, right) -> {
            int order = LoaderVersions.compare(right.loaderVersion, left.loaderVersion);
            if (order == 0) order = left.artifact.compareTo(right.artifact);
            return order == 0 ? left.version.compareTo(right.version) : order;
        });
        for (NeoForgeProduct.Release release : candidates) {
            URL url = new URL(String.format("%s%s/%s/%s-%s-installer.jar", repository, release.artifact, release.version, release.artifact, release.version));
            File installer = InstallerFiles.downloadInstaller(url, release.coordinate(), null);
            InstallerProfile profile = ModLoaderInstaller.readProfile(installer);
            if (profile.minecraft == null) throw new IOException("Missing Minecraft version in NeoForge profile");
            if (request.minecraft != null && !request.minecraft.equals(profile.minecraft)) continue;
            return installer;
        }
        throw new IOException("No NeoForge installer matches Minecraft " + request.minecraft + " / loader " + request.version);
    }

    static List<NeoForgeProduct.Release> selectVersions(LoaderVersions.Request request, String artifact, NeoForgeProduct catalog) throws IOException {
        if (catalog.versions == null) throw new IOException("NeoForge catalog has no versions");
        List<NeoForgeProduct.Release> candidates = new ArrayList<>();
        for (String version : catalog.versions) {
            NeoForgeProduct.Release release = new NeoForgeProduct.Release(artifact, version);
            if (request.version != null && !request.version.equals(release.loaderVersion)) continue;
            if (request.version == null && request.minecraft != null && !release.mayTarget(request.minecraft)) continue;
            candidates.add(release);
        }
        return candidates;
    }
}
